package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Pair")
@NamedQueries({ @NamedQuery(name = "Pair.findAll", query = "SELECT e FROM Pair e"),
		@NamedQuery(name = "Pair.findById", query = "SELECT e FROM Pair e WHERE e.id = :id"),
		@NamedQuery(name = "Pair.findByName", query = "SELECT e FROM Pair e WHERE e.name = :name"), })
public class Pair extends AThing implements Serializable {

	private static final long serialVersionUID = 1L;


	@OneToMany(cascade = CascadeType.ALL)
	public List<DoctorPhrase> possibleDoctorPhrases;

	@OneToMany(cascade = CascadeType.ALL)
	public List<PatientPhrase> possiblePatientPhrases;

	public Pair() {
		super();
	}

	public List<DoctorPhrase> getPossibleDoctorPhrases() {
		return possibleDoctorPhrases;
	}
	public List<DoctorPhrase> getPossibleDoctorPhrasesByType(APhrase.PrimitiveType atype) {
		List<DoctorPhrase> dps = new ArrayList<DoctorPhrase>();
		for(DoctorPhrase dp:possibleDoctorPhrases){
			if(atype.compareTo(dp.primitiveType) == 0){
				dps.add(dp);
			}
		}
		return dps;
	}

	public List<PatientPhrase> getPossiblePatientPhrases() {
		return possiblePatientPhrases;
	}
	public List<PatientPhrase> getPossiblePatientPhrasesByType(APhrase.PrimitiveType atype) {
		List<PatientPhrase> pps = new ArrayList<PatientPhrase>();
		for(PatientPhrase pp:possiblePatientPhrases){
			if(atype.compareTo(pp.primitiveType) == 0){
				pps.add(pp);
			}
		}
		return pps;
		
	}
	
	

}

