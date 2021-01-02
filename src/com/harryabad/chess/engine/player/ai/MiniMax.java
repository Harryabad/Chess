package com.harryabad.chess.engine.player.ai;

import com.harryabad.chess.engine.board.Board;
import com.harryabad.chess.engine.board.Move;
import com.harryabad.chess.engine.player.MoveTransition;

/**
 * @project Chess
 * Created by Harry Abad on 01/01/2021
 */
public class MiniMax implements MoveStrategy {

    private final BoardEvaluator boardEvaluator;
    private final int searchDepth;

    public MiniMax(final int searchDepth) {
        this.boardEvaluator = new StandardBoardEvaluator();
        this.searchDepth = searchDepth;
    }

    @Override
    public String toString(){
        return "MiniMax";
    }

    @Override
    public Move execute(Board board) {
        //execute will return the best move

        final long starTime = System.currentTimeMillis();

        Move bestMove = null;

        int highestSeenValue = Integer.MIN_VALUE;
        int lowestSeenValue = Integer.MAX_VALUE;
        int currentValue;

        System.out.println(board.currentPlayer().getAlliance().toString() + " AI is THINKING with depth = " + this.searchDepth);

        int numMoves = board.currentPlayer().getLegalMoves().size();

        for(final Move move : board.currentPlayer().getLegalMoves()){

            final MoveTransition moveTransition = board.currentPlayer().makeMove(move);
            if(moveTransition.getMoveStatus().isDone()){ //successfully made move

                currentValue = board.currentPlayer().getAlliance().isWhite() ?    //if board you came from was white
                        min(moveTransition.getTransitionBoard(), this.searchDepth -1) :  //next move will be a minimizing move
                        max(moveTransition.getTransitionBoard(), this.searchDepth -1); // else if was black, maximizing move

                if(board.currentPlayer().getAlliance().isWhite() && currentValue >= highestSeenValue){
                    highestSeenValue = currentValue;
                    bestMove = move;
                }else if(board.currentPlayer().getAlliance().isBlack() && currentValue <= lowestSeenValue){
                    lowestSeenValue = currentValue;
                    bestMove = move;
                }
            }
        }
        final long executionTime = System.currentTimeMillis() - starTime;
        return bestMove;
    }

    public int min(final Board board, final int depth){
        //PROCESS OF MINIMIZING
        if(depth == 0 || isEndGameScenario(board)){
            return this.boardEvaluator.evaluate(board, depth);
        }
        int lowestSeenValue = Integer.MAX_VALUE; // set to make number and try final the lowest in the loop
        for(final Move move: board.currentPlayer().getLegalMoves()){ // go through players possible moves
            final MoveTransition moveTransition = board.currentPlayer().makeMove(move); // make each move
            if (moveTransition.getMoveStatus().isDone()){
                final int currentValue = max(moveTransition.getTransitionBoard(), depth -1); //make moves and score them
                if(currentValue <= lowestSeenValue){ // return the lowest value of that
                    lowestSeenValue = currentValue;
                }
            }
        }
        return lowestSeenValue;
    }

    private static boolean isEndGameScenario(final Board board){
        return board.currentPlayer().isInCheckMate() ||
                board.currentPlayer().isInStaleMate();
    }

    public int max(final Board board, final int depth){
        //PROCESS OF MAXIMIZING
        if(depth == 0 || isEndGameScenario(board)){ /* || gameOver*/
            return this.boardEvaluator.evaluate(board, depth);
        }
        int highestSeenValue = Integer.MIN_VALUE; // set to make number and try final the highest in the loop
        for(final Move move: board.currentPlayer().getLegalMoves()){ // go through players possible moves
            final MoveTransition moveTransition = board.currentPlayer().makeMove(move); // make each move
            if (moveTransition.getMoveStatus().isDone()){
                final int currentValue = min(moveTransition.getTransitionBoard(), depth -1); //make moves and score them
                if(currentValue >= highestSeenValue){ // return the highest value of that
                    highestSeenValue = currentValue;
                }
            }
        }
        return highestSeenValue;
    }
}
