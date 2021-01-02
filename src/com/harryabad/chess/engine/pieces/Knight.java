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

import static com.harryabad.chess.engine.board.Move.*;  //on demand static import for move

public class Knight extends Piece{

    /*
    Given Knight is on coord x, the first tile is 0 and final tile is 63,
    the knight will move up or down by the following amount of tiles (given they're legal moves)
     */
    private final static int[] CANDIDATE_MOVE_COORDINATES = { -17, -15, -10, -6, 6, 10, 15, 17}; //offsets || candidates 'legal' moves (destinations)

    public Knight(final Alliance pieceAlliance, final int piecePosition) { //Constructor for Knight Piece
        super(PieceType.KNIGHT, piecePosition, pieceAlliance, true);
    }

    public Knight(final Alliance pieceAlliance, final int piecePosition, final boolean isFirstMove) { //Constructor for Knight Piece
        super(PieceType.KNIGHT, piecePosition, pieceAlliance, isFirstMove);
    }

    @Override
    public Collection<Move> calculateLegalMoves(Board board) { //Method to override from Piece class

        final List<Move> legalMoves = new ArrayList<>(); // catch our legal moves

        for(final int currentCandidateOffset: CANDIDATE_MOVE_COORDINATES){  // for each of our candidates 'legal' moves (destinations)
            int candidateDestinationCoordinate; //declare candidate destination coord

            candidateDestinationCoordinate = this.piecePosition + currentCandidateOffset; // applies the offset to the current knight position

            if (BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)){ // if that tile is valid

                if(isFirstColumnExclusion(this.piecePosition, currentCandidateOffset) || // offset exclusions
                        isSecondColumnExclusion(this.piecePosition, currentCandidateOffset) ||
                        isSeventhColumnExclusion(this.piecePosition, currentCandidateOffset) ||
                        isEighthColumnExclusion(this.piecePosition, currentCandidateOffset)){
                    continue;
                    //this is for the exclusion where the rule breaks
                }

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
    public Knight movePiece(final Move move) {
        return new Knight(move.getMovedPiece().getPieceAlliance(), move.getDestinationCoordinate());
    }

    @Override
    public String toString(){
        return PieceType.KNIGHT.toString();
    }

    //create edge cases, where using offset rule does not work
    private static boolean isFirstColumnExclusion(final int currentPosition, final int candidateOffset){

        return BoardUtils.FIRST_COLUMN[currentPosition] && ((candidateOffset == -17) || (candidateOffset == -10) ||
                (candidateOffset == 6) || (candidateOffset == 15));
        //introducing an array of booleans, first_column of board.
        // if current position is in fist column and candidate offset is -17,-10, 6, 15 (where rule breaks)
        // dont want to move piece to impossible destination
    }

    private static boolean isSecondColumnExclusion(final int currentPosition, final int candidateOffset){
        return BoardUtils.SECOND_COLUMN[currentPosition] && ((candidateOffset == -10) || (candidateOffset == 6));
    }

    private static boolean isSeventhColumnExclusion(final int currentPosition, final int candidateOffset){
        return BoardUtils.SEVENTH_COLUMN[currentPosition] && ((candidateOffset == 10) || (candidateOffset == -6));
    }

    private static boolean isEighthColumnExclusion(final int currentPosition, final int candidateOffset){
        return BoardUtils.EIGHTH_COLUMN[currentPosition] && ((candidateOffset == 17) || (candidateOffset == 10) ||
                (candidateOffset == -6) || (candidateOffset == -15));
    }

}
