package GUI;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class NodeGUI implements java.awt.Shape {
	
	Point center;
	
	public NodeGUI(float x, float y) {
		
		center = new Point((int) x,(int) y);
		
	}

	@Override
	public boolean contains(Point2D p) {
		if (p.distance(center) < 20.0)
			return true;
		
		return false;
	}

	@Override
	public boolean contains(Rectangle2D arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean contains(double arg0, double arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean contains(double arg0, double arg1, double arg2, double arg3) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle(center.y - 20, center.y + 20, center.x - 20, center.x + 20);
	}

	@Override
	public Rectangle2D getBounds2D() {
		// TODO Auto-generated method stub
		return getBounds();
	}

	@Override
	public java.awt.geom.PathIterator getPathIterator(AffineTransform arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public java.awt.geom.PathIterator getPathIterator(AffineTransform arg0,
			double arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean intersects(Rectangle2D arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean intersects(double arg0, double arg1, double arg2, double arg3) {
		// TODO Auto-generated method stub
		return false;
	}
}
