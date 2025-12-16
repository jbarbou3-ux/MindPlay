import java.util.Random;
import java.util.Scanner;

/**
 * MindPlay - Trivia Game Final Project
 * This program runs a menu-driven trivia game with four categories:
 * - Sports
 * - Music
 * - Movies
 * - Pop Culture
 *
 * Each round displays one unused question from each category and the user selects
 * which category to attempt. The user answers using letters (A-D) to reduce
 * spelling/input mistakes.
 *
 * The program tracks:
 * - Score (adds points for correct answers, subtracts points for incorrect answers)
 * - Strikes (a wrong answer adds a strike)
 *
 * The game ends when:
 * - The user reaches 4 strikes, OR
 * - The user answers 6 questions, OR
 * - There are no more usable questions available
 *
 * @author Juliana Barbour
 * @version Fall 2025
 */

public class MindPlay {

    public static void main(String[] args) {
        Scanner kb = new Scanner(System.in);

        // Show the title screen and rules
        printTitleScreen(kb);

        // Build all the questions for the game
        Question[] questions = buildQuestionBank();

        // Variables to track score and when the game should stop
        int score = 0;
        int strikes = 0;
        final int MAX_STRIKES = 4;  // after 4 strikes the game ends
        final int MAX_QUESTIONS_ANSWERED = 6; // total turns allowed

        int questionsAnswered = 0;

        // Main game loop
        while (strikes < MAX_STRIKES
                && questionsAnswered < MAX_QUESTIONS_ANSWERED
                && hasUnusedQuestions(questions)
                && hasUnusedInAllCategories(questions)) {

            // Get one unused question from each category
            int[] optionIndices = getOneFromEachCategory(questions);

            // Show the 4 options for this round
            printRoundOptions(questions, optionIndices, score, strikes, questionsAnswered + 1);

            // Let player pick 1–4
            int choicePos = readOptionChoice(kb, optionIndices.length);
            int questionIndex = optionIndices[choicePos - 1];
            Question q = questions[questionIndex];

            // Ask that question and print answer choices
            askQuestion(q);

            // Read answer A–D
            char answer = readAnswerChoice(kb);

            // Check if correct
            boolean correct = q.checkAnswer(answer);

            // Mark this question as used so we don't ask it again
            q.markUsed();

            // Update score and strikes
            if (correct) {
                System.out.println("Correct! You earned " + q.getPoints() + " points.");
                score += q.getPoints();
            } else {
                System.out.println("Incorrect! You have earned a strike and lost "
                                   + q.getPoints() + " points.");
                score -= q.getPoints();
                strikes++;  // add a strike
                System.out.println("Strikes: " + strikes + "/4");
            }

            questionsAnswered++;

            // Print score after each question
            System.out.println("Current score: " + score);
            System.out.println("------------------------------------");

            // Pause until user presses A
            waitForContinue(kb);

            System.out.println();
        }

        // Game over messages
        System.out.println();
        System.out.println("====================================");

        if (strikes >= MAX_STRIKES) {
            System.out.println("Game Over! You hit 4 strikes.");
        } else if (questionsAnswered >= MAX_QUESTIONS_ANSWERED) {
            System.out.println("Congratulations! You completed MindPlay!");
        } else {
            System.out.println("No more questions available.");
        }

        // Final score
        System.out.println("Final score: " + score);
        System.out.println("====================================");

        kb.close();
    }

    // Title screen and rules
    public static void printTitleScreen(Scanner kb) {
        System.out.println("************************************************");
        System.out.println("*                                              *");
        System.out.println("*                  MindPlay                    *");
        System.out.println("*                                              *");
        System.out.println("************************************************");
        System.out.println();
        System.out.println("A trivia challenge in Sports, Music, Movies,");
        System.out.println("and Pop Culture.");
        System.out.println();
        System.out.println("Rules:");
        System.out.println(" - You will answer up to 6 questions.");
        System.out.println(" - Each round shows 4 choices, one from each category.");
        System.out.println(" - Correct answers add points.");
        System.out.println(" - Wrong answers subtract points and give you a strike.");
        System.out.println(" - 4 strikes and the game is over.");
        System.out.println();
        System.out.print("Press Enter to start MindPlay... ");
        kb.nextLine();
        System.out.println();
    }

    // Pauses and waits for user to press A then Enter
    public static void waitForContinue(Scanner kb) {
        System.out.print("Press A then Enter to continue: ");
        while (true) {
            String input = kb.nextLine().trim();
            if (input.equalsIgnoreCase("A")) {
                break;
            }
            System.out.print("Please press A then Enter to continue: ");
        }
    }

    // Builds all the questions for all categories
    public static Question[] buildQuestionBank() {
        Question[] questions = new Question[16];
        int index = 0;

        // SPORTS
        questions[index++] = new Question("Sports",
                "What is the name of the NFL quarterback who won seven Super Bowl titles with the New England Patriots?",
                new String[]{"Tom Brady", "Peyton Manning", "Joe Montana", "Aaron Rodgers"},
                0, 50);

        questions[index++] = new Question("Sports",
                "What NBA player scored 81 points in one game in 2006?",
                new String[]{"Kobe Bryant", "LeBron James", "Michael Jordan", "Allen Iverson"},
                0, 75);

        questions[index++] = new Question("Sports",
                "Which Olympic sprinter won four gold medals at the 1936 Berlin Olympics?",
                new String[]{"Jesse Owens", "Usain Bolt", "Carl Lewis", "Justin Gatlin"},
                0, 100);

        questions[index++] = new Question("Sports",
                "Which NBA player has won the most championships?",
                new String[]{"Bill Russell", "Michael Jordan", "Kareem Abdul Jabbar", "Magic Johnson"},
                0, 125);

        // MUSIC
        questions[index++] = new Question("Music",
                "Who has the most Grammy Awards?",
                new String[]{"Beyonce", "Taylor Swift", "Quincy Jones", "Adele"},
                0, 50);

        questions[index++] = new Question("Music",
                "Who is the best selling solo artist of all time?",
                new String[]{"Elvis Presley", "Michael Jackson", "Elton John", "Drake"},
                0, 75);

        questions[index++] = new Question("Music",
                "Who was the first female artist to have every track on her album go platinum?",
                new String[]{"Cardi B", "Nicki Minaj", "Taylor Swift", "Doja Cat"},
                0, 100);

        questions[index++] = new Question("Music",
                "Who is the best selling female artist of all time?",
                new String[]{"Madonna", "Rihanna", "Mariah Carey", "Celine Dion"},
                0, 125);

        // MOVIES
        questions[index++] = new Question("Movies",
                "Which actress has the most Oscar wins?",
                new String[]{"Katharine Hepburn", "Meryl Streep", "Frances McDormand", "Ingrid Bergman"},
                0, 50);

        questions[index++] = new Question("Movies",
                "Who directed Oppenheimer?",
                new String[]{"Christopher Nolan", "Greta Gerwig", "Steven Spielberg", "Denis Villeneuve"},
                0, 75);

        questions[index++] = new Question("Movies",
                "If watching Marvel movies in chronological order, which movie comes first?",
                new String[]{"Captain America: The First Avenger", "Iron Man", "Captain Marvel", "Thor"},
                0, 100);

        questions[index++] = new Question("Movies",
                "Which film won all Big Five Academy Awards?",
                new String[]{"The Silence of the Lambs", "Titanic", "Forrest Gump", "Parasite"},
                0, 125);

        // POP CULTURE
        questions[index++] = new Question("Pop Culture",
                "Who is the most followed person on Instagram in 2023?",
                new String[]{"Dwayne The Rock Johnson", "Cristiano Ronaldo", "Selena Gomez", "Kylie Jenner"},
                0, 50);

        questions[index++] = new Question("Pop Culture",
                "What year was the iPhone released?",
                new String[]{"2005", "2007", "2010", "2012"},
                1, 75);

        questions[index++] = new Question("Pop Culture",
                "Which celebrity caused Google Images to be created?",
                new String[]{"Jennifer Lopez", "Britney Spears", "Beyonce", "Kim Kardashian"},
                0, 100);

        questions[index++] = new Question("Pop Culture",
                "Who was the first celebrity to reach 100 million Twitter followers?",
                new String[]{"Katy Perry", "Justin Bieber", "Taylor Swift", "Barack Obama"},
                0, 125);

        return questions;
    }

    // Checks if any unused question is left
    public static boolean hasUnusedQuestions(Question[] questions) {
        for (Question q : questions) {
            if (!q.isUsed()) return true;
        }
        return false;
    }

    // Checks if there is at least one unused question in each category
    public static boolean hasUnusedInAllCategories(Question[] questions) {
        return hasUnusedInCategory(questions, "Sports")
                && hasUnusedInCategory(questions, "Music")
                && hasUnusedInCategory(questions, "Movies")
                && hasUnusedInCategory(questions, "Pop Culture");
    }

    // Helper: checks if there is at least one unused question in a specific category
    public static boolean hasUnusedInCategory(Question[] questions, String category) {
        for (Question q : questions) {
            if (!q.isUsed() && q.getCategory().equals(category)) {
                return true;
            }
        }
        return false;
    }

    // picks one unused question from each category
    public static int[] getOneFromEachCategory(Question[] questions) {
        Random rand = new Random();
        int[] result = new int[4];

        result[0] = getRandomUnusedFromCategory(questions, "Sports", rand);
        result[1] = getRandomUnusedFromCategory(questions, "Music", rand);
        result[2] = getRandomUnusedFromCategory(questions, "Movies", rand);
        result[3] = getRandomUnusedFromCategory(questions, "Pop Culture", rand);

        return result;
    }

    // helper to get unused question from category
    public static int getRandomUnusedFromCategory(Question[] questions, String category, Random rand) {
        int[] temp = new int[questions.length];
        int count = 0;

        for (int i = 0; i < questions.length; i++) {
            if (!questions[i].isUsed() && questions[i].getCategory().equals(category)) {
                temp[count++] = i;
            }
        }

        // count should never be 0 because we check hasUnusedInAllCategories() in the loop,
        // but this avoids crashing if something unexpected happens.
        if (count == 0) {
            return -1;
        }

        return temp[rand.nextInt(count)];
    }

    // Print the question choices and score for the round
    public static void printRoundOptions(Question[] questions, int[] indices,
                                         int score, int strikes, int roundNumber) {
        System.out.println("Round " + roundNumber);
        System.out.println("Score: " + score + "   Strikes: " + strikes + "/4");
        System.out.println("Choose one:");

        for (int i = 0; i < indices.length; i++) {
            Question q = questions[indices[i]];
            System.out.println((i + 1) + ". " + q.getCategory() + " - " + q.getPoints() + " points");
        }
        System.out.println();
    }

    // Read user’s menu choice (1–4)
    public static int readOptionChoice(Scanner kb, int optionCount) {
        int choice;
        while (true) {
            System.out.print("Enter choice: ");
            if (kb.hasNextInt()) {
                choice = kb.nextInt();
                kb.nextLine();
                if (choice >= 1 && choice <= optionCount) return choice;
            } else {
                kb.nextLine();
            }
            System.out.println("Invalid input. Please enter a number between 1 and 4.");
        }
    }

    // Prints the question text and answer choices A–D
    public static void askQuestion(Question q) {
        System.out.println("\nCategory: " + q.getCategory());
        System.out.println("For " + q.getPoints() + " points:");
        System.out.println(q.getText());
        System.out.println();

        String[] opts = q.getOptions();
        char label = 'A';
        for (String opt : opts) {
            System.out.println(label + ") " + opt);
            label++;
        }
        System.out.println();
    }

    // Reads and validates answer input A–D (with a clearer error message)
    public static char readAnswerChoice(Scanner kb) {
        while (true) {
            System.out.print("Your answer (A, B, C, or D): ");
            String input = kb.nextLine().trim().toUpperCase();

            if (input.length() == 1 && "ABCD".indexOf(input.charAt(0)) >= 0) {
                return input.charAt(0);
            }

            System.out.println("Invalid input. Please enter ONLY A, B, C, or D.");
        }
    }
}
