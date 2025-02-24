package kleberlz.libraryapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
	
	
			// configuração padrão do SPRING Security
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http
				.csrf(AbstractHttpConfigurer::disable) // csrf desabilitado pq não é aplicação WEB
								.formLogin(configurer -> { // habilitado formulario de login
					configurer.loginPage("/login").permitAll();
				}) 
				.httpBasic(Customizer.withDefaults()) // habilitado http basic
				.authorizeHttpRequests(authorize -> { 
					authorize.anyRequest().authenticated(); // REGRA DE ACESSO: tem que estar autenticado em qualquer requisição.
				})
				.build();
	}
	
}
