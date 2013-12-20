package me.tyler.schematic.listener;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import me.tyler.schematic.Main;
import me.tyler.schematic.io.SchematicIO;
import me.tyler.schematic.wire.Wire;

public class MouseListener extends MouseAdapter{
	
	public int lastX;
	public int lastY;

	@Override
	public void mouseClicked(MouseEvent e) {
		
		/*if(e.getButton() == 1)
		{
			if(Main.schematics.wireInProgress)
			{
				//Add a new segment to current wire
				Main.schematics.currentWire.addSegment(e.getX(), e.getY());
				Main.schematics.repaint();
			} else {
				
				//select clicked wire
				Wire w = Main.schematics.getWireAt(e.getX(), e.getY());
				if(w != null)
				{
					Main.schematics.selectWire(w);
					Main.schematics.repaint();
				}
				
			}
		} else if(e.getButton() == 3)
		{
			if(Main.schematics.wireInProgress)
			{
				//Finish wire
				Main.schematics.endWire();
				SchematicIO.saveSchematic("wires_backup");
			} else
			{
				//Start wire
				Main.schematics.newWire(Main.schematics.colorChooser.getColor(),e.getX(),e.getY());
			}
		}
		*/
		
	}
	
	@Override
	public void mouseReleased(MouseEvent e)
	{
		if(e.getButton() == 1)
		{
			if(Main.schematics.wireInProgress)
			{
				//Add a new segment to current wire
				Main.schematics.currentWire.addSegment(e.getX(), e.getY());
				Main.schematics.repaint();
			} else {
				
				//select clicked wire
				Wire w = Main.schematics.getWireAt(e.getX(), e.getY());
				if(w != null)
				{
					Main.schematics.selectWire(w);
					Main.schematics.repaint();
				}
				
			}
		} else if(e.getButton() == 3)
		{
			if(Main.schematics.wireInProgress)
			{
				//Finish wire
				Main.schematics.endWire();
				SchematicIO.saveSchematic("wires_backup");
			} else
			{
				//Start wire
				Main.schematics.newWire(Main.schematics.colorChooser.getColor(),e.getX(),e.getY());
			}
		}
	}
	
	@Override
	public void mouseMoved(MouseEvent e){
		
		lastX = e.getX();
		lastY = e.getY();
		
		Main.schematics.repaint();
		
	}
	
}
