package me.tyler.schematic.listener;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import me.tyler.schematic.Main;
import me.tyler.schematic.io.SchematicIO;
import me.tyler.schematic.wire.Wire;

public class KeyboardListener extends KeyAdapter{

	@Override
	public void keyPressed(KeyEvent arg0) {
		
		if(arg0.getKeyCode() == KeyEvent.VK_DELETE)
		{
			Wire wire = Main.schematics.getSelectedWire();
			if(wire != null)
			{
				Main.schematics.removeWire(wire);
			}
		}
		else if(arg0.getKeyCode() == KeyEvent.VK_BACK_SPACE)
		{
			Wire wire = Main.schematics.getSelectedWire();
			if(wire != null)
			{
				wire.removeSegment(wire.lastSegment);
				Main.schematics.repaint();
			}
		}
		else if(arg0.getKeyCode() == KeyEvent.VK_E)
		{
			Wire wire = Main.schematics.getSelectedWire();
			if(wire != null)
			{
				Main.schematics.editWire(wire);
			}
		}
		else if(arg0.getKeyCode() == KeyEvent.VK_S)
		{
			if(arg0.isControlDown())
			{
				if(arg0.isShiftDown())
				{
					//Save colored image
					SchematicIO.saveImage(SchematicIO.makeColoredSchematic(), "Colored Schematic.png");
				}
				else
				{
					//Save colorless image
					SchematicIO.saveImage(SchematicIO.makeSchematic(), "Schematic.png");
				}
				SchematicIO.saveSchematic("wires");
			}
		}	
		else if(arg0.getKeyCode() == KeyEvent.VK_O)
		{
			if(arg0.isControlDown())
			{
				SchematicIO.openSchematic();
			}
		}
	}
}
