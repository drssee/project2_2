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

    //****************댓글 작성하는법****************

    //1.해당 게시글(bno)불러오면서 댓글(해당 게시글 bno에 맞는)리스트도 같이 불러옴

    //2.입력폼에 댓글을 작성하고 전송버튼을 누르면 ajax 메서드가 요청 헤드 post , 요청 바디에 JSON.stringify(js댓글객체) + 쿼리로 bno 을 담아서 http요청 메시지를 보냄

    //3.controller의 postmapping 메서드에서 jackson databind를 이용해서 매개변수@requestbody객체 에 자바 객체로 변환후 전달받고

    //4.해당 메서드에서 객체에 setbno나 setcommenter등 백엔드에서 처리할 작업 + db에 insert 이후

    //5.@responsebody가 붙어있는 해당 메서드에서 리턴한 string->new responseentity<str>(str,상태코드)는 http응답 메시지 바디에 담겨서

    //6.요청을 한 클라이언트에게 도착해서 성공시 댓글 리스트 다시 불러오기 or 실패시 alert



    //****************대댓글(답글) 작성하는법****************

    //1.대댓글(답글) 작성 전용 폼을 만들어서

    //2.댓글 작성과 비슷하지만 다른점이 , pcno의 값도 가져와 js객체에 담아야 한다는 것과 기존의 댓글을 가져오는 쿼리문을 수정해야함

    //3.selectAll if(pcno=0,cno,pcno) order by pcno , cno + controller의 post method에 넘어온 pcno가 없을시 null이 아닌 0이 되게(구분위함)
    // pcno값이 없는(0인) 댓글들 <- 부모댓글들은 pcno=cno 값을 넣어주고 pcno , cno 순서로 정렬

    //4.대댓글과 부모댓글 구분을 위해 뷰에 if(pcno!=cno) 조건으로 구분 해줌

    //5.나머지 update delete는 동일
}
