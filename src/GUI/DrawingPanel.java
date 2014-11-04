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
	private NodeGUI selected;
	
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
	
	@Override
	public void mouseDragged(MouseEvent e) {
		if (!placeEdge)
			return;
		
		System.out.println(e.toString());
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		Point point = e.getPoint();
		
		double distance = 35;
		GraphElement closest = null;
		
		for (EdgeGUI edge : edges)
			if (edge.distance(point) < distance / 2) {
				edge.addAura();
				distance = edge.distance(point);
				closest = edge;
			} else
				edge.removeAura();
		for (NodeGUI node : nodes)
			if (node.distance(point) < distance) {
				distance = node.distance(point);
				closest = node;
			}
		
		
		if (selected != null)
			selected.removeAura();
		for (EdgeGUI edge : edges)
			edge.paint();
		for (NodeGUI node : nodes)
			node.paint();
		
		if (closest != null && closest instanceof NodeGUI && closest.equals(selected)) {
			selected = (NodeGUI) closest;
			selected.addAura();
		}
		
		if (closest instanceof NodeGUI)
			selected = (NodeGUI) closest;
		
		if (placingEdge) {
			edge.paintToPoint(point);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (placeVertex) {
			NodeGUI panel = new NodeGUI(nodes.size(), e.getX(), e.getY(), getGraphics());
			add(panel);
			panel.paint();
			nodes.add(panel);
		} else if (placeEdge) {
			
			if (selected == null)
				return;
			
			if (!placingEdge) {
				edge = new EdgeGUI(selected, getGraphics());
				placingEdge = true;

			} else {
				placingEdge = false;
				edge.setEnd(selected);
				edges.add(edge);
				edge.paint();
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
