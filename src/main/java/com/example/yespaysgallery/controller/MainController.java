package com.example.yespaysgallery.controller;


import java.io.IOException;
import java.security.Principal;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.example.yespaysgallery.model.Category;
import com.example.yespaysgallery.model.Image;
import com.example.yespaysgallery.model.User;
import com.example.yespaysgallery.repository.CategoryRepository;
import com.example.yespaysgallery.services.ImageService;
import com.example.yespaysgallery.services.UserService;


@Controller
public class MainController {

	private static final String[] header = {"Gallery", "My Gallery", "New Orders"};

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private ImageService imageService;

	@Autowired
	private UserService userService;


	@GetMapping("/")
	public String index(Model model) {
		model.addAttribute("categories", categoryRepository.findAll());
		
		return "index";
	}

	@GetMapping("/home")
	public String getHomePage(Model model, Principal principal){
		model.addAttribute("username", principal.getName());
		model.addAttribute("categories", categoryRepository.findAll());

		return "home";
	}

	@GetMapping("/gallery")
	public String getGallery(Model model, Principal principal){
		String username = (principal != null) ? principal.getName(): "anynemous";
		model.addAttribute("isCompleted", true);
		model.addAttribute("header", header[0].toString()) ;
		model.addAttribute("images", imageService.findByStatus(true));
		model.addAttribute("username", username);
		model.addAttribute("categories", categoryRepository.findAll());

		return "gallery";
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping("/orders")
	public String getNewOrders(Model model, Principal principal){
		model.addAttribute("isCompleted", false);
		model.addAttribute("header", header[2].toString());
		model.addAttribute("images", imageService.findByStatusIsNull());
		model.addAttribute("username", principal.getName());
		model.addAttribute("categories", categoryRepository.findAll());

		return "gallery";
	}

	@PreAuthorize("hasAuthority('USER')")
	@GetMapping("/myGallery")
	public String getMyGallery(Model model, Principal principal){
		model.addAttribute("isCompleted", true);
		model.addAttribute("header", header[1].toString()) ;
		model.addAttribute("images", imageService.findByStatus(true));
		model.addAttribute("username", principal.getName());
		model.addAttribute("categories", categoryRepository.findAll());

		return "gallery";
	}


	@RequestMapping(value = "/category/{id}", method = RequestMethod.GET)
	public String getImagesByCategory(@PathVariable Integer id, Model model, Principal principal) {
		Optional<Category> category = categoryRepository.findById(id);

		model.addAttribute("isCompleted", true);
		model.addAttribute("images", imageService.findAllByCategoryAndStatus(category.get(), true));
		model.addAttribute("username", principal.getName());
		model.addAttribute("categories", categoryRepository.findAll());
		return "gallery";
	}


	@GetMapping("/addImage")
	public String getImageForm(Model model , Principal principal){
		model.addAttribute("image", new Image());
		model.addAttribute("username", principal.getName());
		model.addAttribute("categories", categoryRepository.findAll());
		return "imageForm";
	}

	@RequestMapping(value = "/addImage", method = RequestMethod.POST , consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public String addImage(@Valid Image image,
	                       BindingResult bindingResult,
						   @RequestParam("categoryName") String category,
						   @RequestPart("imageFile") MultipartFile file,
						   Model model,
						   Principal principal ){
			
			String imageOwner = principal.getName();
			User user = userService.findUserByUsername(imageOwner);
			model.addAttribute("categories", categoryRepository.findAll());
			model.addAttribute("username", user.getUsername());
			if (file.isEmpty()) {
				model.addAttribute("message", "Please select a file to upload.");
				return "imageForm";
			}
			else{
				if(bindingResult.hasErrors()){
					return "imageForm";
					}
				else{
					try {
						imageService.saveImage(image, file, category, user);
					} catch (IOException e) {
						e.printStackTrace();
					}
					return "redirect:/gallery";
					}
				}
			
		    
	}

	@RequestMapping(value = "/imageViewer/{id}", method = RequestMethod.GET)
	public String viewImage(@PathVariable Long id, Principal principal, Model model){		
		Image image = imageService.findById(id);


		model.addAttribute("image", image);
		return "imageViewer";
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@RequestMapping(value = "/newOrder/{id}", method = RequestMethod.GET)
	public String newOrder(@PathVariable Long id, Principal principal, Model model){		
		Image image = imageService.findById(id);


		model.addAttribute("username", principal.getName());
		model.addAttribute("categories", categoryRepository.findAll());
		model.addAttribute("image", image);
		return "orders";
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@RequestMapping(value = "/updateImage/{id}/{status}", method = RequestMethod.GET)
	public String updateImageStatus(@PathVariable Long id,
	                                @PathVariable Boolean status,
									Principal principal, Model model){		
		Image image = imageService.findById(id);
		imageService.updateImageStatus(image, status);

		model.addAttribute("username", principal.getName());
		model.addAttribute("categories", categoryRepository.findAll());
		model.addAttribute("success", "Image status has beed updated...");
		return "redirect:/orders";
	}


	@GetMapping("access-denied")
    public String accessDeniedPage(Model model ,Principal principal){
        model.addAttribute("username", principal.getName());
        model.addAttribute("categories", categoryRepository.findAll());

        return "accessdenied";

    }



}