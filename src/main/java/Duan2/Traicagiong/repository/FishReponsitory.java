package Duan2.Traicagiong.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import Duan2.Traicagiong.entity.Fish;

@Repository
public interface FishReponsitory extends JpaRepository<Fish, Integer> {
	   List<Fish> findByNameContaining(String term);
	   
	   
	   
	   @Query(value = "SELECT * FROM Fish ORDER BY date DESC  LIMIT 6",nativeQuery = true)
	   List<Fish> findAllByOrderByDateAsc();
	   
	   

	   @Query(value = "SELECT * FROM Fish  LIMIT 10",nativeQuery = true)
	   List<Fish> findAllTop10Fish();
}
