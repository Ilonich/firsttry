package ru.ilonych.testtask.daos;

import ru.ilonych.testtask.entity.User;

import java.util.Calendar;
import java.util.List;

/**
 * Created by Никола on 26.07.2016.
 */
public interface UserDAO {
    /*public List<User> showAllUsers();*/

    public Integer create(User user);

    public User update(User user);

    public List<User> findUserById(int id);

    public void delete(User user);

    public List<User> usersPage(int from, int count);

    public Number usersTotal();

    public List<User> findUsersByName(String name, int from, int count, boolean like);

    public Number countUsersWithName(String name, boolean like);

    public List<User> findUsersByAgeRange(int start, int end, int from, int count);

    public Number countUsersWithAgeRange(int start, int end);

    public List<User> findUsersByDateRange(Calendar start, Calendar end, int from, int count);

    public Number countUsersWithDateRange(Calendar start, Calendar end);

    public List<User> findUsersByAdmin(boolean adm, int from, int count);

    public Number countUsersWithAdmin(boolean adm);

}
