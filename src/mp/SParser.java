package mp;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.ling.Label;
import edu.stanford.nlp.parser.lexparser.LexicalizedParser;
import edu.stanford.nlp.process.DocumentPreprocessor;
import edu.stanford.nlp.process.Tokenizer;
import edu.stanford.nlp.trees.GrammaticalStructure;
import edu.stanford.nlp.trees.GrammaticalStructureFactory;
import edu.stanford.nlp.trees.LabeledScoredTreeFactory;
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
      String sent2 = ("Management must set direction and provide support for information security.");
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
      System.out.println(">   " + parse.getChild(0).getChild(1).value().equals("NP"));
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
      
      
      Tree result = find(parse, "VP");
      Tree remove = find(result, "PP");
      System.out.println("finding::: " + remove(result, remove));
      
    }
  }
  
    public static Tree find(Tree tree, String value) {
	  if (tree.value().equals(value)) {
		  return tree;
	  }
	  for (Tree child : tree.getChildrenAsList()) {
		  Tree result = find(child, value);
		  if (result != null) {
			  return result;
		  }
	  }
	  
	  return null;
  }
  
  public static Tree remove(Tree parent, Tree subtree) {
	  LabeledScoredTreeFactory factory = new LabeledScoredTreeFactory();
	  
	  if (parent == subtree) {
		  return null;
	  }
	  
	  for (parent.getChildrenAsList()) {
		  
	  }
	  
	  return newParent;
  }

  private SParser() {} // static methods only

}
