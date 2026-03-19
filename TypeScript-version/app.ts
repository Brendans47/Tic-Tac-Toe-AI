const board: string[] = Array(9).fill("");
const boardDiv = document.getElementById("board")!;
const statusText = document.getElementById("status")!;

function renderBoard() {
  boardDiv.innerHTML = "";

  board.forEach((cell, index) => {
    const btn = document.createElement("button");
    btn.className = "cell";
    btn.textContent = cell;

    btn.onclick = () => playerMove(index);

    boardDiv.appendChild(btn);
  });
}

function checkWinner(b: string[]): string | null {
  const wins = [
    [0,1,2],[3,4,5],[6,7,8],
    [0,3,6],[1,4,7],[2,5,8],
    [0,4,8],[2,4,6]
  ];

  for (const [a,b1,c] of wins) {
    if (b[a] && b[a] === b[b1] && b[a] === b[c]) {
      return b[a];
    }
  }

  if (!b.includes("")) return "Tie";

  return null;
}

function minimax(b: string[], isMax: boolean): number {
  const result = checkWinner(b);

  if (result === "O") return 1;
  if (result === "X") return -1;
  if (result === "Tie") return 0;

  if (isMax) {
    let best = -Infinity;

    for (let i = 0; i < 9; i++) {
      if (b[i] === "") {
        b[i] = "O";
        best = Math.max(best, minimax(b, false));
        b[i] = "";
      }
    }

    return best;
  } else {
    let best = Infinity;

    for (let i = 0; i < 9; i++) {
      if (b[i] === "") {
        b[i] = "X";
        best = Math.min(best, minimax(b, true));
        b[i] = "";
      }
    }

    return best;
  }
}

function aiMove() {
  let bestScore = -Infinity;
  let move = -1;

  for (let i = 0; i < 9; i++) {
    if (board[i] === "") {
      board[i] = "O";
      const score = minimax(board, false);
      board[i] = "";

      if (score > bestScore) {
        bestScore = score;
        move = i;
      }
    }
  }

  if (move !== -1) {
    board[move] = "O";
  }
}

function playerMove(index: number) {
  if (board[index] !== "") return;

  board[index] = "X";
  updateGame();

  if (!checkWinner(board)) {
    aiMove();
    updateGame();
  }
}

function updateGame() {
  renderBoard();

  const result = checkWinner(board);

  if (result) {
    if (result === "Tie") {
      statusText.textContent = "It's a Tie!";
    } else {
      statusText.textContent = result + " wins!";
    }

    board.fill("");
  }
}

renderBoard();