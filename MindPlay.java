import java.util.Random;
import java.util.Scanner;

public class MindPlay {

    public static void main(String[] args) {
        Scanner kb = new Scanner(System.in);

        // Fancy title screen
        printTitleScreen(kb);

        Question[] questions = buildQuestionBank();

        int score = 0;
        int strikes = 0;
        final int MAX_STRIKES = 4;
        final int MAX_QUESTIONS_ANSWERED = 6;

        int questionsAnswered = 0;

        while (strikes < MAX_STRIKES
                && questionsAnswered < MAX_QUESTIONS_ANSWERED
                && hasUnusedQuestions(questions)) {

            int[] optionIndices = getOneFromEachCategory(questions);

            printRoundOptions(questions, optionIndices, score, strikes, questionsAnswered + 1);

            int choicePos = readOptionChoice(kb, optionIndices.length);
            int questionIndex = optionIndices[choicePos - 1];
            Question q = questions[questionIndex];

            askQuestion(q);

            char answer = readAnswerChoice(kb);

            boolean correct = q.checkAnswer(answer);
            q.markUsed();

            if (correct) {
                System.out.println("Correct! You earned " + q.getPoints() + " points.");
                score += q.getPoints();
            } else {
                System.out.println("Incorrect! You have earned a strike and lost "
                                   + q.getPoints() + " points.");
                score -= q.getPoints();
                strikes++;
                System.out.println("Strikes: " + strikes + "/4");
            }

            questionsAnswered++;

            System.out.println("Current score: " + score);
            System.out.println("------------------------------------");
            waitForContinue(kb);
            System.out.println();
        }

        System.out.println();
        System.out.println("====================================");

        if (strikes >= MAX_STRIKES) {
            System.out.println("Game Over! You hit 4 strikes.");
        } else if (questionsAnswered >= MAX_QUESTIONS_ANSWERED) {
            System.out.println("Congratulations! You completed MindPlay!");
        } else {
            System.out.println("No more questions available.");
        }

        System.out.println("Final score: " + score);
        System.out.println("====================================");

        kb.close();
    }

    // Pretty title screen with ASCII art
    public static void printTitleScreen(Scanner kb) {
        System.out.println("************************************************");
        System.out.println("*                                              *");
        System.out.println("*                  MindPlay                    *");
        System.out.println("*                                              *");
        System.out.println("************************************************");
        System.out.println();
        System.out.println(" __  __ _           _ _ ____  _                ");
        System.out.println("|  \\/  (_)_ __   __| (_)  _ \\| |_   _ _   _    ");
        System.out.println("| |\\/| | | '_ \\ / _` | | |_) | | | | | | | |   ");
        System.out.println("| |  | | | | | | (_| | |  __/| | |_| | |_| |   ");
        System.out.println("|_|  |_|_|_| |_|\\__,_|_|_|   |_|\\__,_|\\__, |   ");
        System.out.println("                                        |___/  ");
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

    // Pause after each question
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

    /** BUILD QUESTION BANK **/
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

    public static boolean hasUnusedQuestions(Question[] questions) {
        for (Question q : questions) {
            if (!q.isUsed()) return true;
        }
        return false;
    }

    public static int[] getOneFromEachCategory(Question[] questions) {
        Random rand = new Random();
        int[] result = new int[4];

        result[0] = getRandomUnusedFromCategory(questions, "Sports", rand);
        result[1] = getRandomUnusedFromCategory(questions, "Music", rand);
        result[2] = getRandomUnusedFromCategory(questions, "Movies", rand);
        result[3] = getRandomUnusedFromCategory(questions, "Pop Culture", rand);

        return result;
    }

    public static int getRandomUnusedFromCategory(Question[] questions, String category, Random rand) {
        int[] temp = new int[questions.length];
        int count = 0;

        for (int i = 0; i < questions.length; i++) {
            if (!questions[i].isUsed() && questions[i].getCategory().equals(category)) {
                temp[count++] = i;
            }
        }

        return temp[rand.nextInt(count)];
    }

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

    public static int readOptionChoice(Scanner kb, int optionCount) {
        int choice = -1;
        while (true) {
            System.out.print("Enter choice: ");
            if (kb.hasNextInt()) {
                choice = kb.nextInt();
                kb.nextLine();
                if (choice >= 1 && choice <= optionCount) return choice;
            } else {
                kb.nextLine();
            }
            System.out.println("Invalid input. Try again.");
        }
    }

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

    public static char readAnswerChoice(Scanner kb) {
        while (true) {
            System.out.print("Your answer (A-D): ");
            String input = kb.nextLine().trim().toUpperCase();
            if (input.length() == 1 &&
                    (input.charAt(0) == 'A' || input.charAt(0) == 'B'
                            || input.charAt(0) == 'C' || input.charAt(0) == 'D')) {
                return input.charAt(0);
            }
            System.out.println("Invalid choice.");
        }
    }
}

