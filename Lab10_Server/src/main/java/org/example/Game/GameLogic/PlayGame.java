package org.example.Game.GameLogic;

import org.example.Game.GameTable.Board;
import org.example.Game.GameTable.Cell;
import org.example.Game.GameTable.MoveValidator;
import org.example.Game.Player;

public class PlayGame {
    private final Board board;
    private final Player redPlayer;
    private final Player bluePlayer;
    private Cell currentTurn;
    private boolean isOver = false;
    private Player winner;

    public PlayGame(Player redPlayer, Player bluePlayer, int boardSize){
        this.board = new Board(boardSize);
        this.redPlayer = redPlayer;
        this.bluePlayer = bluePlayer;
        this.currentTurn = Cell.RED;
        this.winner = null;
    }

    public boolean makeMove(Player player, int row, int col){
        if(isOver){
            return false;
        }

        if(player.getColor() != currentTurn){
            return false;
        }

        if(!MoveValidator.isValid(board, row, col)){
            return false;
        }

        board.setCell(row, col, currentTurn);

        if(WinChecker.checkWin(board, currentTurn)){
            isOver = true;
            winner = player;
        } else {
            currentTurn = (currentTurn == Cell.RED) ? Cell.BLUE : Cell.RED;
        }

        return true;
    }

    public boolean hasEnded(){
        return isOver;
    }

    public Player getWinner(){
        return winner;
    }

    public Cell getCurrentTurn(){
        return currentTurn;
    }

    public Board getBoard(){
        return board;
    }

    public Player getRedPlayer(){
        return redPlayer;
    }

    public Player getBluePlayer(){
        return bluePlayer;
    }
}
