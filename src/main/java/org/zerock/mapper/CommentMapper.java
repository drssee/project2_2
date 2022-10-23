package org.zerock.mapper;

import org.zerock.comment.CommentVO;

import java.util.List;
import java.util.Map;

public interface CommentMapper {
    void deleteAll(int bno);
    int count(int bno);
    int delete(Map map);
    int insert(CommentVO commentVO);
    List<CommentVO> selectAll(int bno);
    CommentVO select(int cno);
    int update(CommentVO commentVO);
}
