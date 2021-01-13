package Duan2.Traicagiong.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Duan2.Traicagiong.entity.Background;


@Repository
public interface BackGroundRepository extends JpaRepository < Background, Integer > {

}