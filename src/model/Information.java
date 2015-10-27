package model;

import java.io.Serializable;
import javax.persistence.*;
import model.Thing;

/**
 * Entity implementation class for Entity: Information
 *
 */
@MappedSuperclass
public abstract class Information extends Thing implements Serializable {

	
	private static final long serialVersionUID = 1L;

	public Information() {
		super();
	}
   
}
