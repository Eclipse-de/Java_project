package catan.game.main;

import java.util.List;

import catan.game.gameboard.board;

public class game {
    private final List<Player> players;
    private final board gameBoard;

    public game(final List<Player> players, final board gameBoard) {
        this.players = players;
        this.gameBoard = gameBoard;
    }

    public void startGame() {
        System.out.println("Game started with " + players.size() + " players.");
    }

    public List<Player> getPlayers() {
        return players;
    }

    public board getBoard() {
        return gameBoard;
    }
}
