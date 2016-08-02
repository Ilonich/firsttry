package ru.ilonych.testtask.service;

import ru.ilonych.testtask.entity.User;

import java.util.Calendar;
import java.util.List;

/**
 * Created by Никола on 26.07.2016.
 */

public interface UserService {
    //public List<User> showAllUsers();

    public Integer create(User user);

    public User update(User user);

    public List<User> findUserById(int id);

    public void delete(User user);

    public List<User> usersPage(String params);

    public Number usersTotal();

    public List<User> findUsersByName(String params);

    public Number countUsersWithName(String name);

    public List<User> findUsersByAgeRange(String params);

    public Number countUsersWithAgeRange(String params);

    public List<User> findUsersByDateRange(String params);

    public Number countUsersWithDateRange(String params);

    public List<User> findUsersByAdmin(String params);

    public Number countUsersWithAdmin(boolean adm);
}
