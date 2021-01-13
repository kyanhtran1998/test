package Duan2.Traicagiong.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Duan2.Traicagiong.entity.Background;
import Duan2.Traicagiong.entity.Store;


@Repository
public interface StoreRepository extends JpaRepository < Store, Integer > {

}