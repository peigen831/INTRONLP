package assignment6lingpipe;

import java.io.File;
import java.util.Scanner;

import com.aliasi.classify.BaseClassifier;
import com.aliasi.classify.Classification;
import com.aliasi.util.AbstractExternalizable;

import common.ReadConfigurationFile;

// From Alron & Jules

public class ClassifyLanguageId {
	static final String PACKAGENAME = "assignment6lingpipe";

	public static void main(String[] args) throws Exception {
        File dataDir = new File(ReadConfigurationFile.getProperty(PACKAGENAME, "directoryName"));
        File modelFile = new File(ReadConfigurationFile.getProperty(PACKAGENAME, "fileName"));

		String[] categories = dataDir.list();

		BaseClassifier<CharSequence> classifier = (BaseClassifier<CharSequence>) AbstractExternalizable
				.readObject(modelFile);

		Scanner scanner = new Scanner(System.in);
		while (scanner.hasNext()) {

			String input = scanner.nextLine();
			Classification classification = classifier.classify(input);
			System.out.println("Language is: " + classification.bestCategory().replace("_Text.txt", ""));
		}
	}
}
