import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TicTacToeAI {
    private JFrame frame;
    private JButton[] buttons = new JButton[9];
    private char[] board = new char[9];

    public TicTacToeAI() {
        frame = new JFrame("Tic Tac Toe - Unbeatable AI");
        frame.setSize(300, 300);
        frame.setLayout(new GridLayout(3, 3));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        for (int i = 0; i < 9; i++) {
            board[i] = ' ';
            buttons[i] = new JButton("");
            buttons[i].setFont(new Font("Arial", Font.BOLD, 40));

            int index = i;
            buttons[i].addActionListener(e -> playerMove(index));

            frame.add(buttons[i]);
        }

        frame.setVisible(true);
    }

    private void playerMove(int index) {
        if (board[index] == ' ') {
            board[index] = 'X';
            buttons[index].setText("X");

            if (checkGameOver()) return;

            aiMove();
        }
    }

    private void aiMove() {
        int bestScore = Integer.MIN_VALUE;
        int move = -1;

        for (int i = 0; i < 9; i++) {
            if (board[i] == ' ') {
                board[i] = 'O';
                int score = minimax(false);
                board[i] = ' ';

                if (score > bestScore) {
                    bestScore = score;
                    move = i;
                }
            }
        }

        if (move != -1) {
            board[move] = 'O';
            buttons[move].setText("O");
            checkGameOver();
        }
    }

    private int minimax(boolean isMaximizing) {
        Character winner = checkWinner();

        if (winner != null) {
            if (winner == 'O') return 1;
            if (winner == 'X') return -1;
            return 0;
        }

        if (isMaximizing) {
            int bestScore = Integer.MIN_VALUE;

            for (int i = 0; i < 9; i++) {
                if (board[i] == ' ') {
                    board[i] = 'O';
                    int score = minimax(false);
                    board[i] = ' ';
                    bestScore = Math.max(score, bestScore);
                }
            }

            return bestScore;
        } else {
            int bestScore = Integer.MAX_VALUE;

            for (int i = 0; i < 9; i++) {
                if (board[i] == ' ') {
                    board[i] = 'X';
                    int score = minimax(true);
                    board[i] = ' ';
                    bestScore = Math.min(score, bestScore);
                }
            }

            return bestScore;
        }
    }

    private Character checkWinner() {
        int[][] wins = {
            {0,1,2},{3,4,5},{6,7,8},
            {0,3,6},{1,4,7},{2,5,8},
            {0,4,8},{2,4,6}
        };

        for (int[] win : wins) {
            if (board[win[0]] != ' ' &&
                board[win[0]] == board[win[1]] &&
                board[win[1]] == board[win[2]]) {
                return board[win[0]];
            }
        }

        for (char c : board) {
            if (c == ' ') return null;
        }

        return 'T'; // Tie
    }

    private boolean checkGameOver() {
        Character winner = checkWinner();

        if (winner != null) {
            if (winner == 'T') {
                JOptionPane.showMessageDialog(frame, "It's a Tie!");
            } else {
                JOptionPane.showMessageDialog(frame, winner + " wins!");
            }
            resetGame();
            return true;
        }

        return false;
    }

    private void resetGame() {
        for (int i = 0; i < 9; i++) {
            board[i] = ' ';
            buttons[i].setText("");
        }
    }

    public static void main(String[] args) {
        new TicTacToeAI();
    }
}