package Duan2.Traicagiong.repository;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import Duan2.Traicagiong.entity.FishTop;
import Duan2.Traicagiong.entity.Order;

@Repository
public interface FishTopReponsitory extends JpaRepository<FishTop, Integer> {
	
	@Query(value = "SELECT * FROM fish_top  Where  id_fish = ?1",nativeQuery = true)
	   FishTop FindFishTopId(Integer id);
}
