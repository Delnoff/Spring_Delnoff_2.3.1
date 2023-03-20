package com.delnoff.server.service;

import com.delnoff.server.model.User;

import java.util.List;

public interface UserService {
    void create(User user);
    List<User> read();
    void update(long id, User user);
    void delete(long id);
    User showId(long id);
}
