/**
 * Question - Trivia Question Class
 * This class stores all the information about a single trivia question.
 * Each Question has:
 * - a category
 * - question text
 * - 4 answer options (A-D)
 * - the correct answer index
 * - a point value
 * - a used flag so questions are not repeated
 *
 * This class includes methods to check answers and mark questions as used.
 *
 * @author Juliana Barbour
 * @version Fall 2025
 */

public class Question {

    private String category;     // Sports, Music, Movies, etc.
    private String text;         // the question being asked
    private String[] options;    // answer choices (A,B,C,D)
    private int correctIndex;    // which option is correct (0-3)
    private int points;          // points awarded for this question
    private boolean used;        // to prevent asking the same question again

    // Constructor – creates a new Question object with all fields
    public Question(String category, String text, String[] options, int correctIndex, int points) {
        this.category = category;
        this.text = text;
        this.options = options;
        this.correctIndex = correctIndex;
        this.points = points;
        this.used = false;
    }

    // Getter methods
    public String getCategory() { return category; }
    public String getText() { return text; }
    public String[] getOptions() { return options; }
    public int getPoints() { return points; }
    public boolean isUsed() { return used; }

    // Marks this question as already asked
    public void markUsed() {
        used = true;
    }

    // Checks if the user's answer (A-D) matches the correct option
    public boolean checkAnswer(char userChoice) {
        userChoice = Character.toUpperCase(userChoice);

        int choiceIndex = -1;

        if (userChoice == 'A') choiceIndex = 0;
        else if (userChoice == 'B') choiceIndex = 1;
        else if (userChoice == 'C') choiceIndex = 2;
        else if (userChoice == 'D') choiceIndex = 3;

        return choiceIndex == correctIndex;
    }

    // Prints basic info about the question
    public String toString() {
        return "[" + category + "] " + text + " (" + points + " pts)";
    }
}
