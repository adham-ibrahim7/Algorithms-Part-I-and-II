import java.util.LinkedList;
import java.util.StringTokenizer;
import java.util.TreeMap;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Topological;

/**
 * "<em>WordNet is a semantic lexicon for the English language 
 * that computational linguists and cognitive scientists use extensively.</em>"
 * <p>
 * This class aims to implement useful methods for traversing a subset of the WordNet.
 * The main functionality is determining the <em>distance</em> between two nouns,
 * which is a rough way to determine how close they are in meaning, as well as 
 * finding the <em>lowest common ancestor of two words</em>, which can be viewed as the 
 * most specific classification that the two words fit into.
 * 
 * @author Adham Ibrahim
 * @version 9/19/2020
 * @see SAP
 * @see edu.princeton.cs.algs4.Digraph
 * 
 */

public class WordNet {
	
	//for every noun store a list of all synsets it belongs to
	private TreeMap<String, LinkedList<Integer>> nouns;
	
	private TreeMap<Integer, String> synsets;
	
	//the SAP that will compute distance between nouns
	private final SAP sap;
	
	/**
	 * Construct a word net given the synsets and the hypernym relations.
	 * 
	 * @param synsets the synsets text file path
	 * @param hypernyms the hypernyms text file path
	 */
	public WordNet(String synsetsFilePath, String hypernymsFilePath) {
		if (synsetsFilePath == null || hypernymsFilePath == null)
			throw new IllegalArgumentException();
		
		//begin with empty maps
		nouns = new TreeMap<>();
		synsets = new TreeMap<>();
		
		//instream for synsets
		In synsetIn = new In(synsetsFilePath);
		
		//keep vertex counter, to construct the digraph later
		int V = 0;
		while (synsetIn.hasNextLine()) {
			StringTokenizer line = new StringTokenizer(synsetIn.readLine(), ",");
			
			//the id of the current synset
			int synsetId = Integer.parseInt(line.nextToken());
			
			//place in synset map, for use by the sap method
			String synset = line.nextToken();
			synsets.put(synsetId, synset);
			
			//read in all words and place in map
			StringTokenizer synsetWords = new StringTokenizer(synset, " ");
			while (synsetWords.hasMoreTokens()) {
				String noun = synsetWords.nextToken();
				nouns.putIfAbsent(noun, new LinkedList<>());
				nouns.get(noun).add(synsetId);
			}
			
			V++;
		}
		
		//instream for hypernym relations
		In hypernymIn = new In(hypernymsFilePath);
		
		//digraph of necessary size
		Digraph G = new Digraph(V);
		
		//read all hypernym relations
		while (hypernymIn.hasNextLine()) {
			StringTokenizer line = new StringTokenizer(hypernymIn.readLine(), ",");
			
			int synsetId = Integer.parseInt(line.nextToken());
			
			while (line.hasMoreTokens()) {
				int ancestor = Integer.parseInt(line.nextToken());
				G.addEdge(synsetId, ancestor);
			}
		}
		
		//check for single root
		int roots = 0;
		for (int u = 0; u < G.V(); u++) {
			if (G.outdegree(u) == 0) 
				roots++;
		}
		
		if (roots != 1)
			throw new IllegalArgumentException("multiple roots");
		
		//check for acyclic
		if (! new Topological(G).hasOrder())
			throw new IllegalArgumentException("cyclic");
		
		//construct SAP for use by sap and distance methods
		sap = new SAP(G);
	}
	
	/**
	 * Getter method for the noun set.
	 * 
	 * @return the set of all nouns as an iterable
	 */
	public Iterable<String> nouns() {
		return nouns.keySet();
	}
	
	/**
	 * Is word among the noun set?
	 * 
	 * @param word
	 * @return if word is in the noun set
	 */
	public boolean isNoun(String word) {
		if (word == null)
			throw new IllegalArgumentException();
		return nouns.containsKey(word);
	}
	
	/**
	 * The <em>distance</em> between two nouns is defined as the 
	 * shortest path between any pair of synsets to which they belong to.
	 * The SAP class handles this computation.
	 * 
	 * @param nounA
	 * @param nounB
	 * @return
	 */
	public int distance(String nounA, String nounB) {
		if (!isNoun(nounA) || !isNoun(nounB))
			throw new IllegalArgumentException();
		return sap.length(nouns.get(nounA), nouns.get(nounB));
	}
	
	/**
	 * The <em>shortest ancestor</em> is defined as the ancestor that
	 * gives the shortest distance as defined by {@link #distance(String, String)}
	 * 
	 * @param nounA
	 * @param nounB
	 * @return the ancestor
	 */
	public String sap(String nounA, String nounB) {
		if (!isNoun(nounA) || !isNoun(nounB))
			throw new IllegalArgumentException();
		int synsetId = sap.ancestor(nouns.get(nounA), nouns.get(nounB));
		return synsets.get(synsetId);
	}
	
	/**
	 * Unit testing
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		WordNet wordNet = new WordNet("assets/synsets5000-subgraph.txt", 
				"assets/hypernyms5000-subgraph.txt");
		
		String nounA = "covalent_bond";
		String nounB = "nuclear_fusion_reaction";
		
		System.out.println(wordNet.distance(nounA, nounB));
		System.out.println(wordNet.sap(nounA, nounB));
		
		//WordNet wordNet = new WordNet("assets/synsets100-subgraph.txt", "assets/hypernyms6InvalidCycle+Path.txt");
	}
	
}
