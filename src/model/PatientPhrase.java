package model;

import java.io.Serializable;
import javax.persistence.*;
import model.APhrase;

/**
 * Entity implementation class for Entity: PatientPhrase
 *
 */
@Entity
@Table(name="PatientPhrase")
@NamedQueries({
@NamedQuery(name = "PatientPhrase.findAll", query = "SELECT e FROM PatientPhrase e"),
@NamedQuery(name = "PatientPhrase.findById", query = "SELECT e FROM PatientPhrase e WHERE e.id = :id"),
@NamedQuery(name = "PatientPhrase.findByType", query = "SELECT e FROM PatientPhrase e WHERE e.primitiveType = :primitiveType"),
@NamedQuery(name = "PatientPhrase.findByName", query = "SELECT e FROM PatientPhrase e WHERE e.name = :name"),
})
public class PatientPhrase extends APhrase implements Serializable {

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
	
	@Column(name="agressiveLevel", nullable=false)
	public AggressiveLevel aggressiveLevel;
	
	@Column(name="clearLevel", nullable=false)
	public ClearLevel clearLevel;
	
	@Column(name="longLevel", nullable=false)
	public LongLevel longLevel;
	
	public PatientPhrase() {
		super();
	}
	public PatientPhrase(String def, APhraseActor act) {   // default new Phrase (Expression&Actor)
		super();
		this.expression = def;
		this.phraseActor = act; 
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
	public AggressiveLevel getAggressiveLevel() {
		return this.aggressiveLevel;
	}

	public void setAggressiveLevel(AggressiveLevel aggressiveLevel) {
		this.aggressiveLevel = aggressiveLevel;
	}
}
