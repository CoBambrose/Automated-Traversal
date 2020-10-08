package CSVReader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class CSVReader {
	
	public static ArrayList<Float> CSVToFloatArray() {
		ArrayList<Float> returnVal = new ArrayList<Float>();
		try {
			BufferedReader csvReader = new BufferedReader(new FileReader("src/test.csv"));
			while (true) {
				String row = csvReader.readLine();
				if (row == null) {
					break;
				}
			    String[] data = row.split(",");
			    nl
			    returnVal.add();
			    for (int i = 0; i < data.length; i++) {
			    	
			    }
			}
			csvReader.close();
		} catch(Exception e) {
			System.out.println("CSV File does not exist");
		}
		return returnVal;
	}

}
