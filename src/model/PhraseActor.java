package model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: PhraseActor
 *
 */
@Entity
@Inheritance(strategy=InheritanceType.JOINED)
public class PhraseActor implements Serializable {

	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@Column(name="name", nullable=false, unique=true)
	private String name;
	
	public PhraseActor() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getId() {
		return id;
	}
   
}
