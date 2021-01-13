package Duan2.Traicagiong.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import Duan2.Traicagiong.entity.Fish;
import Duan2.Traicagiong.entity.Order;


@Repository
public interface OrderReponsitory extends JpaRepository<Order, Integer> {
	@Query(value = "SELECT * FROM orders  Where status = 0 And id_user = ?1",nativeQuery = true)
	   List<Order> findAllByUserStatusNoActice(Integer iduser);
	
	@Query(value = "SELECT * FROM orders  Where status = 1 And id_user = ?1",nativeQuery = true)
	   List<Order> findAllByUserStatusActice(Integer iduser);
}

