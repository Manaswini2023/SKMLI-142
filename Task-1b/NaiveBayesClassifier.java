import java.util.HashMap;
import java.util.List;
import java.util.Map;

class NaiveBayesClassifier {
    private Map<String, Map<String, Integer>> wordCountPerCategory = new HashMap<>();
    private Map<String, Integer> totalWordsPerCategory = new HashMap<>();
    private Map<String, Integer> documentCountPerCategory = new HashMap<>();
    private int totalDocuments = 0;


    public void train(List<Document> documents) {
        for (Document doc : documents) {
            String category = doc.category;
            documentCountPerCategory.put(category, documentCountPerCategory.getOrDefault(category, 0) + 1);
            totalDocuments++;
            String[] words = doc.text.split("\\s+");
            
            wordCountPerCategory.putIfAbsent(category, new HashMap<>());
            for (String word : words) {
                wordCountPerCategory.get(category).put(word, wordCountPerCategory.get(category).getOrDefault(word, 0) + 1);
                totalWordsPerCategory.put(category, totalWordsPerCategory.getOrDefault(category, 0) + 1);
            }
        }
    }

    public String classify(String text) {
        String[] words = text.split("\\s+");
        double maxProbability = Double.NEGATIVE_INFINITY;
        String bestCategory = null;

        for (String category : documentCountPerCategory.keySet()) {
            double logProbability = Math.log(documentCountPerCategory.get(category) / (double) totalDocuments);
            for (String word : words) {
                int wordCount = wordCountPerCategory.getOrDefault(category, new HashMap<>()).getOrDefault(word, 0);
                int totalWords = totalWordsPerCategory.get(category);
                logProbability += Math.log((wordCount + 1) / (double) (totalWords + wordCountPerCategory.get(category).size()));
            }
            if (logProbability > maxProbability) {
                maxProbability = logProbability;
                bestCategory = category;
            }
        }
        return bestCategory;
    }
}


