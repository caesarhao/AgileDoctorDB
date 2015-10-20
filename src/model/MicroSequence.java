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

public class MicroSequence implements Serializable {

	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
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
