package org.maoco.milyoner.question.service;

import org.maoco.milyoner.question.data.entity.AdminEntity;
import org.maoco.milyoner.question.data.entity.UserRoles;
import org.maoco.milyoner.question.data.repository.AdminRepository;
import org.maoco.milyoner.question.web.dto.request.UserRequest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final AdminRepository adminRepository;
    private final PasswordEncoder encode;

    public UserService(AdminRepository adminRepository, PasswordEncoder encode) {
        this.adminRepository = adminRepository;
        this.encode = encode;
    }

    public AdminEntity findByUsername(String username){
        return adminRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Admin bulunamadı"));
    }

    //todo: burada return'e sahip bir mehtod var. Burada bunu kontorl amaçlı kullanmak best practice mi ?
    // performans ve kod kalitesi olarak neye denk gelir ?

    public void registerUser(UserRequest request) {
        //todo : must Exception edited
        if (adminRepository.findByUsername(request.username()).isPresent()){
            throw new RuntimeException("Verilen isimde Admin bulunmaktadir");
        }

        AdminEntity newAdmin = new AdminEntity();
        newAdmin.setUsername(request.username());

        String encodedPassword = encode.encode(request.password());
        newAdmin.setPassword(encodedPassword);
        newAdmin.setUserRoles(UserRoles.ADMIN);

        adminRepository.save(newAdmin);
    }
}
