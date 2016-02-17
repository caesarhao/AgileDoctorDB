package control;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;
public abstract class AJFrameControl<T extends JFrame> {
	public T bindedFrame = null;
	public JFrame parent = null;
	public T getBindedFrame() {
		return bindedFrame;
	}
	abstract void thisWindowClosed(WindowEvent e);
	public AJFrameControl(T frame, JFrame parent) {
		bindedFrame = frame;
		this.parent = parent;
		bindedFrame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				thisWindowClosed(e);
			}
		});
	}
	public abstract void initilizeControl();
}
