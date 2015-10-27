package model;

import java.io.Serializable;
import javax.persistence.*;
import model.Information;

/**
 * Entity implementation class for Entity: PatientInformation
 *
 */
@MappedSuperclass

public abstract class PatientInformation extends Information implements Serializable {

	
	private static final long serialVersionUID = 1L;

	public PatientInformation() {
		super();
	}
   
}
