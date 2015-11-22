package model;

import java.io.Serializable;
import javax.persistence.*;
import model.MedicalInformation;

/**
 * Entity implementation class for Entity: Symptom
 *
 */
@Entity
@Table(name="Symptom")
@NamedQueries({
@NamedQuery(name = "Symptom.findAll", query = "SELECT e FROM Symptom e"),
@NamedQuery(name = "Symptom.findById", query = "SELECT e FROM Symptom e WHERE e.id = :id"),
@NamedQuery(name = "Symptom.findByName", query = "SELECT e FROM Symptom e WHERE e.name = :name"),
})
public class Symptom extends MedicalInformation implements Serializable {

	
	private static final long serialVersionUID = 1L;
	@Column(name="expression", nullable=false)
	public String expression;
	
	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}

	public Symptom() {
		super();
	}
   
}
