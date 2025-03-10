package kleberlz.libraryapi.controller.dto;

import java.time.LocalDate;
import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

//@Schema(name = "Autor")
public record AutorDTO(
		
		UUID id,
		
		@NotBlank(message = "campo obrigatório") // é para Strings somente. A string não venha nula e nem vazia.
		@Size(min = 2, max = 100, message = "campo fora do tamanho padrão")
		@Schema(name = "nome")
		String nome,
		
		@NotNull(message = "campo obrigatório")
		@Past(message = "não pode ser uma data futura")
		@Schema(name = "dataNascimento")
		LocalDate dataNascimento,
		
		@NotBlank(message = "campo obrigatório")
		@Size(max = 50, min = 2, message = "campo fora do tamanho padrão")
		@Schema(name = "nacionalidade")
		String nacionalidade) {

}
