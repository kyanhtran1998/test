package Duan2.Traicagiong.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Duan2.Traicagiong.entity.FishTop;
import Duan2.Traicagiong.repository.FishTopReponsitory;

@Service
public class FishTopServiceImpl implements FishTopService {

    @Autowired
    private FishTopReponsitory fishTopReponsitory;

    @Override
    public List<FishTop> findAll() {
        return (List<FishTop>) fishTopReponsitory.findAll();
    }


    @Override
    public Optional<FishTop> findOne(Integer id) {
		return  fishTopReponsitory.findById(id);
    }

    @Override
    public void save(FishTop fish) {
    	fishTopReponsitory.save(fish);
    }

    @Override
    public void delete(Integer id) {
    	fishTopReponsitory.deleteById(id);
    }


	@Override
	public FishTop checkFishId(Integer id) {
		
		return fishTopReponsitory.FindFishTopId(id);
	}






}