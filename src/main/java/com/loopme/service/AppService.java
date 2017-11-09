package com.loopme.service;

import com.loopme.model.App;
import com.loopme.model.User;
import com.loopme.repository.AppRepository;
import com.loopme.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Igor Holiak.
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AppService {

    private final AppRepository appRepository;
    private final UserRepository userRepository;

    public List<App> findAll() {
        return appRepository.findAll();
    }

    public void saveOrUpdate(final App app) {
        if (app == null) {
            throw new IllegalArgumentException("App shouldn't be null");
        }
        appRepository.saveOrUpdate(app);
    }

    @Transactional
    public void delete(final Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("Id shouldn't be null");
        }
        App app = findOne(id);
        if (app == null) {
            throw new IllegalArgumentException("unknown app");
        }
        User user = app.getUser();
        if (user != null) {
            user.setApp(null);
            userRepository.saveOrUpdate(user);
        }
        appRepository.delete(id);
    }

    public App findOne(final Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("id shouldn't be null");
        }
        return appRepository.findOne(id);
    }
}
