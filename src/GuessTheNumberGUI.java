import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class GuessTheNumberGUI extends JFrame {
    //variables
    private final int MAX_ROUNDS = 3;
    private final int MAX_ATTEMPTS = 5;
    private final int RANGE = 100;

    private int targetNumber;
    private int attemptsLeft;
    private int score;
    private int currentRound;
    private Random random = new Random();
    //Preparing GUI components
    private JTextField guessField;
    private JLabel feedbackLabel;
    private JLabel attemptsLabel;
    private JLabel scoreLabel;
    private JLabel roundLabel;
    private JButton guessButton;

    public GuessTheNumberGUI() {
        setTitle("Guess the Number Game");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(6, 1));

        guessField = new JTextField();
        feedbackLabel = new JLabel("Enter a number between 1 and " + RANGE + ":", SwingConstants.CENTER);
        attemptsLabel = new JLabel("Attempts Left: " + MAX_ATTEMPTS, SwingConstants.CENTER);
        scoreLabel = new JLabel("Score: 0", SwingConstants.CENTER);
        roundLabel = new JLabel("Round: 1/" + MAX_ROUNDS, SwingConstants.CENTER);
        guessButton = new JButton("Guess");

        add(roundLabel);
        add(feedbackLabel);
        add(guessField);
        add(attemptsLabel);
        add(scoreLabel);
        add(guessButton);

        startNewRound();

        guessButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleGuess();
            }
        });
    }

    private void startNewRound() {
        targetNumber = random.nextInt(RANGE) + 1;
        attemptsLeft = MAX_ATTEMPTS;
        currentRound++;
        feedbackLabel.setText("Enter a number between 1 and " + RANGE + ":");
        attemptsLabel.setText("Attempts Left: " + attemptsLeft);
        roundLabel.setText("Round: " + currentRound + "/" + MAX_ROUNDS);
        guessField.setText("");
    }

    private void handleGuess() {
        try {
            int guess = Integer.parseInt(guessField.getText());

            if (guess < 1 || guess > RANGE) {
                feedbackLabel.setText("Please enter a number between 1 and " + RANGE);
                return;
            }

            attemptsLeft--;
            attemptsLabel.setText("Attempts Left: " + attemptsLeft);

            if (guess == targetNumber) {
                feedbackLabel.setText("Correct! Starting a new round.");
                score += calculateScore();
                scoreLabel.setText("Score: " + score);

                if (currentRound < MAX_ROUNDS) {
                    startNewRound();
                } else {
                    JOptionPane.showMessageDialog(this, "Game Over! Your total score is: " + score);
                    resetGame();
                }
            } else if (guess < targetNumber) {
                feedbackLabel.setText("Your guess is too low.");
            } else {
                feedbackLabel.setText("Your guess is too high.");
            }

            if (attemptsLeft == 0) {
                JOptionPane.showMessageDialog(this, "No more attempts left. The correct number was: " + targetNumber);

                if (currentRound < MAX_ROUNDS) {
                    startNewRound();
                } else {
                    JOptionPane.showMessageDialog(this, "Game Over! Your total score is: " + score);
                    resetGame();
                }
            }
        } catch (NumberFormatException e) {
            feedbackLabel.setText("Please enter a valid number.");
        }
    }

    private int calculateScore() {
        return attemptsLeft * 10;
    }

    private void resetGame() {
        currentRound = 0;
        score = 0;
        scoreLabel.setText("Score: 0");
        startNewRound();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GuessTheNumberGUI().setVisible(true);
            }
        });
    }
}
