package GUI;

import java.awt.Point;


public interface GraphElement {
	
	public void setSelected(boolean isSelected);
	
	public void addHighlight();
	
	public void removeHighlight();
	
	public void addAura();
	
	public void removeAura();
	
	public void paint();
	
	public double distance(Point p);
}
