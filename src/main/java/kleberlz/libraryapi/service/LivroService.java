package kleberlz.libraryapi.service;

import org.springframework.stereotype.Service;

import kleberlz.libraryapi.model.Livro;
import kleberlz.libraryapi.repository.LivroRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LivroService {
	
	private final LivroRepository repository;
	
	public Livro salvar(Livro livro) {
		return repository.save(livro);
	}

}
