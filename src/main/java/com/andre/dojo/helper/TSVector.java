package com.andre.dojo.helper;

import java.util.*;

public class TSVector {
    private Map<String, List<Integer>> lexemes;

    public TSVector(String string) {
        this.lexemes = new HashMap<>();
    }

    public void addLexeme(String word, int position) {
        lexemes.computeIfAbsent(word, k -> new ArrayList<>()).add(position);
    }

    public List<Integer> getPositions(String word) {
        return lexemes.getOrDefault(word, Collections.emptyList());
    }

    public Set<String> getLexemes() {
        return lexemes.keySet();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, List<Integer>> entry : lexemes.entrySet()) {
            sb.append("'").append(entry.getKey()).append("':");
            sb.append(entry.getValue()).append(" ");
        }
        return sb.toString().trim();
    }
}
