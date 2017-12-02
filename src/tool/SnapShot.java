package tool;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class SnapShot {

	private String fileName; // 文件的前缀
	private String defaultName = "GuiCamera";
	static int serialNum = 0;
	private String imageFormat; // 图像文件的格式
	private String defaultImageFormat = "png";
	Dimension d = Toolkit.getDefaultToolkit().getScreenSize();

	/**
	 * 单元测试入口
	 */
	static public void test() {
		SnapShot cam = new SnapShot("d:\\Hello", "png");//

		cam.doSnapShot();
	}

	/**
	 * 构造
	 */
	public SnapShot() {
		this.fileName = defaultName;
		this.imageFormat = defaultImageFormat;
	}
	
	public SnapShot(String s, String format) {
		this.fileName = s;
		this.imageFormat = format;
	}

	/**
	 * 把屏幕生成图像
	 */
	public void doSnapShot() {
		try {
			// 拷贝屏幕到一个BufferedImage对象screenshot
			BufferedImage screenshot = (new Robot())
					.createScreenCapture(new Rectangle(0, 0,
							(int) d.getWidth(), (int) d.getHeight())
					);
			serialNum++;
			// 根据文件前缀变量和文件格式变量，自动生成文件名
			String name = fileName + String.valueOf(serialNum) + "."
					+ imageFormat;
			File f = new File(name);
//			System.out.print("Save File " + name);
			
			// 将screenshot对象写入图像文件
			ImageIO.write(screenshot, imageFormat, f);
			
//			System.out.print("..Finished!\n");
		} catch (Exception ex) {
			System.out.println(ex);
		}
	}
}
