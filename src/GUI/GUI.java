package GUI;

import Terrain.Terrain;
import Vehicle.Vehicle;
import automatedTraversal.automatedTraversal;
import processing.core.PConstants;
import processing.core.PGraphics;

public class GUI {
	
	automatedTraversal sketch;
	PGraphics pg;
	
	public GUI(automatedTraversal _sketch) {
		sketch = _sketch;
		pg = sketch.createGraphics(sketch.width, sketch.height/2);
	}
	
	public void update(Terrain terrain, Vehicle vehicle) {
		render(terrain, vehicle);
	}
	
	public void render(Terrain terrain, Vehicle vehicle) {
		sketch.hint(PConstants.DISABLE_DEPTH_TEST);
		
		float error = (int) (vehicle.brain.getError()*100000d)/100000f;
		int epoch = sketch.frameCount/20;
		String location = "{19, 49}";
		String height_ = terrain.getCenterPoint().getHeight()+"m";
		String temperature = terrain.getCenterPoint().getTemperature() + "C";
		String density = terrain.getCenterPoint().getDensity() + "g/cm^3";
		String state = "Solid";
		
		// display screen
		pg.beginDraw();
		
		sketch.push();
		pg.background(0,0,0,150);
		pg.fill(255);
		pg.textSize(28);
		pg.translate(0, (sketch.height/2f - 224) / 2f - 10f);
		pg.text("MSE: "+error, 20, 20, sketch.width, 40);
		pg.text("EPOCH: "+epoch, 20, 20 + 32, sketch.width, 40);
		pg.text("LOCATION: "+location, 20, 20 + 32*2, sketch.width, 40);
		pg.text("    HEIGHT: "+height_, 20, 20 + 32*3, sketch.width, 40);
		pg.text("    TEMPERATURE: "+temperature, 20, 20 + 32*4, sketch.width, 40);
		pg.text("    DENSITY: "+density, 20, 20 + 32*5, sketch.width, 40);
		pg.text("    STATE: "+state, 20, 20 + 32*6, sketch.width, 40);
		sketch.pop();
		
		pg.endDraw();
		sketch.image(pg.get(), 0, sketch.height/2f, sketch.width, sketch.height/2f);
	}
}
