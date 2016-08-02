package ru.ilonych.testtask.daos;

import org.hibernate.*;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.ilonych.testtask.entity.User;

import java.util.Calendar;
import java.util.List;

/**
 * Created by Никола on 26.07.2016.
 */
@Transactional
@Repository
public class UserDAOimpl implements UserDAO {

    @Autowired
    private SessionFactory sessionFactory;

    /*
    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    */

    /*
    public List<User> showAllUsers() {
        Session session = sessionFactory.openSession();
        String hql = "FROM ru.ilonych.testtask.entity.User";
        Query query = session.createQuery(hql);
        List<User> results = query.list();
        session.close();
        return results;
    }
    */

    public List<User> usersPage(int from, int count){
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        String hql = "FROM ru.ilonych.testtask.entity.User";
        Query query = session.createQuery(hql);
        query.setFirstResult(from);
        query.setMaxResults(count);
        List<User> results = query.list();
        tx.commit();
        session.close();
        return results;
    }

    public Number usersTotal(){
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        Number results = (Number) session.createCriteria(User.class).setProjection(Projections.rowCount()).uniqueResult();
        tx.commit();
        session.close();
        return results;
    }

    public List<User> findUserById(int id) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        Criteria cr = session.createCriteria(User.class);
        cr.add(Restrictions.eq("id", id));
        List<User> result = cr.list();
        tx.commit();
        session.close();
        return result;
    }

    public List<User> findUsersByName(String name, int from, int count, boolean like) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        Criteria cr = session.createCriteria(User.class);
        if (like) {
            cr.add(Restrictions.eq("name", name));
        } else {
            cr.add(Restrictions.like("name", name, MatchMode.ANYWHERE));
        }
        cr.setFirstResult(from);
        cr.setMaxResults(count);
        List<User> results = cr.list();
        tx.commit();
        session.close();
        return results;
    }

    public Number countUsersWithName(String name, boolean like) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        Criteria cr = session.createCriteria(User.class);
        if (like) {
            cr.add(Restrictions.eq("name", name));
        } else {
            cr.add(Restrictions.like("name", name, MatchMode.ANYWHERE));
        }
        cr.setProjection(Projections.rowCount());
        Number result = (Number) cr.uniqueResult();
        tx.commit();
        session.close();
        return result;
    }

    public List<User> findUsersByAgeRange(int start, int end, int from, int count){
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        Criteria cr = session.createCriteria(User.class);
        cr.add(Restrictions.between("age", start, end));
        cr.addOrder(Order.asc("age"));
        cr.setFirstResult(from);
        cr.setMaxResults(count);
        List<User> result = cr.list();
        tx.commit();
        session.close();
        return result;
    }

    public Number countUsersWithAgeRange(int start, int end){
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        Criteria cr = session.createCriteria(User.class);
        cr.add(Restrictions.between("age", start, end));
        cr.setProjection(Projections.rowCount());
        Number result = (Number) cr.uniqueResult();
        tx.commit();
        session.close();
        return result;
    }

    public List<User> findUsersByDateRange(Calendar start, Calendar end, int from, int count){
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        Criteria cr = session.createCriteria(User.class);
        cr.add(Restrictions.between("createdDate", start, end));
        cr.addOrder(Order.asc("createdDate"));
        cr.setFirstResult(from);
        cr.setMaxResults(count);
        List<User> result = cr.list();
        tx.commit();
        session.close();
        return result;
    }

    public Number countUsersWithDateRange(Calendar start, Calendar end){
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        Criteria cr = session.createCriteria(User.class);
        cr.add(Restrictions.between("createdDate", start, end));
        cr.setProjection(Projections.rowCount());
        Number result = (Number) cr.uniqueResult();
        tx.commit();
        session.close();
        return result;
    }

    @Override
    public List<User> findUsersByAdmin(boolean adm, int from, int count) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        Criteria cr = session.createCriteria(User.class);
        cr.add(Restrictions.eq("isAdmin", adm));
        cr.setFirstResult(from);
        cr.setMaxResults(count);
        List<User> result = cr.list();
        tx.commit();
        session.close();
        return result;
    }

    @Override
    public Number countUsersWithAdmin(boolean adm) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        Criteria cr = session.createCriteria(User.class);
        cr.add(Restrictions.eq("isAdmin", adm));
        cr.setProjection(Projections.rowCount());
        Number result = (Number) cr.uniqueResult();
        tx.commit();
        session.close();
        return result;
    }


    public void delete(User user) {
        Session session = this.sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.delete(user);
        tx.commit();
        session.close();
    }

    public Integer create(User user) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        Integer newuserid = (Integer)session.save(user);
        session.flush();
        tx.commit();
        session.close();
        return newuserid;
    }

    public User update(User user) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        User updateduser = (User) session.merge(user);
        session.flush();
        tx.commit();
        session.close();
        return updateduser;
    }
}
