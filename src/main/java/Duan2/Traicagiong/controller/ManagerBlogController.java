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

import Duan2.Traicagiong.entity.Blog;
import Duan2.Traicagiong.entity.Fish;
import Duan2.Traicagiong.entity.FishTop;
import Duan2.Traicagiong.service.BlogService;
import Duan2.Traicagiong.service.FishService;
import Duan2.Traicagiong.service.FishTopService;

@Controller
public class ManagerBlogController {

	@Autowired
	private BlogService blogService;
	
	@GetMapping("/Admin/ListBlog")
	public String index(Model model, HttpServletRequest request, RedirectAttributes redirect) {
		request.getSession().setAttribute("listBlog", null);

		if (model.asMap().get("successMessage") != null)
			redirect.addFlashAttribute("successMessage", model.asMap().get("successMessage").toString());

		return "redirect:/Admin/ListBlog/page/1";
	}
	
	@GetMapping("/Admin/ListBlog/search/{pageNumber}")
	public String searchFish(@RequestParam("term") String term, Model model, HttpServletRequest request,
			@PathVariable int pageNumber) {
		List<Blog> list = blogService.search(term);
		PagedListHolder<?> pages = (PagedListHolder<?>) request.getSession().getAttribute("listBlog");
		int pagesize = 5;
		pages = new PagedListHolder<>(list);
		pages.setPageSize(pagesize);

		final int goToPage = pageNumber - 1;
		if (goToPage <= pages.getPageCount() && goToPage >= 0) {
			pages.setPage(goToPage);
		}
		request.getSession().setAttribute("listBlog", pages);
		int current = pages.getPage() + 1;
		int begin = Math.max(1, current - list.size());
		int end = Math.min(begin + 5, pages.getPageCount());
		int totalPageCount = pages.getPageCount();
		String baseUrl = "/Admin/ListBlog/page/";
		model.addAttribute("beginIndex", begin);
		model.addAttribute("endIndex", end);
		model.addAttribute("currentIndex", current);
		model.addAttribute("totalPageCount", totalPageCount);
		model.addAttribute("baseUrl", baseUrl);
		model.addAttribute("Blogs", pages);
		return "ListBlog";
	}
	
	
	@GetMapping("/Admin/ListBlog/page/{pageNumber}")
	public String showFishPage(HttpServletRequest request, @PathVariable int pageNumber, Model model) {
		PagedListHolder<?> pages = (PagedListHolder<?>) request.getSession().getAttribute("listBlog");
		int pagesize = 5;
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
		request.getSession().setAttribute("listBlog", pages);
		int current = pages.getPage() + 1;
		int begin = Math.max(1, current - list.size());
		int end = Math.min(begin + 5, pages.getPageCount());
		int totalPageCount = pages.getPageCount();
		String baseUrl = "/Admin/ListBlog/page/";

		model.addAttribute("beginIndex", begin);
		model.addAttribute("endIndex", end);
		model.addAttribute("currentIndex", current);
		model.addAttribute("totalPageCount", totalPageCount);
		model.addAttribute("baseUrl", baseUrl);
		model.addAttribute("Blogs", pages);

		return "ListBlog";
	}
	
	
	
	
	
	@GetMapping("/Admin/AddBlog")
	public String AddBlog(Model model) {
		model.addAttribute("blog", new Blog());
		return "AddBlog";
	}
	
	@PostMapping("/Admin/Blog/save")
	public String save(@RequestParam("filename") MultipartFile file, Blog contact, BindingResult result,
			RedirectAttributes redirect) {
		if (file.isEmpty()) {
			redirect.addFlashAttribute("message", "Vui lòng chọn ảnh ");
			return "redirect:/Admin/AddBlog";
		}

		if (!(file.getOriginalFilename().endsWith(".png") || file.getOriginalFilename().endsWith(".jpg")
				|| file.getOriginalFilename().endsWith(".jpeg"))) {
			redirect.addFlashAttribute("message", "Vui Lòng Nhập Đúng file");
			return "redirect:/Admin/AddBlog";
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
		Date date = new Date();
		contact.setDate(date);
		blogService.save(contact);
		redirect.addFlashAttribute("successMessage", "Thêm Cá giống Thành Công");
		return "redirect:/Admin/ListBlog";
	}	
	
	
	@RequestMapping("/Admin/BlogUpdate/{id}")
	public String edit(@PathVariable("id") Integer id, Model model) {
		Optional<Blog> Blog = blogService.findOne(id);
		model.addAttribute("blog", Blog.get());
		return "UploadBlog";
	}
	
	@PostMapping("/Admin/BlogUpdate/save")
	public String saveUpload(@RequestParam("filename") MultipartFile file, Blog contact, BindingResult result,
			RedirectAttributes redirect) {
		if (!file.isEmpty()) {
			if (!(file.getOriginalFilename().endsWith(".png") || file.getOriginalFilename().endsWith(".jpg")
					|| file.getOriginalFilename().endsWith(".jpeg") || file.getOriginalFilename().endsWith(".JPEG")
					|| file.getOriginalFilename().endsWith(".PNG") || file.getOriginalFilename().endsWith(".jpg"))) {
				redirect.addFlashAttribute("message", "Vui Lòng Nhập Đúng file");
				return "redirect:/Admin/BlogUpdate/" + contact.getId();
			}
			// normalize the file path
			String fileName = StringUtils.cleanPath(file.getOriginalFilename());

			// save the file on the local file system
			try {
				System.out.print(file.getSize());
				Path path = Paths.get("./uploads/" + fileName);
				Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e) {
				e.printStackTrace();
			}
			contact.setImage(fileName);
			// check if file is empty
		}
		Date date = new Date();
		contact.setDate(date);
		blogService.save(contact);
		redirect.addFlashAttribute("successMessage", "Update Tin Tức thành công");
		return "redirect:/Admin/ListBlog";
	}
	
	@GetMapping("/Admin/BlogDelete/{id}")
	public String detele(@PathVariable int id, RedirectAttributes reAttributes) {
		blogService.delete(id);
		reAttributes.addFlashAttribute("successMessage", "Xóa Tin Tức Thành Công");
		return "redirect:/Admin/ListBlog";

	}

}
