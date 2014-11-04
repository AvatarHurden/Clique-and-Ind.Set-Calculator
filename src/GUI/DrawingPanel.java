package GUI;

import java.awt.Color;
import java.awt.Dimension;
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
	private boolean placingEdge = false;
	
	private List<NodeGUI> nodes;
	private GraphElement selected;
	
	private List<EdgeGUI> edges;
	private EdgeGUI edge;
	
	public DrawingPanel() {
		
		nodes = new ArrayList<NodeGUI>();
		edges = new ArrayList<EdgeGUI>();
		setLayout(null);
		
		setPreferredSize(new Dimension(800, 600));
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
	
	public void setProximity(Point mouse) {
		double distance = 35;
		GraphElement closest = null;
		
		for (EdgeGUI edge : edges)
			if (edge.distance(mouse) < distance) {
				distance = edge.distance(mouse);
				closest = edge;
			}
		for (NodeGUI node : nodes)
			if (node.distance(mouse) < distance) {
				distance = node.distance(mouse);
				closest = node;
			}
				
		selected = closest;
		
		repaintComponents();

		if (placingEdge)
			edge.paintToPoint(mouse);
	}
	
	public void repaintComponents() {
		for (GraphElement e : edges) {
			e.removeAura();
			e.removeHighlight();
			e.paint();
		}
		for (GraphElement e : nodes) {
			e.removeAura();
			e.removeHighlight();
			e.paint();
		}
		
		if (selected != null)
			selected.addAura();
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		if (!placeEdge)
			return;
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		Point point = e.getPoint();
		
		setProximity(point);

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (placeVertex) {
			
			NodeGUI node = new NodeGUI(nodes.size(), e.getX(), e.getY(), getGraphics());
			nodes.add(node);
			repaintComponents();
			
		} else if (placeEdge) {
			
			if (selected == null || !(selected instanceof NodeGUI))
				return;
			
			if (!placingEdge) {
				edge = new EdgeGUI((NodeGUI) selected, getGraphics());
				placingEdge = true;
			} else {
				placingEdge = false;
				edge.setEnd((NodeGUI) selected);
				edges.add(edge);
				repaintComponents();
			}
			
		}
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
	}
	
}
