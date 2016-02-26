package model;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: DialogueSession
 *
 */
@Entity
@Table(name = "DialogueSession")
@NamedQueries({ @NamedQuery(name = "DialogueSession.findAll", query = "SELECT e FROM DialogueSession e"),
		@NamedQuery(name = "DialogueSession.findById", query = "SELECT e FROM DialogueSession e WHERE e.id = :id"),
		@NamedQuery(name = "DialogueSession.findByName", query = "SELECT e FROM DialogueSession e WHERE e.name = :name"), })
public class DialogueSession extends AThing implements Serializable {

	private static final long serialVersionUID = 1L;

	@OneToMany(cascade = CascadeType.ALL)
	public List<Pair> pairs;

	public DialogueSession() {
		super();
	}

	public List<Pair> getPairs() {
		return pairs;
	}

	
}
