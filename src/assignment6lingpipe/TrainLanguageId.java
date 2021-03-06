package assignment6lingpipe;

import com.aliasi.classify.Classification;
import com.aliasi.classify.Classified;
import com.aliasi.classify.DynamicLMClassifier;

import com.aliasi.lm.NGramProcessLM;

import com.aliasi.util.AbstractExternalizable;
import com.aliasi.util.Strings;

import common.ReadConfigurationFile;

import java.io.*;

public class TrainLanguageId {
	
	static final String PACKAGENAME = "assignment6lingpipe";
	static int nGram = Integer.parseInt(ReadConfigurationFile.getProperty(PACKAGENAME, "nGram"));
	static int numChars = 10000000;
	static int minCount = 10;

    // java TrainLanguageId <dataDir>:dir 
    //                      <modelFile>:file 
    //                      <ngram>:int 
    //                      <numChars>:int
    public static void main(String[] args) throws Exception {
        File dataDir = new File(ReadConfigurationFile.getProperty(PACKAGENAME, "directoryName"));
        if (!dataDir.isDirectory()) {
            String msg = "Set first argument to the data directory."
                + " Found dataDir=" + dataDir;
            throw new IllegalArgumentException(msg);
        }
        File modelFile = new File(ReadConfigurationFile.getProperty(PACKAGENAME, "fileName"));
        System.out.println("nGram=" + nGram + " numChars=" + numChars);

        String[] categories = dataDir.list();
        // //
        DynamicLMClassifier<NGramProcessLM> classifier = DynamicLMClassifier.createNGramProcess(categories,nGram);

        char[] csBuf = new char[numChars]; 
        for (int i = 0; i < categories.length; ++i) {
            String category = categories[i];
            System.out.println("Training category=" + category);
            File trainingFile = new File(dataDir,category);
            FileInputStream fileIn = new FileInputStream(trainingFile);
            InputStreamReader reader = new InputStreamReader(fileIn,Strings.UTF8);
            reader.read(csBuf);
            String text = new String(csBuf,0,numChars);
            
            // //
            Classification c = new Classification(category);
            Classified<CharSequence> classified = new Classified<CharSequence>(text,c);
            classifier.handle(classified);
            reader.close();
        }

        System.out.println("\nCompiling model to file=" + modelFile);
        AbstractExternalizable.compileTo(classifier,modelFile);
    }

}

