package me.tyler.schematic;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JColorChooser;
import javax.swing.JPanel;

import me.tyler.schematic.listener.ColorListener;
import me.tyler.schematic.listener.MouseListener;
import me.tyler.schematic.wire.Wire;
import me.tyler.schematic.wire.WireSegment;

public class Schematics extends JPanel{
	
	private static final long serialVersionUID = -4095125622681188982L;

	public Image schematicImage;
	
	public JColorChooser colorChooser = new JColorChooser();
	
	public List<Wire> wires = new ArrayList<Wire>();
	public Wire currentWire = null;
	
	public boolean wireInProgress = false;
	
	MouseListener listener = new MouseListener();

	public BasicStroke brushStroke = new BasicStroke(2f);
	
	public Schematics(String path)
	{
		this.addMouseListener(listener);
		this.addMouseMotionListener(listener);
		
		colorChooser.getPreviewPanel().addPropertyChangeListener(new ColorListener());
		
		try {
			schematicImage = ImageIO.read(new File(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void paint(Graphics g)
	{
		super.paint(g);
		
		Graphics2D g2d = (Graphics2D)g;
		
		g2d.setStroke(brushStroke);
		
		g.drawImage(schematicImage, 0, 0, this);
		
		Wire selectedWire = null;
		//Iterate through all wires
		for(Wire wire : wires)
		{
			//If the wire is selected, highlight it. Otherwise make it the default color
			if(wire.isSelected())
			{
				selectedWire = wire;
			} else
			{
				g2d.setColor(Color.BLACK);
				g2d.draw(wire.makePath());
			}
		}		
		//After all unselected wires are drawn, draw the selected wire (if any)
		//This makes sure the selected wire is drawn on top of all the others.
		if(selectedWire != null)
		{
			g2d.setColor(selectedWire.getColor());
			g2d.draw(selectedWire.makePath());
		}
		
		if(wireInProgress)
		{
			if(currentWire.lastSegment.axis == WireSegment.AXIS_HORIZONTAL)
			{
				
				g.drawLine
				(
						currentWire.lastSegment.endPoint.x,
						currentWire.lastSegment.endPoint.y,
						currentWire.lastSegment.endPoint.x,
						listener.lastY
				);
			} else
			{
				g.drawLine
				(
						currentWire.lastSegment.endPoint.x,
						currentWire.lastSegment.endPoint.y,
						listener.lastX,
						currentWire.lastSegment.endPoint.y
				);			
			}
		}
		
	}
	
	public void newWire(Color wireColor, int x, int y)
	{
		currentWire = new Wire(wireColor,x,y);
		wires.add(currentWire);
		wireInProgress = true;
		selectWire(currentWire);
	}
	
	public void endWire()
	{
		wireInProgress = false;
	}
	
	public void selectWire(Wire wire)
	{
		if(!wire.isSelected())
		{
			for(Wire w : wires)
			{
				if(w.isSelected())
				{
					//Set all currently selected wires to unselected.
					w.setSelected(false);
				}
			}
			wire.setSelected(true);
		}
		this.repaint();
	}
	
	/**Finds a wire if one lies on the given point, or null if not*/
	public Wire getWireAt(int x, int y)
	{
		for(Wire w : wires)
		{
			if(w.contains(x, y))
			{
				return w;
			}
		}
		return null;
	}
	
	public Wire getSelectedWire()
	{
		for(Wire w : wires)
		{
			if(w.isSelected())
			{
				return w;
			}
		}
		return null;
	}
	
	public void removeWire(Wire wire)
	{
		wires.remove(wire);
		if(wire == currentWire)
		{
			currentWire = null;
			wireInProgress = false;
		}
		this.repaint();
	}
	
	public void editWire(Wire wire)
	{
		this.currentWire = wire;
		this.wireInProgress = true;
	}
	
	public void setWires(List<Wire> wires)
	{
		this.currentWire = null;
		this.wireInProgress = false;
		
		this.wires = wires;
	}
}
