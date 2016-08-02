package ru.ilonych.testtask.controller;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.ilonych.testtask.entity.User;
import ru.ilonych.testtask.service.UserService;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Никола on 26.07.2016.
 */

@Controller
@RequestMapping("/test")
public class UserController {
    @Autowired
    UserService userServiceImpl;

    @RequestMapping("/home")
    public String home() {
        return "home";
    }

    /* //Вывод всех разом
    @RequestMapping(value="/usersarray.json", method = RequestMethod.GET)
    @ResponseBody
    public String allUsersList() {
        String users = JSON.toJSONString(userServiceImpl.showAllUsers());
        System.out.println(users);
        return users;
    }
    */

    //Поиск по ID
    @RequestMapping(value="/findbyid/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String userByIdFound(@PathVariable("id") Integer id) {
        return JSON.toJSONString(userServiceImpl.findUserById(id));
    }

    //Вывод юзеров постранично
    @RequestMapping(value="/users/{fc}", method = RequestMethod.GET)
    @ResponseBody
    public String usersPage(@PathVariable("fc") String params) {
        return JSON.toJSONString(userServiceImpl.usersPage(params));
    }

    @RequestMapping(value="/userscount", method = RequestMethod.GET)
    @ResponseBody
    public String usersCount() {
        return JSON.toJSONString(userServiceImpl.usersTotal());
    }

    //Поиск по имени с постраничным выводом
    @RequestMapping(value="/findbyname/{nameFC}", method = RequestMethod.GET)
    @ResponseBody
    public String usersByNameFound(@PathVariable("nameFC") String params) {
        return JSON.toJSONString(userServiceImpl.findUsersByName(params));
    }

    @RequestMapping(value="/countbyname/{name}", method = RequestMethod.GET)
    @ResponseBody
    public String usersByNameCount(@PathVariable("name") String name) {
        return JSON.toJSONString(userServiceImpl.countUsersWithName(name));
    }

    //Поиск по возрасту с постраничным выводом
    @RequestMapping(value="/findbyage/{sefc}", method = RequestMethod.GET)
    @ResponseBody
    public String usersInAgeRangeFound(@PathVariable("sefc") String params) {
        return JSON.toJSONString(userServiceImpl.findUsersByAgeRange(params));
    }

    @RequestMapping(value="/countbyage/{se}", method = RequestMethod.GET)
    @ResponseBody
    public String usersByAgeRangeCount(@PathVariable("se") String params) {
        return JSON.toJSONString(userServiceImpl.countUsersWithAgeRange(params));
    }

    //Поиск по дате создания с постраничным выводом
    @RequestMapping(value="/findbydate/{dsdefc}", method = RequestMethod.GET)
    @ResponseBody
    public String usersInDateRangeFound(@PathVariable("dsdefc") String params) {
        return JSON.toJSONString(userServiceImpl.findUsersByDateRange(params));
    }

    @RequestMapping(value="/countbydate/{asae}", method = RequestMethod.GET)
    @ResponseBody
    public String usersByDateRangeCount(@PathVariable("asae") String params) {
        return JSON.toJSONString(userServiceImpl.countUsersWithDateRange(params));
    }

    //Поиск по юзерам критерий по isAdmin
    @RequestMapping(value="/findadmins/{admFC}", method = RequestMethod.GET)
    @ResponseBody
    public String usersIsAdminFound(@PathVariable("admFC") String params) {
        return JSON.toJSONString(userServiceImpl.findUsersByAdmin(params));
    }

    @RequestMapping(value="/countadmins/{adm}", method = RequestMethod.GET)
    @ResponseBody
    public String usersByDateRangeCount(@PathVariable("adm") boolean admin) {
        return JSON.toJSONString(userServiceImpl.countUsersWithAdmin(admin));
    }

    //Добавление
    @RequestMapping(value="/user/{json}", method = RequestMethod.POST)
    @ResponseBody
    public String createdUserandReturn(@PathVariable("json") String json){
        User usertoadd = JSON.parseObject(json, User.class);
        Calendar createdDate = Calendar.getInstance();
        createdDate.setTime(new Date());
        usertoadd.setCreatedDate(createdDate);
        usertoadd.setId(userServiceImpl.create(usertoadd));
        return JSON.toJSONString(usertoadd);
    }
    //Удаление
    @RequestMapping(value="/user/{uid}", method = RequestMethod.DELETE )
    @ResponseBody
    public String deletePerson(@PathVariable("uid") Integer id) {
        userServiceImpl.delete(userServiceImpl.findUserById(id).get(0));
        return "true";
    }
    //Обновление
    @RequestMapping(value="/user/{json}", method = RequestMethod.PUT)
    @ResponseBody
    public String updateUser(@PathVariable("json") String json){
        User updatedUser = JSON.parseObject(json, User.class);
        userServiceImpl.update(updatedUser);
        return "true";
    }

}
