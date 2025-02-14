package kleberlz.libraryapi.controller.dto;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import kleberlz.libraryapi.model.Autor;

public record AutorDTO(
		UUID id,
		
		@NotBlank(message = "campo obrigatório") // é para Strings somente. A string não venha nula e nem vazia.
		String nome,
		
		@NotNull
		LocalDate dataNascimento,
		
		@NotBlank
		String nacionalidade) {
	
	
	public Autor mapearParaAutor() {
		Autor autor = new Autor();
		autor.setNome(this.nome);
		autor.setDataNascimento(this.dataNascimento);
		autor.setNacionalidade(this.nacionalidade);
		return autor;
	}

}
