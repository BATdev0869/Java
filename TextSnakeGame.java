import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;

public class TextSnakeGame {
    private static final int WIDTH = 20;
    private static final int HEIGHT = 10;
    private static final char SNAKE_CHAR = '!';
    private static final char APPLE_CHAR = '#';
    private static final char EMPTY_CHAR = ' ';

    private static LinkedList<int[]> snake;
    private static int[] apple;
    private static char[][] board;
    private static boolean gameOver;
    private static String direction;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        initGame();

        while (!gameOver) {
            printBoard();
            System.out.print("Enter direction (w/a/s/d): ");
            direction = scanner.nextLine().trim().toLowerCase();

            if (!moveSnake()) {
                gameOver = true;
                System.out.println("Game Over! You hit the wall or yourself.");
            } else if (snake.getFirst()[0] == apple[0] && snake.getFirst()[1] == apple[1]) {
                growSnake();
                placeApple();
            }
        }

        scanner.close();
    }

    private static void initGame() {
        snake = new LinkedList<>();
        snake.add(new int[]{HEIGHT / 2, WIDTH / 2});
        snake.add(new int[]{HEIGHT / 2, WIDTH / 2 - 1});
        snake.add(new int[]{HEIGHT / 2, WIDTH / 2 - 2});

        board = new char[HEIGHT][WIDTH];
        gameOver = false;
        direction = "d";

        placeApple();
    }

    private static void placeApple() {
        Random random = new Random();
        int appleX, appleY;

        do {
            appleX = random.nextInt(HEIGHT);
            appleY = random.nextInt(WIDTH);
        } while (board[appleX][appleY] == SNAKE_CHAR);

        apple = new int[]{appleX, appleY};
    }

    private static void printBoard() {
        clearBoard();

        for (int[] segment : snake) {
            board[segment[0]][segment[1]] = SNAKE_CHAR;
        }

        board[apple[0]][apple[1]] = APPLE_CHAR;

        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                System.out.print(board[i][j]);
            }
            System.out.println();
        }
    }

    private static void clearBoard() {
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                board[i][j] = EMPTY_CHAR;
            }
        }
    }

    private static boolean moveSnake() {
        int[] head = snake.getFirst();
        int newHeadX = head[0];
        int newHeadY = head[1];

        switch (direction) {
            case "w":
                newHeadX--;
                break;
            case "a":
                newHeadY--;
                break;
            case "s":
                newHeadX++;
                break;
            case "d":
                newHeadY++;
                break;
            default:
                return true;
        }

        if (newHeadX < 0 || newHeadX >= HEIGHT || newHeadY < 0 || newHeadY >= WIDTH) {
            return false;
        }

        for (int[] segment : snake) {
            if (segment[0] == newHeadX && segment[1] == newHeadY) {
                return false;
            }
        }

        snake.addFirst(new int[]{newHeadX, newHeadY});
        snake.removeLast();
        return true;
    }

    private static void growSnake() {
        int[] tail = snake.getLast();
        snake.addLast(new int[]{tail[0], tail[1]});
    }
}