package GUI;

import java.awt.Point;

/**
 * Interface para permitir m�todos em comum entre nodos e arestas.
 * 
 * @author Arthur
 */
public interface GraphElement {
	
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
	public void setAura(boolean hasAura);
	
	/**
	 * Pinta o elemento na tela, no estado padr�o
	 */
	public void paint();
	
	/**
	 * Calcula a dist�ncia do elemento at� o ponto
	 */
	public double distance(Point p);
}
