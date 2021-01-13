package Duan2.Traicagiong.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import Duan2.Traicagiong.entity.Fish;
import Duan2.Traicagiong.entity.FishTop;
import Duan2.Traicagiong.service.FishService;
import Duan2.Traicagiong.service.FishTopService;

@Controller
public class ManagerFishController {

	@Autowired
	private FishTopService fishtopService;
	@Autowired
	private FishService fishService;

	@GetMapping("/Admin/ListFish")
	public String index(Model model, HttpServletRequest request, RedirectAttributes redirect) {
		request.getSession().setAttribute("listfish", null);

		if (model.asMap().get("successMessage") != null)
			redirect.addFlashAttribute("successMessage", model.asMap().get("successMessage").toString());
		if (model.asMap().get("failMessage") != null)
		redirect.addFlashAttribute("failMessage", model.asMap().get("failMessage").toString());

		return "redirect:/Admin/ListFish/page/1";
	}

	@GetMapping("/Admin/ListFish/search/{pageNumber}")
	public String searchFish(@RequestParam("term") String term, Model model, HttpServletRequest request,
			@PathVariable int pageNumber) {
		List<Fish> list = fishService.search(term);
		PagedListHolder<?> pages = (PagedListHolder<?>) request.getSession().getAttribute("listfish");
		int pagesize = 5;
		pages = new PagedListHolder<>(list);
		pages.setPageSize(pagesize);

		final int goToPage = pageNumber - 1;
		if (goToPage <= pages.getPageCount() && goToPage >= 0) {
			pages.setPage(goToPage);
		}
		request.getSession().setAttribute("listfish", pages);
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
		model.addAttribute("fishs", pages);
		return "ListFish";
	}

	@GetMapping("/Admin/ListFish/page/{pageNumber}")
	public String showFishPage(HttpServletRequest request, @PathVariable int pageNumber, Model model) {
		PagedListHolder<?> pages = (PagedListHolder<?>) request.getSession().getAttribute("listfish");
		int pagesize = 5;
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
		request.getSession().setAttribute("listfish", pages);
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
		model.addAttribute("fishs", pages);

		return "ListFish";
	}

	@GetMapping("/Admin/AddFish")
	public String add(Model model) {
		model.addAttribute("fish", new Fish());
		model.addAttribute("test", "sssss");
		return "AddFish";
	}

	@PostMapping("/Admin/Fish/save")
	public String save(@RequestParam("filename") MultipartFile file, Fish contact, BindingResult result,
			RedirectAttributes redirect) {
		if (file.isEmpty()) {
			redirect.addFlashAttribute("message", "Vui lòng chọn ảnh ");
			return "redirect:/Admin/AddFish";
		}

		if (!(file.getOriginalFilename().endsWith(".png") || file.getOriginalFilename().endsWith(".jpg")
				|| file.getOriginalFilename().endsWith(".jpeg") || file.getOriginalFilename().endsWith(".JPEG")
				|| file.getOriginalFilename().endsWith(".PNG") || file.getOriginalFilename().endsWith(".jpg"))) {
			redirect.addFlashAttribute("message", "Vui Lòng Nhập Đúng file");
			return "redirect:/Admin/AddFish";
		}

		// normalize the file path
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());

		// save the file on the local file system
		try {
			Path path = Paths.get("./uploads/" + fileName);
			Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
		}
		contact.setImage(fileName);
		if (contact.getPrice() == null) {
			contact.setPrice(0);
		}
		// check if file is empty
		Date date = new Date();
		contact.setDate(date);
		fishService.save(contact);
		redirect.addFlashAttribute("successMessage", "Thêm Cá giống Thành Công");
		return "redirect:/Admin/ListFish";
	}

	@PostMapping("/Admin/FishUpload/save")
	public String saveUpload(@RequestParam("filename") MultipartFile file, Fish contact, BindingResult result,
			RedirectAttributes redirect) {
		if (!file.isEmpty()) {
			if (!(file.getOriginalFilename().endsWith(".png") || file.getOriginalFilename().endsWith(".jpg")
					|| file.getOriginalFilename().endsWith(".jpeg") || file.getOriginalFilename().endsWith(".JPEG")
					|| file.getOriginalFilename().endsWith(".PNG") || file.getOriginalFilename().endsWith(".jpg"))) {
				redirect.addFlashAttribute("message", "Vui Lòng Nhập Đúng file");
				return "redirect:/Admin/FishUpdate/" + contact.getId();
			}
			// normalize the file path
			String fileName = StringUtils.cleanPath(file.getOriginalFilename());

			// save the file on the local file system
			try {
				Path path = Paths.get("./uploads/" + fileName);
				Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e) {
				e.printStackTrace();
			}
			contact.setImage(fileName);
			// check if file is empty
		}
		if (contact.getPrice() == null) {
			contact.setPrice(0);
		}
		Date date = new Date();
		contact.setDate(date);
		
		fishService.save(contact);
		redirect.addFlashAttribute("successMessage", "Update Cá giống thành công");
		return "redirect:/Admin/ListFish";
	}

	@GetMapping("/Admin/FishDelete/{id}")
	public String detele(@PathVariable int id, RedirectAttributes reAttributes)  {
		 try {
			fishService.delete(id);
			reAttributes.addFlashAttribute("successMessage", "Xóa Cá Giống Thành Công");
	        } catch (Exception e) {
				reAttributes.addFlashAttribute("successMessage", "Xóa Cá giống thất bại");
				return "redirect:/Admin/ListFish";
	        }
		
		return "redirect:/Admin/ListFish";

	}

	@RequestMapping("/Admin/FishUpdate/{id}")
	public String edit(@PathVariable("id") Integer id, Model model) {
		Optional<Fish> fish = fishService.findOne(id);
		model.addAttribute("fish", fish.get());
		return "UploadFish";
	}

	@GetMapping("/Admin/ListFishAddTop/{id}")
	public String add(@PathVariable int id, RedirectAttributes reAttributes) {
		Optional<Fish> fish1 = fishService.findOne(id);
		FishTop test =  fishtopService.checkFishId(id) ;
		if(test != null) {
			reAttributes.addFlashAttribute("failMessage", "Sản phẩm Đã Được thêm vào  ");
			return "redirect:/Admin/ListFish";
		}
		FishTop fishTop = new FishTop();
		Fish fish = new Fish(fish1.get().getId());
		fishTop.setFish(fish);
		
		fishtopService.save(fishTop);
		reAttributes.addFlashAttribute("successMessage", "Đã Thêm Sản Phẩm Nổi Bật ");
		return "redirect:/Admin/ListFish";

	}

}