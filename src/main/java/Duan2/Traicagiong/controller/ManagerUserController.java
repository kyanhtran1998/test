package Duan2.Traicagiong.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import Duan2.Traicagiong.entity.Fish;
import Duan2.Traicagiong.entity.User;
import Duan2.Traicagiong.repository.UserRepository;
import Duan2.Traicagiong.service.FishService;
import Duan2.Traicagiong.service.UserService;

@Controller
public class ManagerUserController {
	@Autowired
	private UserService userservice;
	

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    
	@GetMapping("/Admin/ListUser")
	public String getUsers(Model model, HttpServletRequest request, RedirectAttributes redirect) {
		request.getSession().setAttribute("users", null);

		if (model.asMap().get("success") != null)
			redirect.addFlashAttribute("success", model.asMap().get("success").toString());
		return "redirect:/Admin/ListUser/page/1";
	}
	
	
	@GetMapping("/Admin/ListUser/page/{pageNumber}")
	 public String listCustomer(HttpServletRequest request, @PathVariable int pageNumber, Model model) {
		PagedListHolder<?> pages = (PagedListHolder<?>) request.getSession().getAttribute("users");
		int pagesize = 5;
		List<User> list = userservice.findAll();
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
		request.getSession().setAttribute("listfish", pages);
		int current = pages.getPage() + 1;
		int begin = Math.max(1, current - list.size());
		int end = Math.min(begin + 5, pages.getPageCount());
		int totalPageCount = pages.getPageCount();
		String baseUrl = "/Admin/ListUser/page/";

		model.addAttribute("beginIndex", begin);
		model.addAttribute("endIndex", end);
		model.addAttribute("currentIndex", current);
		model.addAttribute("totalPageCount", totalPageCount);
		model.addAttribute("baseUrl", baseUrl);
		model.addAttribute("users", pages);
	  
	
		return "ListUser";
	}
	
	@GetMapping("/Admin/ListUser/search/{pageNumber}")
	public String searchFish(@RequestParam("term") String term, Model model, HttpServletRequest request,
			@PathVariable int pageNumber) {
		List<User> list = (List<User>) userservice.findByEmail(term);
		PagedListHolder<?> pages = (PagedListHolder<?>) request.getSession().getAttribute("users");
		int pagesize = 5;
		pages = new PagedListHolder<>(list);
		pages.setPageSize(pagesize);

		final int goToPage = pageNumber - 1;
		if (goToPage <= pages.getPageCount() && goToPage >= 0) {
			pages.setPage(goToPage);
		}
		request.getSession().setAttribute("users", pages);
		int current = pages.getPage() + 1;
		int begin = Math.max(1, current - list.size());
		int end = Math.min(begin + 5, pages.getPageCount());
		int totalPageCount = pages.getPageCount();
		String baseUrl = "/Admin/ListFish/page/";
		model.addAttribute("beginIndex", begin);
		model.addAttribute("endIndex", end);
		model.addAttribute("currentIndex", current);
		model.addAttribute("totalPageCount", totalPageCount);
		model.addAttribute("baseUrl", baseUrl);
		model.addAttribute("users", pages);
		return "ListUser";
	}
	
	@GetMapping("/Admin/UserDelete/{id}")
	public String detele(@PathVariable int id, RedirectAttributes reAttributes) {
		userservice.delete(id);
		reAttributes.addFlashAttribute("successMessage", "Deleted product succsessfully");
		return "redirect:/Admin/ListUser";

	}
	
	
}
