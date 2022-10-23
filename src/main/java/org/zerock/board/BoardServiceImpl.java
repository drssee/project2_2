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
public class BoardServiceImpl implements BoardService {

    @Autowired(required = false) // boardmapper 구현체는 스프링 구동시 만들어 지기 때문에 현재는 없어서
    private BoardMapper boardMapper;

    @Override
    public Long write(Board board) {
        return boardMapper.insertBoard(board);
    }

    @Override
    public PageResponse<Board> getBoardList(PageRequest pageRequest) {
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

    @Override
    public int getTotalCnt() {
        return boardMapper.getTotalCnt();
    }

    @Override
    public Board getBoard(Long bno) {
        return boardMapper.selectBoard(bno);
    }

    @Override
    public void updateBoard(Board board) {
        boardMapper.updateBoard(board);
    }

    @Override
    public void removeBoard(Long bno) {
        boardMapper.deleteBoard(bno);
    }
}
