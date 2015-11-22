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
@NamedQueries({
@NamedQuery(name = "Phrase.findAll", query = "SELECT e FROM Phrase e"),
@NamedQuery(name = "Phrase.findById", query = "SELECT e FROM Phrase e WHERE e.id = :id"),
@NamedQuery(name = "Phrase.findByName", query = "SELECT e FROM Phrase e WHERE e.name = :name"),
})
public class Phrase extends Thing implements Serializable {
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
	public enum PrimitiveType{
		Statement,
		OpenQuestion,
		CloseQuestion,
		Confirmation,
		Disagree,
		DontUnderstand
	}
	private static final long serialVersionUID = 1L;

	@Column(name="expression", nullable=false)
	public String expression;
	
	@Column(name="agressiveLevel", nullable=false)
	public AggressiveLevel aggressiveLevel;
	
	@Column(name="clearLevel", nullable=false)
	public ClearLevel clearLevel;
	
	@Column(name="longLevel", nullable=false)
	public LongLevel longLevel;
	
	@Column(name="primitiveType", nullable=false)
	public PrimitiveType primitiveType;
	
	@Column(name="phraseActor", nullable=false)
	public PhraseActor phraseActor;
	
	public PhraseActor getPhraseActor() {
		return phraseActor;
	}
	public void setPhraseActor(PhraseActor phraseActor) {
		this.phraseActor = phraseActor;
	}
	public PrimitiveType getPrimitiveType() {
		return primitiveType;
	}
	public void setPrimitiveType(PrimitiveType primitiveType) {
		this.primitiveType = primitiveType;
	}
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
