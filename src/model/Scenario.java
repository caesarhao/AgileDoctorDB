package model;

import java.io.Serializable;
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
public class Scenario implements Serializable {

	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@Column(name="name", nullable=false, unique=true)
	private String name;
	
	@OneToMany(cascade=CascadeType.ALL)
	private Set<MicroSequence> microSequences;
	
	public long getId() {
		return id;
	}

	public Set<MicroSequence> getMicroSequences() {
		return microSequences;
	}

	public Scenario() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
   
	
	
}
