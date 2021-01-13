package Duan2.Traicagiong.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import Duan2.Traicagiong.entity.Cart;
import Duan2.Traicagiong.entity.Fish;
import Duan2.Traicagiong.entity.Order;
import Duan2.Traicagiong.entity.OrderDetail;
import Duan2.Traicagiong.entity.User;
import Duan2.Traicagiong.repository.OrderDetailReponsitory;
import Duan2.Traicagiong.repository.OrderReponsitory;
import Duan2.Traicagiong.repository.StoreRepository;
import Duan2.Traicagiong.repository.UserRepository;
import Duan2.Traicagiong.service.FishService;
import Duan2.Traicagiong.service.FishTopService;
import Duan2.Traicagiong.service.OrderService;
import Duan2.Traicagiong.service.UserService;

@Controller
public class InfoCustomerController {

	@Autowired
	private UserService userservice;
	@Autowired
	private FishService fishService;
	@Autowired
	private OrderReponsitory orderReponsitory;
	@Autowired
	private StoreRepository storeRepository;
	@Autowired
	private OrderDetailReponsitory orderDetailReponsitory;
	@Autowired
	private UserService userService;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	@Autowired
	private OrderService orderService;


	@GetMapping("/User/DoiMatKhau")
	public String DoiMatKhau(Model model) {
		model.addAttribute("lienhe", storeRepository.findAll());
		return "DoiMatKhau";
	}

	@PostMapping("/User/ChangePassword")
	public String ChanpePassword(@RequestParam(value = "oldPassword", required = false) String oldPassword,
			@RequestParam(value = "newPassword", required = false) String NewPassword,
			@RequestParam(value = "confirmPassword", required = false) String confirmPassword,Model model, RedirectAttributes reAttributes) {
		
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		final User currentUser = userService.findByEmail(authentication.getName());
		if (!confirmPassword.equals(NewPassword)) {
			reAttributes.addFlashAttribute("thongtin", "Mật khẩu không Giống Nhau");
		}else if (!passwordEncoder.matches(oldPassword, currentUser.getPassword())){
			reAttributes.addFlashAttribute("thongtin", "Nhập Mật khẩu không Đúng");
		}else {
			currentUser.setPassword(passwordEncoder.encode(NewPassword));
			userRepository.saveAndFlush(currentUser);
			reAttributes.addFlashAttribute("message", "Đổi Mật Khẩu Thành Công");
		}
		return "redirect:/User/DoiMatKhau";

	}
	
	@GetMapping("/User/ThongTinKhachHang")
	public String ShowThongTin(Model model) {
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		final User currentUser = userService.findByEmail(authentication.getName());
		model.addAttribute("user", currentUser);
		model.addAttribute("lienhe", storeRepository.findAll());
		return "ThongTinKhachHang";
	}
	
	
	@PostMapping("/User/ThongTinKhachHang")
	public String UpdateInfor(User contact, BindingResult result,
			RedirectAttributes redirect,Model model) {
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		final User currentUser = userService.findByEmail(authentication.getName());
		currentUser.setAddress(contact.getAddress());
		currentUser.setPhone(contact.getPhone());
		currentUser.setName(contact.getName());

		userService.save(currentUser);
		model.addAttribute("lienhe", storeRepository.findAll());
		redirect.addFlashAttribute("message", "Cập Nhật Thành Công");

		return "redirect:/User/ThongTinKhachHang";
	}
	
	
	@GetMapping("/User/DonHang")
	public String DonHang(Model model) {
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		final User currentUser = userService.findByEmail(authentication.getName());
		List<Order> list = orderService.findByOrder(currentUser.getId());
		model.addAttribute("listorder", list);
		model.addAttribute("check", "true");

		model.addAttribute("donhangdamua", "Đơn Hàng Đang Mua");

		model.addAttribute("lienhe", storeRepository.findAll());
		return "DonHang";
	}
	
	@GetMapping("/User/DonHang/{id}")
	public String XoaDonHang(Model model,@PathVariable int id) {
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		final User currentUser = userService.findByEmail(authentication.getName());
		orderService.delete(id);
		model.addAttribute("lienhe", storeRepository.findAll());
		return "redirect:/User/DonHang";
	}
	@GetMapping("/User/DonHangDaMua")
	public String DonHangDaMua(Model model) {
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		final User currentUser = userService.findByEmail(authentication.getName());
		List<Order> list = orderService.findByOrderUserActive(currentUser.getId());
		model.addAttribute("listorder", list);
		model.addAttribute("donhangdamua", "Đơn Hàng Đã Mua");
		model.addAttribute("lienhe", storeRepository.findAll());
		return "DonHang";
	}
	
	

	
}
