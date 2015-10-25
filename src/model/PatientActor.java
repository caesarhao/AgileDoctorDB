package model;

import java.io.Serializable;
import javax.persistence.*;
import model.PhraseActor;

/**
 * Entity implementation class for Entity: PatientActor
 *
 */
@Entity
@Inheritance(strategy=InheritanceType.JOINED)
@Table(name="PatientActor")
@NamedQueries({
@NamedQuery(name = "PatientActor.findAll", query = "SELECT e FROM PatientActor e"),
@NamedQuery(name = "PatientActor.findById", query = "SELECT e FROM PatientActor e WHERE e.id = :id"),
@NamedQuery(name = "PatientActor.findByName", query = "SELECT e FROM PatientActor e WHERE e.name = :name"),
})
public class PatientActor extends PhraseActor implements Serializable {

	
	private static final long serialVersionUID = 1L;

	public PatientActor() {
		super();
	}
   
}
