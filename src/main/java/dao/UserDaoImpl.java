package dao;

import Util.JDBCUtils;
import com.alibaba.druid.util.JdbcUtils;
import domain.User;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class UserDaoImpl implements UserDao {

    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public User findByUsername(String username) {
        User user = null;
        try {
            // 1. sqlを定義する
            String sql = "select * from tab_user when username = ?";
            // 2. sqlを実行する
            user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), username);
        } catch (Exception e){
    }
      return user;
}

    @Override
    public void save(User user) {
        // 1. sqlを定義する
        String sql = "insert into tab_user(username,password,name,birthday,sex,telephone,email,status,code)values(?,?,?,?,?,?,?,?,?)";
        // 2. sqlを実行する
        template.update(sql,user.getUsername(),
                user.getPassword(),
                user.getName(),
                user.getBirthday(),
                user.getSex(),
                user.getTelephone(),
                user.getEmail(),
                user.getStatus(),
                user.getCode());
    }
}
