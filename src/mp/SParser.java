package mp;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.ling.Label;
import edu.stanford.nlp.parser.lexparser.LexicalizedParser;
import edu.stanford.nlp.process.Tokenizer;
import edu.stanford.nlp.trees.GrammaticalStructure;
import edu.stanford.nlp.trees.GrammaticalStructureFactory;
import edu.stanford.nlp.trees.LabeledScoredTreeFactory;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreebankLanguagePack;
import edu.stanford.nlp.trees.TypedDependency;

class SParser {
	
	public String grammar;
	public String[] options;
	public LexicalizedParser lp;
	public TreebankLanguagePack tlp;
	public GrammaticalStructureFactory gsf;
	
	public void initialize(){
		grammar = "edu/stanford/nlp/models/lexparser/englishPCFG.ser.gz";
		options = new String[]{ "-maxLength", "80", "-retainTmpSubcategories" };
		lp = LexicalizedParser.loadModel(grammar, options);
		tlp = lp.getOp().langpack();
		gsf = tlp.grammaticalStructureFactory();
	}
	
	public Iterable<List<? extends HasWord>> tokenizeSentence(String[] sentences){
		Iterable<List<? extends HasWord>> result;
		List<List<? extends HasWord>> tmp = new ArrayList<List<? extends HasWord>>();
		
		for(String s: sentences){
			Tokenizer<? extends HasWord> toke = tlp.getTokenizerFactory().getTokenizer(new StringReader(s));
			List<? extends HasWord> sTokenized = toke.tokenize();
			tmp.add(sTokenized);
		}
		
		result = tmp;
		return result;
	}
	
	public void parseSentences(String[] input){
		Iterable<List<? extends HasWord>> sTokenized = tokenizeSentence(input);

		for (List<? extends HasWord> sentence : sTokenized) {
        	// Parse for parts of speech
			Tree parse = lp.parse(sentence);
			parse.pennPrint();
			
          // Find grammatical structure
			GrammaticalStructure gs = gsf.newGrammaticalStructure(parse);
			List<TypedDependency> tdl = gs.typedDependenciesCCprocessed();
			System.out.println(tdl);
			System.out.println();

			System.out.println("The words of the sentence:");
			for (Label lab : parse.yield()) {
				if (lab instanceof CoreLabel) {
					System.out.println("Word: " + ((CoreLabel)lab).word() + " Tag:" +((CoreLabel)lab).tag());
              //System.out.println(((CoreLabel) lab).toString(CoreLabel.OutputFormat.VALUE_MAP));
				} else {
					System.out.println("lab "+lab);
				}
			}
			System.out.println(parse.taggedYield());
			
			Tree result = find(parse, "VP");
			Tree remove = find(result, "PP");
			System.out.println("finding::: " + remove(result, remove));
		}
	}
	
	public Tree find(Tree tree, String value) {
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
	
	public Tree remove(Tree parent, Tree subtree) {
		LabeledScoredTreeFactory factory = new LabeledScoredTreeFactory();
		
		if (parent == subtree) {
			return null;
		}
		
		for (Tree child : parent.getChildrenAsList()) {
			// TODO
		}
		
		return parent;
	}
	
	public static void main(String[] args) throws IOException {
		SParser s = new SParser();
		s.initialize();
    	//2.1.1 Management must set direction and provide support for information security.
    	//2.1.2 Implementation of information security activities across government must be coordinated by the Office of the Government Chief Information Officer.
    	//2.1.6 Appropriate contacts shall be maintained with local law enforcement authorities, emergency support staff and service providers.
    	//4.1.1 Security roles and responsibilities for personnel must be documented.
    	//7.1.1 Access to information systems and services must be consistent with business needs and be based on security requirements.
    	//7.3.3 Users must ensure the safety of sensitive information from unauthorized access, loss or damage.
		
		String[] input = {"Implementation of information security activities across government must be coordinated by the Office of the Government Chief Information Officer",
		"Appropriate contacts shall be maintained with local law enforcement authorities, emergency support staff and service providers.",
		"Security roles and responsibilities for personnel must be documented.",
		"Access to information systems and services must be consistent with business needs and be based on security requirements.",
		"Users must ensure the safety of sensitive information from unauthorized access, loss or damage."};
		s.parseSentences(input);
	}
}