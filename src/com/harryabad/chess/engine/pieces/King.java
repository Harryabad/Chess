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

public class King extends Piece {

    private final static int[]  CANDIDATE_MOVE_COORDINATE = { -9, -8, -7, -1, 1, 7, 8, 9};

    private final boolean isCastled;
    private final boolean kingSideCastleCapable;
    private final boolean queenSideCastleCapable;


    public King(final Alliance pieceAlliance,
                final int piecePosition,
                final boolean kingSideCastleCapable,
                final boolean queenSideCastleCapable) { //king constructor
        super(PieceType.KING, piecePosition, pieceAlliance, true);
        this.kingSideCastleCapable = kingSideCastleCapable;
        this.queenSideCastleCapable = queenSideCastleCapable;
        this.isCastled = false;
    }

    public King(final Alliance pieceAlliance,
                final int piecePosition,
                final boolean isFirstMove,
                final boolean isCastled,
                final boolean kingSideCastleCapable,
                final boolean queenSideCastleCapable) { //king constructor
        super(PieceType.KING, piecePosition, pieceAlliance, isFirstMove);
        this.isCastled = isCastled;
        this.kingSideCastleCapable = kingSideCastleCapable;
        this.queenSideCastleCapable = queenSideCastleCapable;
    }

    public boolean isCastled(){
        return this.isCastled;
    }

    public boolean isKingSideCastleCapable(){
        return this.kingSideCastleCapable;
    }


    public boolean isQueenSideCastleCapable(){
        return this.queenSideCastleCapable;
    }

    @Override
    public Collection<Move> calculateLegalMoves(Board board) {

        final List<Move> legalMoves = new ArrayList<>();



        for (final int currentCandidateOffset : CANDIDATE_MOVE_COORDINATE){

            final int candidateDestinationCoordinate = this.piecePosition + currentCandidateOffset;

            //edge cases
            if(isFirstColumnExclusion(this.piecePosition, currentCandidateOffset) ||
                    isEighthColumnExclusion(this.piecePosition, currentCandidateOffset)){
                continue;
            }

            //normal move case for empty Tile
            if(BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)){
                final Tile candidateDestinationTile = board.getTile(candidateDestinationCoordinate);
                //now we have tile

                if(!candidateDestinationTile.isTileOccupied()){ // if tile is not occupied, add legal move
                    legalMoves.add(new MajorMove(board, this, candidateDestinationCoordinate)); //uses this as referencing a piece we have made in this class
                }else{
                    // if tile is occupied we need to know the piece on that tile and whether it's white or black
                    final Piece pieceAtDestination = candidateDestinationTile.getPiece();
                    final Alliance pieceAlliance = pieceAtDestination.getPieceAlliance();

                    if (this.pieceAlliance != pieceAlliance){ //if piece at destination is not equal to piece being moved
                        legalMoves.add(new MajorAttackMove(board, this, candidateDestinationCoordinate, pieceAtDestination));
                    }
                }
            }
        }


        return ImmutableList.copyOf(legalMoves);
    }

    @Override
    public King movePiece(final Move move) {
        return new King(move.getMovedPiece().getPieceAlliance(),
                move.getDestinationCoordinate(),
                false, move.isCastlingMove(), false, false);
    }

    @Override
    public String toString(){
        return PieceType.KING.toString();
    }

    //create edge cases, where using offset rule does not work
    private static boolean isFirstColumnExclusion(final int currentPosition, final int candidateOffset){

        return BoardUtils.FIRST_COLUMN[currentPosition] && ((candidateOffset == -9) || (candidateOffset == -1) ||
                (candidateOffset == 7));
    }

    private static boolean isEighthColumnExclusion(final int currentPosition, final int candidateOffset){
        return BoardUtils.EIGHTH_COLUMN[currentPosition] && ((candidateOffset == -7) || (candidateOffset == 1) ||
                (candidateOffset == 9));
    }
}
