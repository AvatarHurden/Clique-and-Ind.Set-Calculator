package GUI;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

public class EdgeGUI implements GraphElement {
	
	// Graphics � como objetos s�o desenhados na tela
	private Graphics g;
	// Nodo de come�o e fim da aresta (s�o equivalentes, mas precisam de nomes diferentes)
	private NodeGUI start, end;
	
	// �ltima localiza��o da aresta, para permitir atualiza��o em tempo real da posi��o
	private Point lastLocation;
	
	private boolean isEnabled = true;
	
	public EdgeGUI(NodeGUI start, Graphics g) {
		this.g = g;
		this.start = start;
		start.setHighlight(true);
	}
	
	/**
	 * Verifica se a aresta pertence ao nodo pedido	
	 */
	public boolean isEdgeOf(NodeGUI node) {
		return start.equals(node) || (end != null && end.equals(node));
 	}
	
	/**
	 * Dado um nodo, retorna o seu par caso a aresta perten�a ao nodo. Caso contr�rio,
	 * retorna null 
	 */
	public NodeGUI getNeihgbor(NodeGUI node) {
		if (!isEdgeOf(node))
			return null;
		
		if (start.equals(node))
			return end;
		else
			return start;
	}
	
	public NodeGUI getStart() {
		return start;
	}
	
	public NodeGUI getEnd() {
		return end;
	}
	
	/**
	 * Apaga o desenho da aresta, para quando ela estiver completa ou for cancelada
	 */
	public void erase() {
		g.setColor(Color.WHITE);
		
		// Caso n�o tenha fim, deleta a �ltima linha feita
		if (lastLocation != null)
			g.drawLine(start.getX(), start.getY(), lastLocation.x, lastLocation.y);
		
		// Caso tenha fim, deleta a linha completa
		if (end != null) 
			g.drawLine(start.getX(), start.getY(), end.getX(), end.getY());
	
		lastLocation = null;
		start.setHighlight(false);
	}
	
	/**
	 * "Completa" a aresta
	 */
	public void setEnd(NodeGUI end) {
		this.end = end;
		erase();
	}
	
	/**
	 * Desenha a aresta, desde seu nodo de in�cio at� o ponto passado como par�metro
	 */
	public void moveToPoint(Point p) {
		if (lastLocation != null) {
			g.setColor(Color.WHITE);
			g.drawLine(start.getX(), start.getY(), lastLocation.x, lastLocation.y);
		}
		g.setColor(Color.BLACK);
		g.drawLine(start.getX(), start.getY(), p.x, p.y);
		start.setHighlight(true);
		lastLocation = p;
	}
	
	@Override
	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}
	
	@Override
	public void setSelected(boolean isSelected) {
		g.setColor(isSelected ? Color.RED : Color.WHITE);
		((Graphics2D) g).setStroke(new BasicStroke(5));
		g.drawLine(start.getX(), start.getY(), end.getX(), end.getY());
		((Graphics2D) g).setStroke(new BasicStroke(1));
		paint();
	}
	
	@Override
	public void setHovered(boolean isHovered) {
		//TODO criar representa��o melhor disso
		g.setColor(isHovered ? Color.DARK_GRAY : Color.WHITE);
		g.drawLine(start.getX() + 5, start.getY() + 5, end.getX() + 5, end.getY() + 5);
		g.drawLine(start.getX() - 5, start.getY() - 5, end.getX() - 5, end.getY() - 5);
	}

	@Override
	public void paint() {
		g.setColor(isEnabled ? Color.BLACK : Color.LIGHT_GRAY);
		g.drawLine(start.getX(), start.getY(), end.getX(), end.getY());
	}

	@Override
	public double distance(Point p) {

		// y = ax + b
		double a = (end.getY() - start.getY()) / (double) (end.getX() - start.getX());
		double b = end.getY() - a * end.getX();
		
		// Essa f�rmula � encontrada derivando a dist�ncia de um ponto a uma reta
		int x = (int) Math.round((p.x - a * b + a * p.y) / (double) (1 + a * a)); 
		int y = (int) Math.round(a * x + b);
		
		// N�o permite poi��es fora do segmento de reta
		if (x > Math.max(start.getX(), end.getX()))
			x = Math.max(start.getX(), end.getX());
		if (x < Math.min(start.getX(), end.getX()))
			x = Math.min(start.getX(), end.getX());
		
		if (y > Math.max(start.getY(), end.getY()))
			y = Math.max(start.getY(), end.getY());
		if (y < Math.min(start.getY(), end.getY()))
			y = Math.min(start.getY(), end.getY());
		
		double distance = p.distance(new Point(x, y));
		if (start.distance(p) < NodeGUI.AURA || end.distance(p) < NodeGUI.AURA)
			return distance + 100;
		else
			return distance;
	}

	@Override
	public void setHighlight(boolean hasHighlight) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * Uma aresta � considerada igual a outra se seus nodos s�o iguais, n�o importando
	 * se s�o objetos diferentes.
	 */
	@Override
	public boolean equals(Object edge) {
		if (!(edge instanceof EdgeGUI))
			return false;
		
		return (start.equals(((EdgeGUI)edge).start) && end.equals(((EdgeGUI)edge).end)) || 
				(start.equals(((EdgeGUI)edge).end) && end.equals(((EdgeGUI)edge).start));
	}

}
