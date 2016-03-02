package model;

import java.io.Serializable;
import javax.persistence.*;
import model.APatientInformation;

/**
 * Entity implementation class for Entity: MedicalInformation
 *
 */
@Entity
@Table(name="MedicalInformation")
@NamedQueries({
@NamedQuery(name = "MedicalInformation.findAll", query = "SELECT e FROM MedicalInformation e"),
@NamedQuery(name = "MedicalInformation.findById", query = "SELECT e FROM MedicalInformation e WHERE e.id = :id"),
@NamedQuery(name = "MedicalInformation.findByName", query = "SELECT e FROM MedicalInformation e WHERE e.name = :name"),
@NamedQuery(name = "MedicalInformation.findBySuperInformation", query = "SELECT e FROM MedicalInformation e WHERE e.superInformation = :superInformation"),
@NamedQuery(name = "MedicalInformation.findSuperInformationNull", query = "SELECT e FROM MedicalInformation e WHERE e.superInformation IS NULL"),
})
public class MedicalInformation extends APatientInformation implements Serializable {

	
	private static final long serialVersionUID = 1L;

	public MedicalInformation() {
		super();
	}
   
}
