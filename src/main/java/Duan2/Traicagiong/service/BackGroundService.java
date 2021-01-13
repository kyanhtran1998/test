package Duan2.Traicagiong.service;

import java.util.List;
import java.util.Optional;

import Duan2.Traicagiong.entity.Background;

public interface BackGroundService {

	List<Background> findAll();

	Optional<Background> findOne(Integer id);

	void save(Background fish);

	void delete(Integer id);
}
