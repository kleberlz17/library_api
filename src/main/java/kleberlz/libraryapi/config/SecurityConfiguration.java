package kleberlz.libraryapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
	
	
			// configuração padrão do SPRING Security
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http
				.csrf(AbstractHttpConfigurer::disable) // csrf desabilitado pq não é aplicação WEB
				.httpBasic(Customizer.withDefaults())
				.formLogin(configurer -> { // habilitado formulario de login
					configurer.loginPage("/login");
				}) 
				.authorizeHttpRequests(authorize -> { 
					authorize.requestMatchers("/login").permitAll();
					authorize.requestMatchers("/autores/**").hasRole("ADMIN"); // esses ** são pra indicar acesso pra tudo que tem em autores, ex: {id}
					authorize.requestMatchers("/livros/**").hasAnyRole("USER", "ADMIN");
					
					authorize.anyRequest().authenticated(); // REGRA DE ACESSO: tem que estar autenticado em qualquer requisição.
				})
				.build();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() { // criptografar a senha 10 vezes pra segurança.
		return new BCryptPasswordEncoder(10);
	}
	
	@Bean
	public UserDetailsService userDetailsService(PasswordEncoder encoder) {
		UserDetails user1 = User.builder()
				.username("usuario")
				.password(encoder.encode("123"))
				.roles("USER")
				.build();
		
		UserDetails user2 = User.builder()
				.username("admin")
				.password(encoder.encode("321"))
				.roles("ADMIN")
				.build();
		
		return new InMemoryUserDetailsManager(user1, user2);
		
		
	}
	
}
