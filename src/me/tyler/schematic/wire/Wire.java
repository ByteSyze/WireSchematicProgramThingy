package me.tyler.schematic.wire;

import java.awt.Color;
import java.awt.Point;
import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.List;

import me.tyler.schematic.Main;

public class Wire {
	
	private List<WireSegment> segments;
	public WireSegment lastSegment;
	private Color color;
	
	int x, y;
	
	private boolean selected;
	
	public Wire(Color wireColor, int x, int y)
	{
		color = wireColor;
		segments = new ArrayList<WireSegment>();
		
		lastSegment = new WireSegment(new Point(x,y),new Point(x,y),WireSegment.AXIS_VERTICAL);
		segments.add(lastSegment);
		
		this.x = x;
		this.y = y;
	}
	
	public List<WireSegment> getSegments()
	{
		return segments;
	}
	
	/**Adds a new segment starting at the end of last segment, and
	 * ending at the given coordinates*/
	public void addSegment(int x, int y)
	{
		WireSegment seg;
		if(lastSegment == null)
		{
			seg = new WireSegment(new Point(x,y), new Point(x,y), WireSegment.AXIS_VERTICAL);
		}
		else if(lastSegment.axis == WireSegment.AXIS_VERTICAL)
		{
			seg = new WireSegment(lastSegment.endPoint, new Point(x,lastSegment.endPoint.y), WireSegment.AXIS_HORIZONTAL);
		} 
		else
		{
			seg = new WireSegment(lastSegment.endPoint, new Point(lastSegment.endPoint.x,y), WireSegment.AXIS_VERTICAL);	
		}
		
		segments.add(seg);
		lastSegment = seg;
	}
	
	public Path2D makePath()
	{
		Path2D path = new Path2D.Double();
		path.moveTo(x, y);
		for(WireSegment p : segments)
		{
			path.lineTo(p.endPoint.x,p.endPoint.y);
		}
		return path;
	}
	
	public void setColor(Color color)
	{
		this.color = color;
	}
	
	public Color getColor()
	{
		return color;
	}
	
	public void setSelected(boolean selected)
	{
		this.selected = selected;
	}
	
	public boolean isSelected()
	{
		return selected;
	}
	
	/**Checks if this wire contains the given point*/
	public boolean contains(int x, int y)
	{
		for(WireSegment seg : segments)
		{
			if(seg.contains(x, y))
			{
				return true;
			}
		}
		return false;
	}
	
	public void removeSegment(WireSegment segment)
	{
		segments.remove(segment);
		if(segment == lastSegment)
		{
			if(segments.size() > 0)
			{
				lastSegment = segments.get(segments.size()-1);
			}
			else
			{
				Main.schematics.removeWire(this);
			}
		}
	}

}
