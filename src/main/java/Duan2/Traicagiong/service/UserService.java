package Duan2.Traicagiong.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import Duan2.Traicagiong.entity.Fish;
import Duan2.Traicagiong.entity.User;
import Duan2.Traicagiong.entity.UserRegistrationDto;

public interface UserService extends UserDetailsService {

    User findByEmail(String email);

    User save(UserRegistrationDto registration);
    
    User save(User user);
   
    List<User> findAll();
    
    void delete(Integer id);
 
}
