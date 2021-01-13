package Duan2.Traicagiong.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import Duan2.Traicagiong.repository.StoreRepository;

@Controller
public class MainController {
	@Autowired
	private StoreRepository storeRepository;


    @GetMapping("/DangNhap")
    public String login(Model model) {
    	model.addAttribute("lienhe", storeRepository.findAll());
        return "DangNhap";
    }

    @GetMapping("/user")
    public String userIndex() {
        return "user/index";
    }

    @GetMapping("/test")
    public String hahaha() {
        return "add";
    }
}