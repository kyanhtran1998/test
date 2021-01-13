package Duan2.Traicagiong.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Duan2.Traicagiong.entity.Background;
import Duan2.Traicagiong.entity.FishTop;
import Duan2.Traicagiong.repository.BackGroundRepository;


@Service
public class BackGroundServiceImpl implements BackGroundService {

    @Autowired
    private BackGroundRepository backGroundRepository;

    @Override
    public List<Background> findAll() {
        return (List<Background>) backGroundRepository.findAll();
    }


    @Override
    public Optional<Background> findOne(Integer id) {
		return  backGroundRepository.findById(id);
    }

    @Override
    public void save(Background fish) {
    	backGroundRepository.save(fish);
    }

    @Override
    public void delete(Integer id) {
    	backGroundRepository.deleteById(id);
    }



}