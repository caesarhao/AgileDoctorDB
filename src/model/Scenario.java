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
public class Scenario implements Serializable {

	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
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
   
	
	
}
