package Duan2.Traicagiong.controller;

import java.security.Principal;	

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import Duan2.Traicagiong.entity.Fish;
import Duan2.Traicagiong.entity.User;
import Duan2.Traicagiong.entity.UserRegistrationDto;
import Duan2.Traicagiong.repository.StoreRepository;
import Duan2.Traicagiong.repository.UserRepository;
import Duan2.Traicagiong.service.UserService;

@Controller
public class UserRegistrationController {
	
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
	private StoreRepository storeRepository;
    @ModelAttribute("user")
    public UserRegistrationDto userRegistrationDto() {
        return new UserRegistrationDto();
    }

    @GetMapping("/DangKy")
    public String showRegistrationForm(Model model) {
    	model.addAttribute("lienhe", storeRepository.findAll());
        return "DangKy";
    }
    


    @PostMapping("/DangKy")
    public String registerUserAccount(@ModelAttribute("user") @Valid UserRegistrationDto userDto,
        BindingResult result) {

        User existing = userService.findByEmail(userDto.getEmail());
        if (existing != null) {
            result.rejectValue("email", null, "Email đã Tồn Tại");
        }
        
        if (!userDto.getPassword().equals(userDto.getConfirmPassword())) {
            result.rejectValue("password", null, "Mật khẩu không trùng khớp");
        }
        if (result.hasErrors()) {
            return "DangKy";
        }
        System.out.print("ss"+userDto.getAddress());
        userService.save(userDto);
        return "redirect:/DangKy?success";
    }

    
   

}