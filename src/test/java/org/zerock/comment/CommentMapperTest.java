package org.zerock.comment;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.zerock.mapper.CommentMapper;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/**/root-context.xml")
public class CommentMapperTest {

    @Autowired(required = false)
    CommentMapper commentMapper;

    @Test
    public void count() throws Exception {
        commentMapper.deleteAll(1);
        assertTrue(commentMapper.count(1)==0);
    }

    @Test
    public void delete() throws Exception {
        commentMapper.deleteAll(1);
        CommentVO commentDto = new CommentVO(1, 0, "comment", "asdf");
        assertTrue(commentMapper.insert(commentDto)==1);
        assertTrue(commentMapper.count(1)==1);
    }

    @Test
    public void insert() throws Exception {
        commentMapper.deleteAll(1);
        CommentVO commentVO = new CommentVO(1, 0, "comment", "asdf");
        assertTrue(commentMapper.insert(commentVO)==1);
        assertTrue(commentMapper.count(1)==1);

        commentVO = new CommentVO(1, 0, "comment", "asdf");
        assertTrue(commentMapper.insert(commentVO)==1);
        assertTrue(commentMapper.count(1)==2);
    }

    @Test
    public void selectAll() throws Exception {
        commentMapper.deleteAll(1);
        CommentVO commentVO = new CommentVO(1, 0, "comment", "asdf");
        assertTrue(commentMapper.insert(commentVO)==1);
        assertTrue(commentMapper.count(1)==1);

        List<CommentVO> list = commentMapper.selectAll(1);
        assertTrue(list.size()==1);

        commentVO = new CommentVO(1, 0, "comment", "asdf");
        assertTrue(commentMapper.insert(commentVO)==1);
        assertTrue(commentMapper.count(1)==2);

        list = commentMapper.selectAll(1);
        assertTrue(list.size()==2);
    }

    @Test
    public void select() throws Exception {
        commentMapper.deleteAll(1);
        CommentVO commentVO = new CommentVO(1, 0, "comment", "asdf");
        assertTrue(commentMapper.insert(commentVO)==1);
        assertTrue(commentMapper.count(1)==1);

        List<CommentVO> list = commentMapper.selectAll(1);
        String comment = list.get(0).getComment();
        String commenter = list.get(0).getCommenter();
        assertTrue(comment.equals(commentVO.getComment()));
        assertTrue(commenter.equals(commentVO.getCommenter()));
    }

    @Test
    public void update() throws Exception {
        commentMapper.deleteAll(1);
        CommentVO commentVO = new CommentVO(1, 0, "comment", "asdf");
        assertTrue(commentMapper.insert(commentVO)==1);
        assertTrue(commentMapper.count(1)==1);

        List<CommentVO> list = commentMapper.selectAll(1);
        commentVO.setCno(list.get(0).getCno());
        commentVO.setComment("comment2");
        assertTrue(commentMapper.update(commentVO)==1);

        list = commentMapper.selectAll(1);
        String comment = list.get(0).getComment();
        String commenter = list.get(0).getCommenter();
        assertTrue(comment.equals(commentVO.getComment()));
        assertTrue(commenter.equals(commentVO.getCommenter()));
    }
}
