package org.maoco.milyoner.question.service;

import org.maoco.milyoner.common.exception.MilyonerException;
import org.maoco.milyoner.question.data.entity.AdminEntity;
import org.maoco.milyoner.question.data.entity.UserRoles;
import org.maoco.milyoner.question.data.repository.AdminRepository;
import org.maoco.milyoner.question.web.dto.request.AdminRequest;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    private final AdminRepository adminRepository;
    private final PasswordEncoder encode;

    public AdminService(AdminRepository adminRepository, PasswordEncoder encode) {
        this.adminRepository = adminRepository;
        this.encode = encode;
    }

    public AdminEntity findByUsername(String username){
        return adminRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Admin bulunamadı"));
    }

    //todo: burada return'e sahip bir mehtod var. Burada bunu kontorl amaçlı kullanmak best practice mi ?
    // performans ve kod kalitesi olarak neye denk gelir ?

    public void registerAdmin(AdminRequest request) {
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
