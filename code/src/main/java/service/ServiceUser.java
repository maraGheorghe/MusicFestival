package service;

import model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import repository.interfaces.RepositoryInterfaceUser;
import util.EncryptPassword;
import validator.Validator;

import java.util.Optional;


public class ServiceUser {
    private RepositoryInterfaceUser repository;
    private Validator<User> validator;
    private static final Logger logger = LogManager.getLogger();

    public ServiceUser(RepositoryInterfaceUser repository, Validator<User> validator) {
        logger.info("Initializing ServiceUser.");
        this.repository = repository;
        this.validator = validator;
    }

    public Optional<User> save(String username, String password) {
        logger.traceEntry("Saving user with username {} and password {}.", username, password);
        User user = new User(username, password);
        validator.validate(user);
        return repository.save(user);
    }

    public Optional<User> login(String username, String password) {
        logger.traceEntry("Login user with username {} and password {}.", username, password);
       // String encryptedPassword = EncryptPassword.encodePassword(password);
        return repository.checkConnection(username, password);
    }
}
