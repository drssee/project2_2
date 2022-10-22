package org.zerock.mapper;

import org.zerock.board.Board;

import java.util.List;
import java.util.Map;

public interface BoardMapper {
    Long insertBoard(Board board);
    List<Board> selectBoardList(Map<String,Integer> paging);
    Board selectBoard(Long bno);
    void updateBoard(Board board);
    void deleteBoard(Long bno);
    int getTotalCnt();
}
