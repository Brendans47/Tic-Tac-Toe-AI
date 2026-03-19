#include <stdio.h>
#include <stdlib.h>

char board[9] = {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '};

void printBoard() {
    printf("\n");
    for (int i = 0; i < 9; i++) {
        printf(" %c ", board[i]);
        if (i % 3 != 2) printf("|");
        if (i % 3 == 2 && i != 8) printf("\n---+---+---\n");
    }
    printf("\n\n");
}

int checkWinner() {
    int wins[8][3] = {
        {0,1,2},{3,4,5},{6,7,8},
        {0,3,6},{1,4,7},{2,5,8},
        {0,4,8},{2,4,6}
    };

    for (int i = 0; i < 8; i++) {
        int a = wins[i][0], b = wins[i][1], c = wins[i][2];
        if (board[a] == board[b] && board[b] == board[c] && board[a] != ' ')
            return board[a] == 'O' ? 1 : -1;
    }

    for (int i = 0; i < 9; i++) {
        if (board[i] == ' ') return 0;
    }

    return 2; // Tie
}

int minimax(int isMaximizing) {
    int result = checkWinner();

    if (result != 0) {
        return result;
    }

    if (isMaximizing) {
        int bestScore = -1000;

        for (int i = 0; i < 9; i++) {
            if (board[i] == ' ') {
                board[i] = 'O';
                int score = minimax(0);
                board[i] = ' ';
                if (score > bestScore) bestScore = score;
            }
        }

        return bestScore;
    } else {
        int bestScore = 1000;

        for (int i = 0; i < 9; i++) {
            if (board[i] == ' ') {
                board[i] = 'X';
                int score = minimax(1);
                board[i] = ' ';
                if (score < bestScore) bestScore = score;
            }
        }

        return bestScore;
    }
}

void aiMove() {
    int bestScore = -1000;
    int move = -1;

    for (int i = 0; i < 9; i++) {
        if (board[i] == ' ') {
            board[i] = 'O';
            int score = minimax(0);
            board[i] = ' ';
            if (score > bestScore) {
                bestScore = score;
                move = i;
            }
        }
    }

    if (move != -1) {
        board[move] = 'O';
    }
}

int main() {
    int move;

    printf("Tic Tac Toe (You = X, AI = O)\n");

    while (1) {
        printBoard();

        // Player move
        printf("Enter your move (1-9): ");
        scanf("%d", &move);
        move--;

        if (move < 0 || move > 8 || board[move] != ' ') {
            printf("Invalid move. Try again.\n");
            continue;
        }

        board[move] = 'X';

        if (checkWinner() != 0) break;

        // AI move
        aiMove();

        if (checkWinner() != 0) break;
    }

    printBoard();

    int result = checkWinner();
    if (result == 1) printf("AI wins!\n");
    else if (result == -1) printf("You win!\n");
    else printf("It's a tie!\n");

    return 0;
}