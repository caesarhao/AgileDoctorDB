package control;
import javax.swing.*;
public abstract class AJFrameControl<T extends JFrame> {
	public T bindedFrame = null;
	public T getBindedFrame() {
		return bindedFrame;
	}
	public AJFrameControl(T frame) {
		bindedFrame = frame;
	}
	abstract void initilizeControl();
}
