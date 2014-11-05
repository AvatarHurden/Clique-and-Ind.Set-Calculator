package GUI;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

public class NodeGUI extends JPanel implements GraphElement {
	
	public static final int RADIUS = 15;
	public static final int AURA = 30;
	
	private Graphics g;
	
	private String value;
	private int x, y;
	
	private boolean highlight;
	
	private List<EdgeGUI> edges;
	
	public NodeGUI(int value, int x, int y, Graphics g) {
		this.value = String.valueOf(value);
		this.x = x;
		this.y = y;
		this.g = g;
		
		edges = new ArrayList<EdgeGUI>();
	}

	public void addEdge(EdgeGUI edge) {
		edges.add(edge);
	}
	
	@Override
	public void setSelected(boolean isSelected) {
		if (isSelected) addAura();
		else removeAura();
		
		for (EdgeGUI edge : edges)
			if (isSelected)
				edge.addAura();
			else
				edge.removeAura();
	}
	
	@Override
	public void addHighlight() {
		highlight = true;
		paint();
	}

	@Override
	public void removeHighlight() {
		highlight = false;
		g.setColor(Color.WHITE);
		g.fillOval(x - RADIUS - 4, y - RADIUS - 4, 2*RADIUS + 8, 2*RADIUS + 8);
		paint();
	}

	@Override
	public void addAura() {
		g.setColor(Color.DARK_GRAY);
		g.drawOval(x - AURA, y - AURA, 2*AURA, 2*AURA);
	}

	@Override
	public void removeAura() {
		g.setColor(Color.WHITE);
		g.drawOval(x - AURA, y - AURA, 2*AURA, 2*AURA);
	}
	
	@Override
	public void paint() {
		if (highlight) {
			g.setColor(Color.BLUE);
			g.fillOval(x - RADIUS - 4, y - RADIUS - 4, 2*RADIUS + 8, 2*RADIUS + 8);
		}
		paint(g);
	}
	
	@Override
	public void paint(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillOval(x - RADIUS, y - RADIUS, 2*RADIUS, 2*RADIUS);
		
		g.setColor(Color.WHITE);
		FontMetrics fm = getFontMetrics(getFont());
        g.drawString(value, x - fm.stringWidth(value) / 2, y + (fm.getMaxAscent() - fm.getMaxDescent()) / 2);
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
}
