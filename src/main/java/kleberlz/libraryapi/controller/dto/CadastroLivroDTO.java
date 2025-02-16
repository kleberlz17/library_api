package kleberlz.libraryapi.controller.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import org.hibernate.validator.constraints.ISBN;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import kleberlz.libraryapi.model.GeneroLivro;




public record CadastroLivroDTO(
		@ISBN
		@NotBlank(message = "campo obrigatório")
		String isbn,
		
		@NotBlank(message = "campo obrigatório")
		String titulo,
		
		@NotNull(message = "campo obrigatório")
		@Past(message = "não pode ser uma data futura")
		LocalDate dataPublicacao,
		
		GeneroLivro genero,
		BigDecimal preco,
		
		@NotNull(message = "campo obrigatório")
		UUID idAutor
		) {

}
