package model;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Scenario
 *
 */
@Entity
@Table(name="Scenario")
@NamedQueries({
@NamedQuery(name = "Scenario.findAll", query = "SELECT e FROM Scenario e"),
@NamedQuery(name = "Scenario.findById", query = "SELECT e FROM Scenario e WHERE e.id = :id"),
@NamedQuery(name = "Scenario.findByName", query = "SELECT e FROM Scenario e WHERE e.name = :name"),
})
public class Scenario extends AThing implements Serializable {

	
	private static final long serialVersionUID = 1L;
	
	@OneToMany(cascade=CascadeType.ALL)
	public List<MicroSequence> microSequences;
	

	public List<MicroSequence> getMicroSequences() {
		return microSequences;
	}

	public Scenario() {
		super();
	}
	
}
