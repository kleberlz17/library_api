package kleberlz.libraryapi.model;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.ToString;

@Entity
@Table(name = "livro")
@Data // já possui o getter and setter dentro dela.
@ToString(exclude = "autor")
@EntityListeners(AuditingEntityListener.class)
public class Livro {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	
	@Column(name = "isbn", length = 20, nullable = false)
	private String isbn;
	
	@Column(name = "titulo", length = 150, nullable = false)
	private String titulo;
	
	@Column(name = "data_publicacao")
	private LocalDate dataPublicacao;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "genero", length = 30, nullable = false)
	private GeneroLivro genero;
	
	@Column(name = "preco", precision = 18, scale = 2)
	private BigDecimal preco;
	
	
	@ManyToOne(
			fetch = FetchType.LAZY)//(cascade = CascadeType.ALL) //muitos livros para 1 autor
	@JoinColumn(name = "id_autor")
	private Autor autor;
	
	@CreatedDate
	@Column(name = "data_cadastro")
	private LocalDateTime dataCadastro;
	
	@LastModifiedDate
	@Column(name = "data_atualizacao")
	private LocalDateTime dataAtualizacao;
	
	@Column(name = "id_usuario")
	private UUID idUsuario;
	
	
}
