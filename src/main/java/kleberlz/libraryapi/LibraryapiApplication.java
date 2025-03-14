package kleberlz.libraryapi;



import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;



@SpringBootApplication
@EnableJpaAuditing
public class LibraryapiApplication {

	public static void main(String[] args) {
		var context = SpringApplication.run(LibraryapiApplication.class, args);
//		AutorRepository repository = context.getBean(AutorRepository.class);
//		
//		exemploSalvarRgistro(repository);
	}

//	public static void exemploSalvarRgistro(AutorRepository autorRepository){
//		Autor autor = new Autor();
//		autor.setNome("José");
//		autor.setNacionalidade("Brasileira");
//		autor.setDataNascimento(LocalDate.of(1950, 1, 31));
//		
//		var autorSalvo = autorRepository.save(autor);
//		System.out.println("Autor Salvo: " + autorSalvo);
//	}

}