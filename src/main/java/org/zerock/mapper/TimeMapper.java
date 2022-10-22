package org.zerock.mapper;

import org.apache.ibatis.annotations.Select;

public interface TimeMapper {
    @Select("SELECT now() FROM dual")
    String getTime();

    String getTime2();
}
