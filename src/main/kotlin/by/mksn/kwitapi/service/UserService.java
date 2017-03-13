package by.mksn.kwitapi.service;

import by.mksn.kwitapi.entity.User;

public interface UserService {

    User findByUsername(String username);

}
