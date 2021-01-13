package Duan2.Traicagiong.service;

import java.util.List;
import java.util.Optional;

import Duan2.Traicagiong.entity.Fish;
import Duan2.Traicagiong.entity.Order;
import Duan2.Traicagiong.entity.User;


public interface OrderService{

	List<Order> findByOrder(String name);
   
    List<Order> GetAllOrder();
    
    void delete(Integer id);
    
    Optional<Order> findOne(Integer id);
    
    void save(Order order);
    
    List<Order> findByOrder(Integer user);
    List<Order> findByOrderUserActive(Integer user);

}