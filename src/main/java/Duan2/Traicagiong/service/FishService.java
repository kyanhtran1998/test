package Duan2.Traicagiong.service;

import java.util.List;
import java.util.Optional;

import Duan2.Traicagiong.entity.Fish;

public interface  FishService {

    List<Fish> findAll();
    
    List<Fish> findTopFishNew();
    
    List<Fish> findAllTop10Fish();
    
    List<Fish> search(String term);

    Optional<Fish> findOne(Integer id);

    void save(Fish fish);

    void delete(Integer id);
}
