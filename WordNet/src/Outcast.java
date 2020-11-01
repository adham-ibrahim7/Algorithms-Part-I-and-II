import edu.princeton.cs.algs4.In;

/**
 * Define the <em>outcast</em> of a group of words to be the word
 * least similar in meaning to the rest. We can (roughly) determine 
 * this using a WordNet, with the similarity of two words being the 
 * WordNet distance between the two. Clearly then we can compute the
 * sum of distances of each word to all others to determine for which
 * word this total dis tance is largest. 
 * 
 * @author Adham Ibrahim
 * @version 9/19/2020
 * @see WordNet
 */

public class Outcast {
	
	private WordNet wordNet;
	
	/**
	 * Construct Outcast with a wordNet
	 * @param wordNet
	 */
	public Outcast(WordNet wordNet) {
		if (wordNet == null) 
			throw new IllegalArgumentException();
		this.wordNet = wordNet;
	}
	
	/**
	 * Compute the outcast of an array of nouns
	 * 
	 * @param nouns array of nouns
	 * @return the outcast
	 */
	public String outcast(String[] nouns) {
		if (nouns == null || nouns.length == 0)
			throw new IllegalArgumentException();
		
		//precompute all distances to avoid computing the same distance twice
		int[][] distances = new int[nouns.length][nouns.length];
		for (int i = 0; i < nouns.length; i++) {
			for (int j = i; j < nouns.length; j++) {
				distances[i][j] = wordNet.distance(nouns[i], nouns[j]);
				distances[j][i] = distances[i][j];
			}
		}
		
		//detemine outcase using simple max search in linear time
		
		String outcast = null;
		int largestTotalDistance = 0;
		
		for (int i = 0; i < nouns.length; i++) {
			int totalDistance = 0;
			for (int j = 0; j < nouns.length; j++) 
				totalDistance += distances[i][j];
			
			if (totalDistance > largestTotalDistance) {
				largestTotalDistance = totalDistance;
				outcast = nouns[i];
			}
		}
		
		return outcast;
	}
	
	/**
	 * Unit testing
	 * @param args
	 */
	public static void main(String[] args) {
		WordNet wordNet = new WordNet("assets/synsets.txt", "assets/hypernyms.txt");
		Outcast o = new Outcast(wordNet);
		
		In in = new In("assets/outcast29.txt");
		String[] nouns = in.readAllStrings();
		
		System.out.println(o.outcast(nouns));
	}
	
}
