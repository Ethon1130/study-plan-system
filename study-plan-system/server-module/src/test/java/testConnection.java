import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.group3.AppApplication;

import javax.sql.DataSource;
import java.sql.Connection;

//@SpringBootTest(classes = AppApplication.class)
public class testConnection {

    @Autowired
    private DataSource dataSource;

    @Test
    void testConnection() throws Exception {
        try (Connection conn = dataSource.getConnection()) {
            System.out.println("数据库连接成功: " + conn.getMetaData().getURL());
        }
    }
}