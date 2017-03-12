package by.mksn.kwitapi.service.impl;

import by.mksn.kwitapi.entity.User;
import by.mksn.kwitapi.repository.UserRepository;
import by.mksn.kwitapi.service.UserService;

public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
