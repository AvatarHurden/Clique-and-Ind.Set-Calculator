package GUI;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Point;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class NodeGUI extends JPanel implements GraphElement {
	
	// tamanho do raio do Nodo e da aura do nodo
	public static final int RADIUS = 15;
	public static final int AURA = 30;
	
	private Graphics g;
	
	// O valor é uma String para facilitar o desenho na tela
	private String value = "";
	private int x, y;
	
	private boolean isEnabled = true;
	private boolean isHovered = false;
	private boolean isDeleteHovered = false;
	
	public NodeGUI(int x, int y, Graphics g) {
		this.g = g;
		this.x = x;
		this.y = y;
	}
	
	public void setValue(int value) {
		this.value = String.valueOf(value);
	}
	
	public int getValue() {
		return Integer.valueOf(value);
	}
	
	@Override
	public void setGraphics(Graphics g) {
		this.g = g;
	}
	
	@Override
	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
		if (!this.isEnabled)
			setHovered(false);
	}

	@Override
	public void setDeleteHovered(boolean isDeleteHovered) {
		if (this.isDeleteHovered == isDeleteHovered)
			return;
		
		this.isDeleteHovered = isDeleteHovered;
		g.setColor(isDeleteHovered ? Color.RED : Color.WHITE);
		g.fillOval(x - RADIUS - 4, y - RADIUS - 4, 2*RADIUS + 8, 2*RADIUS + 8);
		paint();
	}
	
//	@Override
//	public void setHighlight(boolean isHighlighted) {
//		if (isHighlighted == isHighlighted)
//			return;
//		
//		this.isHighlighted = isHighlighted;
//		g.setColor(isHighlighted ? (isEnabled? Color.BLUE : Color.LIGHT_GRAY) : Color.WHITE);
//		g.fillOval(x - RADIUS - 4, y - RADIUS - 4, 2*RADIUS + 8, 2*RADIUS + 8);
//		paint();
//	}

	@Override
	public void setHovered(boolean isHovered) {
		if (this.isHovered == isHovered)
			return;
		
		this.isHovered = isHovered;
		g.setColor(isHovered ? (isEnabled? Color.DARK_GRAY : Color.LIGHT_GRAY) : Color.WHITE);
		g.drawOval(x - AURA, y - AURA, 2*AURA, 2*AURA);
	}

	@Override
	public void paint() {
		g.setColor(isEnabled ? Color.BLACK : Color.LIGHT_GRAY);
		g.fillOval(x - RADIUS, y - RADIUS, 2*RADIUS, 2*RADIUS);
		
		g.setColor(Color.WHITE);
		FontMetrics fm = new JLabel().getFontMetrics(new JLabel().getFont());
        g.drawString(value, x - fm.stringWidth(value) / 2, y + (fm.getMaxAscent() - fm.getMaxDescent()) / 2);
	}
	
	@Override
	public void erase() {
		setHovered(false);
		setDeleteHovered(false);
//		setHighlight(false);
		g.setColor(Color.WHITE);
		g.fillOval(x - RADIUS, y - RADIUS, 2*RADIUS, 2*RADIUS);
	}
	
	@Override
	public double distance(Point p) {
		return p.distance(new Point(x, y));
	}
	
	public String toString() {
		return value;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void moveToPoint(Point p) {
		erase();
		x = p.x;
		y = p.y;
		paint();
	}
}
