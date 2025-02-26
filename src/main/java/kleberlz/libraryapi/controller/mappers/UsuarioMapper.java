package kleberlz.libraryapi.controller.mappers;

import org.mapstruct.Mapper;

import kleberlz.libraryapi.controller.dto.UsuarioDTO;
import kleberlz.libraryapi.model.Usuario;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {
	
	Usuario toEntity(UsuarioDTO dto);

}
