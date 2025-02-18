package kleberlz.libraryapi.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import jakarta.validation.Valid;
import kleberlz.libraryapi.controller.dto.AutorDTO;
import kleberlz.libraryapi.controller.dto.ErroResposta;
import kleberlz.libraryapi.controller.mappers.AutorMapper;
import kleberlz.libraryapi.exceptions.OperacaoNaoPermitidaException;
import kleberlz.libraryapi.exceptions.RegistroDuplicadoException;
import kleberlz.libraryapi.model.Autor;
import kleberlz.libraryapi.service.AutorService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/autores")
@RequiredArgsConstructor
// http://localhost:8080/autores
public class AutorController implements GenericController {
	
	private final AutorService service;
	private final AutorMapper mapper;
	
	
	@PostMapping
	public ResponseEntity<Object> salvar (@RequestBody @Valid AutorDTO dto) {
		try {
		Autor autor = mapper.toEntity(dto);
		service.salvar(autor);
		
		// criação da URL do localhost:8080
		URI location = gerarHeaderLocation(autor.getId());
		
		return ResponseEntity.created(location).build();
		
	} catch (RegistroDuplicadoException e){
		var erroDTO = ErroResposta.conflito(e.getMessage());
		return ResponseEntity.status(erroDTO.status()).body(erroDTO);
		
	}
	
}
	
	@GetMapping("{id}")
	public ResponseEntity<AutorDTO> obterDetalhes(@PathVariable("id") String id) {
		var idAutor = UUID.fromString(id);
		
		return service
				.obterPorId(idAutor)
				.map(autor -> {
					AutorDTO dto = mapper.toDTO(autor);
					return ResponseEntity.ok(dto);
				}).orElseGet( () -> ResponseEntity.notFound().build() );	
	}
	
	// indempontente
	@DeleteMapping("{id}")
	public ResponseEntity<Object> deletar(@PathVariable("id") String id){
		try {
			var idAutor = UUID.fromString(id);
			Optional<Autor> autorOptional = service.obterPorId(idAutor);
			
			if(autorOptional.isEmpty()) {
				return ResponseEntity.notFound().build();
			}
			
			service.deletar(autorOptional.get());
		
		return ResponseEntity.noContent().build();
	} catch (OperacaoNaoPermitidaException e) {
		var erroResposta = ErroResposta.respostaPadrao(e.getMessage());
		return ResponseEntity.status(erroResposta.status()).body(erroResposta);
	}
}
		
	@GetMapping
	public ResponseEntity<List<AutorDTO>> pesquisar(
		@RequestParam(value = "nome", required = false) String nome, 
		@RequestParam(value ="nacionalidade", required = false) String nacionalidade){
		List<Autor> resultado = service.pesquisaByExample(nome, nacionalidade);
		List<AutorDTO> lista = resultado
				.stream()
				.map(mapper::toDTO)
				.collect(Collectors.toList());
		
		return ResponseEntity.ok(lista);
	  
	}
	@PutMapping("{id}")
	public ResponseEntity<Object> atualizar(
			@PathVariable("id") String id, @RequestBody @Valid AutorDTO dto){
		
		try {
			
		var idAutor = UUID.fromString(id);
		Optional<Autor>autorOptional = service.obterPorId(idAutor);
		
		if(autorOptional.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		
		var autor = autorOptional.get();
		autor.setNome(dto.nome());
		autor.setNacionalidade(dto.nacionalidade());
		autor.setDataNascimento(dto.dataNascimento());
		
		service.atualizar(autor);
		
		return ResponseEntity.noContent().build();
		} catch (RegistroDuplicadoException e) {
			var erroDTO = ErroResposta.conflito(e.getMessage());
			return ResponseEntity.status(erroDTO.status()).body(erroDTO);
		}
	}
}
