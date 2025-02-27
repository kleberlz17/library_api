package kleberlz.libraryapi.service;


import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import kleberlz.libraryapi.model.GeneroLivro;
import kleberlz.libraryapi.model.Livro;
import kleberlz.libraryapi.model.Usuario;
import kleberlz.libraryapi.repository.LivroRepository;
import kleberlz.libraryapi.security.SecurityService;
import kleberlz.libraryapi.validator.LivroValidator;
import lombok.RequiredArgsConstructor;
import static kleberlz.libraryapi.repository.specs.LivroSpecs.*;


@Service
@RequiredArgsConstructor
public class LivroService {
	
	private final LivroRepository repository;
	private final LivroValidator validator;
	private final SecurityService securityService;
	
	
	public Livro salvar(Livro livro) {
		validator.validar(livro);
		Usuario usuario = securityService.obterUsuarioLogado();
		livro.setUsuario(usuario);
		return repository.save(livro);
	}

	public Optional<Livro> obterPorId(UUID id){
		return repository.findById(id);
	}
	
	public void deletar(Livro livro) {
		repository.delete(livro);
	}
	//isbn, titulo, nome autor, genero, ano de publicação
	public Page<Livro> pesquisa(
			String isbn,
			String titulo,
			String nomeAutor,
			GeneroLivro genero,
			Integer anoPublicacao,
			Integer pagina,
			Integer tamanhoPagina){
		
		//select * from livro where isbn = :isbn and nomeAutor =
		
//		Specification<Livro> specs = Specification
//				.where(LivroSpecs.isbnEqual(isbn))
//				.and(LivroSpecs.tituloLike(titulo))
//				.and(LivroSpecs.generoEqual(genero))
//				;
				
		// select * from livro where 0 = 0
		Specification<Livro> specs = Specification.where((root, query, cb) -> cb.conjunction() );
		
		if(isbn != null) {
			// query = query and isbn = :isbn
			specs = specs.and(isbnEqual(isbn)); // esses 3 specs foram importados diretamente, linha 14
		}
		
		if(titulo != null) {
			specs = specs.and(tituloLike(titulo));
		}
		
		if(genero != null) {
			specs = specs.and(generoEqual(genero));
		}
		
		if(anoPublicacao != null) {
			specs = specs.and(anoPublicacaoEqual(anoPublicacao));
		}
		
		if(nomeAutor != null) {
			specs = specs.and(nomeAutorLike(nomeAutor));
		}
		
		Pageable pageRequest = PageRequest.of(pagina, tamanhoPagina);
		
		return repository.findAll(specs, pageRequest);
		
	}

	public void atualizar(Livro livro) {
		if(livro.getId() == null) {
			throw new IllegalArgumentException("Para atualizar é necessário que o livro já esteja salvo na base.");
		}
		validator.validar(livro);
		repository.save(livro);
		
	}
}
