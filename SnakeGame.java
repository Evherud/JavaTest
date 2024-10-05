import java.awt.Point;
import java.util.LinkedList;
import java.util.Random;

public class SnakeGame {
    private LinkedList<Point> snake;
    private Point food;
    private int boardSize;
    private Random random;
    private String direction;

    public SnakeGame(int size) {
        boardSize = size;
        snake = new LinkedList<>();
        snake.add(new Point(size / 2, size / 2)); // Start in the middle
        random = new Random();
        direction = "w"; // Initial direction
        spawnFood();
    }

    public boolean move(String newDirection) {
        if (isOppositeDirection(newDirection)) {
            return false; // Ignore opposite direction
        }
        if (newDirection != null) {
            direction = newDirection;
        }
        Point head = snake.getFirst();
        Point newHead = new Point(head);

        switch (direction) {
            case "w": newHead.x--; break;//up
            case "a": newHead.y--; break;//left
            case "s": newHead.x++; break;//down
            case "d": newHead.y++; break;//right
        }

        // Wrap around logic
        if (newHead.x < 0) newHead.x = boardSize - 1;
        if (newHead.x >= boardSize) newHead.x = 0;
        if (newHead.y < 0) newHead.y = boardSize - 1;
        if (newHead.y >= boardSize) newHead.y = 0;

        if (snake.contains(newHead)) {
            return false; // Game over if snake runs into itself
        }

        snake.addFirst(newHead);
        if (!newHead.equals(food)) {
            snake.removeLast();
        } else {
            spawnFood();
        }
        return true;
    }

    private boolean isOppositeDirection(String newDirection) {
        return (direction.equals("w") && newDirection.equals("s")) ||
               (direction.equals("s") && newDirection.equals("w")) ||
               (direction.equals("a") && newDirection.equals("d")) ||
               (direction.equals("d") && newDirection.equals("a"));
    }

    private void spawnFood() {
        do {
            food = new Point(random.nextInt(boardSize), random.nextInt(boardSize));
        } while (snake.contains(food));
    }

    public LinkedList<Point> getSnake() {
        return snake;
    }

    public Point getFood() {
        return food;
    }
}