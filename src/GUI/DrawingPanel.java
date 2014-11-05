package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import main.Graph;

public class DrawingPanel extends JPanel implements MouseMotionListener, MouseListener {

	/**
	 * Estados poss�veis do painel
	 */
	public enum DrawingState {
		CREATING, MOVING, DELETING, NONE;
	}
	
	private DrawingState state = DrawingState.NONE;
	private boolean placingEdge = false;
	
	private GraphGUI graph;
	
	private GraphElement hoveredElement;
	private EdgeGUI edge;
	
	public DrawingPanel() {
		
		setPreferredSize(new Dimension(800, 600));
		setBorder(new LineBorder(Color.BLACK));
		setBackground(Color.white);
		
		addMouseMotionListener(this);
		addMouseListener(this);

		graph = new GraphGUI();
	}
	
	public Graph getGraph() {
		return graph.toGraph();
	}
	
	public void setGraph(GraphGUI graph) {
		this.graph = graph;
	}
	
	/**
	 * Define o estado do painel
	 */
	public void setState(DrawingState state) {
		this.state = state;
	}
	
	/**
	 * Define o elemento a ser "hovered", dada a posi��o do mouse.
	 * Tamb�m pinta o elemento "hovered" com a aura
	 */
	public void setHovered(Point point) {
		GraphElement closest = graph.getClosest(point, false);
		
		if (hoveredElement != closest)
			graph.setAura(hoveredElement, false);
		
		hoveredElement = closest;

		repaintComponents();
	}
	
	/**
	 * Pinta todos os elementos na tela, colocando �nfase nos adequados
	 */
	public void repaintComponents() {
		graph.drawGraph();
		graph.setAura(hoveredElement, true);
	}
	
	/**
	 * M�todo a ser chamado quando o mouse for arrastado no modo CREATING.
	 * Se estiver uma aresta sendo criada, ela � reposicionada e repintada
	 */
	public void dragCreate(Point p) {
		if (placingEdge) {
			edge.paintToPoint(p);
			setHovered(p);
			repaintComponents();
		}
	}
	
	/**
	 * M�todo a ser chamado quando o mouse for clicado no modo CREATING.
	 * Cria um novo nodo e adiciona-o no grafo
	 * @param p
	 */
	public void clickCreate(Point p) {
		graph.createNode(p, getGraphics());
		repaintComponents();
	}
	
	/**
	 * M�todo a ser chamado quando o mouse for pressionado no modo CREATING.
	 * Se hoveredElement for um nodo, come�a a cria��o de uma aresta no nodo
	 */
	public void pressCreate() {
		if (hoveredElement instanceof NodeGUI) {
			placingEdge = true;
			edge = new EdgeGUI((NodeGUI) hoveredElement, getGraphics());
		}
	}
	
	/**
	 * M�todo a ser chamado quando o mouse for largado no modo CREATING.
	 * Se hoveredElement for um nodo e uma aresta estiver sendo criada, termina a cria��o
	 * e adiciona no grafo.
	 */
	public void releaseCreate() {
		if (!placingEdge)
			return;
		
		placingEdge = false;
		
		// Finaliza a cria��o apenas se o hoveredElement for um nodo diferente do in�cio da aresta
		if (hoveredElement instanceof NodeGUI && !edge.isEdgeOf((NodeGUI) hoveredElement)) {
			edge.setEnd((NodeGUI) hoveredElement);
			graph.addEdge(edge);
		}
		
		edge.erase();
		repaintComponents();	
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		switch (state) {
		case CREATING:
			dragCreate(e.getPoint());
			break;
		default:
			break;
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		setHovered(e.getPoint());
	}

	// Um clique s� � chamado quando o press e release ocorrem num per�odo curto de tempo
	// e em locais pr�ximos (eu acho)
	@Override
	public void mouseClicked(MouseEvent e) {
		switch (state) {
		case CREATING:
			clickCreate(e.getPoint());
			break;
		default:
			break;
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		switch (state) {
		case CREATING:
			pressCreate();
			break;
		default:
			break;
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		switch (state) {
		case CREATING:
			releaseCreate();
			break;
		default:
			break;
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {}

	@Override
	public void mouseExited(MouseEvent arg0) {}

}
