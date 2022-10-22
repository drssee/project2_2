package org.zerock.board;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.stream.IntStream;

@ExtendWith(SpringExtension.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/**/root-context.xml")
class BoardControllerTest {

    @Autowired(required = false)
    private BoardService boardService;

    @Test
    public void writeTest() throws Exception {
        IntStream.rangeClosed(1,10).forEach(i -> {
            Board board = Board.builder()
                    .title("title "+i)
                    .writer("writer "+i)
                    .content("content "+i)
                    .build();
            System.out.println(boardService.write(board));
        });
    }

//    @Test
//    public void selectAllTest() throws Exception {
//        List<Board> boardList = boardService.getBoardList();
//        System.out.println("boardList = " + boardList);
//    }
}