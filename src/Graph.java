import java.util.*;

public class Graph {
    private Map<String, Vertex> map = new HashMap<>();

    public Vertex getOrCreate(String name) {
        Vertex new_vertex = map.get(name);
        if (new_vertex == null) {
            new_vertex = new Vertex(name);
            map.put(name, new_vertex);
        }
        return new_vertex;
    }

    public void addNode(String name) {
        getOrCreate(name);
    }

    public void addEdge(String from, String to) {
        Vertex from_vertex = getOrCreate(from);
        Vertex to_vertex = getOrCreate(to);
        from_vertex.adj.add(to_vertex);
        to_vertex.indegree++;
    }


    public void topsort() throws CycleFound {
        Queue<Vertex> q = new LinkedList<>();
        int counter = 0;

        for (Vertex v : map.values()) {
            if (v.indegree == 0)
                q.add(v);
        }

        while (!q.isEmpty()) {
            Vertex v = q.remove();
            v.topNum = ++counter;

            for (Vertex w : v.adj) {
                w.indegree--;
                if (w.indegree == 0)
                    q.add(w);
            }
        }

        if (counter != map.size())
            throw new CycleFound();

        // Printa ut den Topologiska ordningen
        System.out.println("\nTopologisk ordning:");
        map.values().stream().sorted(Comparator.comparingInt(a -> a.topNum)).forEach(v -> System.out.print(v.name + " "));

    }
}


class CycleFound extends Exception {
    public CycleFound() {
        super("Grafen innehåller en cykel – topologisk sortering ej möjlig.");
    }
}

class Vertex {
    String name;
    List<Vertex> adj = new ArrayList<>();
    int indegree = 0;
    int topNum = 0; //

    Vertex(String n) {
        name = n;
    }
}
