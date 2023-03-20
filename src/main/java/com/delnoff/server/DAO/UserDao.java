package com.delnoff.server.DAO;

import com.delnoff.server.model.User;

import java.util.List;

public interface UserDao {
    void create(User user);
    List<User> read();
    void update(long id, User user);
    void delete(long id);
    User showId(long id);
}
