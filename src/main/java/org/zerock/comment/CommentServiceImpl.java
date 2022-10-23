package org.zerock.comment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zerock.mapper.CommentMapper;

import java.util.List;
import java.util.Map;

@Service
public class CommentServiceImpl implements CommentService{

    @Autowired(required = false) // commentmapper의 구현체는 스프링 구동시 만들어 지기 때문에 현재는 없어서
    CommentMapper commentMapper;

    @Override
    public void removeAll(int bno) {
        commentMapper.deleteAll(bno);
    }

    @Override
    public int count(int bno) {
        return commentMapper.count(bno);
    }

    @Override
    public int remove(Map map) {
        return commentMapper.delete(map);
    }

    @Override
    public int wrtie(CommentVO commentVO) {
        return commentMapper.insert(commentVO);
    }

    @Override
    public List<CommentVO> getComments(int bno) {
        return commentMapper.selectAll(bno);
    }

    @Override
    public CommentVO getComment(int cno) {
        return commentMapper.select(cno);
    }

    @Override
    public int update(CommentVO commentVO) {
        return commentMapper.update(commentVO);
    }
}
