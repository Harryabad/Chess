package com.harryabad.chess.engine.player.ai;

import com.harryabad.chess.engine.board.Board;
import com.harryabad.chess.engine.board.Move;

/**
 * @project Chess
 * Created by Harry Abad on 01/01/2021
 */
public interface MoveStrategy {

    Move execute(Board board);
}
