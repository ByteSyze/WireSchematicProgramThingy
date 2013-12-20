package me.tyler.schematic.wire;

import java.awt.Point;

import me.tyler.schematic.Main;

public class WireSegment {
	
	public Point startPoint;
	public Point endPoint;
	
	public int axis; //Direction the line is going
	
	public static int AXIS_VERTICAL = 0;
	public static int AXIS_HORIZONTAL = 1;
	
	public WireSegment(Point start, Point end, int direction)
	{
		startPoint = start;
		endPoint = end;
		this.axis = direction;
	}
	
	public boolean contains(int x, int y)
	{
		int width = (int)Main.schematics.brushStroke.getLineWidth();
		if(axis == AXIS_VERTICAL)
		{
			if(((startPoint.x-width) < x) && (startPoint.x+width > x))
			{
				if ((y >= startPoint.y && y <= endPoint.y) 
						|| (y <= startPoint.y && y >= endPoint.y))
				{
					//Y coordinate lies between start and end point, and X coordinates
					//are a perfect match, so this segment does infact contain the given values.
					return true;
				}
			}
		}
		else if(axis == AXIS_HORIZONTAL)
		{
			if((startPoint.y-width < y) && (startPoint.y+width > y))
			{ 
				if ((x >= startPoint.x && x <= endPoint.x) 
						|| (x <= startPoint.x && x >= endPoint.x))
				{
					//X coordinate lies between start and end point, and Y coordinates
					//are a perfect match, so this segment does infact contain the given values.
					return true;
				}	
			}
		}
		return false;
	}

}
