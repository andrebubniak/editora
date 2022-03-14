package dw.editora.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;


@Entity
@Table(name = "artigo")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Artigo 
{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	@Column
    private String titulo;

	@Column
    private String resumo;

	@Column
    private boolean publicado;

}
