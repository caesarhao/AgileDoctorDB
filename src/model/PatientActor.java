package model;

import java.io.Serializable;
import javax.persistence.*;
import model.APhraseActor;

/**
 * Entity implementation class for Entity: PatientActor
 *
 */
@Entity
//@Inheritance(strategy=InheritanceType.JOINED)
@Table(name="PatientActor")
@NamedQueries({
@NamedQuery(name = "PatientActor.findAll", query = "SELECT e FROM PatientActor e"),
@NamedQuery(name = "PatientActor.findById", query = "SELECT e FROM PatientActor e WHERE e.id = :id"),
@NamedQuery(name = "PatientActor.findByName", query = "SELECT e FROM PatientActor e WHERE e.name = :name"),
})
public class PatientActor extends APhraseActor implements Serializable {

	
	private static final long serialVersionUID = 1L;
	
	@Column(name="aggressiveLevel", nullable=false)
	public int aggressiveLevel;
	
	@Column(name="longPhraseLevel", nullable=false)
	public int longPhraseLevel;
	
	@Column(name="clearLevel", nullable=false)
	public int clearLevel;
	
	@Column(name="confidentLevel", nullable=false)
	public int confidentLevel;
	
	public int getConfidentLevel() {
		return confidentLevel;
	}

	public void setConfidentLevel(int confidentLevel) {
		this.confidentLevel = confidentLevel;
	}

	public PatientActor() {
		super();
	}

	public int getAggressiveLevel() {
		return aggressiveLevel;
	}

	public void setAggressiveLevel(int aggressiveLevel) {
		this.aggressiveLevel = aggressiveLevel;
	}

	public int getLongPhraseLevel() {
		return longPhraseLevel;
	}

	public void setLongPhraseLevel(int longPhraseLevel) {
		this.longPhraseLevel = longPhraseLevel;
	}

	public int getClearLevel() {
		return clearLevel;
	}

	public void setClearLevel(int clearLevel) {
		this.clearLevel = clearLevel;
	}
	public String getTitle(){
		String title = (this.sex)?"M. ":"Mme " + this.getName();
		return title;
	}
   
}
