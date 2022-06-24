package Util;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

//@Configuration
public class JDBCUtils {
    // 1. static型のメンバ変数を宣言
    private static DataSource ds;

    // 2. DBに接続するオブジェクトを定義
    static{
        // 設定ファイル中のデータをロード
        InputStream is = JDBCUtils.class.getClassLoader().getResourceAsStream("druid.properties");
        Properties p = new Properties();
        try {
            p.load(is);
            // データソースを取得する
            ds = DruidDataSourceFactory.createDataSource(p);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // データソースを取得する方法を定義する
//    @Bean
    public static DataSource getDataSource(){return ds;}

    // データに接続する方法を定義する
    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    // 資源を閉じる
    public static void close(Connection conn, Statement stmt, ResultSet rs){
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {}
        }

        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {}
        }

        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {}
        }
    }

    // 6.閉じる方法をリロード
    public static void close(Connection conn, Statement stmt) {
        close(conn, stmt, null);
    }
}
