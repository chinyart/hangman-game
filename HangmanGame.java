import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class HangmanGame extends JFrame {
    private static final String[] WORDS = {"hello", "world", "hangman", "java", "programming"};
    private static final int MAX_TRIES = 10;

    private String wordToGuess;
    private StringBuilder guessedWord;
    private int triesLeft;
    private int score;

    private JLabel wordLabel;
    private JLabel triesLabel;
    private JTextField guessField;
    private JButton guessButton;
    private JLabel scoreLabel;
    private JPanel hangmanPanel;

    private int incorrectGuesses;
    private boolean gameOver;

    public HangmanGame() {
        wordToGuess = WORDS[(int) (Math.random() * WORDS.length)];
        guessedWord = new StringBuilder("-".repeat(wordToGuess.length()));
        triesLeft = MAX_TRIES;
        score = 0;
        incorrectGuesses = 0;
        gameOver = false;

        setTitle("Hangman Game");
        setSize(400, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(5, 1));

        wordLabel = new JLabel("Word: " + guessedWord.toString());
        add(wordLabel);

        triesLabel = new JLabel("Tries left: " + triesLeft);
        add(triesLabel);

        guessField = new JTextField();
        add(guessField);

        guessButton = new JButton("Guess");
        guessButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                checkGuess();
            }
        });
        add(guessButton);

        scoreLabel = new JLabel("Score: " + score);
        add(scoreLabel);

        hangmanPanel = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (gameOver) {
                    return;
                }
                g.setColor(Color.BLACK);
                int centerX = getWidth() / 2;
                int centerY = getHeight() / 3;

                if (incorrectGuesses >= 1) {
                    g.drawLine(centerX - 30, centerY + 120, centerX + 30, centerY + 120); // Base
                }
                if (incorrectGuesses >= 2) {
                    g.drawLine(centerX, centerY + 120, centerX, centerY); // Pole
                }
                if (incorrectGuesses >= 3) {
                    g.drawLine(centerX, centerY, centerX + 50, centerY); // Beam
                }
                if (incorrectGuesses >= 4) {
                    g.drawLine(centerX + 50, centerY, centerX + 50, centerY + 20); // Rope
                }
                if (incorrectGuesses >= 5) {
                    g.drawOval(centerX + 40, centerY + 20, 20, 20); // Head
                }
                if (incorrectGuesses >= 6) {
                    g.drawLine(centerX + 50, centerY + 40, centerX + 50, centerY + 80); // Body
                }
                if (incorrectGuesses >= 7) {
                    g.drawLine(centerX + 50, centerY + 60, centerX + 30, centerY + 100); // Left Arm
                }
                if (incorrectGuesses >= 8) {
                    g.drawLine(centerX + 50, centerY + 60, centerX + 70, centerY + 100); // Right Arm
                }
                if (incorrectGuesses >= 9) {
                    g.drawLine(centerX + 50, centerY + 80, centerX + 30, centerY + 140); // Left Leg
                }
                if (incorrectGuesses >= 10) {
                    g.drawLine(centerX + 50, centerY + 80, centerX + 70, centerY + 140); // Right Leg
                }
            }
        };
        add(hangmanPanel);

        setVisible(true);
    }

    private void checkGuess() {
        if (gameOver) {
            return;
        }
        String guess = guessField.getText();
        if (guess.length() != 1) {
            JOptionPane.showMessageDialog(this, "Please enter a single letter guess.", "Invalid Guess", JOptionPane.ERROR_MESSAGE);
            return;
        }

        char guessChar = guess.charAt(0);
        boolean found = false;
        for (int i = 0; i < wordToGuess.length(); i++) {
            if (wordToGuess.charAt(i) == guessChar) {
                guessedWord.setCharAt(i, guessChar);
                found = true;
            }
        }

        if (!found) {
            triesLeft--;
            incorrectGuesses++;
        }

        updateLabels();

        if (guessedWord.toString().equals(wordToGuess) || triesLeft == 0) {
            gameOver = true;
            if (guessedWord.toString().equals(wordToGuess)) {
                score++;
                JOptionPane.showMessageDialog(this, "Congratulations! You guessed the word: " + wordToGuess, "Game Over", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Sorry, you didn't guess the word. The word was: " + wordToGuess, "Game Over", JOptionPane.INFORMATION_MESSAGE);
            }
            int option = JOptionPane.showConfirmDialog(this, "Do you want to play again?", "Play Again", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                resetGame();
            } else {
                dispose();
            }
        }
    }

    private void updateLabels() {
        wordLabel.setText("Word: " + guessedWord.toString());
        triesLabel.setText("Tries left: " + triesLeft);
        guessField.setText("");
        scoreLabel.setText("Score: " + score);
        hangmanPanel.repaint();
    }

    private void resetGame() {
        wordToGuess = WORDS[(int) (Math.random() * WORDS.length)];
        guessedWord = new StringBuilder("-".repeat(wordToGuess.length()));
        triesLeft = MAX_TRIES;
        incorrectGuesses = 0;
        gameOver = false;
        updateLabels();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new HangmanGame();
            }
        });
    }
}
