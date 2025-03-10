package kleberlz.libraryapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kleberlz.libraryapi.model.Client;
import kleberlz.libraryapi.service.ClientService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("clients")
@RequiredArgsConstructor
@Tag(name = "Clientes")
public class ClientController {
	
	private final ClientService service;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@PreAuthorize("hasRole('GERENTE')")
	@Operation(summary = "Salvar", description = "Cadastrar novo Cliente")
	@ApiResponses({
		@ApiResponse(responseCode = "201", description = "Cadastrado com sucesso."),
		@ApiResponse(responseCode = "422", description = "Erro de validação."),
		@ApiResponse(responseCode = "409", description = "Cliente já cadastrado. ")	
	})
	public void salvar(@RequestBody Client client) {
		service.salvar(client);
	}

}
