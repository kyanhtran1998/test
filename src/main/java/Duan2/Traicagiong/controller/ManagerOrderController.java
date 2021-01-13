package Duan2.Traicagiong.controller;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import Duan2.Traicagiong.entity.Cart;
import Duan2.Traicagiong.entity.Fish;
import Duan2.Traicagiong.entity.Order;
import Duan2.Traicagiong.entity.OrderDetail;
import Duan2.Traicagiong.entity.User;
import Duan2.Traicagiong.repository.OrderDetailReponsitory;
import Duan2.Traicagiong.repository.OrderReponsitory;
import Duan2.Traicagiong.service.FishService;
import Duan2.Traicagiong.service.OrderService;
import Duan2.Traicagiong.service.UserService;

@Controller
public class ManagerOrderController {	

	@Autowired
	private OrderService orderService;
	
	@Autowired
	private OrderDetailReponsitory orderDetailReponsitory;
	
	@Autowired
	private FishService fishService;

	
	@GetMapping("/Admin/ListOrder")
	public String ListOrder(Model model, HttpServletRequest request, RedirectAttributes redirect) {
		request.getSession().setAttribute("listorder", null);

		if (model.asMap().get("success") != null)
			redirect.addFlashAttribute("success", model.asMap().get("success").toString());
		return "redirect:/Admin/ListOrder/page/1";
	}
	
	
	@GetMapping("/Admin/ListOrder/page/{pageNumber}")
	 public String listOrderPage(HttpServletRequest request, @PathVariable int pageNumber, Model model) {
		PagedListHolder<?> pages = (PagedListHolder<?>) request.getSession().getAttribute("listorder");
		int pagesize = 5;
		List<Order> list = orderService.GetAllOrder();
		System.out.println(list.size());
		if (pages == null) {
			pages = new PagedListHolder<>(list);
			pages.setPageSize(pagesize);
		} else {
			final int goToPage = pageNumber - 1;
			if (goToPage <= pages.getPageCount() && goToPage >= 0) {
				pages.setPage(goToPage);
			}
		}
		request.getSession().setAttribute("listorder", pages);
		int current = pages.getPage() + 1;
		int begin = Math.max(1, current - list.size());
		int end = Math.min(begin + 5, pages.getPageCount());
		int totalPageCount = pages.getPageCount();
		String baseUrl = "/Admin/ListOrder/page/";

		model.addAttribute("beginIndex", begin);
		model.addAttribute("endIndex", end);
		model.addAttribute("currentIndex", current);
		model.addAttribute("totalPageCount", totalPageCount);
		model.addAttribute("baseUrl", baseUrl);
		model.addAttribute("listorder", pages);
	  
	
		return "ListOrder";
	}
	
	@GetMapping("/Admin/ListOrder/search/{pageNumber}")
	public String searchOrder(@RequestParam("term") String term, Model model, HttpServletRequest request,
			@PathVariable int pageNumber) {
		List<Order> list = orderService.GetAllOrder();
		PagedListHolder<?> pages = (PagedListHolder<?>) request.getSession().getAttribute("listorder");
		int pagesize = 5;
		pages = new PagedListHolder<>(list);
		pages.setPageSize(pagesize);

		final int goToPage = pageNumber - 1;
		if (goToPage <= pages.getPageCount() && goToPage >= 0) {
			pages.setPage(goToPage);
		}
		request.getSession().setAttribute("listorder", pages);
		int current = pages.getPage() + 1;
		int begin = Math.max(1, current - list.size());
		int end = Math.min(begin + 5, pages.getPageCount());
		int totalPageCount = pages.getPageCount();
		String baseUrl = "/Admin/ListOrder/page/";
		model.addAttribute("beginIndex", begin);
		model.addAttribute("endIndex", end);
		model.addAttribute("currentIndex", current);
		model.addAttribute("totalPageCount", totalPageCount);
		model.addAttribute("baseUrl", baseUrl);
		model.addAttribute("listorder", pages);
		return "ListUser";
	}
	
	@GetMapping("/Admin/OrderDelete/{id}")
	public String detele(@PathVariable int id, RedirectAttributes reAttributes) {
		orderService.delete(id);
		reAttributes.addFlashAttribute("successMessage", "Xóa Đơn Hàng Thành Công");
		return "redirect:/Admin/ListOrder";

	}
	
	

	@GetMapping("/Admin/ListOrder/OrderDetail/{id}")
	public String Orderdetail(@PathVariable int id, Model model) {
		Optional<Order> orderdetail = orderService.findOne(id);
		model.addAttribute("listorder", orderdetail.get());
		
		return "ListOrderdetail";

	}
	
	@GetMapping("/Admin/ListOrder/OrderDetailUpdate/{id}")
	public String UpdateOrderdetail(@PathVariable int id, Model model,RedirectAttributes redirect) {
		Optional<Order> orderdetail = orderService.findOne(id);
		Order order = new Order(orderdetail.get().getId(),orderdetail.get().getUser(),1,orderdetail.get().getDate(),orderdetail.get().getAmount());
		orderService.save(order);
		List<OrderDetail> list = orderDetailReponsitory.FindOrder(id);
		UpdateFish(list);
		redirect.addFlashAttribute("successMessage", "Xác Nhận Đơn Hàng Thành Công");
		return "redirect:/Admin/ListOrder/OrderDetail/{id}";

	}
	
	
	 public void UpdateFish(List<OrderDetail> list) {
	   for (OrderDetail orderDetail : list) {
		   Optional<Fish> fish = fishService.findOne(orderDetail.getFish().getId());
		   int soluong = fish.get().getQuality();
		   Fish fishupdate = new Fish(fish.get().getId(),fish.get().getName(),fish.get().getDescription(),fish.get().getImage(),
				   fish.get().getPrice(), soluong-orderDetail.getQuality(),fish.get().getDate());
		   fishService.save(fishupdate);
	   	}
	  
	}
}
