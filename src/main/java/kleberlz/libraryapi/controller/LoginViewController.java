package kleberlz.libraryapi.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import io.swagger.v3.oas.annotations.tags.Tag;
import kleberlz.libraryapi.security.CustomAuthentication;

@Controller // Controller é quando utiliza páginas WEB, RestController é pra API.
@Tag(name = "Página de Login")
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
	
	@GetMapping("/authorized")
	@ResponseBody // ABAIXO PARA OBTER O AUTHORIZATION CODE MANUALMENTE
	public String getAuthorizationCodeString(@RequestParam("code")String code) {
		return "Seu authorization code: " + code;
	}

}
