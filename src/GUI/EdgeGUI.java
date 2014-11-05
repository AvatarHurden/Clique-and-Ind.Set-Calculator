package GUI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import javax.swing.JPanel;

public class EdgeGUI extends JPanel implements GraphElement {
	
	private Graphics g;
	private NodeGUI start, end;
	private boolean aura;
	
	private Point lastLocation;
	
	public EdgeGUI(NodeGUI start, Graphics g) {
		this.g = g;
		this.start = start;
		start.addHighlight();
		start.addEdge(this);
	}
	
	public void erase() {
		if (lastLocation != null) {
			g.setColor(Color.WHITE);
			g.drawLine(start.getX(), start.getY(), lastLocation.x, lastLocation.y);
			lastLocation = null;
		}
		start.removeHighlight();
	}
	
	public void setEnd(NodeGUI end) {
		this.end = end;
		end.addEdge(this);
		start.removeHighlight();
	}
	
	public void paintToPoint(Point p) {
		if (lastLocation != null) {
			g.setColor(Color.WHITE);
			g.drawLine(start.getX(), start.getY(), lastLocation.x, lastLocation.y);
		}
		g.setColor(Color.BLACK);
		g.drawLine(start.getX(), start.getY(), p.x, p.y);
		lastLocation = p;
	}
	
	@Override
	public void setSelected(boolean isSelected) {
		if (isSelected) {
			start.addAura();
			end.addAura();
			addAura();
		} else {
			start.removeAura();
			end.removeAura();
			removeAura();
		}
	}
	
	@Override
	public void addAura() {
		if (aura)
			return;
		
		aura = true;
		g.setColor(Color.DARK_GRAY);
		g.drawLine(start.getX() + 5, start.getY() + 5, end.getX() + 5, end.getY() + 5);
		g.drawLine(start.getX() - 5, start.getY() - 5, end.getX() - 5, end.getY() - 5);
	}

	@Override
	public void removeAura() {
		if (!aura)
			return;
		
		aura = false;
		g.setColor(Color.WHITE);
		g.drawLine(start.getX() + 5, start.getY() + 5, end.getX() + 5, end.getY() + 5);
		g.drawLine(start.getX() - 5, start.getY() - 5, end.getX() - 5, end.getY() - 5);
	}

	@Override
	public void paint() {
		paint(g);
	}
	
	@Override
	public void paint(Graphics g) {
		if (lastLocation != null) {
			g.setColor(Color.WHITE);
			g.drawLine(start.getX(), start.getY(), lastLocation.x, lastLocation.y);
			lastLocation = null;
		}
		
		g.setColor(Color.BLACK);
		g.drawLine(start.getX(), start.getY(), end.getX(), end.getY());
	}

	@Override
	public double distance(Point p) {
		double a, b;
		a = (end.getY() - start.getY()) / (double) (end.getX() - start.getX());
		b = end.getY() - a * end.getX();
		
		int x = (int) Math.round((p.x - a * b + a * p.y) / (double) (1 + a * a)); 
		int y = (int) Math.round(a * x + b);
		
		// Não permite poições fora do segmento de reta
		if (x > Math.max(start.getX(), end.getX()))
			x = Math.max(start.getX(), end.getX());
		if (x < Math.min(start.getX(), end.getX()))
			x = Math.min(start.getX(), end.getX());
		
		if (y > Math.max(start.getY(), end.getY()))
			y = Math.max(start.getY(), end.getY());
		if (y < Math.min(start.getY(), end.getY()))
			y = Math.min(start.getY(), end.getY());
		
		double distance = p.distance(new Point(x, y));
		if (start.distance(p) < NodeGUI.AURA || Math.abs(end.distance(p) - distance) < 5)
			return distance + 100;
		else
			return distance;
	}

	@Override
	public void addHighlight() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeHighlight() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public boolean equals(Object edge) {
		if (!(edge instanceof EdgeGUI))
			return false;
		
		return (start.equals(((EdgeGUI)edge).start) && end.equals(((EdgeGUI)edge).end)) || 
				(start.equals(((EdgeGUI)edge).end) && end.equals(((EdgeGUI)edge).start));
	}
}
