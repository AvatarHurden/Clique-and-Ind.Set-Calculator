package GUI;

import java.awt.Point;

/**
 * Interface para permitir m�todos em comum entre nodos e arestas.
 * 
 * @author Arthur
 */
public interface GraphElement {
	
	/**
	 * Se ativo, objeto tem cores normais. Caso contr�rio, fica cinza
	 */
	public void setEnabled(boolean isEnabled);
	
	/**
	 * M�todo chamado quando o usu�rio est� modificando o elemento, como criar uma aresta
	 * em um nodo ou deslocar um nodo
	 * @param isSelected
	 */
	public void setSelected(boolean isSelected);
	
	/**
	 * M�todo chamado quando o usu�rio "hover" sobre o elemento no estado de dele��o
	 * @param hasHighlight
	 */
	public void setHighlight(boolean hasHighlight);
	
	/**
	 * M�todo chamado quando o usu�rio "hover" sobre o elemento no estado de cria��o
	 * @param hasAura
	 */
	public void setHovered(boolean isHovered);
	
	/**
	 * Pinta o elemento na tela, no estado padr�o
	 */
	public void paint();
	
	/**
	 * Move o elemento at� o ponto dado, eliminando a pintura anterior e pintando no novo local
	 */
	public void moveToPoint(Point point);
	
	/**
	 * Remove a pintura do elemento na tela
	 */
	public void erase();
	
	/**
	 * Calcula a dist�ncia do elemento at� o ponto
	 */
	public double distance(Point p);
}
