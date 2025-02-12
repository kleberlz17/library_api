package kleberlz.libraryapi.repositorytest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import jakarta.transaction.Transactional;
import kleberlz.libraryapi.model.Autor;
import kleberlz.libraryapi.model.GeneroLivro;
import kleberlz.libraryapi.model.Livro;
import kleberlz.libraryapi.repository.AutorRepository;
import kleberlz.libraryapi.repository.LivroRepository;

@SpringBootTest
public class AutorRepositoryTest {

	@Autowired
	AutorRepository autorRepository;
	
	@Autowired
	LivroRepository livroRepository;
	
	@Test
	public void salvarTest() {
		Autor autor = new Autor();
		autor.setNome("Maria");
		autor.setNacionalidade("Brasileira");
		autor.setDataNascimento(LocalDate.of(1951, 1, 31));
		
		var autorSalvo = autorRepository.save(autor);
		System.out.println("Autor Salvo: " + autorSalvo);
	}
	
	@Test
	public void atualizarTest() {
		var id = UUID.fromString("059c99cd-9ff4-4239-b4a8-789f8b0484cb");
		
		Optional<Autor> possivelAutor = autorRepository.findById(id);
		
		if(possivelAutor.isPresent()) {
			
			Autor autorEncontrado = possivelAutor.get();
			System.out.println("Dados do Autor:");
			System.out.println(autorEncontrado);
			
			autorEncontrado.setDataNascimento(LocalDate.of(1960, 1, 30));
			
			autorRepository.save(autorEncontrado);
		}
	}
	
	@Test
	public void listarTest() {
		List<Autor> lista = autorRepository.findAll();
		lista.forEach(System.out::println);
	}
	
	@Test
	public void countTest() {
		System.out.println("Contagem de autores: " + autorRepository.count());
	}
	
	@Test
	public void deletePorIdTeste() {
		var id = UUID.fromString("456c708c-025a-4e8c-8f26-f244d2a8fd94");
		autorRepository.deleteById(id);
	}
	@Test
	public void deleteTeste() {
		var id = UUID.fromString("456c708c-025a-4e8c-8f26-f244d2a8fd94");
		var maria = autorRepository.findById(id).get();
		autorRepository.delete(maria);
	}
	
	@Test
	void salvarAutorComLivrosTest() {
		Autor autor = new Autor();
		autor.setNome("Antonio");
		autor.setNacionalidade("Americana");
		autor.setDataNascimento(LocalDate.of(1970, 8, 5));
		
		Livro livro = new Livro();
		livro.setIsbn("20847-84874");
		livro.setPreco(BigDecimal.valueOf(204));
		livro.setGenero(GeneroLivro.MISTERIO);
		livro.setTitulo("O roubo da casa assombrada");
		livro.setDataPublicacao(LocalDate.of(1999, 1, 2));
		livro.setAutor(autor);
		
		Livro livro2 = new Livro();
		livro2.setIsbn("99999-84874");
		livro2.setPreco(BigDecimal.valueOf(650));
		livro2.setGenero(GeneroLivro.MISTERIO);
		livro2.setTitulo("O roubo da casa normal");
		livro2.setDataPublicacao(LocalDate.of(2000, 1, 2));
		livro2.setAutor(autor);
		
		autor.setLivros(new ArrayList<>());
		autor.getLivros().add(livro);
		autor.getLivros().add(livro2);
		
		autorRepository.save(autor);
		
		//livroRepository.saveAll(autor.getLivros());
	}
	
	@Test
	void listarLivrosAutor() {
		var id = UUID.fromString("9013cd35-4881-4185-a68d-ad6fbad7f195");
		var autor = autorRepository.findById(id).get();
		
		// buscar os livros do autor
		List<Livro> livrosLista = livroRepository.findByAutor(autor);
		autor.setLivros(livrosLista);
		
		autor.getLivros().forEach(System.out::println);
	}
}
