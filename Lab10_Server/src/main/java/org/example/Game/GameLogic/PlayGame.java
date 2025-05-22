package org.example.Game.GameLogic;

import org.example.Game.GameTable.Board;
import org.example.Game.GameTable.Cell;
import org.example.Game.GameTable.MoveValidator;
import org.example.Game.Players.AIHexPlayer;
import org.example.Game.Players.Player;

import java.io.PrintWriter;

public class PlayGame {
    private final Board board;
    private Player redPlayer;
    private Player bluePlayer;
    private AIHexPlayer aiPlayer;
    private Cell currentTurn;
    private boolean isOver = false;
    private Player winner;
    private PrintWriter redWriter;
    private PrintWriter blueWriter;
    private int lastHumanRow = -1;
    private int lastHumanCol = -1;

    public PlayGame(Player redPlayer, Player bluePlayer, int boardSize){
        this.board = new Board(boardSize);
        this.redPlayer = redPlayer;
        this.bluePlayer = bluePlayer;
        this.currentTurn = Cell.RED;
        this.winner = null;
    }

    public PlayGame(Player redPlayer, AIHexPlayer bluePlayer, int boardSize){
        this.board = new Board(boardSize);
        this.redPlayer = redPlayer;
        this.aiPlayer = bluePlayer;
        this.currentTurn = Cell.RED;
        this.winner = null;
    }

    public boolean makeMove(Player player, int row, int col){
        if((isOver) || (player.getColor() != currentTurn)){
            return false;
        }

        if(player.getColor() == Cell.RED){
            lastHumanRow = row;
            lastHumanCol = col;
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

            if((isAIvsHuman()) && (currentTurn == Cell.BLUE) && (aiPlayer != null)){
                int[] aiMove = aiPlayer.chooseMove(board, lastHumanRow,lastHumanCol);
                board.setCell(aiMove[0], aiMove[1], currentTurn);
                broadcastMove(aiMove[0], aiMove[1], currentTurn);

                if(WinChecker.checkWin(board, currentTurn)){
                    isOver = true;
                    winner = new Player(aiPlayer.getNameAI(), null, Cell.BLUE);
                } else {
                    currentTurn = Cell.RED;
                }
            }
        }

        return true;
    }

    private boolean isAIvsHuman() {
        return aiPlayer != null;
    }

    public void broadcastMove(int row, int col, Cell cell){
        String message = "ok " + row + " " + col + " " + cell.name().toLowerCase();
        String nextTurnMessage = isOver ? "Game over! Winner: " + winner.getName()
                : "Next turn: " + currentTurn.name().toLowerCase();

        if(redWriter != null){
            redWriter.println(message);
            redWriter.println(nextTurnMessage);
            redWriter.println();
        }

        if(blueWriter != null){
            blueWriter.println(message);
            blueWriter.println(nextTurnMessage);
            blueWriter.println();
        }
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

    public void setRedWriter(PrintWriter Writer){
        redWriter = Writer;
    }

    public void setBlueWriter(PrintWriter Writer){
        blueWriter = Writer;
    }

    public void setBluePlayer(Player bluePlayer) {
        if (this.bluePlayer == null) {
            this.bluePlayer = bluePlayer;
        }
    }
}
