package com.loopme.repository;

import com.loopme.model.User;
import com.loopme.model.UserRole;
import org.hibernate.Query;
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
public class UserRepository {

    @Autowired
    HibernateTemplate hibernateTemplate;;

    @Resource(name = "sessionFactory")
    SessionFactory sessionFactory;

    public void saveOrUpdate(final User user) {
        hibernateTemplate.saveOrUpdate(user);
    }

    public void delete(final Integer id) {
        User user = findOne(id);
        if (user != null) {
            hibernateTemplate.delete(user);
        }
    }

    public User findOne(final Integer id) {
        return (User) hibernateTemplate.get(User.class, id);
    }

    public User findOneByEmail(final String email) {
        Query query = sessionFactory.getCurrentSession().createQuery("from User u where u.email = :email");
        query.setParameter("email", email);
        return (User) query.uniqueResult();
    }

    public List<User> findByRole(final UserRole role) {
        Query query = sessionFactory.getCurrentSession().createQuery("from User u where u.role = :role");
        query.setParameter("role", role);
        return query.list();
    }

    public List<User> findFreePublisher() {
        return (List<User>) hibernateTemplate
            .find("select u from User u left join u.app app where app is null and u.role = com.loopme.model.UserRole.PUBLISHER");
    }
}
