package model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: DialogueSession
 *
 */
@Entity
@Table(name = "DialogueSession")
@NamedQueries({ @NamedQuery(name = "DialogueSession.findAll", query = "SELECT e FROM DialogueSession e"),
		@NamedQuery(name = "DialogueSession.findById", query = "SELECT e FROM DialogueSession e WHERE e.id = :id"),
		@NamedQuery(name = "DialogueSession.findByName", query = "SELECT e FROM DialogueSession e WHERE e.name = :name"), })
public class DialogueSession extends AThing implements Serializable {

	private static final long serialVersionUID = 1L;

	//@JoinColumn(name = "doctorPhrase", nullable = true)
	@Transient // just used for simulation, no need to save in db.
	public DoctorPhrase doctorPhrase;

	//@JoinColumn(name = "patientPhrase", nullable = true)
	@Transient // just used for simulation, no need to save in db.
	public PatientPhrase patientPhrase;

	public DialogueSession() {
		super();
	}

	public DoctorPhrase getDoctorPhrase() {
		return doctorPhrase;
	}

	public void setDoctorPhrase(DoctorPhrase doctorPhrase) {
		this.doctorPhrase = doctorPhrase;
	}

	public PatientPhrase getPatientPhrase() {
		return patientPhrase;
	}

	public void setPatientPhrase(PatientPhrase patientPhrase) {
		this.patientPhrase = patientPhrase;
	}

}
