package model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: DialogueSession
 *
 */
@Entity
@Table(name="DialogueSession")

public class DialogueSession implements Serializable {

	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	public long getId() {
		return id;
	}
	public DialogueSession() {
		super();
	}
   
}
