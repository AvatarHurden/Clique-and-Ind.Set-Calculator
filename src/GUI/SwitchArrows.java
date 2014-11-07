package GUI;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;

public abstract class SwitchArrows extends JLabel {
	
	private final int[] xs = new int[] { 1, 1, 36, 44, 44, 75, 75, 44, 44, 35 };
	private final int[] ys = new int[] { 23, 26, 47, 47, 35, 35, 15, 15, 3, 3 };
	
	private Polygon leftArrow, rightArrow;
	
	private Point start;
	
	private boolean isHovered;
	private boolean isActive;
	
	public SwitchArrows(Graphics g, Point p, GraphGUI graph) {
		start = p;
		
		setBounds(p.x, p.y, 180, 50);
		
		int[] xsLeft = new int[xs.length];
		for (int i = 0; i < xs.length; i++)
			xsLeft[i] = xs[i] + p.x;
		
		int[] xsRight = new int[xs.length];
		for (int i = 0; i < xs.length; i++)
			xsRight[i] = 76 - xs[i] + p.x + 90;
		
		int[] ysBoth = new int[ys.length];
		for (int i = 0; i < ys.length; i++)
			ysBoth[i] = ys[i] + p.y;
		
		leftArrow = new Polygon(xsLeft, ysBoth, 10);
		rightArrow = new Polygon(xsRight, ysBoth, 10);
		
		setMouseListener();
	}
	
	public void setActive(boolean isActive, Graphics g) {
		if (this.isActive == true && isActive == false) {
			g.setColor(Color.WHITE);
			g.fillRect(start.x, start.y, 179, 50);
		}
			
		this.isActive = isActive;
	}
	
	private void setMouseListener() {
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (leftArrow.contains(e.getPoint()))
					System.out.println("leftClicked");
				if (rightArrow.contains(e.getPoint()))
					System.out.println("rightClickjed");
			}
		});
	}
	
	public boolean isHovered(Point p, Graphics g) {
		if (!isActive)
			return false;
		
		paintHoverPolygon(g, leftArrow, leftArrow.contains(p));
		paintHoverPolygon(g, rightArrow, rightArrow.contains(p));
			
		return leftArrow.contains(p) || rightArrow.contains(p);
	}
	
	private void paintHoverPolygon(Graphics g, Polygon p, boolean isHovered) {
		Graphics2D g2d = (Graphics2D) g.create();
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
		
		if (isHovered) {
			g2d.setColor(new Color(200, 200, 200));
			g2d.fillPolygon(p);
		} else {
			if (this.isHovered) {
				g2d.setColor(Color.WHITE);
				g2d.fillPolygon(p);
			}
		
			g2d.setColor(new Color(220, 220, 220));
			g2d.fillPolygon(p);
			this.isHovered = false;
		}
	}
	
	public boolean wasCliked(Point p) {
		if (!isActive)
			return false;
		if (leftArrow.contains(p))
			clickLeft();
		if (rightArrow.contains(p))
			clickRight();
		return rightArrow.contains(p) || leftArrow.contains(p);
	}
	
	@Override
	public void paint(Graphics g) {
		if (!isActive)
			return;
		
		Graphics2D g2d = (Graphics2D) g.create();
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
		
		if (isHovered) {
			g2d.setColor(Color.WHITE);
			g2d.fillRect(start.x, start.y, 179, 50);
		}
		
		g2d.setColor(new Color(220, 220, 220));
		g2d.fillPolygon(leftArrow);
		g2d.fillPolygon(rightArrow);
		
		g2d.dispose();
	}
	
	public abstract void clickRight();
	public abstract void clickLeft();
}
