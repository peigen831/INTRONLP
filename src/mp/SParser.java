package mp;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.ling.Label;
import edu.stanford.nlp.parser.lexparser.LexicalizedParser;
import edu.stanford.nlp.process.DocumentPreprocessor;
import edu.stanford.nlp.process.Tokenizer;
import edu.stanford.nlp.trees.GrammaticalStructure;
import edu.stanford.nlp.trees.GrammaticalStructureFactory;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreebankLanguagePack;
import edu.stanford.nlp.trees.TypedDependency;

class SParser {

  /** This example shows a few more ways of providing input to a parser.
   *
   *  Usage: ParserDemo2 [grammar [textFile]]
   */
  public static void main(String[] args) throws IOException {
    
	// Initialize Models
	String grammar = args.length > 0 ? args[0] : "edu/stanford/nlp/models/lexparser/englishPCFG.ser.gz";
    String[] options = { "-maxLength", "80", "-retainTmpSubcategories" };
    LexicalizedParser lp = LexicalizedParser.loadModel(grammar, options);
    TreebankLanguagePack tlp = lp.getOp().langpack();
    GrammaticalStructureFactory gsf = tlp.grammaticalStructureFactory();

    Iterable<List<? extends HasWord>> sentences;
    if (args.length > 1) {
      DocumentPreprocessor dp = new DocumentPreprocessor(args[1]);
      List<List<? extends HasWord>> tmp =
        new ArrayList<List<? extends HasWord>>();
      for (List<HasWord> sentence : dp) {
        tmp.add(sentence);
      }
      sentences = tmp;
    } else {
      String sent2 = ("Ministries may develop and implement additional information security policies, standards and guidelines for use within their organization or for a specific information system or program.");
      // Use the default tokenizer for this TreebankLanguagePack
      Tokenizer<? extends HasWord> toke =
        tlp.getTokenizerFactory().getTokenizer(new StringReader(sent2));
      List<? extends HasWord> sentence2 = toke.tokenize();
      
      List<List<? extends HasWord>> tmp =
        new ArrayList<List<? extends HasWord>>();
      tmp.add(sentence2);
      sentences = tmp;
    }

    for (List<? extends HasWord> sentence : sentences) {
    	// Parse for parts of speech
      Tree parse = lp.parse(sentence);
      parse.pennPrint();
      System.out.println();
      
      // Find grammatical structure
      GrammaticalStructure gs = gsf.newGrammaticalStructure(parse);
      List<TypedDependency> tdl = gs.typedDependenciesCCprocessed();
      System.out.println(tdl);
      System.out.println();

      System.out.println("The words of the sentence:");
      for (Label lab : parse.labeledYield()) {
        if (lab instanceof CoreLabel) {
          System.out.println(((CoreLabel) lab).toString(CoreLabel.OutputFormat.VALUE_MAP));
        } else {
          System.out.println(lab);
        }
      }
      System.out.println();
      System.out.println(parse.taggedYield());
      System.out.println();

    }
  }

  private SParser() {} // static methods only

}
