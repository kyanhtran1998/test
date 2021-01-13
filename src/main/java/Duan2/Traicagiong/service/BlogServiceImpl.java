package Duan2.Traicagiong.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Duan2.Traicagiong.entity.Blog;
import Duan2.Traicagiong.entity.Fish;
import Duan2.Traicagiong.repository.BlogReponsitory;
import Duan2.Traicagiong.repository.FishReponsitory;

@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogReponsitory blogReponsitory;

	@Override
	public List<Blog> findAll() {
		// TODO Auto-generated method stub
		return blogReponsitory.findAll();
	}

	@Override
	public List<Blog> search(String term) {
		 return blogReponsitory.findByNameContaining(term);
	}

	@Override
	public Optional<Blog> findOne(Integer id) {
		return  blogReponsitory.findById(id);
	}

	@Override
	public void save(Blog blog) {
		blogReponsitory.save(blog);
	}

	@Override
	public void delete(Integer id) {
		blogReponsitory.deleteById(id);
		
	}

   

}