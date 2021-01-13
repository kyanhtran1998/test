package Duan2.Traicagiong.service;

import java.util.List;
import java.util.Optional;

import Duan2.Traicagiong.entity.Fish;
import Duan2.Traicagiong.entity.FishTop;

public interface  FishTopService {

    List<FishTop> findAll();

    Optional<FishTop> findOne(Integer id);

    void save(FishTop fish);

    void delete(Integer id);
     
    
    FishTop checkFishId(Integer id);
}
