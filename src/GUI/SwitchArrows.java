package GUI;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;

import javax.swing.JLabel;

public class SwitchArrows extends JLabel {

	public SwitchArrows(Graphics g, int width, GraphGUI graph) {
		Graphics2D g2d = (Graphics2D) g.create();
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
		
		g2d.setColor(Color.WHITE);
		g2d.fillRect(width - 180, 5, 167, 49);
		
		g2d.drawImage(Toolkit.getDefaultToolkit().getImage(
				SwitchArrows.class.getResource("left_arrow.png")), width - 180, 5, null);

		g2d.drawImage(Toolkit.getDefaultToolkit().getImage(
				SwitchArrows.class.getResource("right_arrow.png")), width - 90, 5, null);
		g2d.dispose();
	}
	
//	public abstract void clickRight();
//	public abstract void clickLeft();
}
