package org.zerock.board;

import org.zerock.PageRequest;
import org.zerock.PageResponse;

public interface BoardService {
    Long write(Board board);

    PageResponse<Board> getBoardList(PageRequest pageRequest);

    int getTotalCnt();

    Board getBoard(Long bno);

    void updateBoard(Board board);

    void removeBoard(Long bno);
}
