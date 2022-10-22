package org.zerock.board;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.zerock.PageRequest;

import java.util.Optional;

@Controller
@RequestMapping("board")
@Slf4j
public class BoardController {

    private final BoardService boardService;

    @Autowired
    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping("list")
    public String boardList(Optional<Integer> page, Optional<Integer> size, Model model) {
        log.info("/board/list ... get");
        PageRequest pageRequest = PageRequest.builder()
                .page(page.orElse(1))
                .size(size.orElse(10))
                .build();
        model.addAttribute("pageResponse",boardService.getBoardList(pageRequest));
        return "boardList";
    }

    @GetMapping("write")
    public String boardWrite1(int page,Model model) {
        log.info("/board/write .... get");
        model.addAttribute("page",page);
        return "write";
    }

    @PostMapping("write")
    public String boardWrite2(Board board,int page) {
        log.info("/board/write .... post");
        log.info(board.toString());
        boardService.write(board);
        return "redirect:list?page="+page;
    }

    @GetMapping("read")
    public String boardRead1(Long bno,int page,Model model) {
        log.info("/board/read .... get");
        model.addAttribute("board",boardService.getBoard(bno));
        model.addAttribute("page",page);
        return "read";
    }

    @GetMapping("modify")
    public String boardModify1(Long bno,int page,Model model) {
        log.info("/board/modify .... get");
        model.addAttribute("board",boardService.getBoard(bno));
        model.addAttribute("page",page);
        return "modify";
    }

    @PostMapping("update")
    public String boardUpdate1(Board board,int page) {
        log.info("/board/update .... post");
        boardService.updateBoard(board);
        return "redirect:list?page="+page;
    }

    @GetMapping("delete")
    public String boardDelete1(Long bno,int page) {
        log.info("/board/modify .... get");
        boardService.removeBoard(bno);
        return "redirect:list?page="+page;
    }
}
