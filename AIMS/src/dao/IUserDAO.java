package dao;

import entity.user.User;

import java.util.List;

public interface IUserDAO{
    List<User> getAll();
    User getUserById(int id);
    void createUser(User user);
    void updateUserById(User user);
    void deleteUserById(int id);
}
