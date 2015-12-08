package model;

import java.io.Serializable;
import javax.persistence.*;
import model.APhrase;

/**
 * Entity implementation class for Entity: PatientPhrase
 *
 */
@Entity
@Table(name="PatientPhrase")
@NamedQueries({
@NamedQuery(name = "PatientPhrase.findAll", query = "SELECT e FROM PatientPhrase e"),
@NamedQuery(name = "PatientPhrase.findById", query = "SELECT e FROM PatientPhrase e WHERE e.id = :id"),
@NamedQuery(name = "PatientPhrase.findByName", query = "SELECT e FROM PatientPhrase e WHERE e.name = :name"),
})
public class PatientPhrase extends APhrase implements Serializable {

	
	private static final long serialVersionUID = 1L;

	public PatientPhrase() {
		super();
	}
   
}
