package kleberlz.libraryapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.web.authentication.BearerTokenAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;

import kleberlz.libraryapi.security.JwtCustomAuthenticationFilter;
import kleberlz.libraryapi.security.LoginSocialSuccessHandler;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class SecurityConfiguration {
	
	
			// configuração padrão do SPRING Security
	@Bean
	public SecurityFilterChain securityFilterChain(
			HttpSecurity http, 
			LoginSocialSuccessHandler successHandler,
			JwtCustomAuthenticationFilter jwtCustomAuthenticationFilter) throws Exception {
		return http
				.csrf(AbstractHttpConfigurer::disable) // csrf desabilitado pq não é aplicação WEB
//				.httpBasic(Customizer.withDefaults())
				.formLogin(configurer -> { // habilitado formulario de login
					configurer.loginPage("/login");
				}) 
				.authorizeHttpRequests(authorize -> { 
					authorize.requestMatchers("/login/**").permitAll();
					authorize.requestMatchers(HttpMethod.POST, "/usuarios/**").permitAll();
					
					
					authorize.anyRequest().authenticated(); // REGRA DE ACESSO: tem que estar autenticado em qualquer requisição.
				})
				.oauth2Login(oauth2 -> {
					oauth2
						.loginPage("/login")
						.successHandler(successHandler); //RECEBER AUTHENTICATION DE UM TOKEN DE LUGAR DIFERENTE(LOGIN PELO EMAIL GOOGLE)
				})
				.oauth2ResourceServer(oauth2RS -> oauth2RS.jwt(Customizer.withDefaults())) //Configuração TOKEN JWT
				.addFilterAfter(jwtCustomAuthenticationFilter, BearerTokenAuthenticationFilter.class)
				.build();
	}
	// ignorar docs padroes do swagger na documentação.
	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return web -> web.ignoring().requestMatchers(
				"/v2/api-docs/**",
				"/v3/api-docs/**",
				"/swagger-resources/**",
				"/swagger-ui.html",
				"/swagger-ui/**",
				"/webjars/**"
			);
				
	}
	
	// CONFIGURA O PREFIXO ROLE
	@Bean
	public GrantedAuthorityDefaults grantedAuthorityDefaults() { // Tirar a obrigatoriedade de ROLE_ (GERENTE, OPERADOR.. ETC) com "".
		return new GrantedAuthorityDefaults("");
	}
	
	// CONFIGURA NO TOKEN JWT O PREFIXO SCOPE.
	@Bean// abaixo ele pega o token acima, pega a ROLE e define qual o prefixo ou não que será usado. No caso abaixo é sem prefixo. Tipo = ROLE_GERENTE, sem o ROLE_
	public JwtAuthenticationConverter jwtAuthenticationConverter() {
		var authoritiesConverter = new JwtGrantedAuthoritiesConverter();
		authoritiesConverter.setAuthorityPrefix("");
		
		var converter = new JwtAuthenticationConverter();
		converter.setJwtGrantedAuthoritiesConverter(authoritiesConverter);
		
		return converter;
	}
	
}
