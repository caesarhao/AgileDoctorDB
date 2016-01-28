package model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: PhraseActor
 *
 */
//@Entity
//@Table(name="APhraseActor")
@MappedSuperclass
public abstract class APhraseActor extends AThing implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Column(name="sex", nullable=false)
	public boolean sex;
	
	public APhraseActor() {
		super();
	}

	public boolean isSex() {
		return sex;
	}

	public void setSex(boolean sex) {
		this.sex = sex;
	}
   
}
