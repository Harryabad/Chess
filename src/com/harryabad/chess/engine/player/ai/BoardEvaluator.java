package com.harryabad.chess.engine.player.ai;

import com.harryabad.chess.engine.board.Board;

/**
 * @project Chess
 * Created by Harry Abad on 01/01/2021
 */
public interface BoardEvaluator   {

    /*
    if positive white is winning, if negative black is winning
     */
    int evaluate(Board board, int depth);
}
