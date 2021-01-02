package com.harryabad.chess.engine.player.ai;

import com.harryabad.chess.engine.board.Board;
import com.harryabad.chess.engine.pieces.Piece;
import com.harryabad.chess.engine.player.Player;

/**
 * @project Chess
 * Created by Harry Abad on 01/01/2021
 */
public final class StandardBoardEvaluator implements BoardEvaluator { //final classes are non extensible

    private static final int CHECK_BONUS = 50;
    private static final int CHECK_MATE_BONUS = 10000;
    private static final int DEPTH_BONUS = 100;
    private static final int CASTLE_BONUS = 60;

    @Override
    public int evaluate(final Board board, final int depth) {
        return scorePlayer(board, board.whitePlayer(), depth) - scorePlayer(board, board.blackPlayer(), depth);
    }

    private int scorePlayer(final Board board, final Player player, final int depth) {
        return pieceValue(player)
                + mobility(player)
                + check(player)
                + checkmate(player, depth)
                + castled(player);
        // + checkmate, check, castled, mobility...
    }

    private static int castled(Player player) {
        return player.isCastled() ? CASTLE_BONUS : 0;
    }

    private static int checkmate(Player player, int depth) {
        //if we find checkmate before our depth is 0, return an even bigger bonus
        return player.getOpponent().isInCheckMate() ? CHECK_MATE_BONUS * depthBonus(depth) : 0;
    }

    private static int depthBonus(int depth) {
        return depth == 0 ? 1 : DEPTH_BONUS * depth;
    }

    private static int check(final Player player) {
        return player.getOpponent().isInCheck() ? CHECK_BONUS : 0;
    }

    private static int mobility(final Player player) {
        //for given board we're looking at, how many options do they have
        return player.getLegalMoves().size();
    }

    private static int pieceValue(final Player player) {
        int pieceValueScore = 0;
        for (final Piece piece : player.getActivePieces()) {
            pieceValueScore += piece.getPieceValue();
        }
        return pieceValueScore;
    }
}
