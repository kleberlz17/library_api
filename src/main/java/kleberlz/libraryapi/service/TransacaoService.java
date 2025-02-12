package kleberlz.libraryapi.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kleberlz.libraryapi.model.Autor;
import kleberlz.libraryapi.model.GeneroLivro;
import kleberlz.libraryapi.model.Livro;
import kleberlz.libraryapi.repository.AutorRepository;
import kleberlz.libraryapi.repository.LivroRepository;

@Service
public class TransacaoService {

	@Autowired
	private AutorRepository autorRepository;
	@Autowired
	private LivroRepository livroRepository;
	
	@Transactional
	public void salvarLivroComFoto() {
		// salva o livro
		// repository.save(livro);
		
		// pega o id do livro = livro.getId();
		// var id = livro.getId();
		
		// salvar foto do livro -> bucket na nuvem
		// bucketService.salvar(livro.getFoto(), id + ".png";
		
		// atualizar o nome arquivo que foi salvo
		// livro.setNomeArquivoFoto(id + ".png");
	}
	
	
	@Transactional
	public void atualizacaoSemAtualizar() {
		var livro = livroRepository
				.findById(UUID.fromString("633ea222-b8f9-4b8e-b7c8-8fc1b87ba2f4"))
				.orElse(null);
		
		livro.setDataPublicacao(LocalDate.of(2024, 6, 1));
	}
	
	@Transactional
	public void executar() {
		// salvar o autor
		Autor autor = new Autor();
		autor.setNome("Teste Francisco");
		autor.setNacionalidade("Brasileira");
		autor.setDataNascimento(LocalDate.of(1951, 1, 31));
		
		autorRepository.save(autor);
		
		
		
		// salvar o livro
		Livro livro = new Livro();
		livro.setIsbn("90887-84874");
		livro.setPreco(BigDecimal.valueOf(100));
		livro.setGenero(GeneroLivro.FICCAO);
		livro.setTitulo("Teste Livro do Francisco");
		livro.setDataPublicacao(LocalDate.of(1980, 1, 2));
		
		livro.setAutor(autor);
		
		livroRepository.save(livro);
		
		if(autor.getNome().equals("Teste Francisco")) {
			throw new RuntimeException("Rollback!");
		}
		
		
	}
}
