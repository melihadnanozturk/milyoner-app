package org.maoco.milyoner.question.service;

import org.maoco.milyoner.question.data.entity.UserEntity;
import org.maoco.milyoner.question.data.entity.UserRoles;
import org.maoco.milyoner.question.data.repository.UserRepository;
import org.maoco.milyoner.question.web.dto.request.UserRequest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

    public void adminRegisterUser(UserRequest request) {
        this.checkUserName(request.username());

        UserEntity newAdmin = new UserEntity();
        newAdmin.setUsername(request.username());

        String encodedPassword = encode.encode(request.password());
        newAdmin.setPassword(encodedPassword);
        newAdmin.setUserRoles(UserRoles.ADMIN);

        userRepository.save(newAdmin);
    }

    public void userRegisterUser(UserRequest request) {
        this.checkUserName(request.username());

        UserEntity newUser = new UserEntity();
        newUser.setUsername(request.username());

        String encodedPassword = encode.encode(request.password());
        newUser.setPassword(encodedPassword);
        newUser.setUserRoles(UserRoles.READER);

        userRepository.save(newUser);
    }

    private void checkUserName(String username) {
        if (userRepository.findByUsername(username).isPresent()) {
            //todo: must Exception edited
            throw new RuntimeException("Verilen isimde Admin bulunmaktadir");
        }
    }
}
