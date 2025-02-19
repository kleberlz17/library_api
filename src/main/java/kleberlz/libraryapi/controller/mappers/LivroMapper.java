package kleberlz.libraryapi.controller.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import kleberlz.libraryapi.controller.dto.CadastroLivroDTO;
import kleberlz.libraryapi.controller.dto.ResultadoPesquisaLivroDTO;
import kleberlz.libraryapi.model.Livro;
import kleberlz.libraryapi.repository.AutorRepository;

@Mapper(componentModel = "spring", uses = AutorMapper.class)
public abstract class LivroMapper {
	
	@Autowired
	AutorRepository autorRepository;
	
	@Mapping(target = "autor", expression = "java( autorRepository.findById(dto.idAutor()).orElse(null) )")
	public abstract Livro toEntity(CadastroLivroDTO dto);

	public abstract ResultadoPesquisaLivroDTO toDTO(Livro livro);
	
	
	

}
