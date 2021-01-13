package Duan2.Traicagiong.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import Duan2.Traicagiong.entity.Blog;
import Duan2.Traicagiong.entity.Fish;

@Repository
public interface BlogReponsitory extends JpaRepository<Blog, Integer> {
	   List<Blog> findByNameContaining(String term);
	  
}
