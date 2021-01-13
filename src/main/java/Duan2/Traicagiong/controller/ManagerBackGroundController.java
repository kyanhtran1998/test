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

import Duan2.Traicagiong.entity.Background;
import Duan2.Traicagiong.entity.Fish;
import Duan2.Traicagiong.repository.StoreRepository;
import Duan2.Traicagiong.service.BackGroundService;
import Duan2.Traicagiong.service.FishService;
import Duan2.Traicagiong.service.FishTopService;

@Controller
public class ManagerBackGroundController {

	@Autowired
	private BackGroundService backGroundService;
	


	@RequestMapping(value = "/Admin/ListBackGround", method = RequestMethod.GET)
	public String getAllBackground(Model model) {
		model.addAttribute("fishs", backGroundService.findAll());
		return "ListBackGround";
	}
	@GetMapping("/Admin/ListBackGround/{id}")
	public String detele(@PathVariable int id, RedirectAttributes reAttributes) {
		backGroundService.delete(id);
		reAttributes.addFlashAttribute("successMessage", "Xóa Dữ Liệu Thành Công");
		return "redirect:/Admin/ListBackGround";

	}
	
	
	@RequestMapping(value =  "/Admin/ListBackGround/Add", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public String AddImageBackGround(@RequestParam("filename") MultipartFile file, Background contact, BindingResult result,
			RedirectAttributes redirect) throws IOException 
			 {	
				Background background = new Background();

				boolean isFileOK = false;
				
				byte[] bytes = file.getBytes();
			    String filename = file.getOriginalFilename();
			    Path path = Paths.get("./uploads/" + filename);
				String fileName = StringUtils.cleanPath(file.getOriginalFilename());
				if (filename.endsWith("jpg") || filename.endsWith("png")|| filename.endsWith("jpeg")||filename.endsWith("JPG") || filename.endsWith("PNG")|| filename.endsWith("JPEG")) {
			            isFileOK = true;
			    }
				 if (!isFileOK) {
					 redirect.addFlashAttribute("message", "Hình Không Đúng Định Dạng");
					 redirect.addFlashAttribute("alertClass", "alert-danger");
			            
				} else {
		            background.setImage(fileName);
					backGroundService.save(background);
		            Files.write(path, bytes);
		            redirect.addFlashAttribute("message", "Thêm Ảnh Thành Công");
					redirect.addFlashAttribute("alertClass", "alert-success");
		        }
				
			
				return "redirect:/Admin/ListBackGround";
	}
	
	
	
	
}