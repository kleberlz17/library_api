package kleberlz.libraryapi.controller;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.catalina.connector.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import kleberlz.libraryapi.controller.dto.CadastroLivroDTO;
import kleberlz.libraryapi.controller.dto.ResultadoPesquisaLivroDTO;
import kleberlz.libraryapi.controller.mappers.LivroMapper;
import kleberlz.libraryapi.model.GeneroLivro;
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
	public ResponseEntity<Void> salvar(@RequestBody @Valid CadastroLivroDTO dto) {
		Livro livro = mapper.toEntity(dto);

		service.salvar(livro);

		var url = gerarHeaderLocation(livro.getId());

		return ResponseEntity.created(url).build();
	}
	
	@GetMapping("{id}")
	public ResponseEntity<ResultadoPesquisaLivroDTO> obterDetalhes(@PathVariable("id")String id){
		return service.obterPorId(UUID.fromString(id))
				.map(livro -> {
					var dto = mapper.toDTO(livro);
					return ResponseEntity.ok(dto);
				}).orElseGet( () -> ResponseEntity.notFound().build() );
	
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<Object> deletar(@PathVariable("id") String id){
		return service.obterPorId(UUID.fromString(id))
				.map(livro -> {
					service.deletar(livro);
					return ResponseEntity.noContent().build();
				}).orElseGet(() -> ResponseEntity.notFound().build());
		
		
	}
	
	@GetMapping
	public ResponseEntity<List<ResultadoPesquisaLivroDTO>> pesquisa(
			@RequestParam(value = "isbn", required = false)
			String isbn,
			@RequestParam(value = "titulo", required = false)
			String titulo,
			@RequestParam(value = "nome-autor", required = false)
			String nomeAutor,
			@RequestParam(value = "genero", required = false)
			GeneroLivro genero,
			@RequestParam(value = "ano-publicacao", required = false)
			Integer anoPublicacao
		){
			var resultado = service.pesquisa(isbn, titulo, nomeAutor, genero, anoPublicacao);
			var lista = resultado
					.stream()
					.map(mapper::toDTO)
					.collect(Collectors.toList());
			
			return ResponseEntity.ok(lista);
			
	}
	
	

}
