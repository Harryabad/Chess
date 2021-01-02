package com.harryabad.chess;

import com.harryabad.chess.engine.board.Board;
import com.harryabad.chess.gui.Table;

public class JChess {

    public static void main(String[] args){

        Board board = Board.createStandardBoard();

        System.out.println(board);

        Table.get().show();
    }
}
