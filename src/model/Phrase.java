package model;

import java.io.Serializable;
import java.lang.String;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: Phrase
 *
 */
@Entity
@Table(name="Phrase")
public class Phrase implements Serializable {
	public enum AggressiveLevel{
		Aggressive,
		Neutral,
		Polite
	}
	public enum ClearLevel{
		Clear,
		Unclear
	}
	public enum LongLevel{
		Concise,
		Normal,
		TooLong
	}
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@Column(name="expression", nullable=false)
	private String expression;
	
	@Column(name="agressiveLevel", nullable=false)
	private AggressiveLevel aggressiveLevel;
	
	@Column(name="clearLevel", nullable=false)
	private ClearLevel clearLevel;
	
	@Column(name="longLevel", nullable=false)
	private LongLevel longLevel;
	
	public ClearLevel getClearLevel() {
		return clearLevel;
	}
	public void setClearLevel(ClearLevel clearLevel) {
		this.clearLevel = clearLevel;
	}
	public LongLevel getLongLevel() {
		return longLevel;
	}
	public void setLongLevel(LongLevel longLevel) {
		this.longLevel = longLevel;
	}
	public long getId() {
		return id;
	}
	public Phrase() {
		super();
	}   
	public String getExpression() {
		return this.expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}   
	public AggressiveLevel getAggressiveLevel() {
		return this.aggressiveLevel;
	}

	public void setAggressiveLevel(AggressiveLevel aggressiveLevel) {
		this.aggressiveLevel = aggressiveLevel;
	}
   
}
