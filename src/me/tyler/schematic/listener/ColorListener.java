package me.tyler.schematic.listener;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import me.tyler.schematic.Main;
import me.tyler.schematic.wire.Wire;

public class ColorListener implements PropertyChangeListener{

	@Override
	public void propertyChange(PropertyChangeEvent arg0) {
		
		Wire selectedWire = Main.schematics.getSelectedWire();
		if(selectedWire != null)
		{
			selectedWire.setColor(Main.schematics.colorChooser.getColor());
			Main.schematics.repaint();
		}
	}

}
