package model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Scenario
 *
 */
//@Entity
//@Table(name="Thing")
//@Inheritance(strategy = InheritanceType.JOINED)
@MappedSuperclass
public abstract class Thing implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "name", nullable = false, unique = true)
	private String name;

	public long getId() {
		return id;
	}

	public Thing() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
