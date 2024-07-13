import java.util.*;

public class Main {
    public static void main(String[] args) {
        List<Document> trainingSet = Arrays.asList(
            new Document("the quick brown fox", "animal"),
            new Document("buy pharmaceuticals now", "spam"),
            new Document("the lazy dog", "animal"),
            new Document("cheap meds for sale", "spam")
        );

        List<Document> testSet = Arrays.asList(
            new Document("quick brown", "animal"),
            new Document("cheap pharmaceuticals", "spam")
        );

        NaiveBayesClassifier classifier = new NaiveBayesClassifier();
        classifier.train(trainingSet);

        int correct = 0;
        int total = testSet.size();
        int tp = 0, fp = 0, fn = 0;

        for (Document doc : testSet) {
            String predictedCategory = classifier.classify(doc.text);
            if (predictedCategory.equals(doc.category)) {
                correct++;
                tp++;
            } else {
                if (predictedCategory.equals("spam")) {
                    fp++;
                } else {
                    fn++;
                }
            }
        }

        double accuracy = correct / (double) total;
        double precision = tp / (double) (tp + fp);
        double recall = tp / (double) (tp + fn);

        System.out.println("Accuracy: " + accuracy);
        System.out.println("Precision: " + precision);
        System.out.println("Recall: " + recall);
    }
}
