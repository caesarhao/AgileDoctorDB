package model;

import java.io.Serializable;
import javax.persistence.*;
import model.APhrase;

/**
 * Entity implementation class for Entity: DoctorPhrase
 *
 */
@Entity
@Table(name="DoctorPhrase")
@NamedQueries({
@NamedQuery(name = "DoctorPhrase.findAll", query = "SELECT e FROM DoctorPhrase e"),
@NamedQuery(name = "DoctorPhrase.findById", query = "SELECT e FROM DoctorPhrase e WHERE e.id = :id"),
@NamedQuery(name = "DoctorPhrase.findByName", query = "SELECT e FROM DoctorPhrase e WHERE e.name = :name"),
})
public class DoctorPhrase extends APhrase implements Serializable {

	
	private static final long serialVersionUID = 1L;

	public DoctorPhrase() {
		super();
	}
   
}
