package module4;

import java.util.*;

public class DinicMaxFlow {

    static class Edge {
        int to;
        int capacity;
        int reverse;

        Edge(int to, int capacity, int reverse) {
            this.to = to;
            this.capacity = capacity;
            this.reverse = reverse;
        }
    }

    private List<Edge>[] graph;
    private int[] level;
    private int[] pointer;

    @SuppressWarnings("unchecked")
    public DinicMaxFlow(int nodes) {

        graph = new ArrayList[nodes];

        for (int i = 0; i < nodes; i++) {
            graph[i] = new ArrayList<>();
        }

        level = new int[nodes];
        pointer = new int[nodes];
    }

    public void addEdge(int from, int to, int capacity) {

        Edge forward =
                new Edge(to, capacity, graph[to].size());

        Edge backward =
                new Edge(from, 0, graph[from].size());

        graph[from].add(forward);
        graph[to].add(backward);
    }

    private boolean bfs(int source, int sink) {

        Arrays.fill(level, -1);

        Queue<Integer> queue = new LinkedList<>();

        level[source] = 0;
        queue.add(source);

        while (!queue.isEmpty()) {

            int current = queue.poll();

            for (Edge edge : graph[current]) {

                if (edge.capacity > 0
                        && level[edge.to] == -1) {

                    level[edge.to] =
                            level[current] + 1;

                    queue.add(edge.to);
                }
            }
        }

        return level[sink] != -1;
    }

    private int dfs(
            int current,
            int sink,
            int flow) {

        if (current == sink) {
            return flow;
        }

        for (; pointer[current] < graph[current].size();
             pointer[current]++) {

            Edge edge =
                    graph[current].get(pointer[current]);

            if (edge.capacity > 0
                    && level[edge.to]
                    == level[current] + 1) {

                int pushed =
                        dfs(
                            edge.to,
                            sink,
                            Math.min(flow, edge.capacity));

                if (pushed > 0) {

                    edge.capacity -= pushed;

                    graph[edge.to]
                            .get(edge.reverse)
                            .capacity += pushed;

                    return pushed;
                }
            }
        }

        return 0;
    }

    public int maxFlow(int source, int sink) {

        int flow = 0;

        while (bfs(source, sink)) {

            Arrays.fill(pointer, 0);

            int pushed;

            while ((pushed =
                    dfs(source, sink, Integer.MAX_VALUE)) > 0) {

                flow += pushed;
            }
        }

        return flow;
    }

    public static void demonstratePassengerFlow() {

        System.out.println(
                "\n===== PASSENGER FLOW =====");

        /*
         * Airport passenger movement:
         *
         * Entrance
         *    ↓
         * Security
         *    ↓
         * Check-in
         *    ↓
         * Boarding
         */

        int entrance = 0;
        int security = 1;
        int checkIn = 2;
        int boarding = 3;

        DinicMaxFlow network =
                new DinicMaxFlow(4);

        network.addEdge(
                entrance,
                security,
                100);

        network.addEdge(
                security,
                checkIn,
                60);

        network.addEdge(
                checkIn,
                boarding,
                50);

        int maximumPassengers =
                network.maxFlow(
                        entrance,
                        boarding);

        System.out.println(
                "Entrance → Security capacity: 100");

        System.out.println(
                "Security → Check-in capacity: 60");

        System.out.println(
                "Check-in → Boarding capacity: 50");

        System.out.println(
                "\nMaximum passenger flow: "
                + maximumPassengers
                + " passengers");
    }
}