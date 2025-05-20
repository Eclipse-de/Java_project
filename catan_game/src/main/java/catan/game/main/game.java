package catan.game.main;

import java.util.List;

import catan.game.gameboard.board;

public class game {
    private List<player> players;
    private board gameBoard;

    public game(List<player> players, board gameBoard) {
        this.players = players;
        this.gameBoard = gameBoard;
    }

    public void startGame() {
        System.out.println("Game started with " + players.size() + " players.");
    }

    public List<player> getPlayers() {
        return players;
    }

    public board getBoard() {
        return gameBoard;
    }
}
