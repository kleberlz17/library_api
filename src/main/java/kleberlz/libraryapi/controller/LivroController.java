package kleberlz.libraryapi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import kleberlz.libraryapi.controller.dto.CadastroLivroDTO;
import kleberlz.libraryapi.controller.dto.ErroResposta;
import kleberlz.libraryapi.controller.mappers.LivroMapper;
import kleberlz.libraryapi.exceptions.RegistroDuplicadoException;
import kleberlz.libraryapi.model.Livro;
import kleberlz.libraryapi.service.LivroService;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("livros")
@RequiredArgsConstructor
public class LivroController implements GenericController {
	
	private final LivroService service;
	private final LivroMapper mapper;
	
	@PostMapping
	public ResponseEntity<Object> salvar(@RequestBody @Valid CadastroLivroDTO dto){
		try {
			Livro livro = mapper.toEntity(dto);
			
			service.salvar(livro);
			
			var url = gerarHeaderLocation(livro.getId());
			
			return ResponseEntity.created(url).build();
			
		} catch (RegistroDuplicadoException e) {
			var erroDTO = ErroResposta.conflito(e.getMessage());
			return ResponseEntity.status(erroDTO.status()).body(erroDTO);
		}
	}

}
