package model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.*;
import model.AInformation;

/**
 * Entity implementation class for Entity: PatientInformation
 *
 */
@Entity
@Inheritance(strategy=InheritanceType.JOINED)
@Table(name="APatientInformation")
@NamedQueries({
@NamedQuery(name = "APatientInformation.findAll", query = "SELECT e FROM APatientInformation e"),
@NamedQuery(name = "APatientInformation.findById", query = "SELECT e FROM APatientInformation e WHERE e.id = :id"),
@NamedQuery(name = "APatientInformation.findByName", query = "SELECT e FROM APatientInformation e WHERE e.name = :name"),
})
public abstract class APatientInformation extends AInformation implements Serializable {
	public enum AcquiringMethod{
		SaidByPatient,
		AskedByDoctor,
		Examination
	}
	
	private static final long serialVersionUID = 1L;
	
	// low 0 --> high 100
	@Column(name="importance", nullable=false)
	public int importance;
	
	// low 0 --> high 100
	@Column(name="priority", nullable=false)
	public int priority;
	
	@Column(name="acquiringMethod", nullable=false)
	public AcquiringMethod acquiringMethod;
	
	@OneToMany(cascade=CascadeType.ALL)
	public Set<Phrase> possibleAskPhrases;
	
	@OneToMany(cascade=CascadeType.ALL)
	public Set<Phrase> possibleResponsePhrases;
	
	@OneToMany(cascade=CascadeType.ALL)
	public Set<APatientInformation> subInformations;

	public APatientInformation() {
		super();
	}

	public int getImportance() {
		return importance;
	}

	public void setImportance(int importance) {
		this.importance = importance;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public AcquiringMethod getAcquiringMethod() {
		return acquiringMethod;
	}

	public void setAcquiringMethod(AcquiringMethod acquiringMethod) {
		this.acquiringMethod = acquiringMethod;
	}

	public Set<APatientInformation> getSubInformations() {
		return subInformations;
	}

	public Set<Phrase> getPossibleAskPhrases() {
		return possibleAskPhrases;
	}

	public Set<Phrase> getPossibleResponsePhrases() {
		return possibleResponsePhrases;
	}
   
}
