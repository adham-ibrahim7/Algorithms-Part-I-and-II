/*
 * Created by Adham Ibrahim on 12/4/2020
 */

import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FordFulkerson;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class BaseballElimination {

    private final int N;
    private final Map<String, Integer> nameToId;
    private final Map<Integer, String> idToName;
    private final int[] wins, losses, remaining;
    private final int[][] against;

    private final boolean[] isEliminated;
    private final List<List<String>> certificates;

    public BaseballElimination(String filename) {
        In file = new In(filename);

        this.N = file.readInt();
        nameToId = new HashMap<>();
        idToName = new HashMap<>();
        wins = new int[N];
        losses = new int[N];
        remaining = new int[N];
        against = new int[N][N];

        for (int curr = 0; curr < N; curr++) {
            String teamName = file.readString();
            nameToId.put(teamName, curr);
            idToName.put(curr, teamName);

            wins[curr] = file.readInt();
            losses[curr] = file.readInt();
            remaining[curr] = file.readInt();

            for (int other = 0; other < N; other++) {
                against[curr][other] = against[other][curr] = file.readInt();
            }
        }

        isEliminated = new boolean[N];
        certificates = new LinkedList<>();

        int networkSize = 1 + N + (N-1) * (N-2) / 2;

        outermost: for (int s = 0; s < N; s++) {
            FlowNetwork network = new FlowNetwork(networkSize);

            // let 0 ... N-1 without s be the team nodes, so they can easily be looked up later
            int t = networkSize-1;

            // save w to be the current team node
            int w = N;
            for (int u = 0; u < N; u++) {
                if (u == s) continue;

                if (wins[s] + remaining[s] < wins[u]) {
                    // trivial elimination
                    isEliminated[s] = true;
                    certificates.add(new LinkedList<>());
                    certificates.get(s).add(idToName.get(u));
                    continue outermost;
                }

                network.addEdge(new FlowEdge(u, t, wins[s] + remaining[s] - wins[u]));

                for (int v = u + 1; v < N; v++) {
                    if (v == s) continue;

                    network.addEdge(new FlowEdge(s, w, against[u][v]));
                    network.addEdge(new FlowEdge(w, u, Double.POSITIVE_INFINITY));
                    network.addEdge(new FlowEdge(w, v, Double.POSITIVE_INFINITY));

                    w++;
                }
            }

            FordFulkerson flow = new FordFulkerson(network, s, t);

            int totalCapacity = 0;
            for (FlowEdge edge : network.adj(s)) {
                totalCapacity += edge.capacity();
            }

            isEliminated[s] = totalCapacity != flow.value();

            if (isEliminated[s]) {
                certificates.add(new LinkedList<String>());

                for (int u = 0; u < N; u++) {
                    if (u == s) {
                        continue;
                    }

                    if (flow.inCut(u)) {
                        certificates.get(s).add(idToName.get(u));
                    }
                }
            } else {
                certificates.add(null);
            }
        }
    }

    public int numberOfTeams() {
        return this.N;
    }

    public Iterable<String> teams() {
        return nameToId.keySet();
    }

    private void validate(String team) {
        if (!nameToId.containsKey(team)) {
            throw new IllegalArgumentException("Team not in division: " + team);
        }
    }

    public int wins(String team) {
        validate(team);
        return wins[nameToId.get(team)];
    }

    public int losses(String team) {
        validate(team);
        return losses[nameToId.get(team)];
    }

    public int remaining(String team) {
        validate(team);
        return remaining[nameToId.get(team)];
    }

    public int against(String team1, String team2) {
        validate(team1);
        validate(team2);
        return against[nameToId.get(team1)][nameToId.get(team2)];
    }

    public boolean isEliminated(String team) {
        validate(team);
        return isEliminated[nameToId.get(team)];
    }

    public Iterable<String> certificateOfElimination(String team) {
        validate(team);
        return certificates.get(nameToId.get(team));
    }

    public static void main(String[] args) {
        BaseballElimination division = new BaseballElimination(args[0]);
        for (String team : division.teams()) {
            if (division.isEliminated(team)) {
                StdOut.print(team + " is eliminated by the subset R = { ");
                for (String t : division.certificateOfElimination(team)) {
                    StdOut.print(t + " ");
                }
                StdOut.println("}");
            }
            else {
                StdOut.println(team + " is not eliminated");
            }
        }
    }

}
