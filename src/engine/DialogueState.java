package engine;

public enum DialogueState {
	N, // Normal
	DU, // Don't Understand
	Q, // Questioning
	R, // Refuse
	D, // Drop
	END; // End of session.
	
	public DialogueState nextState(){
		DialogueState nxtSt = this.N;
		switch(this){
		case N:
			// N, DU, Q, R
			break;
		case DU:
			// N, DU, Q, R, D
			break;
		case Q:
			// N, R, D
			break;
		case R:
			// N, Q, D
			break;
		case D:
			nxtSt = END;
			break;
		default:
			nxtSt = this.N;
			break;
			
		}
		return nxtSt;
		
	}
}
