package kleberlz.libraryapi.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import kleberlz.libraryapi.model.Autor;

public interface AutorRepository extends JpaRepository<Autor, UUID>{

	List<Autor> findByNome(String nome);
	List<Autor> findByNacionalidade(String nacionalidade);
	List<Autor> findByNomeAndNacionalidade(String nome, String nacionalidade);
}
