package org.maoco.milyoner.question.service;

import org.maoco.milyoner.common.exception.UsernameAlreadyExistsException;
import org.maoco.milyoner.question.data.entity.UserEntity;
import org.maoco.milyoner.question.data.entity.UserRoles;
import org.maoco.milyoner.question.data.repository.UserRepository;
import org.maoco.milyoner.question.domain.User;
import org.maoco.milyoner.question.web.dto.request.UserRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder encode;

    public UserService(UserRepository userRepository, PasswordEncoder encode) {
        this.userRepository = userRepository;
        this.encode = encode;
    }

    //todo: burada return'e sahip bir mehtod var. Burada bunu kontorl amaçlı kullanmak best practice mi ?
    // performans ve kod kalitesi olarak neye denk gelir ?

    public User userRegister(UserRequest request, boolean isAdmin) {
        this.checkUserName(request.username());

        UserEntity newAdmin = new UserEntity();
        newAdmin.setUsername(request.username());

        String encodedPassword = encode.encode(request.password());
        newAdmin.setPassword(encodedPassword);
        if (isAdmin) {
            newAdmin.setUserRoles(UserRoles.ADMIN);
        } else {
            newAdmin.setUserRoles(UserRoles.READER);
        }

        UserEntity userEntity = userRepository.save(newAdmin);
        return new User(userEntity.getUsername(), userEntity.getPassword());
    }

    private void checkUserName(String username) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new UsernameAlreadyExistsException();
        }
    }
}
