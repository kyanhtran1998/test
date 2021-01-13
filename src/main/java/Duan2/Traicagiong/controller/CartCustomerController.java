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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
import Duan2.Traicagiong.repository.StoreRepository;
import Duan2.Traicagiong.service.FishService;
import Duan2.Traicagiong.service.FishTopService;
import Duan2.Traicagiong.service.OrderService;
import Duan2.Traicagiong.service.UserService;
@Controller
public class CartCustomerController {

	@Autowired
	private UserService userservice;
	@Autowired
	private FishService fishService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private StoreRepository storeRepository;
	@Autowired
	private OrderDetailReponsitory orderDetailReponsitory;
	@Autowired
	private UserService userService;
	
	    
	    @GetMapping("/User/GioHang")
	    public String ShowGioHang(HttpSession session, Model model) {
	    	  if (session.getAttribute("cart") == null) {
	    		  return "redirect:/TrangChu";
		        }
		        Map<Integer, Cart> cart = (Map<Integer, Cart>) session.getAttribute("cart");
		        model.addAttribute("cart", cart);
		        model.addAttribute("notCartViewPage", true);
		        model.addAttribute("lienhe", storeRepository.findAll());
	        return "GioHang";
	    }
	    
	    

	    
	    @GetMapping("/User/ThemVaoGioHang/{id}")
	    public String add( HttpServletRequest httpServletRequest,@PathVariable int id, Model model,RedirectAttributes redirect, HttpSession session,@RequestParam(value = "cartPage", required = false) String cartPage) {

	        Optional<Fish> product = fishService.findOne(id);
	       
	        if (session.getAttribute("cart") == null) {
	            Map<Integer, Cart> cart = new HashMap<Integer, Cart>();
	            cart.put(id, new Cart(id, product.get().getName(), product.get().getPrice(), 1, product.get().getImage()));
	            session.setAttribute("cart", cart);
	        } else {
	            Map<Integer, Cart> cart = (Map<Integer, Cart>) session.getAttribute("cart");
	            if (cart.containsKey(id)) {
	                int quantity = cart.get(id).getQuantity();
	                if(product.get().getQuality()< quantity+1 ) {
	    	        	redirect.addFlashAttribute("successMessage", "Số lượng Cá Giống Không Đủ");
	    	        	String referLink = httpServletRequest.getHeader("referer");
	    	 	        return "redirect:" + referLink;
	    	        }
	                cart.put(id, new Cart(id, product.get().getName(), product.get().getPrice(), ++quantity, product.get().getImage()));
	                
	            } else {
	                cart.put(id,new Cart(id, product.get().getName(), product.get().getPrice(), 1, product.get().getImage()));
	                session.setAttribute("cart", cart);
	            }
	        }
	        Map<Integer, Cart> cart = (Map<Integer, Cart>) session.getAttribute("cart");
	        
	        session.setAttribute("size", cart.size());
	        session.setAttribute("total", totalPrice(cart));
	        if (cartPage != null) {
		        return "redirect:/User/GioHang";

	        }
	        return "redirect:/User/GioHang";
	    }
	    
	    @GetMapping("/User/ThemVaoGioHangChiTiet")
	    public String addGiohangChitiet( HttpServletRequest httpServletRequest, @RequestParam(value = "id") int id, Model model,RedirectAttributes redirect, HttpSession session,@RequestParam(value = "quality") Integer soluong) {
	    	
	        Optional<Fish> product = fishService.findOne(id);
	        if(product.get().getQuality()< soluong ) {
	        	redirect.addFlashAttribute("successMessage", "Số lượng Cá Giống Không Đủ");
	        	String referLink = httpServletRequest.getHeader("referer");
	 	        return "redirect:" + referLink;
	        }
	        if (session.getAttribute("cart") == null) {
	            Map<Integer, Cart> cart = new HashMap<Integer, Cart>();
	            cart.put(id, new Cart(id, product.get().getName(), product.get().getPrice(), soluong, product.get().getImage()));
	            session.setAttribute("cart", cart);
	        } else {
	            Map<Integer, Cart> cart = (Map<Integer, Cart>) session.getAttribute("cart");
	            if (cart.containsKey(id)) {
	                int quantity = cart.get(id).getQuantity() + soluong;
	                cart.put(id, new Cart(id, product.get().getName(), product.get().getPrice(), ++quantity, product.get().getImage()));
	            } else {
	                cart.put(id,new Cart(id, product.get().getName(), product.get().getPrice(), soluong, product.get().getImage()));
	                session.setAttribute("cart", cart);
	            }
	        }
	        Map<Integer, Cart> cart = (Map<Integer, Cart>) session.getAttribute("cart");
	        
	        session.setAttribute("size", cart.size());
	        session.setAttribute("total", totalPrice(cart));
	        return "redirect:/User/GioHang";
	    }
	  

	    @GetMapping("/subtract/{id}")
	    public String subtract(@PathVariable int id, Model model, HttpSession session, RedirectAttributes redirect,HttpServletRequest httpServletRequest) {

	    	Optional<Fish> product = fishService.findOne(id);

	        Map<Integer, Cart> cart = (Map<Integer, Cart>) session.getAttribute("cart");

	        int quantity = cart.get(id).getQuantity();
	        if (quantity == 1) {
	            cart.remove(id);
	            if (cart.size() == 0) {
	                session.removeAttribute("cart");
	            }
	        } else {
	            cart.put(id, new Cart(id, product.get().getName(), product.get().getPrice(), --quantity, product.get().getImage()));
	        }

	        String referLink = httpServletRequest.getHeader("referer");
	        
	        session.setAttribute("size", cart.size());
	        session.setAttribute("total", totalPrice(cart));
	        return "redirect:" + referLink;
	    }

	    @GetMapping("/remove/{id}")
	    public String remove(@PathVariable int id, Model model, HttpSession session,RedirectAttributes redirect, HttpServletRequest httpServletRequest) {
	        Map<Integer, Cart> cart = (Map<Integer, Cart>) session.getAttribute("cart");

	        cart.remove(id);
	        if (cart.size() == 0) {
	            session.removeAttribute("cart");
	        }
	        session.setAttribute("size", cart.size());
	        session.setAttribute("total", totalPrice(cart));
	        String referLink = httpServletRequest.getHeader("referer");
	        return "redirect:" + referLink;
	    }

	    @GetMapping("/clear")
	    public String clear(HttpSession session, HttpServletRequest httpServletRequest) {

	        session.removeAttribute("cart");
	        
	        String referLink = httpServletRequest.getHeader("referer");

	        return "redirect:" + referLink;
	    }
	        
	    
	    @GetMapping("/User/Giohang/checkout")
	    public String checkout(HttpSession session,RedirectAttributes redirect, HttpServletRequest httpServletRequest) {
	    	Map<Integer, Cart> cart = (Map<Integer, Cart>) session.getAttribute("cart");
	    	Order order = new Order();
	    	final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			final User currentUser = userService.findByEmail(authentication.getName());
	    	order.setUser(currentUser);
	    	order.setAmount(totalPrice(cart));
	    	order.setStatus(0);
	    	Date date = new Date();
	    	order.setDate(date);
	    	List<OrderDetail> detail =  new ArrayList<>();
	    	   for (Cart value : cart.values()) {
	    		   OrderDetail newdetail = new OrderDetail();
	    		   Fish fish = new Fish(value.getId());
	    		   newdetail.setFish(fish);
	    		   newdetail.setQuality(value.getQuantity());
	    		   newdetail.setOrder(order);
	    		   detail.add(newdetail);
		        } 
	    	order.setOrderDetail(detail);
	    	orderService.save(order);
	    	orderDetailReponsitory.saveAll(detail);
	    	session.removeAttribute("cart");
			redirect.addFlashAttribute("successMessage", "Đặt Hàng Thành Công");

	       return "redirect:/DanhsachCagiong";
	    }
	    
	    public Integer totalPrice(Map<Integer, Cart> cartItems) {
	        int count = 0;
	        for (Entry<Integer, Cart> list : cartItems.entrySet()) {
	            count += list.getValue().getPrice() * list.getValue().getQuantity();
	        }
	        return count;
	    }
	    
	    
}
