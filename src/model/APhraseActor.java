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
public abstract class APhraseActor extends AThing implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public APhraseActor() {
		super();
	}
   
}
