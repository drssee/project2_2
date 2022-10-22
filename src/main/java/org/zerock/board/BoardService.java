package org.zerock.board;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zerock.PageRequest;
import org.zerock.PageResponse;
import org.zerock.mapper.BoardMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BoardService {

    @Autowired(required = false)
    private BoardMapper boardMapper;

    Long write(Board board) {
        return boardMapper.insertBoard(board);
    }

    PageResponse<Board> getBoardList(PageRequest pageRequest) {
        Map<String,Integer> paging = new HashMap<>();
        paging.put("skip",pageRequest.getSkip());
        paging.put("size",pageRequest.getSize());
        List<Board> boardList = boardMapper.selectBoardList(paging);
        return PageResponse.<Board>withAll()
                .pageRequest(pageRequest)
                .pageList(boardList)
                .total(getTotalCnt())
                .build();
    }

    int getTotalCnt() {
        return boardMapper.getTotalCnt();
    }

    Board getBoard(Long bno) {
        return boardMapper.selectBoard(bno);
    }

    void updateBoard(Board board) {
        boardMapper.updateBoard(board);
    }

    void removeBoard(Long bno) {
        boardMapper.deleteBoard(bno);
    }
}
