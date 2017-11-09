package com.loopme.repository;

import com.loopme.model.App;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Igor Holiak.
 */
@Repository
@Transactional
public class AppRepository {

    @Autowired
    HibernateTemplate hibernateTemplate;

    @Resource(name = "sessionFactory")
    SessionFactory sessionFactory;
    

    public void saveOrUpdate(final App app) {
        sessionFactory.getCurrentSession().saveOrUpdate(app);
    }

    public void delete(final Integer id) {
        App app = findOne(id);
        if (app != null) {
            sessionFactory.getCurrentSession().delete(app);
        }
    }

    @SuppressWarnings("unchecked")
    public App findOne(final Integer id) {
        return (App) sessionFactory.getCurrentSession().get(App.class, id);
    }

    @SuppressWarnings("unchecked")
    public List<App> findAll() {
        return (List<App>) hibernateTemplate.find("from App a");
    }
}
