package dao;

import domain.User;

public interface UserDao {

    // usernameでuser情報を調べる
    public User findByUsername(String username);

    // 新規userを保存
    public void save(User user);

}
