package ru.ilonych.testtask.service;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ilonych.testtask.daos.UserDAO;
import ru.ilonych.testtask.entity.User;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Никола on 26.07.2016.
 */

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserDAO userDAOimpl;

    /*
    @Autowired
    public void setUserDAO(UserDAO userDAO){
        this.userDAO = userDAO;
    }
    public List<User> showAllUsers() {
        return userDAOimpl.showAllUsers();
    }
    */
    public Integer create(User user) {
        return userDAOimpl.create(user);
    }

    public User update(User user) {
        return userDAOimpl.update(user);
    }

    public List<User> findUserById(int id) {
        return userDAOimpl.findUserById(id);
    }

    public void delete(User user) {
        userDAOimpl.delete(user);
    }

    public List<User> usersPage(String params){
        String[] props = params.split("&");
        return userDAOimpl.usersPage(Integer.parseInt(props[0]), Integer.parseInt(props[1]));
    }

    @Override
    public Number usersTotal() {
        return userDAOimpl.usersTotal();
    }

    @Override
    public List<User> findUsersByName(String params) {
        String[] props = params.split("&");
        return userDAOimpl.findUsersByName(props[0], Integer.parseInt(props[1]), Integer.parseInt(props[2]), Boolean.valueOf(props[3]));
    }

    @Override
    public Number countUsersWithName(String name) {
        String[] props = name.split("&");
        return userDAOimpl.countUsersWithName(props[0], Boolean.valueOf(props[1]));
    }

    @Override
    public List<User> findUsersByAgeRange(String params) {
        String[] props = params.split("&");
        return userDAOimpl.findUsersByAgeRange(Integer.parseInt(props[0]), Integer.parseInt(props[1]),
                Integer.parseInt(props[2]), Integer.parseInt(props[3]));
    }

    @Override
    public Number countUsersWithAgeRange(String params) {
        String[] props = params.split("&");
        return userDAOimpl.countUsersWithAgeRange(Integer.parseInt(props[0]), Integer.parseInt(props[1]));
    }

    @Override
    public List<User> findUsersByDateRange(String params) {
        //dsdefc
        String[] props = params.split("&");
        Calendar datestart = Calendar.getInstance();
        Calendar dateend = Calendar.getInstance();
        Date ds = new Date(Long.valueOf(props[0]));
        Date de = new Date(Long.valueOf(props[1]));
        datestart.setTime(ds);
        dateend.setTime(de);
        return userDAOimpl.findUsersByDateRange(datestart, dateend, Integer.parseInt(props[2]), Integer.parseInt(props[3]));
    }

    @Override
    public Number countUsersWithDateRange(String params) {
        String[] props = params.split("&");
        Calendar datestart = Calendar.getInstance();
        Calendar dateend = Calendar.getInstance();
        Date ds = new Date(Long.valueOf(props[0]));
        Date de = new Date(Long.valueOf(props[1]));
        datestart.setTime(ds);
        dateend.setTime(de);
        return userDAOimpl.countUsersWithDateRange(datestart, dateend);
    }

    @Override
    public List<User> findUsersByAdmin(String params) {
        String[] props = params.split("&");
        return userDAOimpl.findUsersByAdmin(Boolean.valueOf(props[0]), Integer.parseInt(props[1]), Integer.parseInt(props[2]));
    }

    @Override
    public Number countUsersWithAdmin(boolean adm) {
        return userDAOimpl.countUsersWithAdmin(adm);
    }
}
