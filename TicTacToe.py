import tkinter as tk
from tkinter import messagebox

# Game setup
root = tk.Tk()
root.title("Tic Tac Toe - Unbeatable AI")

board = [" " for _ in range(9)]
buttons = []

# Check winner
def check_winner(b):
    win_conditions = [
        [0,1,2],[3,4,5],[6,7,8],
        [0,3,6],[1,4,7],[2,5,8],
        [0,4,8],[2,4,6]
    ]
    for cond in win_conditions:
        if b[cond[0]] == b[cond[1]] == b[cond[2]] != " ":
            return b[cond[0]]
    if " " not in b:
        return "Tie"
    return None

# Minimax algorithm
def minimax(b, is_maximizing):
    result = check_winner(b)
    if result == "O":
        return 1
    elif result == "X":
        return -1
    elif result == "Tie":
        return 0

    if is_maximizing:
        best_score = -float("inf")
        for i in range(9):
            if b[i] == " ":
                b[i] = "O"
                score = minimax(b, False)
                b[i] = " "
                best_score = max(score, best_score)
        return best_score
    else:
        best_score = float("inf")
        for i in range(9):
            if b[i] == " ":
                b[i] = "X"
                score = minimax(b, True)
                b[i] = " "
                best_score = min(score, best_score)
        return best_score

# AI move
def ai_move():
    best_score = -float("inf")
    move = None

    for i in range(9):
        if board[i] == " ":
            board[i] = "O"
            score = minimax(board, False)
            board[i] = " "
            if score > best_score:
                best_score = score
                move = i

    if move is not None:
        make_move(move, "O")

# Make move
def make_move(index, player):
    if board[index] == " ":
        board[index] = player
        buttons[index].config(text=player)

        winner = check_winner(board)
        if winner:
            if winner == "Tie":
                messagebox.showinfo("Game Over", "It's a Tie!")
            else:
                messagebox.showinfo("Game Over", f"{winner} wins!")
            reset_game()
        elif player == "X":
            ai_move()

# Reset game
def reset_game():
    global board
    board = [" " for _ in range(9)]
    for btn in buttons:
        btn.config(text="")

# Button click
def on_click(i):
    if board[i] == " ":
        make_move(i, "X")

# Create GUI
for i in range(9):
    btn = tk.Button(root, text="", font=("Arial", 24), width=5, height=2,
                    command=lambda i=i: on_click(i))
    btn.grid(row=i//3, column=i%3)
    buttons.append(btn)

root.mainloop()