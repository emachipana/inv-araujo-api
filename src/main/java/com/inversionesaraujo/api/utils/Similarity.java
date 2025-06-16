package com.inversionesaraujo.api.utils;

import java.util.List;

public class Similarity {
    public static double cosineSimilarity(List<Double> vector1, List<Double> vector2) {
        if (vector1 == null || vector2 == null || vector1.isEmpty() || vector2.isEmpty() || vector1.size() != vector2.size()) {
            return 0.0;
        }

        double dotProduct = 0.0;
        double normA = 0.0;
        double normB = 0.0;

        for (int i = 0; i < vector1.size(); i++) {
            Double v1 = vector1.get(i);
            Double v2 = vector2.get(i);
            
            if (v1 == null || v2 == null) {
                continue;
            }
            
            dotProduct += v1 * v2;
            normA += v1 * v1;
            normB += v2 * v2;
        }

        // Evitar división por cero
        if (normA <= 0.0 || normB <= 0.0) {
            return 0.0;
        }

        double cosineSimilarity = dotProduct / (Math.sqrt(normA) * Math.sqrt(normB));
        
        // Asegurarse de que el resultado esté entre -1 y 1
        return Math.max(-1.0, Math.min(1.0, cosineSimilarity));
    }
}
