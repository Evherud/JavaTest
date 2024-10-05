import java.util.Random;
import java.util.Scanner;

public class Game2048 {
    private int[][] board;
    private Random random;
    public static final int SIZE = 4; // Made public

    public Game2048() {
        board = new int[SIZE][SIZE];
        random = new Random();
        addNewNumber();
        addNewNumber();
    }

    public boolean move(String direction) { // Made public
        boolean moved = false;
        switch (direction) {
            case "w": moved = moveUp(); break;
            case "s": moved = moveDown(); break;
            case "a": moved = moveLeft(); break;
            case "d": moved = moveRight(); break;
        }
        return moved;
    }

    public void addNewNumber() { // Made public
        int row, col;
        do {
            row = random.nextInt(SIZE);
            col = random.nextInt(SIZE);
        } while (board[row][col] != 0);

        board[row][col] = (random.nextInt(10) < 9) ? 2 : 4;
    }

    private void printBoard() {
        for (int[] row : board) {
            for (int cell : row) {
                System.out.printf("%4d", cell);
            }
            System.out.println();
        }
        System.out.println();
    }

    private boolean moveLeft() {
        boolean moved = false;
        for (int i = 0; i < SIZE; i++) {
            int[] row = board[i];
            int[] newRow = new int[SIZE];
            int index = 0;
            for (int j = 0; j < SIZE; j++) {
                if (row[j] != 0) {
                    if (index > 0 && newRow[index - 1] == row[j]) {
                        newRow[index - 1] *= 2;
                        moved = true;
                    } else {
                        newRow[index++] = row[j];
                        if (j != index - 1) moved = true;
                    }
                }
            }
            board[i] = newRow;
        }
        return moved;
    }

    private boolean moveRight() {
        reverseRows();
        boolean moved = moveLeft();
        reverseRows();
        return moved;
    }

    private boolean moveUp() {
        rotateLeft();
        boolean moved = moveLeft();
        rotateRight();
        return moved;
    }

    private boolean moveDown() {
        rotateRight();
        boolean moved = moveLeft();
        rotateLeft();
        return moved;
    }

    private void reverseRows() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE / 2; j++) {
                int temp = board[i][j];
                board[i][j] = board[i][SIZE - 1 - j];
                board[i][SIZE - 1 - j] = temp;
            }
        }
    }

    private void rotateLeft() {
        int[][] newBoard = new int[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                newBoard[i][j] = board[j][SIZE - 1 - i];
            }
        }
        board = newBoard;
    }

    private void rotateRight() {
        int[][] newBoard = new int[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                newBoard[i][j] = board[SIZE - 1 - j][i];
            }
        }
        board = newBoard;
    }

    public boolean isGameOver() { // Made public
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j] == 0) return false;
                if (i < SIZE - 1 && board[i][j] == board[i + 1][j]) return false;
                if (j < SIZE - 1 && board[i][j] == board[i][j + 1]) return false;
            }
        }
        return true;
    }

    public int[][] getBoard() { // Added getter method
        return board;
    }

    public void play() {
        Scanner scanner = new Scanner(System.in);
        while (!isGameOver()) {
            printBoard();
            System.out.print("Enter move (w/a/s/d): ");
            String move = scanner.nextLine().toLowerCase();
            if ("wasd".contains(move) && move.length() == 1) {
                if (move(move)) {
                    addNewNumber();
                }
            } else {
                System.out.println("Invalid move. Use w (up), a (left), s (down), or d (right).");
            }
        }
        printBoard();
        System.out.println("Game Over!");
        scanner.close();
    }

    public static void main(String[] args) {
        new Game2048().play();
    }
}