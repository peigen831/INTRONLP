package mp;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

import common.FileWriter;
import edu.stanford.nlp.ling.CoreAnnotations;
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
			System.out.println(Arrays.toString(getSubjects(tdl)));
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
			System.out.println("finding::: " + remove(parse, remove));
		}
	}
	
	public String[] getGoals(List<TypedDependency> tdl) {
		Set<String> results = new HashSet<>();
		String root = null;
		for (TypedDependency dependency : tdl) {
			if (dependency.gov().get(CoreAnnotations.ValueAnnotation.class).equals("ROOT")) {
				root = dependency.dep().getString(CoreAnnotations.ValueAnnotation.class);
				results.add(root);
			}
			else if (dependency.gov().get(CoreAnnotations.ValueAnnotation.class).equals(root) &&
					 dependency.reln().getShortName().equals("conj") &&
					 (dependency.reln().getSpecific().equals("or") ||
					  dependency.reln().getSpecific().equals("and"))) {
				results.add(dependency.dep().getString(CoreAnnotations.ValueAnnotation.class));
			}
		}
		
		String[] arrResults = Arrays.copyOf(results.toArray(), results.size(), String[].class);
		
		return (arrResults.length > 0) ? arrResults : null;
	}
	
	public String[] getSubjects(List<TypedDependency> tdl) {
		Set<String> results = new HashSet<>();
		for (TypedDependency dependency : tdl) {
			if (dependency.reln().getShortName().equals("nsubj") ||
				(dependency.reln().getShortName().equals("nmod") &&
				 dependency.reln().getSpecific().equals("agent"))) {
				results.add(dependency.dep().getString(CoreAnnotations.ValueAnnotation.class));
			}
			else if (dependency.gov().get(CoreAnnotations.ValueAnnotation.class).equals("nsubjpass")) {
				results.remove(dependency.dep().getString(CoreAnnotations.ValueAnnotation.class));
			}
		}
		
		String[] arrResults = Arrays.copyOf(results.toArray(), results.size(), String[].class);
		
		return (arrResults.length > 0) ? arrResults : null;
	}
	
	private String getSubjectDependencies(List<TypedDependency> tdl, String subject) {
		String results = "";
		boolean hasDependency = true;
		TreeMap<Integer, String> ordering = new TreeMap<>();
		ArrayList<String> dependencies = new ArrayList<>();
		
		dependencies.add(subject);
		
		while (hasDependency) {
			hasDependency = false;
			for (TypedDependency dependency : tdl) {
				if (dependencies.contains(dependency.gov().get(CoreAnnotations.ValueAnnotation.class))) {
					if (dependency.gov().get(CoreAnnotations.ValueAnnotation.class).equals(subject)) {
						ordering.put(dependency.gov().get(CoreAnnotations.IndexAnnotation.class), subject);
					}
					if (dependency.reln().getShortName().equals("compound") ||
						dependency.reln().getShortName().equals("case") ||
						dependency.reln().getShortName().equals("det") ||
						(dependency.reln().getShortName().equals("nmod") &&
						 dependency.reln().getSpecific().equals("of"))) {
						hasDependency = true;
						dependencies.add(dependency.dep().get(CoreAnnotations.ValueAnnotation.class));
						ordering.put(dependency.dep().get(CoreAnnotations.IndexAnnotation.class),
								     dependency.dep().get(CoreAnnotations.ValueAnnotation.class));
					}
				}
			}
		}
		
		for (String strOrdering : ordering.values()) {
			results += strOrdering + " ";
		}
		
		return (results.length() > 0) ? results.replace("by ", "").trim() : null;
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
		
		Tree newParent = factory.newTreeNode(parent.label(), null);
		
		for (Tree child : parent.getChildrenAsList()) {
			Tree result = remove(child, subtree);
			if (result != null) {
				newParent.addChild(result);
			}
		}
		
		return newParent;
	}
	
	public static void main(String[] args) throws IOException {
		try {
			FileWriter.getNewInstance("mp", "Results.txt").createNewFile();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		SParser s = new SParser();
		s.initialize();
    	//2.1.1 Management must set direction and provide support for information security.
    	//2.1.2 Implementation of information security activities across government must be coordinated by the Office of the Government Chief Information Officer.
    	//2.1.6 Appropriate contacts shall be maintained with local law enforcement authorities, emergency support staff and service providers.
    	//4.1.1 Security roles and responsibilities for personnel must be documented.
    	//7.1.1 Access to information systems and services must be consistent with business needs and be based on security requirements.
    	//7.3.3 Users must ensure the safety of sensitive information from unauthorized access, loss or damage.
		
		String[] input = {"Management must set direction and provide support for information security.",
		"Implementation of information security activities across government must be coordinated by the Office of the Government Chief Information Officer",
		"Appropriate contacts shall be maintained with local law enforcement authorities, emergency support staff and service providers.",
		"Security roles and responsibilities for personnel must be documented.",
		"Access to information systems and services must be consistent with business needs and be based on security requirements.",
		"Users must ensure the safety of sensitive information from unauthorized access, loss or damage."};
		s.parseSentences(input);
	}
}