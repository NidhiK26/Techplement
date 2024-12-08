import java.util.*;

// Class to represent question with its options and correct answer
class Question {
    String questionText;
    List<String> options;
    int correctOption;

    public Question(String questionText, List<String> options, int correctOption) {
        this.questionText = questionText;
        this.options = options;
        this.correctOption = correctOption;
    }
}

// Class to represent quiz with name, topic, and list of questions
class Quiz {
    String name;
    String topic;
    List<Question> questions;

    public Quiz(String name, String topic) {
        this.name = name;
        this.topic = topic;
        this.questions = new ArrayList<>();
    }

    public void addQuestion(Question question) {
        questions.add(question);
    }
}

// Manager class to handle quizzes: creating, adding questions, listing, and taking quizzes
class QuizManager {
    private Map<String, Quiz> quizzes = new HashMap<>();

    public void createQuiz(String quizName, String topic) {
        if (quizzes.containsKey(quizName)) {
            System.out.println("A quiz with this name already exists!");
        } else {
            quizzes.put(quizName, new Quiz(quizName, topic));
            System.out.println("Quiz '" + quizName + "' created successfully on topic '" + topic + "'.");
        }
    }

    public void addQuestion(String quizName, Question question) {
        Quiz quiz = quizzes.get(quizName);
        if (quiz != null) {
            quiz.addQuestion(question);
            System.out.println("Question added to quiz: " + quizName);
        } else {
            System.out.println("Quiz not found! Please create the quiz first.");
        }
    }

    public void listQuizzes() {
        if (quizzes.isEmpty()) {
            System.out.println("No quizzes available yet.");
        } else {
            System.out.println("Available quizzes:");
            for (Quiz quiz : quizzes.values()) {
                System.out.println("- " + quiz.name + " (Topic: " + quiz.topic + ")");
            }
        }
    }

    public void takeQuiz(String quizName) {
        Quiz quiz = quizzes.get(quizName);
        if (quiz == null) {
            System.out.println("Quiz not found! Please try again.");
            return;
        }

        System.out.println("\nStarting quiz: " + quiz.name + " (Topic: " + quiz.topic + ")");
        int score = 0;
        Scanner sc = new Scanner(System.in);

        for (Question question : quiz.questions) {
            System.out.println("\n" + question.questionText);
            for (int i = 0; i < question.options.size(); i++) {
                System.out.println((i + 1) + ". " + question.options.get(i));
            }

            System.out.print("Enter your answer (1-" + question.options.size() + "): ");
            int userAnswer = sc.nextInt();

            if (userAnswer == question.correctOption) {
                score++;
            }
        }

        System.out.println("\nYou completed the quiz!");
        System.out.println("Your score: " + score + "/" + quiz.questions.size());

        if (score == quiz.questions.size()) {
            System.out.println("Great job! You got all questions right!");
        } else if (score >= quiz.questions.size() / 2) {
            System.out.println("Well done! Keep practicing to improve.");
        } else {
            System.out.println("Don't give up! Try again and you'll do better.");
        }
    }
}

// Main class for user interaction
public class QuizApplication {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        QuizManager manager = new QuizManager();
        String command;

        System.out.println("Welcome to QuizGenerator! Use the commands below to create, manage, and take quizzes.");
        while (true) {
            System.out.println("\nCommands: create quiz, add question, list quizzes, take quiz, exit");
            System.out.print("Enter command: ");
            command = sc.nextLine();

            switch (command) {
                case "create quiz":
                    System.out.print("Enter quiz name: ");
                    String quizName = sc.nextLine();
                    System.out.print("Enter quiz topic: ");
                    String topic = sc.nextLine();
                    manager.createQuiz(quizName, topic);
                    break;

                case "add question":
                    System.out.print("Enter quiz name: ");
                    quizName = sc.nextLine();
                    System.out.print("Enter question text: ");
                    String questionText = sc.nextLine();
                    System.out.print("Enter options (comma separated): ");
                    List<String> options = Arrays.asList(sc.nextLine().split(","));
                    System.out.print("Enter correct option number: ");
                    int correctOption = sc.nextInt();
                    sc.nextLine(); // Consume the leftover newline
                    manager.addQuestion(quizName, new Question(questionText, options, correctOption));
                    break;

                case "list quizzes":
                    manager.listQuizzes();
                    break;

                case "take quiz":
                    System.out.print("Enter quiz name: ");
                    quizName = sc.nextLine();
                    manager.takeQuiz(quizName);
                    break;

                case "exit":
                    System.out.println("Exiting QuizGenerator. Thanks for using it!");
                    return;

                default:
                    System.out.println("Unknown command. Please try again.");
            }
        }
    }
}
