package Duan2.Traicagiong.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import Duan2.Traicagiong.entity.Fish;
import Duan2.Traicagiong.entity.Order;
import Duan2.Traicagiong.entity.Role;
import Duan2.Traicagiong.entity.User;
import Duan2.Traicagiong.entity.UserRegistrationDto;
import Duan2.Traicagiong.repository.OrderReponsitory;
import Duan2.Traicagiong.repository.RoleRepository;
import Duan2.Traicagiong.repository.UserRepository;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderReponsitory orderReponsitory;
    
    

	@Override
	public List<Order> findByOrder(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Order> GetAllOrder() {
		// TODO Auto-generated method stub
		return orderReponsitory.findAll();
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		orderReponsitory.deleteById(id);
	}

	@Override
	public Optional<Order> findOne(Integer id) {
		// TODO Auto-generated method stub
		return orderReponsitory.findById(id);
	}

	@Override
	public void save(Order order) {
	 orderReponsitory.save(order);
		
	}

	@Override
	public List<Order> findByOrder(Integer id) {
		// TODO Auto-generated method stub
		return orderReponsitory.findAllByUserStatusNoActice(id);
	}

	@Override
	public List<Order> findByOrderUserActive(Integer id) {
		return orderReponsitory.findAllByUserStatusActice(id);
	}
}