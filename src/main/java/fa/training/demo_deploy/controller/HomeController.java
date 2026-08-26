package fa.training.demo_deploy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
	
	@GetMapping({"/",""})
	public String home(Model model) {
		model.addAttribute("title", "My Spring Boot Server");
		model.addAttribute("message",
		                   "Hello from Spring Boot + Thymeleaf!");
		
		return "home";
	}
}