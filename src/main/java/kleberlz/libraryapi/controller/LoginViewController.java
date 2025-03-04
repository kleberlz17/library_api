package kleberlz.libraryapi.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kleberlz.libraryapi.security.CustomAuthentication;

@Controller // Controller é quando utiliza páginas WEB, RestController é pra API.
public class LoginViewController {
	
	@GetMapping("/login")
	public String paginaLogin() {
		return "login";
	}
	
	@GetMapping("/")
	@ResponseBody //@Controller o return espera páginas, com o ResponseBody ele pega o return e coloca na resposta, sem esperar uma página, sem ele vai ficar pedindo página.
	public String paginaHome(Authentication authentication) {
		if(authentication instanceof CustomAuthentication customAuth) {
			System.out.println(customAuth.getUsuario());
		}
		return "Olá " + authentication.getName();
		
	}

}
