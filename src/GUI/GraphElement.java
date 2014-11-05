package GUI;

import java.awt.Point;

/**
 * Interface para permitir métodos em comum entre nodos e arestas.
 * 
 * @author Arthur
 */
public interface GraphElement {
	
	/**
	 * Se ativo, objeto tem cores normais. Caso contrário, fica cinza
	 */
	public void setEnabled(boolean isEnabled);
	
	/**
	 * Método chamado quando o usuário está modificando o elemento, como criar uma aresta
	 * em um nodo ou deslocar um nodo
	 * @param isSelected
	 */
	public void setSelected(boolean isSelected);
	
	/**
	 * Método chamado quando o usuário "hover" sobre o elemento no estado de deleção
	 * @param hasHighlight
	 */
	public void setHighlight(boolean hasHighlight);
	
	/**
	 * Método chamado quando o usuário "hover" sobre o elemento no estado de criação
	 * @param hasAura
	 */
	public void setHovered(boolean isHovered);
	
	/**
	 * Pinta o elemento na tela, no estado padrão
	 */
	public void paint();
	
	/**
	 * Move o elemento até o ponto dado, eliminando a pintura anterior e pintando no novo local
	 */
	public void moveToPoint(Point point);
	
	/**
	 * Remove a pintura do elemento na tela
	 */
	public void erase();
	
	/**
	 * Calcula a distância do elemento até o ponto
	 */
	public double distance(Point p);
}
