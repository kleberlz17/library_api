package kleberlz.libraryapi.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import kleberlz.libraryapi.exceptions.OperacaoNaoPermitidaException;
import kleberlz.libraryapi.model.Autor;
import kleberlz.libraryapi.repository.AutorRepository;
import kleberlz.libraryapi.repository.LivroRepository;
import kleberlz.libraryapi.validator.AutorValidator;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AutorService {
	
	private final AutorRepository repository;
	private final AutorValidator validator;
	private final LivroRepository livroRepository;
	
	
	public Autor salvar(Autor autor) {
		validator.validar(autor);
		return repository.save(autor);
	}
	
	public void atualizar(Autor autor) {
		if(autor.getId() == null) {
			throw new IllegalArgumentException("Para atualizar é necessário que o autor já esteja salvo na base.");
		}
		validator.validar(autor);
		repository.save(autor);
	}
	
	public Optional<Autor> obterPorId(UUID id){
		return repository.findById(id);
	}

	public void deletar(Autor autor) {
		if(possuiLivro(autor)) {
			throw new OperacaoNaoPermitidaException(
					"Não é permitido excluir um Autor que possuí livros cadastrados!");
		}
		repository.delete(autor);
	}
	
	public List<Autor> pesquisa(String nome, String nacionalidade){
		if(nome != null && nacionalidade != null) {
			return repository.findByNomeAndNacionalidade(nome, nacionalidade);
		}
		
		if(nome != null) {
			return repository.findByNome(nome);
		}
		
		if(nacionalidade != null) {
			return repository.findByNacionalidade(nacionalidade);
		}
		
		return repository.findAll();
	}
	
	public boolean possuiLivro(Autor autor){
		return livroRepository.existsByAutor(autor);
		
	}
}
