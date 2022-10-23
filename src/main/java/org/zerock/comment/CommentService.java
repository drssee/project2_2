package org.zerock.comment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zerock.mapper.BoardMapper;

import java.util.List;
import java.util.Map;

public interface CommentService {

    void removeAll(int bno);
    int count(int bno);
    int remove(Map map);
    int wrtie(CommentVO commentVO);
    List<CommentVO> getComments(int bno);
    CommentVO getComment(int cno);
    int update(CommentVO commentVO);
}
