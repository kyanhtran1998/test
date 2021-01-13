package Duan2.Traicagiong.service;

import java.util.List;
import java.util.Optional;

import Duan2.Traicagiong.entity.Blog;
import Duan2.Traicagiong.entity.Fish;

public interface  BlogService {

    List<Blog> findAll();

    List<Blog> search(String term);

    Optional<Blog> findOne(Integer id);

    void save(Blog blog);

    void delete(Integer id);
}
