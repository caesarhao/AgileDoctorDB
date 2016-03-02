package model;

import java.io.Serializable;
import javax.persistence.*;
import model.APatientInformation;

/**
 * Entity implementation class for Entity: FamilyInformation
 *
 */
@Entity
@Table(name="FamilyInformation")
@NamedQueries({
@NamedQuery(name = "FamilyInformation.findAll", query = "SELECT e FROM FamilyInformation e"),
@NamedQuery(name = "FamilyInformation.findById", query = "SELECT e FROM FamilyInformation e WHERE e.id = :id"),
@NamedQuery(name = "FamilyInformation.findByName", query = "SELECT e FROM FamilyInformation e WHERE e.name = :name"),
@NamedQuery(name = "FamilyInformation.findBySuperInformation", query = "SELECT e FROM FamilyInformation e WHERE e.superInformation = :superInformation"),
@NamedQuery(name = "FamilyInformation.findSuperInformationNull", query = "SELECT e FROM FamilyInformation e WHERE e.superInformation IS NULL"),})
public class FamilyInformation extends APatientInformation implements Serializable {

	
	private static final long serialVersionUID = 1L;

	public FamilyInformation() {
		super();
	}
   
}
