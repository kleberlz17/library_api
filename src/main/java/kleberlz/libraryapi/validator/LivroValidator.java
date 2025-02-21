package kleberlz.libraryapi.validator;

import java.util.Optional;

import org.springframework.stereotype.Component;

import kleberlz.libraryapi.exceptions.RegistroDuplicadoException;
import kleberlz.libraryapi.model.Livro;
import kleberlz.libraryapi.repository.LivroRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class LivroValidator {
	
	private final LivroRepository repository;
	
	public void validar(Livro livro) {
		if(existeLivroComIsbn(livro)) {
			throw new RegistroDuplicadoException("ISBN já cadastrado!");
		}
	}
	
	private boolean existeLivroComIsbn(Livro livro) {
		Optional<Livro> livroEncontrado = repository.findByIsbn(livro.getIsbn());
		
		if(livro.getId() == null) {
			return livroEncontrado.isPresent();
		}
		
		return livroEncontrado
				.map(Livro::getId)
				.stream()
				.anyMatch(id -> !id.equals(livro.getId()));
	}

}
