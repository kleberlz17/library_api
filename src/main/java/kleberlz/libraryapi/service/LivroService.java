package kleberlz.libraryapi.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import kleberlz.libraryapi.model.GeneroLivro;
import kleberlz.libraryapi.model.Livro;
import kleberlz.libraryapi.repository.LivroRepository;
import lombok.RequiredArgsConstructor;
import static kleberlz.libraryapi.repository.specs.LivroSpecs.*;


@Service
@RequiredArgsConstructor
public class LivroService {
	
	private final LivroRepository repository;
	
	public Livro salvar(Livro livro) {
		return repository.save(livro);
	}

	public Optional<Livro> obterPorId(UUID id){
		return repository.findById(id);
	}
	
	public void deletar(Livro livro) {
		repository.delete(livro);
	}
	//isbn, titulo, nome autor, genero, ano de publicação
	public List<Livro> pesquisa(String isbn, String titulo, String nomeAutor, GeneroLivro genero, Integer anoPublicacao){
		
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
		
		return repository.findAll(specs);
		
	}
}
