package module2;

import java.util.*;

public class AhoCorasick {

    static class Node {
        Map<Character, Node> children = new HashMap<>();
        Node fail;
        boolean isEnd;
        String word;
    }

    private Node root = new Node();

    // Add a keyword
    public void addWord(String word) {

        Node current = root;

        for (char c : word.toLowerCase().toCharArray()) {

            current.children.putIfAbsent(c, new Node());
            current = current.children.get(c);
        }

        current.isEnd = true;
        current.word = word;
    }

    // Build failure links
    public void build() {

        Queue<Node> queue = new LinkedList<>();

        for (Node child : root.children.values()) {
            child.fail = root;
            queue.add(child);
        }

        while (!queue.isEmpty()) {

            Node current = queue.poll();

            for (Map.Entry<Character, Node> entry : current.children.entrySet()) {

                char c = entry.getKey();
                Node child = entry.getValue();

                Node failure = current.fail;

                while (failure != root &&
                       !failure.children.containsKey(c)) {

                    failure = failure.fail;
                }

                if (failure.children.containsKey(c) &&
                    failure.children.get(c) != child) {

                    child.fail = failure.children.get(c);

                } else {
                    child.fail = root;
                }

                queue.add(child);
            }
        }
    }

    // Search for alerts
    public List<String> search(String text) {

        List<String> results = new ArrayList<>();

        Node current = root;

        text = text.toLowerCase();

        for (char c : text.toCharArray()) {

            while (current != root &&
                   !current.children.containsKey(c)) {

                current = current.fail;
            }

            if (current.children.containsKey(c)) {
                current = current.children.get(c);
            }

            Node temp = current;

            while (temp != root) {

                if (temp.isEnd) {
                    results.add(temp.word);
                }

                temp = temp.fail;
            }
        }

        return results;
    }
}