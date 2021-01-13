package Duan2.Traicagiong.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

import Duan2.Traicagiong.entity.Blog;
import Duan2.Traicagiong.entity.Fish;
import Duan2.Traicagiong.entity.User;
import Duan2.Traicagiong.repository.StoreRepository;
import Duan2.Traicagiong.service.BackGroundService;
import Duan2.Traicagiong.service.BlogService;
import Duan2.Traicagiong.service.FishService;
import Duan2.Traicagiong.service.FishTopService;
import Duan2.Traicagiong.service.MailService;
import Duan2.Traicagiong.service.UserService;

@Controller
public class HomeCustomerController {
	@Autowired
	private FishTopService fishtopService;
	@Autowired
	private UserService userservice;

	@Autowired
	private BackGroundService backGroundService;

	@Autowired
	private StoreRepository storeRepository;

	@Autowired
	private BlogService blogService;

	@Autowired
	MailService mailService;
	@Autowired
	private FishService fishService;
	
	@Autowired
    private BCryptPasswordEncoder passwordEncoder;

	@GetMapping({ "/TrangChu", "/" })
	public String Trangchu(Model model) {
		model.addAttribute("fishs", fishtopService.findAll());
		model.addAttribute("fishTop", fishService.findTopFishNew());
		model.addAttribute("backGround", backGroundService.findAll());
		model.addAttribute("Top10Fish", fishService.findAllTop10Fish());
		model.addAttribute("lienhe", storeRepository.findAll());
		return "TrangChu";
	}

	@GetMapping("/ChiTietCaGiong/{id}")
	public String Chitietcagiong(@PathVariable("id") Integer id, Model model) {
		Optional<Fish> fish = fishService.findOne(id);
		model.addAttribute("fish", fish.get());
		model.addAttribute("fishTop", fishService.findTopFishNew());
		model.addAttribute("user", userservice.findByEmail("Admin"));
		model.addAttribute("fishs", fishtopService.findAll());
		model.addAttribute("lienhe", storeRepository.findAll());
		model.addAttribute("Top10Fish", fishService.findAllTop10Fish());

		return "ChiTietCaGiong";
	}

	@GetMapping("/LienHe")
	public String LienHe(Model model) {
		model.addAttribute("lienhe", storeRepository.findAll());
		return "LienHe";
	}

	@GetMapping("/GioiThieu")
	public String GioiThieu(Model model) {
		model.addAttribute("lienhe", storeRepository.findAll());
		return "GioiThieu";
	}

	@GetMapping("/DanhsachCagiong")
	public String ShowTrangcagiong(Model model, HttpServletRequest request, RedirectAttributes redirect) {
		if (model.asMap().get("successMessage") != null)
			redirect.addFlashAttribute("successMessage", model.asMap().get("successMessage").toString());

		request.getSession().setAttribute("DanhsachCagiong", null);
		return "redirect:/DanhsachCagiong/trang/1";
	}

	@GetMapping("/DanhsachCagiong/trang/{pageNumber}")
	public String showFishPage(HttpServletRequest request, @PathVariable int pageNumber, Model model) {
		PagedListHolder<?> pages = (PagedListHolder<?>) request.getSession().getAttribute("DanhsachCagiong");

		int pagesize = 9;
		List<Fish> list = fishService.findAll();
		if (pages == null) {
			pages = new PagedListHolder<>(list);
			pages.setPageSize(pagesize);
		} else {
			final int goToPage = pageNumber - 1;
			if (goToPage <= pages.getPageCount() && goToPage >= 0) {
				pages.setPage(goToPage);
			}
		}
		request.getSession().setAttribute("DanhsachCagiong", pages);
		int current = pages.getPage() + 1;
		int begin = Math.max(1, current - list.size());
		int end = Math.min(begin + 5, pages.getPageCount());
		int totalPageCount = pages.getPageCount();
		String baseUrl = "/DanhsachCagiong/trang/";

		model.addAttribute("beginIndex", begin);
		model.addAttribute("endIndex", end);
		model.addAttribute("currentIndex", current);
		model.addAttribute("totalPageCount", totalPageCount);
		model.addAttribute("baseUrl", baseUrl);
		model.addAttribute("fishs", pages);
		model.addAttribute("lienhe", storeRepository.findAll());
		model.addAttribute("fishtop", fishtopService.findAll());
		model.addAttribute("Top10Fish", fishService.findAllTop10Fish());

		return "ShowCaGiong";
	}

	@GetMapping("/DanhsachCagiong/timkiem/{pageNumber}")
	public String searchFish(@RequestParam("term") String term, Model model, HttpServletRequest request,
			@PathVariable int pageNumber) {
		List<Fish> list = fishService.search(term);
		PagedListHolder<?> pages = (PagedListHolder<?>) request.getSession().getAttribute("DanhsachCagiong");
		int pagesize = 9;
		pages = new PagedListHolder<>(list);
		pages.setPageSize(pagesize);

		final int goToPage = pageNumber - 1;
		if (goToPage <= pages.getPageCount() && goToPage >= 0) {
			pages.setPage(goToPage);
		}
		request.getSession().setAttribute("DanhsachCagiong", pages);
		int current = pages.getPage() + 1;
		int begin = Math.max(1, current - list.size());
		int end = Math.min(begin + 5, pages.getPageCount());
		int totalPageCount = pages.getPageCount();
		String baseUrl = "/DanhsachCagiong/trang/";
		model.addAttribute("beginIndex", begin);
		model.addAttribute("endIndex", end);
		model.addAttribute("currentIndex", current);
		model.addAttribute("totalPageCount", totalPageCount);
		model.addAttribute("baseUrl", baseUrl);
		model.addAttribute("fishs", pages);
		model.addAttribute("lienhe", storeRepository.findAll());
		model.addAttribute("fishtop", fishtopService.findAll());
		model.addAttribute("Top10Fish", fishService.findAllTop10Fish());

		return "ShowCaGiong";
	}

	@GetMapping("/TinTuc")
	public String TinTuc(Model model, HttpServletRequest request, RedirectAttributes redirect) {
		request.getSession().setAttribute("Danhsachtintuc", null);
		return "redirect:/TinTuc/trang/1";
	}

	@GetMapping("/TinTuc/trang/{pageNumber}")
	public String TinTucPage(HttpServletRequest request, @PathVariable int pageNumber, Model model) {
		PagedListHolder<?> pages = (PagedListHolder<?>) request.getSession().getAttribute("Danhsachtintuc");

		int pagesize = 3;
		List<Blog> list = blogService.findAll();
		if (pages == null) {
			pages = new PagedListHolder<>(list);
			pages.setPageSize(pagesize);
		} else {
			final int goToPage = pageNumber - 1;
			if (goToPage <= pages.getPageCount() && goToPage >= 0) {
				pages.setPage(goToPage);
			}
		}
		request.getSession().setAttribute("Danhsachtintuc", pages);
		int current = pages.getPage() + 1;
		int begin = Math.max(1, current - list.size());
		int end = Math.min(begin + 5, pages.getPageCount());
		int totalPageCount = pages.getPageCount();
		String baseUrl = "/TinTuc/trang/";

		model.addAttribute("beginIndex", begin);
		model.addAttribute("endIndex", end);
		model.addAttribute("currentIndex", current);
		model.addAttribute("totalPageCount", totalPageCount);
		model.addAttribute("baseUrl", baseUrl);
		model.addAttribute("fishs", pages);
		model.addAttribute("lienhe", storeRepository.findAll());
		model.addAttribute("fishtop", fishtopService.findAll());
		model.addAttribute("Top10Fish", fishService.findAllTop10Fish());

		return "TinTuc";
	}

	@GetMapping("/TinTuc/{id}")
	public String TinTucChiTiet(@PathVariable("id") Integer id, Model model) {
		Optional<Blog> fish = blogService.findOne(id);
		model.addAttribute("fish", fish.get());
		model.addAttribute("lienhe", storeRepository.findAll());

		return "TinTucChiTiet";
	}
	
	@GetMapping("/QuenMatKhau")
	public String SendEmail() {
		return "GuiEmail";
	}
	
	@PostMapping("/QuenMatKhau")
	public String SendEmail(@RequestParam(value = "email", required = false) String email,Model model, RedirectAttributes reAttributes) {
		User user = userservice.findByEmail(email);
	    if (user == null){
	    	reAttributes.addFlashAttribute("message", "Tài Khoản Không tồn tại trong hệ thống");
	    	return "redirect:/QuenMatKhau" ;
	    }
	    String password = MailService.randomAlphaNumeric(8);
		mailService.sendMail(user.getName(),password);
	    user.setPassword(passwordEncoder.encode(password));
	    userservice.save(user);
		reAttributes.addFlashAttribute("success", "Mật Khẩu Mới Đã Gửi Đến Email");
		return "redirect:/QuenMatKhau" ;
	}
	
}
