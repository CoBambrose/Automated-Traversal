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

		pg.push();
		
		pg.background(0,0,0,150);
		pg.fill(255);
		pg.textSize(28);
		pg.translate(0, (pg.height - 224) / 2f - 10f);
		pg.text("MSE: "+error, 20, 20, pg.width, 40);
		pg.text("EPOCH: "+epoch, 20, 20 + 32, pg.width, 40);
		pg.text("LOCATION: "+location, 20, 20 + 32*2, pg.width, 40);
		pg.text("    HEIGHT: "+height_, 20, 20 + 32*3, pg.width, 40);
		pg.text("    TEMPERATURE: "+temperature, 20, 20 + 32*4, pg.width, 40);
		pg.text("    DENSITY: "+density, 20, 20 + 32*5, pg.width, 40);
		pg.text("    STATE: "+state, 20, 20 + 32*6, pg.width, 40);
		
		pg.pop();
		pg.push();
		
		pg.fill(255);
		pg.stroke(0);
		pg.translate(pg.width/2f, 0);
		pg.line(0,0,0,pg.height);
		int inputs = 27;
		int hiddens = 10;
		int outputs = 8;
		
		for (int i = 0; i < inputs; i++) {
			float x = (i+1f) * (pg.width/2f)/(inputs+1f);
			float y = pg.height/5f;
			pg.ellipse(x, y, 20, 20);
			for (int j = 0; j < hiddens; j++) {
				float x2 = (j+1f) * (pg.width/2f)/(hiddens+1f);
				float y2 = 2f*(pg.height/5f);
				pg.strokeWeight(.1f);
				pg.stroke((float) (55f + vehicle.brain.weights[0].data[j][i]*200f));
				pg.line(x,y,x2,y2);
			}
		}
		for (int i = 0; i < hiddens; i++) {
			float x = (i+1f) * (pg.width/2f)/(hiddens+1f);
			float y = 2f*(pg.height/5f);
			pg.ellipse(x,y,20,20);
			for (int j = 0; j < hiddens; j++) {
				float x2 = (j+1f) * (pg.width/2f)/(hiddens+1f);
				float y2 = 3f*(pg.height/5f);
				pg.strokeWeight(.1f);
				pg.stroke((float) (55f + vehicle.brain.weights[1].data[j][i]*200f));
				pg.line(x,y,x2,y2);
			}
		}
		for (int i = 0; i < hiddens; i++) {
			float x = (i+1f) * (pg.width/2f)/(hiddens+1f);
			float y = 3f*(pg.height/5f);
			pg.ellipse(x,y,20,20);
			for (int j = 0; j < outputs; j++) {
				float x2 = (j+1f) * (pg.width/2f)/(outputs+1f);
				float y2 = 4f*(pg.height/5f);
				pg.strokeWeight(.1f);
				pg.stroke((float) (55f + vehicle.brain.weights[2].data[j][i]*200f));
				pg.line(x,y,x2,y2);
			}
		}
		for (int i = 0; i < outputs; i++) {
			float x = (i+1f) * (pg.width/2f)/(outputs+1f);
			float y = 4f*(pg.height/5f);
			pg.ellipse(x,y,20,20);
		}
		
		pg.pop();
		
		pg.endDraw();
		sketch.image(pg.get(), 0, sketch.height/2f, pg.width, pg.height);
	}
}
