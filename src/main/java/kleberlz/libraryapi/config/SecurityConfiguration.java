package kleberlz.libraryapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


import kleberlz.libraryapi.security.CustomUserDetailsService;
import kleberlz.libraryapi.service.UsuarioService;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class SecurityConfiguration {
	
	
			// configuração padrão do SPRING Security
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http
				.csrf(AbstractHttpConfigurer::disable) // csrf desabilitado pq não é aplicação WEB
				.httpBasic(Customizer.withDefaults())
//				.formLogin(configurer -> { // habilitado formulario de login
//					configurer.loginPage("/login");
//				}) 
				.formLogin(Customizer.withDefaults())
				.authorizeHttpRequests(authorize -> { 
					authorize.requestMatchers("/login/**").permitAll();
					authorize.requestMatchers(HttpMethod.POST, "/usuarios/**").permitAll();
					
					
					authorize.anyRequest().authenticated(); // REGRA DE ACESSO: tem que estar autenticado em qualquer requisição.
				})
				.oauth2Login(Customizer.withDefaults())
				.build();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() { // criptografar a senha 10 vezes pra segurança.
		return new BCryptPasswordEncoder(10);
	}
	
//	@Bean
	public UserDetailsService userDetailsService(UsuarioService usuarioService) {
//		UserDetails user1 = User.builder()
//				.username("usuario")
//				.password(encoder.encode("123"))
//				.roles("USER")
//				.build();
//		
//		UserDetails user2 = User.builder()
//				.username("admin")
//				.password(encoder.encode("321"))
//				.roles("ADMIN")
//				.build();
//		
//		return new InMemoryUserDetailsManager(user1, user2);
//		
		return new CustomUserDetailsService(usuarioService);
	}
	
	@Bean
	public GrantedAuthorityDefaults grantedAuthorityDefaults() { // Tirar a obrigatoriedade de ROLE_ (GERENTE, OPERADOR.. ETC) com "".
		return new GrantedAuthorityDefaults("");
	}
	
}
