package Duan2.Traicagiong.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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
import Duan2.Traicagiong.service.FishService;
import Duan2.Traicagiong.service.FishTopService;

@Controller
public class ManagerFishTopController {

	@Autowired
	private FishTopService fishtopService;
	
	@Autowired
	private FishService fishService;
	
	@GetMapping("/Admin/ListFishTop")
	public String ListFishTop(Model model) {
		model.addAttribute("fishs", fishtopService.findAll());
		model.addAttribute("fishTop", fishService.findTopFishNew());
		return "ListFishTop";
	}

	
	
	@GetMapping("/Admin/ListFishTop/{id}")
	public String detele(@PathVariable int id, RedirectAttributes reAttributes) {
		fishtopService.delete(id);
		reAttributes.addFlashAttribute("successMessage", "Xóa Cá Thành Công");
		return "redirect:/Admin/ListFishTop";

	}
	
	
	
}