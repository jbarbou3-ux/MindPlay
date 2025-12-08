public class Question {

    private String category;
    private String text;
    private String[] options;   // multiple choice options
    private int correctIndex;   // 0 to 3
    private int points;
    private boolean used;

    public Question(String category, String text, String[] options, int correctIndex, int points) {
        this.category = category;
        this.text = text;
        this.options = options;
        this.correctIndex = correctIndex;
        this.points = points;
        this.used = false;
    }

    public String getCategory() {
        return category;
    }

    public String getText() {
        return text;
    }

    public String[] getOptions() {
        return options;
    }

    public int getPoints() {
        return points;
    }

    public boolean isUsed() {
        return used;
    }

    public void markUsed() {
        used = true;
    }

    /**
     * Check if the user choice (A, B, C, D) matches the correct answer.
     */
    public boolean checkAnswer(char userChoice) {
        userChoice = Character.toUpperCase(userChoice);

        int choiceIndex = -1;
        if (userChoice == 'A') {
            choiceIndex = 0;
        } else if (userChoice == 'B') {
            choiceIndex = 1;
        } else if (userChoice == 'C') {
            choiceIndex = 2;
        } else if (userChoice == 'D') {
            choiceIndex = 3;
        }

        return choiceIndex == correctIndex;
    }

    /**
     * Optional helper for debugging.
     */
    public String toString() {
        return "[" + category + "] " + text + " (" + points + " pts)";
    }
}
