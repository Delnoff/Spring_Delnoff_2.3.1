package com.delnoff.server.service;

import com.delnoff.server.DAO.UserDao;
import com.delnoff.server.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImp implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    @Transactional
    public void create(User user) {
        userDao.create(user);
    }

    @Override
    @Transactional
    public List<User> read() {
        return userDao.read();
    }

    @Override
    @Transactional
    public void update(long id, User user) {
        userDao.update(id,user);
    }

    @Override
    @Transactional
    public void delete(long id) {
        userDao.delete(id);
    }

    @Override
    @Transactional
    public User showId(long id) {
        return userDao.showId(id);
    }
}
