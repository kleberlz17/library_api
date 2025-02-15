package kleberlz.libraryapi.controller.dto;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import kleberlz.libraryapi.model.Autor;

public record AutorDTO(
		
		UUID id,
		
		@NotBlank(message = "campo obrigatório") // é para Strings somente. A string não venha nula e nem vazia.
		@Size(min = 2, max = 100, message = "campo fora do tamanho padrão")
		String nome,
		
		@NotNull(message = "campo obrigatório")
		@Past(message = "não pode ser uma data futura")
		LocalDate dataNascimento,
		
		@NotBlank(message = "campo obrigatório")
		@Size(max = 50, min = 2, message = "campo fora do tamanho padrão")
		String nacionalidade) {
	
	
	public Autor mapearParaAutor() {
		Autor autor = new Autor();
		autor.setNome(this.nome);
		autor.setDataNascimento(this.dataNascimento);
		autor.setNacionalidade(this.nacionalidade);
		return autor;
	}

}
