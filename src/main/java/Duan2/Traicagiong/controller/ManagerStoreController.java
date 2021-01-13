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
import org.springframework.http.MediaType;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import Duan2.Traicagiong.entity.Fish;
import Duan2.Traicagiong.entity.Store;
import Duan2.Traicagiong.repository.StoreRepository;
import Duan2.Traicagiong.service.BackGroundService;
import Duan2.Traicagiong.service.FishService;
import Duan2.Traicagiong.service.FishTopService;

@Controller
public class ManagerStoreController {

	

	@Autowired
	private StoreRepository storeRepository;

	
	@RequestMapping(value = "/Admin/Store", method = RequestMethod.GET)
	public String store (Model model) {
		if(storeRepository.findAll().size() == 1) {
			model.addAttribute("size", 1);
		}
		model.addAttribute("fishs", storeRepository.findAll());
		return "Store_infor";
	}
	
	@RequestMapping(value = "/Admin/StoreUpdate/{id}", method = RequestMethod.GET)
	public String storeUpdate (@PathVariable("id") Integer id, Model model) {
		Optional<Store> fish = storeRepository.findById(id);
		model.addAttribute("fish", fish.get());
		return "Store";
	}
	
	@RequestMapping(value = "/Admin/StoreUpdate/save", method = RequestMethod.POST)
	public String storeUpdateSave (@RequestParam("filename") MultipartFile file, Store contact, BindingResult result,
			RedirectAttributes redirect) {
		if (!file.isEmpty()) {
			if (!(file.getOriginalFilename().endsWith(".png") || file.getOriginalFilename().endsWith(".jpg")
					|| file.getOriginalFilename().endsWith(".jpeg"))) {
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


		storeRepository.save(contact);
		redirect.addFlashAttribute("successMessage", "Update thành công");
		return "redirect:/Admin/Store";
	}
	
	@PostMapping("/Admin/Store/save")
	public String save(@RequestParam("filename") MultipartFile file, Store contact, BindingResult result,
			RedirectAttributes redirect) {
		if (file.isEmpty()) {
			redirect.addFlashAttribute("message", "Vui lòng chọn ảnh ");
			return "redirect:/Admin/AddStore";
		}

		if (!(file.getOriginalFilename().endsWith(".png") || file.getOriginalFilename().endsWith(".jpg")
				|| file.getOriginalFilename().endsWith(".jpeg") || file.getOriginalFilename().endsWith(".JPEG")
				|| file.getOriginalFilename().endsWith(".PNG") || file.getOriginalFilename().endsWith(".jpg"))) {
			redirect.addFlashAttribute("message", "Vui Lòng Nhập Đúng file");
			return "redirect:/Admin/AddStore";
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
		storeRepository.save(contact);
		redirect.addFlashAttribute("successMessage", "Thêm Thông Tin Cửa Hàng Thành Công");
		return "redirect:/Admin/Store";
	}
	
	@RequestMapping(value = "/Admin/AddStore", method = RequestMethod.GET)
	public String AddStore (Model model) {
		model.addAttribute("store",new Store() );
		return "AddStore";
	}
	
}