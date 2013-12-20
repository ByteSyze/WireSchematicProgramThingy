package me.tyler.schematic.io;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import me.tyler.schematic.Main;
import me.tyler.schematic.wire.Wire;
import me.tyler.schematic.wire.WireSegment;

public class SchematicIO {
	
	/**Creates an image containing the initial schematic image as well as
	 * colorless (black) wires */
	public static BufferedImage makeSchematic()
	{
		BufferedImage output = (BufferedImage)Main.schematics.schematicImage;
		Graphics2D g = (Graphics2D)output.getGraphics();
		
		g.setColor(Color.BLACK);
		
		for(Wire w : Main.schematics.wires)
		{	
			g.draw(w.makePath());
		}
		
		return output;
	}
	
	/**Creates an image containing the initial schematic image as well as
	 * colored wires, based on their specified colors.*/
	public static BufferedImage makeColoredSchematic()
	{

		BufferedImage output = (BufferedImage)Main.schematics.schematicImage;
		Graphics2D g = (Graphics2D)output.getGraphics();
		
		for(Wire w : Main.schematics.wires)
		{
			g.setColor(w.getColor());		
			g.draw(w.makePath());
		}
		return output;
	}
	
	public static void saveImage(BufferedImage image, String outputName)
	{
		try {
			ImageIO.write(image, "png", new File(outputName));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void saveSchematic(String outputName)
	{
		try {
			PrintWriter writer = new PrintWriter(outputName+".sch");
			
			writer.println("Wires:");
			for(int w = 0; w < Main.schematics.wires.size(); w++)
			{
				Wire wire = Main.schematics.wires.get(w);
				Point start = wire.getSegments().get(0).startPoint;
				writer.println("wire"+w+":"+wire.getColor().getRGB()+":"+start.x+":"+start.y);
				
				for(int s = 1; s < Main.schematics.wires.get(w).getSegments().size(); s++)
				{
					WireSegment segment = wire.getSegments().get(s);
					writer.println("segment"+s+":"+segment.endPoint.x+":"+segment.endPoint.y);
				}
			}
			
			writer.println("end");
			writer.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void openSchematic()
	{
		try {
			FileReader reader = new FileReader("wires.sch");
			BufferedReader bReader = new BufferedReader(reader);
			
			String currentLine;
			
			//A list to hold all wires read from file
			List<Wire> wires = new ArrayList<Wire>();
			//Current wire being built from file
			Wire currentWire = null;
			
			
			//All schematic files should have "end" as their last line, hence this should
			//loop until the end of file.
			while((currentLine = bReader.readLine()) != null)
			{
				if(currentLine.startsWith("wire"))
				{
					if(currentWire != null) wires.add(currentWire);
					
					currentLine = currentLine.substring(currentLine.indexOf(":")+1);
					int rgb = Integer.parseInt(currentLine.substring(0, currentLine.indexOf(":")));

					currentLine = currentLine.substring(currentLine.indexOf(":")+1);
					int x = Integer.parseInt(currentLine.substring(0, currentLine.indexOf(":")));
					
					currentLine = currentLine.substring(currentLine.indexOf(":")+1);
					int y = Integer.parseInt(currentLine);
					
					currentWire = new Wire(new Color(rgb),x,y);
				}
				else if(currentLine.startsWith("segment"))
				{		

					currentLine = currentLine.substring(currentLine.indexOf(":")+1);
					int x = Integer.parseInt(currentLine.substring(0, currentLine.indexOf(":")));
					currentLine = currentLine.substring(currentLine.indexOf(":")+1);
					int y = Integer.parseInt(currentLine);	
					
					currentWire.addSegment(x,y);
					
				}
				
			}
			wires.add(currentWire);
			
			Main.schematics.setWires(wires);
			Main.schematics.repaint();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
