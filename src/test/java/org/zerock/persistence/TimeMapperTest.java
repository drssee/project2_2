package org.zerock.persistence;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.zerock.mapper.TimeMapper;

@ExtendWith(SpringExtension.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/**/root-context.xml")
public class TimeMapperTest {

    @Autowired(required = false)
    TimeMapper timeMapper;

    @Test
    public void testGetTime(){
        System.out.println(timeMapper.getClass().getName()); //timeMapper를 구현한 가짜proxy객체를 만들어 구현후 사용?
        String time1 = timeMapper.getTime(); //어노테이션 기반이라 mybatis-scan으로 작동했음
        String time2 = timeMapper.getTime2(); //xml기반이라 root의 sqlsessionfactory에 mapper.xml의 경로를 설정해줘야함
        Assertions.assertEquals(time1,time2);
    }
}
