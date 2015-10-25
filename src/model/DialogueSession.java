package model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: DialogueSession
 *
 */
@Entity
@Table(name="DialogueSession")
@NamedQueries({
@NamedQuery(name = "DialogueSession.findAll", query = "SELECT e FROM DialogueSession e"),
@NamedQuery(name = "DialogueSession.findById", query = "SELECT e FROM DialogueSession e WHERE e.id = :id"),
@NamedQuery(name = "DialogueSession.findByName", query = "SELECT e FROM DialogueSession e WHERE e.name = :name"),
})
public class DialogueSession implements Serializable {

	
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
	public long getId() {
		return id;
	}
	public DialogueSession() {
		super();
	}
   
}
