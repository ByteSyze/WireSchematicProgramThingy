package me.tyler.schematic;

import javax.swing.JFrame;

import me.tyler.schematic.listener.KeyboardListener;

public class Main {
	
	public static Schematics schematics;
	
	public static void main(String[] args)
	{
		JFrame f = new JFrame("Schematic Creator");
		f.addKeyListener(new KeyboardListener());
		schematics = new Schematics(args[0]);
		
		f.add(schematics);
		f.setSize(700,700);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
		
		JFrame colorFrame = new JFrame("Choose Color");
		colorFrame.add(schematics.colorChooser);
		colorFrame.pack();
		colorFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		colorFrame.setVisible(true);
		
		colorFrame.setAlwaysOnTop(true);
		
	}

}
