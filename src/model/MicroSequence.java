package model;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: MicroSequence
 *
 */
@Entity
@Table(name="MicroSequence")
@NamedQueries({
@NamedQuery(name = "MicroSequence.findAll", query = "SELECT e FROM MicroSequence e"),
@NamedQuery(name = "MicroSequence.findById", query = "SELECT e FROM MicroSequence e WHERE e.id = :id"),
@NamedQuery(name = "MicroSequence.findByName", query = "SELECT e FROM MicroSequence e WHERE e.name = :name"),
})
public class MicroSequence extends AThing implements Serializable {

	
	private static final long serialVersionUID = 1L;

	@OneToMany(cascade=CascadeType.ALL)
	public List<DialogueSession> dialogueSessions;
	
	public List<DialogueSession> getDialogueSessions() {
		return dialogueSessions;
	}
	
	@OneToMany(cascade=CascadeType.ALL)
	public List<MedicalInformation> medicalInfos;
	
	@OneToMany(cascade=CascadeType.ALL)
	public List<FamilyInformation> familyInfos;

	public List<MedicalInformation> getMedicalInfos() {
		return medicalInfos;
	}

	public List<FamilyInformation> getFamilyInfos() {
		return familyInfos;
	}

	public MicroSequence() {
		super();
	}
   
}
