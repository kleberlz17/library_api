package kleberlz.libraryapi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller // Controller é quando utiliza páginas WEB, RestController é pra API.
public class LoginViewController {
	
	@GetMapping("/login")
	public String paginaLogin() {
		return "login";
	}

}
