package com.harryabad.chess.engine.pieces;

import com.google.common.collect.ImmutableList;
import com.harryabad.chess.engine.Alliance;
import com.harryabad.chess.engine.board.Board;
import com.harryabad.chess.engine.board.BoardUtils;
import com.harryabad.chess.engine.board.Move;
import com.harryabad.chess.engine.board.Tile;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.harryabad.chess.engine.board.Move.*;

public class Bishop extends Piece{

    private final static int[] CANDIDATE_MOVE_VECTOR_COORDINATES = {-9, -7, 7, 9 };

    public Bishop(final Alliance pieceAlliance, final int piecePosition) {
        super(PieceType.BISHOP, piecePosition, pieceAlliance, true);
    }

    public Bishop(final Alliance pieceAlliance, final int piecePosition, final boolean isFirstMove) {
        super(PieceType.BISHOP, piecePosition, pieceAlliance, isFirstMove);
    }

    @Override
    public Collection<Move> calculateLegalMoves(final Board board) {

        final List<Move> legalMoves = new ArrayList<>();

        for (final int candidateCoordinateOffset: CANDIDATE_MOVE_VECTOR_COORDINATES) { //loop through each one of the vectors

            int candidateDestinationCoordinate = this.piecePosition; //candidate we want to consider starts at current positon

            while (BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) { //make sure it's valid, not gone off edge of board

                if(isFirstColumnExclusion(candidateDestinationCoordinate, candidateCoordinateOffset) ||
                    isEighthColumnExclusion(candidateDestinationCoordinate, candidateCoordinateOffset)){
                    break;
                }

                candidateDestinationCoordinate += candidateCoordinateOffset; //apply offset to start position

                if (BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
                    //same code from knight class
                    final Tile candidateDestinationTile = board.getTile(candidateDestinationCoordinate);
                    //now we have tile

                    if (!candidateDestinationTile.isTileOccupied()) { // if tile is not occupied, add legal move
                        legalMoves.add(new MajorMove(board, this, candidateDestinationCoordinate)); //uses this as referencing a piece we have made in this class
                        //if unoccupied is a normal move (major move)
                    } else {
                        // if tile is occupied we need to know the piece on that tile and whether it's white or black
                        final Piece pieceAtDestination = candidateDestinationTile.getPiece();
                        final Alliance pieceAlliance = pieceAtDestination.getPieceAlliance();

                        if (this.pieceAlliance != pieceAlliance) { //if piece at destination is not equal to piece being moved
                            legalMoves.add(new MajorAttackMove(board, this, candidateDestinationCoordinate, pieceAtDestination));
                            //if move is occupied, will be an attack move
                        }
                        break; //only change from knight class
                        //purpose of break is to stop looking down the vector once a piece is in the way
                        // the loop keeps checking for a valid square until no more
                    }
                }
            }
        }

        return ImmutableList.copyOf(legalMoves);
    }

    @Override
    public Bishop movePiece(final Move move) {
        return new Bishop(move.getMovedPiece().getPieceAlliance(), move.getDestinationCoordinate());
    }

    @Override
    public String toString(){
        return PieceType.BISHOP.toString();
    }

    //where rule falls apart and no longer works
    private static boolean isFirstColumnExclusion(final int currentPosition, final int candidateOffset){
        return BoardUtils.FIRST_COLUMN[currentPosition] && (candidateOffset == -9 || candidateOffset == 7);
    }

    private static boolean isEighthColumnExclusion(final int currentPosition, final int candidateOffset){
        return BoardUtils.EIGHTH_COLUMN[currentPosition] && (candidateOffset == -7 || candidateOffset == 9);
    }
}
