package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import main.Graph;

public class DrawingPanel extends JPanel implements MouseMotionListener, MouseListener {

	/**
	 * Estados possíveis do painel
	 */
	public enum DrawingState {
		CREATING, MOVING, DELETING;
	}
	
	private DrawingState state = DrawingState.CREATING;
	
	private GraphGUI graph;
	
	private GraphElement hoveredElement;
	private NodeGUI selected, tempNode;
	private EdgeGUI edge;
	
	public DrawingPanel() {

		setFont(new Font("Times New Roman", Font.PLAIN, 14));
		
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
	 * Desativa elementos do grafo que não estejam em seu subgrafo, pintando o resultado
	 */
	public void enableSubGraph(Graph subGraph) {
		graph.enableSubGraph(subGraph);
		graph.drawGraph();
	}
	
	/**
	 * Define o estado do painel
	 */
	public void setState(DrawingState state) {
		if (this.state == state)
			return;
		this.state = state;
		
		Graphics2D g = (Graphics2D) getGraphics();
		
		g.setColor(Color.WHITE);
		g.fillRect(1, 1, 100, 20);
		
		g.setColor(Color.LIGHT_GRAY);
		FontMetrics fm = getFontMetrics(getFont());
		g.drawString(state.toString().substring(0, 1) + state.toString().substring(1).toLowerCase(), 
				10, fm.getMaxAscent() + 5);
	
		setHovered(new Point(-100, -100), true);
		setHovered(new Point(-100, -100), false);
		setHovered(getMousePosition(), state == DrawingState.DELETING);
	}
	
	/**
	 * Define o elemento a ser "hovered", dada a posição do mouse.
	 * Também pinta o elemento "hovered" com a aura
	 * Se toDelete, coloca highlight no elemento no lugar da aura
	 */
	public void setHovered(Point point, boolean toDelete) {
		GraphElement closest = graph.getClosest(point, toDelete);
		
		if (hoveredElement != closest)
			if (toDelete)
				graph.setSelected(hoveredElement, false);
			else
				graph.setHovered(hoveredElement, false);
		
		hoveredElement = closest;
		
		if (toDelete)
			graph.setSelected(hoveredElement, toDelete);
		
		repaintComponents();
	}
	
	/**
	 * Pinta todos os elementos na tela, colocando ênfase nos adequados
	 */
	public void repaintComponents() {
		graph.drawGraph();
		graph.setHovered(hoveredElement, true);
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		switch (state) {
		case CREATING:
			dragCreate(e.getPoint());
			break;
		case MOVING:
			dragMove(e.getPoint());
			break;
		default:
			break;
		}
	}
	
	/**
	 * Método a ser chamado quando o mouse for arrastado no modo CREATING.
	 * Se estiver uma aresta sendo criada, ela é reposicionada e repintada
	 */
	public void dragCreate(Point p) {
		setHovered(p, false);
		
		if (hoveredElement == null) {
			graph.moveToPoint(edge, p);
			graph.moveToPoint(tempNode, p);
		} else {
			tempNode.erase();
			graph.moveToPoint(edge, p);
		}
		
		repaintComponents();
	}

	/**
	 * Método a ser chamado quando o mouse for arrastado no modo MOVING.
	 * Se estiver uma aresta sendo criada, ela é reposicionada e repintada
	 */
	public void dragMove(Point p) {
		if (selected != null) {
			graph.moveToPoint(selected, p);
			repaintComponents();
		}
	}

	
	@Override
	public void mouseMoved(MouseEvent e) {
		setHovered(e.getPoint(), state == DrawingState.DELETING);
	}

	// Um clique só é chamado quando o press e release ocorrem num período curto de tempo
	// e em locais próximos (eu acho)
	@Override
	public void mouseClicked(MouseEvent e) {
		switch (state) {
		case DELETING:
			clickDelete();
			break;
		default:
			break;
		}
	}
	
	public void clickDelete() {
		graph.delete(hoveredElement);
		hoveredElement = null;
		repaintComponents();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		switch (state) {
		case CREATING:
			pressCreate(e.getPoint());
			break;
		case MOVING:
			pressMove();
			break;
		default:
			break;
		}
	}
	
	/**
	 * Método a ser chamado quando o mouse for pressionado no modo CREATING.
	 * Se hoveredElement for um nodo, começa a criação de uma aresta no nodo
	 */
	public void pressCreate(Point point) {
		if (hoveredElement == null)
			hoveredElement = graph.createNode(point, getGraphics());
		
		if (hoveredElement instanceof NodeGUI) {
			edge = new EdgeGUI((NodeGUI) hoveredElement, getGraphics());
			tempNode = new NodeGUI(point.x, point.y, getGraphics());
		}
			
	}
	
	/**
	 * Método a ser chamado quando o mouse for pressionado no modo MOVING.
	 * Se hoveredElement for um nodo, coloca-o como sendo o nodo a ser movido
	 */
	public void pressMove() {
		if (hoveredElement instanceof NodeGUI)
			selected = (NodeGUI) hoveredElement;
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		switch (state) {
		case CREATING:
			releaseCreate(e.getPoint());
			break;
		case MOVING:
			releaseMove();
			break;
		default:
			break;
		}
	}
	
	/**
	 * Método a ser chamado quando o mouse for largado no modo CREATING.
	 * Se hoveredElement for um nodo e uma aresta estiver sendo criada, termina a criação
	 * e adiciona no grafo.
	 */
	public void releaseCreate(Point point) {
		
		if (hoveredElement == null) {
			tempNode = graph.createNode(point, getGraphics());
			edge.setEnd(tempNode);
			graph.addEdge(edge);
		} else if (hoveredElement.equals(edge.getStart())) {
			tempNode.erase();
			edge.erase();
		} else if (!edge.isEdgeOf((NodeGUI) hoveredElement)) {
			edge.setEnd((NodeGUI) hoveredElement);
			graph.addEdge(edge);
		}
		
		edge.erase();
		setHovered(point, false);
	}
	
	/**
	 * Método a ser chamado quando o mouse for largado no modo MOVING.
	 * Se um nodo estiver sendo movido, termina de movê-lo.
	 */
	public void releaseMove() {
		selected = null;
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {}

	@Override
	public void mouseExited(MouseEvent arg0) {}

}
