package kleberlz.libraryapi.repositorytest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import kleberlz.libraryapi.model.Autor;
import kleberlz.libraryapi.model.GeneroLivro;
import kleberlz.libraryapi.model.Livro;
import kleberlz.libraryapi.repository.AutorRepository;
import kleberlz.libraryapi.repository.LivroRepository;

@SpringBootTest
public class LivroRepositoryTest {
	
	@Autowired
	LivroRepository livroRepository;
	
	@Autowired
	AutorRepository autorRepository;
	
	@Test
	void salvarTest() {
		Livro livro = new Livro();
		livro.setIsbn("90887-84874");
		livro.setPreco(BigDecimal.valueOf(100));
		livro.setGenero(GeneroLivro.CIENCIA);
		livro.setTitulo("Ciencias");
		livro.setDataPublicacao(LocalDate.of(1980, 1, 2));
		
		@SuppressWarnings("unused")
		Autor autor = autorRepository
			.findById(UUID.fromString("fa37a309-caf8-4b6b-8bba-d47c2e679c6b"))
				.orElse(null);
		
		livro.setAutor(new Autor());
		
		livroRepository.save(livro);
	}
	
	@Test
	void salvarCascadeTest() {
		Livro livro = new Livro();
		livro.setIsbn("90887-84874");
		livro.setPreco(BigDecimal.valueOf(100));
		livro.setGenero(GeneroLivro.FICCAO);
		livro.setTitulo("Outro Livro");
		livro.setDataPublicacao(LocalDate.of(1980, 1, 2));
		
		Autor autor = new Autor();
		autor.setNome("João");
		autor.setNacionalidade("Brasileira");
		autor.setDataNascimento(LocalDate.of(1951, 1, 31));
		
		
		
		livro.setAutor(autor);
		
		livroRepository.save(livro);
	}
	
	@Test
	void salvarAutorELivroTest() {
		Livro livro = new Livro();
		livro.setIsbn("90887-84874");
		livro.setPreco(BigDecimal.valueOf(100));
		livro.setGenero(GeneroLivro.FICCAO);
		livro.setTitulo("Terceiro Livro");
		livro.setDataPublicacao(LocalDate.of(1980, 1, 2));
//		
//		Autor autor = new Autor();
//		autor.setNome("José");
//		autor.setNacionalidade("Brasileira");
//		autor.setDataNascimento(LocalDate.of(1951, 1, 31));
		
//		autorRepository.save(autor);
//		
//		livro.setAutor(autor);
		
		livroRepository.save(livro);
	}
	
	@Test
	void atualizarAutorDoLivro() {
		var livroParaAtualizar = livroRepository.findById(UUID.fromString("9243ebcd-c5a1-4015-bdf4-933a6abece85"))
				.orElse(null);
		
		Autor maria  = autorRepository.findById(UUID.fromString("f6607421-87ba-4bf8-8bf4-c67ade9fc892"))
				.orElse(null);
		
		livroParaAtualizar.setAutor(maria);
		
		livroRepository.save(livroParaAtualizar);
		
	}
	
	@Test
	void deletar() {
		 livroRepository.deleteById(UUID.fromString("9243ebcd-c5a1-4015-bdf4-933a6abece85"));
				
	}
	
	@Test
	void deletarCascade() {
		 livroRepository.deleteById(UUID.fromString("3618e651-809a-47ea-8783-88a661f2aca6"));
				
	}
	
	@Test
	void buscarLivroTest() {
		UUID id = UUID.fromString("65fb4675-9269-4477-b435-76145e15ceab");
		Livro livro = livroRepository.findById(id).orElse(null);
		System.out.println("Livro:");
		System.out.println(livro.getTitulo());
		
//		System.out.println("Autor:");
//		System.out.println(livro.getAutor().getNome());
		
	}
	@Test
	void pesquisaPorTituloTest() {
		List<Livro> lista = livroRepository.findByTitulo("O roubo da casa assombrada");
		lista.forEach(System.out::println);
	}
	
	@Test
	void pesquisaPorISBNTest() {
		Optional<Livro> livro = livroRepository.findByIsbn("99999-84874");
		livro.ifPresent(System.out::println);
	
	}
	
	@Test
	void pesquisaPorTituloEPrecoTest() {
		var preco = BigDecimal.valueOf(204.00);
		var tituloPesquisa = "O roubo da casa assombrada";
		
		List<Livro> lista = livroRepository.findByTituloAndPreco(tituloPesquisa, preco);
		lista.forEach(System.out::println);
	}
	
	@Test
	void listarLivrosComQueryJPQL() {
		var resultado = livroRepository.listarTodosOrdenadoPorTituloAndPreco();
		resultado.forEach(System.out::println);
	}
	
	@Test
	void listarAutoresDosLivros() {
		var resultado = livroRepository.listarAutoresDosLivros();
		resultado.forEach(System.out::println);
	}
	
	@Test
	void listarTitulosNaoRepetidosDosLivros() {
		var resultado = livroRepository.listarNomesDiferentesLivros();
		resultado.forEach(System.out::println);
	}
	
	@Test
	void listarGenerosDeLivrosAutoresBrasileiros() {
		var resultado = livroRepository.listarGenerosAutoresBrasileiros();
		resultado.forEach(System.out::println);
	}
	
	@Test
	void listarPorGeneroQueryParamTest() {
		var resultado = livroRepository.findByGenero(GeneroLivro.MISTERIO, "preco");
		resultado.forEach(System.out::println);
	}
	
	@Test
	void listarPorGeneroPositionalParamTest() {
		@SuppressWarnings("unused")
		var resultado = livroRepository.findByGeneroPositionalParameters(GeneroLivro.MISTERIO, "preco");
	}
	
	@Test
	void deletePorGeneroTest() {
		livroRepository.deleteByGenero(GeneroLivro.CIENCIA);
	}
	
	@Test
	void updateDataPublicacaoTest() {
		livroRepository.updateDataPublicacao(LocalDate.of(2000, 1, 1));
	}
}
