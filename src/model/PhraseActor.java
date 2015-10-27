package model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: PhraseActor
 *
 */
//@Entity
//@Table(name="PhraseActor")
@MappedSuperclass
public abstract class PhraseActor extends Thing implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public PhraseActor() {
		super();
	}
   
}
