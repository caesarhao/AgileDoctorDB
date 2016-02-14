package control;

import view.JPlay;

public class Play extends AJFrameControl<JPlay> {

	public Play(JPlay frame) {
		super(frame);
		// TODO Auto-generated constructor stub
	}

	@Override
	void initilizeControl() {
		bindedFrame.setVisible(true);
	}

}
