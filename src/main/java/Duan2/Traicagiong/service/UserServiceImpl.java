package Duan2.Traicagiong.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import Duan2.Traicagiong.entity.Fish;
import Duan2.Traicagiong.entity.Role;
import Duan2.Traicagiong.entity.User;
import Duan2.Traicagiong.entity.UserRegistrationDto;
import Duan2.Traicagiong.repository.RoleRepository;
import Duan2.Traicagiong.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private RoleRepository roleRepository;
    


    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User save(UserRegistrationDto registration) {
        User user = new User();
        user.setName(registration.getName());
        user.setPhone(registration.getPhone());
        user.setEmail(registration.getEmail());
        user.setPassword(passwordEncoder.encode(registration.getPassword()));
        user.setAddress(registration.getAddress());
        if(userRepository.findAll().isEmpty()) {
            user.setRoles(Arrays.asList(new Role("ROLE_ADMIN")));
        }else {
            user.setRoles(Arrays.asList(new Role("ROLE_USER")));
        }
        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null){
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(),
                user.getPassword(),
                mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles){
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }


	@Override
	public List<User> findAll() {
		// TODO Auto-generated method stub
		 return (List<User>) userRepository.findAll();
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		userRepository.deleteById(id);

	}

	@Override
	public User save(User user) {
		 return userRepository.save(user);
	}
	
}
