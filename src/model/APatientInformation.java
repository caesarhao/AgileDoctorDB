package model;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.persistence.*;
import model.AInformation;

/**
 * Entity implementation class for Entity: FamilyInformation, MedicalInformation  //PatientInformation
 *
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "APatientInformation")
@NamedQueries({ @NamedQuery(name = "APatientInformation.findAll", query = "SELECT e FROM APatientInformation e"),
		@NamedQuery(name = "APatientInformation.findById", query = "SELECT e FROM APatientInformation e WHERE e.id = :id"),
		@NamedQuery(name = "APatientInformation.findByName", query = "SELECT e FROM APatientInformation e WHERE e.name = :name"),
		@NamedQuery(name = "APatientInformation.findBySuperInformation", query = "SELECT e FROM APatientInformation e WHERE e.superInformation = :superInformation"),
		@NamedQuery(name = "APatientInformation.findSuperInformationNull", query = "SELECT e FROM APatientInformation e WHERE e.superInformation IS NULL"),})
public abstract class APatientInformation extends AInformation implements Serializable {
	public enum AcquiringMethod {
		SaidByPatient, AskedByDoctor, Examination, ConsultToFile
	}

	private static final long serialVersionUID = 1L;

	// low 0 --> high 100
	@Column(name = "importance", nullable = false)
	public int importance;

	// low 0 --> high 100
	@Column(name = "priority", nullable = false)
	public int priority;

	@Column(name = "acquiringMethod", nullable = false)
	
	public AcquiringMethod acquiringMethod;

	@OneToMany(cascade = CascadeType.ALL)
	public List<Pair> pairs;



	// hard to maintain, use superInformation as a replacement
	//@OneToMany(cascade = CascadeType.ALL)
	//public Set<APatientInformation> subInformations;

	@ManyToOne
	@JoinColumn(name = "superInformation", nullable = true)
	public APatientInformation superInformation;

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
		return this.acquiringMethod;
	}

	public void setAcquiringMethod(AcquiringMethod acquiringMethod) {
		this.acquiringMethod = acquiringMethod;
	}
/*	public int numberAcquiringMethod(){
		return this.acquiringMethod.size();
	}*/


	/*
	//Info tree: get ResponsePhrases by session state
	public Set<PatientPhrase> getPossibleResponsePhrasesByState(enum type){
		
		Set<PatientPhrase> res =  
		
	}*/

	public List<Pair> getPairs() {
		return pairs;
	}

	public APatientInformation getSuperInformation() {
		return superInformation;
	}

	public void setSuperInformation(APatientInformation superInformation) {
		this.superInformation = superInformation;
	}
	
	

}
