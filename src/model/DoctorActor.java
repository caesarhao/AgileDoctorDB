package model;

import java.io.Serializable;
import javax.persistence.*;
import model.APhraseActor;

/**
 * Entity implementation class for Entity: DoctorActor
 *
 */
@Entity
@Table(name="DoctorActor")
@NamedQueries({
@NamedQuery(name = "DoctorActor.findAll", query = "SELECT e FROM DoctorActor e"),
@NamedQuery(name = "DoctorActor.findById", query = "SELECT e FROM DoctorActor e WHERE e.id = :id"),
@NamedQuery(name = "DoctorActor.findByName", query = "SELECT e FROM DoctorActor e WHERE e.name = :name"),
})
public class DoctorActor extends APhraseActor implements Serializable {

	
	private static final long serialVersionUID = 1L;

	public DoctorActor() {
		super();
	}
   
}
