package logic;

import music.*;

import java.util.List;
import java.util.ArrayList;

import java.io.*;
import java.net.URL;
import org.dom4j.*;
import org.dom4j.io.*;

public class XMLWriter {
	public XMLWriter() {
	}
	
	public Piece write(Piece p, String fileName) throws Exception {
		/*
		Document document = DocumentHelper.createDocument();
		
		Element root = DocumentHelper.createElement("SOLAR");
		document.setRootElement(root);
		
		//write solar attributes
		root.addAttribute("TIME", "0");
		root.addAttribute("TIMESTEP" , "" + SolarPhysics.timestep);
		root.addAttribute("TIMESTEPS_TO_RUN", "10000");
		
		//create XML tree of all objects
		Set<SolarBody> bodies = solarSystem.getBodies();
		for(SolarBody body : bodies){
			String type = body.getType();
			if(type.equals("PLANET")){
				writePlanet(body, root);
			}
			else if(type.equals("ROCKET")){
				writeRocket((Rocket) body, root);
			}
			else{
				System.out.println("Object unrecognized");
				System.exit(1);
			}
		}
		
		//write to file
		XMLWriter writer = new XMLWriter(new FileWriter(fileName), OutputFormat.createPrettyPrint());
		writer.write(document);
		writer.close();*/
	}
}