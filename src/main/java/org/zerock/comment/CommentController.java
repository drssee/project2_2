package org.zerock.comment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class CommentController {

    private final CommentService commentService;
    private final String COMMENTER = "작성자"; //테스트용 commenter // 원래는 session에서 id를 가져와야함

    @Autowired //생략 가능
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/comments") // /comments?bno=45 GET <-- 해당 게시판 글에 있는 모든 댓글 리스트를 가져오기
    @ResponseBody // 댓글은 비동기방식으로 구현할 예정이라 responsebody 붙임
    public ResponseEntity<List<CommentVO>> readAll(Integer bno) {
        try {
            // 응답코드 test
//            throw new Exception("고의 에러 발생");

//        return commentService.getComments(bno); // 자바 객체 리스트를 jackson databind 가 json을 거쳐 js객체 리스트로 변환해줌
            return new ResponseEntity<List<CommentVO>>(commentService.getComments(bno),HttpStatus.OK); // 응답 코드(200)를 지정해줄 수 있음
        } catch (Exception e) {
            return new ResponseEntity<List<CommentVO>>(HttpStatus.BAD_REQUEST); // 응답 코드(400)를 지정해줄 수 있음
        }
    }

    @PostMapping("/comments") // /comments?bno=45 POST <--해당 게시물번호에 댓글쓰기
    @ResponseBody
    public ResponseEntity<String> write(@RequestBody CommentVO commentVO , Integer bno){
        try {
            System.out.println("commentVO!!!!!!!! = " + commentVO);
            commentVO.setCommenter(COMMENTER);
            commentVO.setBno(bno);
            if(commentVO.getPcno()==null){
                commentVO.setPcno(0);
            }
            int rowCnt = commentService.wrtie(commentVO);
            if(rowCnt!=1){
                throw new Exception("write failed");
            }
            return new ResponseEntity<String>("WRT_OK",HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>("WRT_ERR",HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/comments/{cno}") // /comments/10 <-- 삭제할 댓글 번호
    @ResponseBody
    public ResponseEntity<String> delete(@PathVariable Integer cno, Integer bno) { //cno는 쿼리스트링이 아닌 REST 방식의 리소스에 포함되므로 {cno},@PathVariable 붙여줌
        try {
            Map map = new HashMap<>();
            map.put("cno",cno);
            map.put("bno",bno);
            map.put("commenter",COMMENTER);
            int rowCnt = commentService.remove(map);
            if(rowCnt!=1){
                throw new Exception("delete failed");
            }
            return new ResponseEntity<String>("DEL_OK",HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>("DEL_ERR",HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/comments/{cno}") // /comments/{46} PATCH <--해당 게시물번호에 있는 cno댓글 업데이트하기
    @ResponseBody
    public ResponseEntity<String> modify(@PathVariable Integer cno, @RequestBody CommentVO commentVO){
        try {
            System.out.println("commentVO!!!!!!!! = " + commentVO);
            commentVO.setCno(cno);
            commentVO.setCommenter(COMMENTER);
            int rowCnt = commentService.update(commentVO);
            if(rowCnt!=1){
                throw new Exception("modify failed");
            }
            return new ResponseEntity<String>("MOD_OK",HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>("MOD_ERR",HttpStatus.BAD_REQUEST);
        }
    }
}
