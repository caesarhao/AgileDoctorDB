package engine;

public enum DialogueState {
	N, // Normal
	DU, // Don't Understand
	Q, // Questioning
	R, // Refuse
	D, // Drop (go Session Exit Point)
	END; // End of session.
	
	public DialogueState nextState(){
		DialogueState nxtSt = this.N;
		switch(this){
		case N:  // State Normal
			// N, DU, Q, R
			break;
		case DU: // State Don't Understand 
			// N, DU, Q, R, D
			break;
		case Q:  // State Questionnement
			// N, R, D
			break;
		case R:  // State Refuse
			// N, Q, D
			break;
		case D:  // 
			nxtSt = END;
			break;
		default:
			nxtSt = this.N;
			break;
			
		}
		return nxtSt;
		
	}
}
