import java.util.*;
import java.io.*;

public class EulerianCycle {
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws IOException {
		setIO();
		
		st = nl();
		int N = ni(st), M = ni(st);
		
		adj = new TreeSet[N+1];
		for (int i = 1; i <= N; i++) adj[i] = new TreeSet<>();
		
		for (int i = 0; i < M; i++) {
			st = nl();
			int u = ni(st), v = ni(st);
			adj[u].add(v);
			adj[v].add(u);
		}
		
		boolean good = true;
		for (int u = 1; u <= N; u++) {
			if (adj[u].size() % 2 != 0) {
				good = false;
				break;
			}
		}
		
		if (good) {
			List<Integer> ans = new ArrayList<>();
			Stack<Integer> s = new Stack<>();
			s.add(1);
			
			while (!s.isEmpty()) {
				int u = s.peek();
				
				if (adj[u].size() == 0) {
					ans.add(u);
					s.pop();
				} else {
					int v = adj[u].pollFirst();
					adj[v].remove(u);
					s.add(v);
				}
			}
			
			if (ans.size() != M+1) {
				out.println("IMPOSSIBLE");
			} else {
				for (int u : ans) out.print(u + " ");
			}
		} else {
			out.println("IMPOSSIBLE");
		}
		
		
		f.close();
		out.close();
	}
	
	static TreeSet<Integer> adj[];
	
	static BufferedReader f;
	static PrintWriter out;
	static StringTokenizer st;
 
	static String rl() throws IOException {
		return f.readLine();
	}
 
	static int ni(StringTokenizer st) {
		return Integer.parseInt(st.nextToken());
	}
 
	static long nlg(StringTokenizer st) {
		return Long.parseLong(st.nextToken());
	}
 
	static int ni() throws IOException {
		return Integer.parseInt(rl());
	}
 
	static long nlg() throws IOException {
		return Long.parseLong(rl());
	}
 
	static StringTokenizer nl() throws IOException {
		return new StringTokenizer(rl());
	}
 
	static int[] nia(int N) throws IOException {
		StringTokenizer st = nl();
		int[] A = new int[N];
		for (int i = 0; i < N; i++)
			A[i] = ni(st);
		return A;
	}
 
	static void setIn(String s) throws IOException {
		f = new BufferedReader(new FileReader(s));
	}
 
	static void setOut(String s) throws IOException {
		out = new PrintWriter(new FileWriter(s));
	}
 
	static void setIn() {
		f = new BufferedReader(new InputStreamReader(System.in));
	}
 
	static void setOut() {
		out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
	}
 
	static void setIO(String s) throws IOException {
		setIn(s + ".in");
		setOut(s + ".out");
	}
 
	static void setIO() {
		setIn();
		setOut();
	}
}
