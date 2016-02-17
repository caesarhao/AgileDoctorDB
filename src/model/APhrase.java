package model;

import java.io.Serializable;
import java.lang.String;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: Phrase
 *
 */
@MappedSuperclass
public abstract class APhrase extends AThing implements Serializable {
	/* As per the discussion on 9th Feb 2016. These three are used only by PatientPhrase
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
	*/
	public enum PrimitiveType{
		Statement,
		OpenQuestion,
		CloseQuestion,
		AnswerWithInfo,
		Confirmation,
		Disagree,
		DontUnderstand
	}
	private static final long serialVersionUID = 1L;

	@Column(name="expression", nullable=false)
	public String expression;
/*	
	@Column(name="agressiveLevel", nullable=false)
	public AggressiveLevel aggressiveLevel;
	
	@Column(name="clearLevel", nullable=false)
	public ClearLevel clearLevel;
	
	@Column(name="longLevel", nullable=false)
	public LongLevel longLevel;
*/	
	@Column(name="primitiveType", nullable=false)
	public PrimitiveType primitiveType;
	
	@Column(name="phraseActor", nullable=false)
	public APhraseActor phraseActor;
	
	public APhraseActor getPhraseActor() {
		return phraseActor;
	}
	public void setPhraseActor(APhraseActor phraseActor) {
		this.phraseActor = phraseActor;
	}
	public PrimitiveType getPrimitiveType() {
		return primitiveType;
	}
	public void setPrimitiveType(PrimitiveType primitiveType) {
		this.primitiveType = primitiveType;
	}
	/*
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
	public AggressiveLevel getAggressiveLevel() {
		return this.aggressiveLevel;
	}

	public void setAggressiveLevel(AggressiveLevel aggressiveLevel) {
		this.aggressiveLevel = aggressiveLevel;
	}
	*/
	public APhrase() {
		super();
	}   
	public String getExpression() {
		return this.expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}   
	
   
}
