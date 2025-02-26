package kleberlz.libraryapi.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import kleberlz.libraryapi.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {

	Usuario findByLogin(String login);
}
