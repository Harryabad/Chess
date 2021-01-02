package com.tests.harryabad.chess.engine.board;

import com.google.common.collect.Iterables;
import com.harryabad.chess.engine.board.Board;
import com.harryabad.chess.engine.board.BoardUtils;
import com.harryabad.chess.engine.board.Move;
import com.harryabad.chess.engine.pieces.Piece;
import com.harryabad.chess.engine.player.MoveTransition;
import com.harryabad.chess.engine.player.ai.MiniMax;
import com.harryabad.chess.engine.player.ai.MoveStrategy;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @project Chess
 * Created by Harry Abad on 01/01/2021
 */
public class TestBoard {


    @Test
    public void initialBoard() {

        final Board board = Board.createStandardBoard();
        assertEquals(board.currentPlayer().getLegalMoves().size(), 20);
        assertEquals(board.currentPlayer().getOpponent().getLegalMoves().size(), 20);
        assertFalse(board.currentPlayer().isInCheck());
        assertFalse(board.currentPlayer().isInCheckMate());
        assertFalse(board.currentPlayer().isCastled());
        //assertTrue(board.currentPlayer().isKingSideCastleCapable());
        //assertTrue(board.currentPlayer().isQueenSideCastleCapable());
        assertEquals(board.currentPlayer(), board.whitePlayer());
        assertEquals(board.currentPlayer().getOpponent(), board.blackPlayer());
        assertFalse(board.currentPlayer().getOpponent().isInCheck());
        assertFalse(board.currentPlayer().getOpponent().isInCheckMate());
        assertFalse(board.currentPlayer().getOpponent().isCastled());
        //assertTrue(board.currentPlayer().getOpponent().isKingSideCastleCapable());
        //assertTrue(board.currentPlayer().getOpponent().isQueenSideCastleCapable());
        //assertTrue(board.whitePlayer().toString().equals("White"));
        //assertTrue(board.blackPlayer().toString().equals("Black"));

    }
/*
    @Test
    public void testFoolsMate(){

        final Board board = Board.createStandardBoard(); //create standard board
        final MoveTransition t1 = board.currentPlayer()
                .makeMove(Move.MoveFactory.createMove(board, BoardUtils.getCoordinateAtPosition("f2"),
                        BoardUtils.getCoordinateAtPosition("f3")));
        //open with white f2 to f3
        assertTrue(t1.getMoveStatus().isDone());


        final MoveTransition t2 = t1.getTransitionBoard()
                .currentPlayer()
                .makeMove(Move.MoveFactory.createMove(t1.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("e7"),
                        BoardUtils.getCoordinateAtPosition("e5")));
        //next move, black e7 to e5
        assertTrue(t2.getMoveStatus().isDone());


        final MoveTransition t3 = t2.getTransitionBoard()
                .currentPlayer()
                .makeMove(Move.MoveFactory.createMove(board, BoardUtils.getCoordinateAtPosition("g2"),
                        BoardUtils.getCoordinateAtPosition("g4")));
        //white move g2 to g4
        assertTrue(t3.getMoveStatus().isDone());


        final MoveStrategy strategy = new MiniMax(4);

        final Move aiMove = strategy.execute(t3.getTransitionBoard());

        final Move bestMove = Move.MoveFactory.createMove(t3.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("d8"),
                BoardUtils.getCoordinateAtPosition("h4"));

        assertEquals(aiMove, bestMove);
    //can black find checkmate

    }
*/
}