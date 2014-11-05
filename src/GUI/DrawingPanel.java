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

import main.Graph;

public class DrawingPanel extends JPanel implements MouseMotionListener, MouseListener {

	private boolean creating = false;
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
	
	public Graph getGraph() {
		
		return null;
	}
	
	public void setCreating(boolean set) {
		creating = set;
	}
	
	public void setProximity(Point mouse) {
		double distance = 35;
		GraphElement closest = null;
		
		for (NodeGUI node : nodes)
			if (node.distance(mouse) < distance) {
				distance = node.distance(mouse);
				closest = node;
			}
		
		if (selected != null && selected != closest)
			selected.removeAura();
		
		selected = closest;

		if (placingEdge)
			edge.paintToPoint(mouse);
		
		repaintComponents();
	}
	
	public void repaintComponents() {
		for (GraphElement e : edges)
			e.paint();
		for (GraphElement e : nodes) 
			e.paint();
		
		if (selected != null)
			selected.addAura();
	}
	
	public void dragCreate(Point p) {
		if (placingEdge) {
			edge.paintToPoint(p);
			setProximity(p);
			repaintComponents();
		}
	}
	
	public void clickCreate(Point p) {
		NodeGUI node = new NodeGUI(nodes.size(), p.x, p.y, getGraphics());
		nodes.add(node);
		repaintComponents();
	}
	
	public void pressCreate() {
		if (selected != null && selected instanceof NodeGUI) {
			placingEdge = true;
			edge = new EdgeGUI((NodeGUI) selected, getGraphics());
		}
	}
	
	public void releaseCreate() {
		if (!placingEdge)
			return;
		
		placingEdge = false;
		
		if (selected != null && edge != null && selected instanceof NodeGUI) {
			edge.setEnd((NodeGUI) selected);
			if (!edges.contains(edge))
				edges.add(edge);
			System.out.println(edges.size());
		}
		
		edge.erase();
		edge = null;
		repaintComponents();	
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		if (creating)
			dragCreate(e.getPoint());
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		setProximity(e.getPoint());
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (creating)
			clickCreate(e.getPoint());
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (creating)
			pressCreate();
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		if (creating)
			releaseCreate();

		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
