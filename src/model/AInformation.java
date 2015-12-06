package model;

import java.io.Serializable;
import javax.persistence.*;
import model.AThing;

/**
 * Entity implementation class for Entity: Information
 *
 */
@MappedSuperclass
public abstract class AInformation extends AThing implements Serializable {

	
	private static final long serialVersionUID = 1L;

	public AInformation() {
		super();
	}
   
}
