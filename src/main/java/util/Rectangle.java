package main.java.util;

import java.awt.geom.*;

@SuppressWarnings("serial")
public class Rectangle extends Rectangle2D.Double {
	public Rectangle(double x, double y, double width, double height) {
		super(x, y, width, height);
	}
	
	public double getLeft() {
		return x;
	}

	public double getRight() {
		return x + width;
	}

	public double getTop() {
		return y;
	}

	public double getBottom() {
		return y + height;
	}
}
