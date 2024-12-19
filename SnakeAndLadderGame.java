import java.util.*;

class Board {
    private final int size;
    private final Map<Integer, Integer> snakes;
    private final Map<Integer, Integer> ladders;

    public Board(int size) {
        this.size = size;
        this.snakes = new HashMap<>();
        this.ladders = new HashMap<>();
    }

    public void addSnake(int start, int end) {
        if (start > end && start <= size && end > 0) {
            snakes.put(start, end);
        }
    }

    public void addLadder(int start, int end) {
        if (start < end && start > 0 && end <= size) {
            ladders.put(start, end);
        }
    }

    public int getNextPosition(int position) {
        if (snakes.containsKey(position)) {
            return snakes.get(position);
        }
        return ladders.getOrDefault(position, position);
    }

    public int getSize() {
        return size;
    }
}

class Player {
    private final String name;
    private int position;

    public Player(String name) {
        this.name = name;
        this.position = 0;
    }

    public String getName() {
        return name;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}

class Game {
    private final Board board;
    private final List<Player> players;
    private final Random random;
    private int currentPlayerIndex;
    private Player winner;

    public Game(int boardSize) {
        this.board = new Board(boardSize);
        this.players = new ArrayList<>();
        this.random = new Random();
        this.currentPlayerIndex = 0;
        this.winner = null;
    }

    public void addPlayer(String name) {
        players.add(new Player(name));
    }

    public void addSnake(int start, int end) {
        board.addSnake(start, end);
    }

    public void addLadder(int start, int end) {
        board.addLadder(start, end);
    }

    public void playTurn() {
        Player currentPlayer = players.get(currentPlayerIndex);
        int diceRoll = rollDice();
        int newPosition = currentPlayer.getPosition() + diceRoll;
        if (newPosition <= board.getSize()) {
            newPosition = board.getNextPosition(newPosition);
            currentPlayer.setPosition(newPosition);
        }
        System.out.println(currentPlayer.getName() + " rolled " + diceRoll + " and moved to " + currentPlayer.getPosition());

        if (currentPlayer.getPosition() == board.getSize()) {
            winner = currentPlayer;
        } else {
            currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
        }
    }

    private int rollDice() {
        return random.nextInt(6) + 1; // Rolls a number between 1 and 6
    }

    public boolean isGameOver() {
        return winner != null;
    }

    public void startGame() {
        System.out.println("Game started!");
        while (!isGameOver()) {
            playTurn();
        }
        System.out.println("\n" + winner.getName() + " wins the game!");
    }
}

public class SnakeAndLadderGame {
    public static void main(String[] args) {
        Game game = new Game(100); // Create a board of size 100
        game.addPlayer("Alice");
        game.addPlayer("Bob");

        // Adding snakes
        game.addSnake(16, 6);
        game.addSnake(47, 26);
        game.addSnake(49, 11);
        game.addSnake(56, 53);
        game.addSnake(62, 19);
        game.addSnake(64, 60);
        game.addSnake(87, 24);
        game.addSnake(93, 73);
        game.addSnake(95, 75);
        game.addSnake(98, 78);

        // Adding ladders
        game.addLadder(1, 38);
        game.addLadder(4, 14);
        game.addLadder(9, 31);
        game.addLadder(21, 42);
        game.addLadder(28, 84);
        game.addLadder(36, 44);
        game.addLadder(51, 67);
        game.addLadder(71, 91);
        game.addLadder(80, 100);

        // Start the game
        game.startGame();
    }
}
