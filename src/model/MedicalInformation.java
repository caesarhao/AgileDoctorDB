package model;

import java.io.Serializable;
import javax.persistence.*;
import model.PatientInformation;

/**
 * Entity implementation class for Entity: MedicalInformation
 *
 */
@MappedSuperclass

public abstract class MedicalInformation extends PatientInformation implements Serializable {

	
	private static final long serialVersionUID = 1L;

	public MedicalInformation() {
		super();
	}
   
}
