package GUI;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class DrawingPanel extends JPanel implements MouseMotionListener, MouseListener{

	private boolean placeVertex = false;
	private boolean placeEdge = false;
	
	List<Point> nodes;
	
	public DrawingPanel() {
		
		nodes = new ArrayList<Point>();
		
		setPreferredSize(new Dimension(600, 400));
		setBorder(new LineBorder(Color.BLACK));
		setBackground(Color.white);
		
		addMouseMotionListener(this);
		addMouseListener(this);
	}
	
	public void setPlaceVertex(boolean set) {
		placeVertex = set;
	}

	public void setPlaceEdge(boolean set) {
		placeEdge = set;
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		if (!placeEdge)
			return;
		
		System.out.println(e.toString());
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		Point point = e.getPoint();
		Graphics2D g = (Graphics2D) getGraphics();	
		
		for (Point p : nodes)
			if (p.distance(point) < 40) {
				g.setColor(Color.gray);
				g.setStroke(new BasicStroke());
				g.drawOval(p.x - 10, p.y - 10, 20, 20);
			} else {
				g.setColor(Color.white);
				g.drawOval(p.x - 10, p.y - 10, 20, 20);
			}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (!placeVertex)
			return;
		
		Graphics2D g = (Graphics2D) getGraphics();	
		g.fillOval(e.getX() - 5, e.getY() - 5, 10, 10);
		nodes.add(e.getPoint());
}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
}
