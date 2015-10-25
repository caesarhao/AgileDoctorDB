package model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: MicroSequence
 *
 */
@Entity
@Table(name="MicroSequence")
@NamedQueries({
@NamedQuery(name = "MicroSequence.findAll", query = "SELECT e FROM MicroSequence e"),
@NamedQuery(name = "MicroSequence.findById", query = "SELECT e FROM MicroSequence e WHERE e.id = :id"),
@NamedQuery(name = "MicroSequence.findByName", query = "SELECT e FROM MicroSequence e WHERE e.name = :name"),
})
public class MicroSequence implements Serializable {

	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@Column(name="name", nullable=false, unique=true)
	private String name;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@OneToMany(cascade=CascadeType.ALL)
	private Set<DialogueSession> dialogueSessions;
	
	public long getId() {
		return id;
	}

	public Set<DialogueSession> getDialogueSessions() {
		return dialogueSessions;
	}

	public MicroSequence() {
		super();
	}
   
}
