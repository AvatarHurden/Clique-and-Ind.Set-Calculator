package GUI;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
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
	}
	
	public void setEnd(NodeGUI end) {
		this.end = end;
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
	public void addAura() {
		if (aura)
			return;
		
		aura = true;
		g.setColor(Color.BLUE);
		((Graphics2D) g).setStroke(new BasicStroke(5));
		g.drawLine(start.getX(), start.getY(), end.getX(), end.getY());
	}

	@Override
	public void removeAura() {
		if (!aura)
			return;
		
		aura = false;
		g.setColor(Color.WHITE);
		((Graphics2D) g).setStroke(new BasicStroke(5));
		g.drawLine(start.getX(), start.getY(), end.getX(), end.getY());
		((Graphics2D) g).setStroke(new BasicStroke(1));
		paint();
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
		
		return p.distance(new Point(x, y));
	}

	@Override
	public void addHighlight() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeHighlight() {
		// TODO Auto-generated method stub
		
	}

}
