package time;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;//对齐的

import tool.SnapShot;

import java.awt.Button;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//import javax.swing.JRootPane;
import java.awt.event.WindowStateListener;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.Timer;
import java.util.Calendar;

public class App {
	private final static long serialVersionUID = 1L;

	public static void main(String[] args) {

		JFrame myFrame = new JFrame();

		try {
			BufferedImage image;
			image = ImageIO.read(App.class.getResource("/time.png"));
			myFrame.setIconImage(image);
		} catch (Exception e) {
			e.printStackTrace();
		}
		myFrame.setTitle("tt");
		// myFrame.setUndecorated( true);
		// myFrame.getRootPane().setWindowDecorationStyle(JRootPane.PLAIN_DIALOG);
		myFrame.addWindowStateListener(new TimeWindowStateListioner(myFrame));
		myFrame.setSize(260, 0);
		myFrame.setLocation(1400, 10);
		myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		myFrame.setAlwaysOnTop(true);

		// 加log的label
		JLabel l1 = new JLabel();
		l1.setVerticalAlignment(SwingConstants.TOP);
		l1.setBounds(0, 31, 300, 530);

		// 加屏幕快照
		Button b1 = new Button("snapshot");
		SnapshotButton sButton1 = new SnapshotButton(l1);
		b1.addActionListener(sButton1);

		b1.setBounds(0, 0, 100, 30);

		Button b2 = new Button("clear_log");
		b2.addActionListener((ActionEvent e) -> {
			sButton1.clear();
		});
		b2.setBounds(105, 0, 100, 30);

		Button b3 = new Button("0");
		b3.addActionListener((ActionEvent e) -> {
			if (b3.getLabel() != "0") {
				b3.setLabel("0");
				StopWatch.close();
			} else {
				StopWatch.instance().schedule(new StopWatch(b3), 0L, 1000);
			}
		});
		b3.setBounds(205, 0, 100, 30);

		// 加画布
		JPanel p1 = new JPanel();
		// JPanel p2 = new JPanel();
		// p1.setSize(10, 200);
		p1.add(b1);
		p1.add(b2);
		p1.add(b3);
		p1.add(l1);
		p1.setLayout(null);

		myFrame.add(p1);
		// myFrame.add(p2);

		myFrame.setVisible(true);

		Timer timer = new Timer();
		timer.schedule(new PrintTimer(myFrame), 300, 500);
	}

}

class PrintTimer extends java.util.TimerTask {
	JFrame f;

	public PrintTimer(JFrame f) {
		this.f = f;
	}

	public void run() {
		// Date now=new Date();
		Calendar c = Calendar.getInstance();
		String TimeStr = String.format("%d/%d %d:%d:%d", c.get(Calendar.DATE),
				c.get(Calendar.MONTH) + 1, c.get(Calendar.HOUR_OF_DAY),
				c.get(Calendar.MINUTE), c.get(Calendar.SECOND));
		this.f.setTitle(TimeStr);
	}
}

// 跑表
class StopWatch extends java.util.TimerTask {
	Button b;
	int a = 0;

	static Timer t3;

	/**
	 * 取出timer
	 * 
	 * @return
	 */
	public static Timer instance() {
		if (StopWatch.t3 == null) {
			StopWatch.t3 = new Timer();
			return StopWatch.t3;
		} else
			return StopWatch.t3;
	}

	public static void close() {
		StopWatch.t3.cancel();
		StopWatch.t3 = null;
	}

	public StopWatch(Button b) {
		this.b = b;
	}

	public void run() {
		this.a++;
		this.b.setLabel(String.valueOf(this.a));
	}
}

// 窗体最小化事件时,窗口马上恢复出来
class TimeWindowStateListioner implements WindowStateListener {
	JFrame f;

	public TimeWindowStateListioner(JFrame f) {
		this.f = f;
	}

	public void windowStateChanged(WindowEvent e) {
		if (e.getNewState() != Frame.NORMAL) {
			this.f.setState(Frame.NORMAL);
		}
		// System.out.println(e.getNewState()+"----"+e.getOldState()+Frame.ICONIFIED
		// );
	}
}

class SnapshotButton implements ActionListener {
	JLabel l1;

	StringBuilder sb = new StringBuilder();

	public SnapshotButton(JLabel l1) {
		this.l1 = l1;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Calendar c = Calendar.getInstance();
		String TimeStr = String.format("%02d%02d%02d%02d%02d",
				c.get(Calendar.MONTH) + 1, c.get(Calendar.DATE),
				c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE),
				c.get(Calendar.SECOND));
		StringBuilder s1 = new StringBuilder("snapshot_").append(TimeStr);

		SnapShot cam = new SnapShot(s1.toString(), "png");//
		cam.doSnapShot();

		this.sb.append("<br>").append(s1).append("__done");
		this.l1.setText("<html>" + this.sb.toString() + "</html>");

	}

	public void clear() {
		this.sb.delete(0, this.sb.length());
		this.l1.setText("");
	}

}