package Duan2.Traicagiong.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Duan2.Traicagiong.entity.Fish;
import Duan2.Traicagiong.repository.FishReponsitory;

@Service
public class FishServiceImpl implements FishService {

    @Autowired
    private FishReponsitory fishReponsitory;

    @Override
    public List<Fish> findAll() {
        return (List<Fish>) fishReponsitory.findAll();
    }

    @Override
    public List<Fish> search(String term) {
        return fishReponsitory.findByNameContaining(term);
    }

    @Override
    public Optional<Fish> findOne(Integer id) {
		return  fishReponsitory.findById(id);
    }

    @Override
    public void save(Fish fish) {
    	fishReponsitory.save(fish);
    }

    @Override
    public void delete(Integer id) {
    	fishReponsitory.deleteById(id);
    }

	@Override
	public List<Fish> findTopFishNew() {
		return fishReponsitory.findAllByOrderByDateAsc();
	}

	@Override
	public List<Fish> findAllTop10Fish() {
		return fishReponsitory.findAllTop10Fish();
	}


}