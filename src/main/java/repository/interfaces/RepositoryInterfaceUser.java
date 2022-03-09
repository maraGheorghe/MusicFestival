package repository.interfaces;

import model.User;
import repository.interfaces.RepositoryInterface;

import java.util.Optional;

public interface RepositoryInterfaceUser extends RepositoryInterface<User> {

    Optional<User> findUserByUsername(String username);

    Optional<User> checkConnection(String username, String password);
}
