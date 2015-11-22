package assignment6lingpipe;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import com.aliasi.classify.BaseClassifier;
import com.aliasi.classify.BaseClassifierEvaluator;
import com.aliasi.classify.Classification;
import com.aliasi.classify.Classified;
import com.aliasi.util.AbstractExternalizable;
import com.aliasi.util.Strings;

import common.ReadConfigurationFile;

public class EvalLanguageId {
	
	static final String PACKAGENAME = "assignment6lingpipe";
	static int nGram = Integer.parseInt(ReadConfigurationFile.getProperty(PACKAGENAME, "nGram"));
	static int numChars = 10000;
	static int minCount = 10;
    static int testSize = 10;
    static int numTests = 10;


    // java EvalLanguageId <corpusDir>: dir
    //                     <modelFile>:file 
    //                     <trainSize>:int
    //                     <testSize>:int
    //                     <numTestsPerCat>:int
    public static void main(String[] args) throws Exception {
        File dataDir = new File(ReadConfigurationFile.getProperty(PACKAGENAME, "directoryName"));
        File modelFile = new File(ReadConfigurationFile.getProperty(PACKAGENAME, "fileName"));

        char[] csBuf = new char[testSize];
        
        String[] categories = dataDir.list();

        boolean boundSequences = false;

        System.out.println("Reading classifier from file=" + modelFile);
        @SuppressWarnings("unchecked") // required for deserialization
        BaseClassifier<CharSequence> classifier 
            = (BaseClassifier<CharSequence>) 
            AbstractExternalizable.readObject(modelFile);

        boolean storeInputs = false;
        BaseClassifierEvaluator<CharSequence> evaluator
            = new BaseClassifierEvaluator<CharSequence>(classifier,categories,storeInputs);

        for (int i = 0; i < categories.length; ++i) {
            String category = categories[i];
            System.out.println("Evaluating category=" + category);
	    File trainingFile = new File(dataDir,category);
	    FileInputStream fileIn 
		= new FileInputStream(trainingFile);
	    InputStreamReader reader
		= new InputStreamReader(fileIn,Strings.UTF8);

	    reader.skip(numChars); // skip training data

	    for (int k = 0; k < numTests; ++k) {
		reader.read(csBuf);
                Classification c = new Classification(category);
                Classified<CharSequence> cl
                    = new Classified<CharSequence>(new String(csBuf),c);
		evaluator.handle(cl);
	    }
	    reader.close();
	}

        System.out.println("TEST RESULTS");
        System.out.println(evaluator.toString());
    }

}