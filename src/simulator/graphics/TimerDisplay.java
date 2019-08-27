package simulator.graphics;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.io.File;

import javax.swing.JComponent;

public class TimerDisplay extends JComponent {
	//Attributes
	private long startTime;
	private long deltaTime;
	private float fontSize;
	private boolean running;
	
	public TimerDisplay(int width, int height, float fontSize) {
		super();
		this.setPreferredSize(new Dimension(width, height));
		this.fontSize = fontSize;
		running = false;
	}
	
	public void start() {
		this.startTime = System.currentTimeMillis();
		running = true;
	}
	
	public void updateTime() {
		long currentTime = System.currentTimeMillis();
		if (running) {
			deltaTime = (long) ((currentTime - startTime)*0.92);
		} else {
			deltaTime = 0;
		}
		repaint();
	}
	
	public void setTime(long time) {
		startTime = 0;
		deltaTime = time;
	}
	
	public void stop() {
		running = false;
	}
	
	public void paintComponent(Graphics g) {
		Font f = new Font(Font.SANS_SERIF, Font.PLAIN, (int) fontSize);
		try {
			f = Font.createFont(Font.TRUETYPE_FONT, new File("src/resources/Roboto-Regular.ttf"));
			f = f.deriveFont(fontSize);
		} catch (Exception e) {
			e.printStackTrace();
		}
		g.setFont(f);
		
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		
		g.setColor(Color.WHITE);
		if (running) {
			updateTime();
		}
		
		String timeStr = Double.toString((double) deltaTime / 1000);
		
		g.drawString(timeStr, this.getWidth()/2 - 200, this.getHeight() - 15);
	}
}
