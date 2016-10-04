package ru.ilonych.testtask.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Calendar;

/**
 * Created by Никола on 25.07.2016.
 */

@Entity
@Table(name="users")
public class User implements Serializable{
    private static final long serialVersionUID = 2L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", length = 8)
    private int id;
    @Column(name="name", length = 25)
    private String name;
    @Column(name="age", length = 3)
    private int age;
    @Column(name="isAdmin", length = 1)
    private Boolean isAdmin;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="createdDate")
    private Calendar createdDate;

    public User(){
    }

    public User(int id, String name, int age, Boolean isAdmin, Calendar createdDate) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.createdDate = createdDate;
        this.isAdmin = isAdmin;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public Boolean isAdmin() {
        return isAdmin;
    }

    public Calendar getCreatedDate() {
        return createdDate;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }

    public void setCreatedDate(Calendar createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (getId() != user.getId()) return false;
        if (getAge() != user.getAge()) return false;
        if (isAdmin() != user.isAdmin()) return false;
        if (getName() != null ? !getName().equals(user.getName()) : user.getName() != null) return false;
        return getCreatedDate() != null ? getCreatedDate().equals(user.getCreatedDate()) : user.getCreatedDate() == null;

    }

    @Override
    public int hashCode() {
        int result = getId();
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + getAge();
        result = 31 * result + (isAdmin() ? 1 : 0);
        result = 31 * result + (getCreatedDate() != null ? getCreatedDate().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", isAdmin=" + isAdmin +
                ", createdDate=" + createdDate.getTime() +
                '}';
    }
}
