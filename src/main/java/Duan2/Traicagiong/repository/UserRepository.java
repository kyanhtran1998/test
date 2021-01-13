package Duan2.Traicagiong.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import Duan2.Traicagiong.entity.User;


@Repository
public interface UserRepository extends JpaRepository < User, Integer > {
    User findByEmail(String email);
    
    
    @Query("SELECT e FROM User e")
    Page<User> findUsers(Pageable pageable);
    
    	List<User> findByNameContaining(String term);
    
}
