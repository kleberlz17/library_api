package kleberlz.libraryapi.controller.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import kleberlz.libraryapi.controller.dto.AutorDTO;
import kleberlz.libraryapi.model.Autor;

@Mapper(componentModel = "spring")
public interface AutorMapper {
	
	@Mapping(source = "nome", target = "nome") // nesses 3 casos é opcional quando target e soruce tem o mesmo nome/valor.
	@Mapping(source = "dataNascimento", target = "dataNascimento")
	@Mapping(source = "nacionalidade", target = "nacionalidade")
	Autor toEntity(AutorDTO dto);
	
	AutorDTO toDTO(Autor autor);

}
