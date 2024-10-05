import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

public class Game2048Window extends JFrame {
    private Game2048 game2048;
    private SnakeGame snakeGame;
    private JLabel[][] labels2048;
    private JLabel[][] labelsSnake;
    private Timer gameTimer;
    private String currentDirection;

    public Game2048Window() {
        game2048 = new Game2048();
        snakeGame = new SnakeGame(50); // Set Snake game to 50x50 grid
        labels2048 = new JLabel[Game2048.SIZE][Game2048.SIZE];
        labelsSnake = new JLabel[50][50]; // Adjust labels for Snake game
        currentDirection = "w"; // Initial direction

        setTitle("Game 2048 / Snake");
        setSize(1000, 500); // Adjusted size for two panels
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(1, 2, 10, 0)); // Add space between panels

        // Panel for 2048 game with background image
        JPanel panel2048 = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon icon = new ImageIcon("resources/MidDayQueen.jpg"); // Background image
                Image image = icon.getImage();
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }
        };
        panel2048.setLayout(new GridLayout(Game2048.SIZE, Game2048.SIZE)); // Use GridLayout for the game grid
        for (int i = 0; i < Game2048.SIZE; i++) {
            for (int j = 0; j < Game2048.SIZE; j++) {
                labels2048[i][j] = new JLabel("", SwingConstants.CENTER);
                labels2048[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                labels2048[i][j].setOpaque(false); // Make labels transparent
                panel2048.add(labels2048[i][j]);
            }
        }
        add(panel2048);

        // Panel for Snake game with background image
        JPanel panelSnake = new JPanel(new GridLayout(50, 50)) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon icon = new ImageIcon("resources/princess_midnight.jpg");
                Image image = icon.getImage();
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }
        };
        panelSnake.setOpaque(false);
        for (int i = 0; i < 50; i++) {
            for (int j = 0; j < 50; j++) {
                labelsSnake[i][j] = new JLabel("", SwingConstants.CENTER);
                labelsSnake[i][j].setOpaque(true); // Ensure label is opaque for snake
                labelsSnake[i][j].setBackground(new Color(0, 0, 0, 0)); // Transparent background
                labelsSnake[i][j].setBorder(BorderFactory.createEmptyBorder()); // Remove border
                panelSnake.add(labelsSnake[i][j]);
            }
        }
        add(panelSnake);

        updateBoards();

        // Add key listener for user input
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                boolean moved2048 = false;
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_W:
                        if (!currentDirection.equals("s")) currentDirection = "w"; // Snake up
                        moved2048 = game2048.move("w");
                        break;
                    case KeyEvent.VK_A:
                        if (!currentDirection.equals("d")) currentDirection = "a"; // Snake left
                        moved2048 = game2048.move("a");
                        break;
                    case KeyEvent.VK_S:
                        if (!currentDirection.equals("w")) currentDirection = "s"; // Snake down
                        moved2048 = game2048.move("s");
                        break;
                    case KeyEvent.VK_D:
                        if (!currentDirection.equals("a")) currentDirection = "d"; // Snake right
                        moved2048 = game2048.move("d");
                        break;
                }

                if (moved2048) {
                    game2048.addNewNumber();
                    if (game2048.isGameOver()) {
                        JOptionPane.showMessageDialog(null, "2048 Game Over!");
                    }
                    updateBoards();
                }
            }
        });

        // Set up a timer to update the game state
        gameTimer = new Timer();
        gameTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (!snakeGame.move(currentDirection)) {
                    JOptionPane.showMessageDialog(null, "Snake Game Over!");
                    snakeGame = new SnakeGame(50); // Restart Snake game
                }
                updateBoards();
            }
        }, 0, 200); // Update every 200 milliseconds

        setVisible(true);
    }

    private void updateBoards() {
        // Update 2048 Game
        int[][] board2048 = game2048.getBoard();
        for (int i = 0; i < Game2048.SIZE; i++) {
            for (int j = 0; j < Game2048.SIZE; j++) {
                if (board2048[i][j] != 0) {
                    labels2048[i][j].setText(String.valueOf(board2048[i][j]));
                    labels2048[i][j].setBackground(Color.LIGHT_GRAY);
                } else {
                    labels2048[i][j].setText("");
                    labels2048[i][j].setBackground(Color.WHITE);
                }
            }
        }

        // Update Snake Game
        LinkedList<Point> snake = snakeGame.getSnake();
        Point food = snakeGame.getFood();

        // Clear only the parts of the board not occupied by the snake or food
        for (int i = 0; i < 50; i++) {
            for (int j = 0; j < 50; j++) {
                Point currentPoint = new Point(i, j);
                if (!snake.contains(currentPoint) && !currentPoint.equals(food)) {
                    labelsSnake[i][j].setText("");
                    labelsSnake[i][j].setBackground(new Color(0, 0, 0, 0)); // Transparent background
                    labelsSnake[i][j].setOpaque(false); // Ensure transparency
                }
            }
        }

        // Draw the snake
        for (Point p : snake) {
            labelsSnake[p.x][p.y].setText(""); // No text for snake body
            labelsSnake[p.x][p.y].setBackground(Color.WHITE); // White color for snake body
            labelsSnake[p.x][p.y].setOpaque(true); // Make snake visible
        }

        // Draw the food
        labelsSnake[food.x][food.y].setText("*"); // Asterisk for food
        labelsSnake[food.x][food.y].setBackground(Color.RED); // Red color for food
        labelsSnake[food.x][food.y].setOpaque(true); // Make food visible
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Game2048Window::new);
    }
}