package GUI;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
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
		CREATING, MOVING, DELETING;
	}
	
	private DrawingState state;
	
	private GraphGUI graph;
	
	private GraphElement hoveredElement;
	private NodeGUI selected, tempNode;
	private EdgeGUI edge;
	
	private String message;
	
	public DrawingPanel() {

		setFont(new Font("Times New Roman", Font.PLAIN, 14));
		
		setPreferredSize(new Dimension(800, 600));
		setBorder(new LineBorder(Color.BLACK));
		setBackground(Color.white);
		
		addMouseMotionListener(this);
		addMouseListener(this);

		graph = new GraphGUI(getGraphics());
	}
	
	public Graph getGraph() {
		return graph.toGraph();
	}
	
	public void setGraph(GraphGUI graph) {
		this.graph = graph;
	}
	
	/**
	 * Desativa elementos do grafo que n�o estejam em seu subgrafo, pintando o resultado
	 */
	public void enableSubGraph(Graph subGraph) {
		graph.setSubGraph(subGraph);		
		graph.setGraphics(getGraphics());
	}
	
	/**
	 * Define o estado do painel
	 */
	public void setState(DrawingState state) {
		if (this.state == state)
			return;
		
		this.state = state;
		
		setMessage(state.toString().substring(0, 1) + state.toString().substring(1).toLowerCase());
	
		setHovered(new Point(-100, -100), state != DrawingState.DELETING);
		if (getMousePosition() != null)
			setHovered(getMousePosition(), state == DrawingState.DELETING);
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	/**
	 * Define o elemento a ser "hovered", dada a posi��o do mouse.
	 * Tamb�m pinta o elemento "hovered" com a aura
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
		
		if (state == DrawingState.MOVING && hoveredElement != null)
			setCursor(new Cursor(Cursor.HAND_CURSOR));
		else
			setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			
		repaintComponents(getGraphics());
	}
	
	/**
	 * Pinta todos os elementos na tela, colocando �nfase nos adequados
	 */
	public void repaintComponents(Graphics g) {
		graph.setGraphics(g);
		graph.setHovered(hoveredElement, true);
		
		drawMessage();

		g.setColor(Color.BLACK);
		g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
		
		new SwitchArrows(g, getWidth(), graph);
	}
	
	public void drawMessage() {
		Graphics g = getGraphics();
		
		if (getMousePosition() == null || 
				(getMousePosition().getX() < 100 && getMousePosition().getY() < 20)) {
			g.setColor(Color.WHITE);
			g.fillRect(1, 1, 100, 20);
		}
		
		g.setColor(Color.LIGHT_GRAY);
		FontMetrics fm = getFontMetrics(getFont());
		g.drawString(message, 10, fm.getMaxAscent() + 5);
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
	 * M�todo a ser chamado quando o mouse for arrastado no modo CREATING.
	 * Se estiver uma aresta sendo criada, ela � reposicionada e repintada
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
		
		repaintComponents(getGraphics());
	}

	/**
	 * M�todo a ser chamado quando o mouse for arrastado no modo MOVING.
	 * Se estiver uma aresta sendo criada, ela � reposicionada e repintada
	 */
	public void dragMove(Point p) {
		if (selected != null) {
			graph.moveToPoint(selected, p);
			repaintComponents(getGraphics());
		}
	}

	
	@Override
	public void mouseMoved(MouseEvent e) {
		setHovered(e.getPoint(), state == DrawingState.DELETING);
	}

	// Um clique s� � chamado quando o press e release ocorrem num per�odo curto de tempo
	// e em locais pr�ximos (eu acho)
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
		repaintComponents(getGraphics());
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
	 * M�todo a ser chamado quando o mouse for pressionado no modo CREATING.
	 * Se hoveredElement for um nodo, come�a a cria��o de uma aresta no nodo
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
	 * M�todo a ser chamado quando o mouse for pressionado no modo MOVING.
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
	 * M�todo a ser chamado quando o mouse for largado no modo CREATING.
	 * Se hoveredElement for um nodo e uma aresta estiver sendo criada, termina a cria��o
	 * e adiciona no grafo.
	 */
	public void releaseCreate(Point point) {
		
		if (hoveredElement == null) {
			tempNode = graph.createNode(point, getGraphics());
			graph.addEdge(edge);
			edge.setEnd(tempNode);
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
	 * M�todo a ser chamado quando o mouse for largado no modo MOVING.
	 * Se um nodo estiver sendo movido, termina de mov�-lo.
	 */
	public void releaseMove() {
		selected = null;
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {}

	@Override
	public void mouseExited(MouseEvent arg0) {}
	
	@Override
	public void paint(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		repaintComponents(g);
	}
}
