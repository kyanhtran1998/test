package Duan2.Traicagiong.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import Duan2.Traicagiong.entity.Order;
import Duan2.Traicagiong.entity.OrderDetail;


@Repository
public interface OrderDetailReponsitory extends JpaRepository<OrderDetail, Integer> {

	@Query(value = "SELECT * FROM OrderDetail  Where  id_order = ?1",nativeQuery = true)
	List<OrderDetail> FindOrder(Integer order);

	   
}

