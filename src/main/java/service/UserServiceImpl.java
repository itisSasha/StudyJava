package service;

import dao.UserDao;
import dao.UserDaoImpl;
import domain.User;

public class UserServiceImpl implements UserService {
    private UserDao userDao = new UserDaoImpl();

    @Override
    public boolean regist(User user) {
        // usernameでuser情報を確認する
        User u = userDao.findByUsername(user.getUsername());
        // uがnullかを判断
        if(u != null){
            // 登録は失敗
            return false;
        }

        userDao.save(user);
        return true;
    }

    @Override
    public boolean active(String code) {
        return false;
    }

    @Override
    public User login(User user) {
        return null;
    }
}
