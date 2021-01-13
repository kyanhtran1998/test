package Duan2.Traicagiong.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Duan2.Traicagiong.entity.Role;
import Duan2.Traicagiong.entity.User;


@Repository
public interface RoleRepository extends JpaRepository < Role, Long > {

}