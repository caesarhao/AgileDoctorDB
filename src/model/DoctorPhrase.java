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
@NamedQuery(name = "DoctorPhrase.findByPrimitiveType", query = "SELECT e FROM DoctorPhrase e WHERE e.primitiveType = :primitiveType"),
})
public class DoctorPhrase extends APhrase implements Serializable {

	
	private static final long serialVersionUID = 1L;
	
	@Column(name="effTrust", nullable=false)
	public double effTrust = 0.0;
	
	@Column(name="effDisturbance", nullable=false)
	public double effDisturbance = 0.0;
	
	@Column( name="valClarity", nullable=false)
	public double valClarity = 0.5;
	
	public DoctorPhrase() {
		super();
	}

	public double getEffTrust() {
		return effTrust;
	}

	public void setEffTrust(double effTrust) {
		this.effTrust = effTrust;
	}

	public double getEffDisturbance() {
		return effDisturbance;
	}

	public void setEffDisturbance(double effDisturbance) {
		this.effDisturbance = effDisturbance;
	}
	
	public double getvalClarity() {
		return valClarity;
	}
	
	public void setvalClarity(double valClarity){
		this.valClarity = valClarity;
	}
   
}
