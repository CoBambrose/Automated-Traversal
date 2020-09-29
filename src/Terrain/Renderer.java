package Terrain;

import automatedTraversal.automatedTraversal;
import processing.core.PApplet;
import processing.core.PConstants;

public class Renderer {

	int width, height;
	automatedTraversal sketch;
	
	public Renderer(automatedTraversal _sketch, int _width, int _height) {
		System.out.println("Renderer created");
		width = _width;
		height = _height;
		sketch = _sketch;
	}
	
	public void update(Location[][] _map) {
		render(_map);
	}
	
	public void render(Location[][] _map) {
		int rows = _map[0].length;
		int cols = _map.length;
		sketch.push();

		sketch.translate(-width/2 + width/cols/2, -height/2, -200);
		sketch.directionalLight(255,255,255,0,0,-1);
		sketch.strokeWeight(.5f);
		
		for (int i = 0; i < cols-1; i++) {
			sketch.beginShape(PConstants.TRIANGLE_STRIP);
			sketch.push();
			for (int j = 0; j < rows; j++) {
				float temp = PApplet.map(_map[i][j].getTemperature(), 0, 100, 0, 255);
				float density = PApplet.map(_map[i][j].getDensity(), 0, 10, 255, 0);
				sketch.fill(temp, 0, 255-temp);
				sketch.stroke(density);
				sketch.vertex(_map[i][j].pos.x, _map[i][j].pos.y, _map[i][j].getHeight());
				sketch.vertex(_map[i+1][j].pos.x, _map[i+1][j].pos.y, _map[i+1][j].getHeight());
			}
			sketch.pop();
			sketch.endShape();
		}
		
		sketch.pop();
	}
}
