package org.zerock.board;

import lombok.Builder;
import lombok.Data;

import java.util.Date;
@Data
@Builder
public class Board {
    private Long bno;
    private String title;
    private String writer;
    private String content;
    private Date regDate;
    private Date modDate;
}
