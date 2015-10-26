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
public class DialogueSession extends Thing implements Serializable {

	
	private static final long serialVersionUID = 1L;
	
	public DialogueSession() {
		super();
	}
   
}
