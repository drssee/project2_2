package org.zerock.persistence;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@ExtendWith(SpringExtension.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/**/root-context.xml")
public class ConnectionTest {
    @Autowired
    DataSource dataSource;
    @Autowired
    SqlSessionFactory sqlSessionFactory;

    @Test
    @DisplayName("DB연결 테스트")
    public void connectionTest() throws SQLException {
        Connection conn = dataSource.getConnection();
        Assertions.assertNotNull(conn);
    }

    @Test
    @DisplayName("sql세션으로 커넥션 얻어오기")
    public void testMyBatis(){
        SqlSession session = sqlSessionFactory.openSession();
        Assertions.assertNotNull(session);
        Connection conn = session.getConnection();
        Assertions.assertNotNull(conn);
        System.out.println("session = " + session);
        System.out.println("conn = " + conn);
    }
}
