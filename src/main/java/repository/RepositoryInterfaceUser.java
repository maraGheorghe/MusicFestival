package repository;

import model.User;

import java.util.Optional;

public interface RepositoryInterfaceUser extends RepositoryInterface<User> {

    Optional<User> findUserByUsername(String username);
}
