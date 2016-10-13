package io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVIO {
	
	public static List<List<Double>> read(File file) throws IOException {
		List<List<Double>> data = new ArrayList<List<Double>>();
		
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";

        try {

            br = new BufferedReader(new FileReader(file));
            while ((line = br.readLine()) != null) {
            	
            	List<Double> dataLine = new ArrayList<Double>();
                // use comma as separator
                String[] splitLine = line.split(cvsSplitBy);
                
                for(String entry: splitLine) {
                	dataLine.add(Double.parseDouble(entry));
                }
                
                data.add(dataLine);

            }

        } finally {
            if (br != null) {
                br.close();
            }
        }
        return data;
	}
	
	public static void write(List<List<Double>> data, String file) throws IOException {
		FileWriter writer = new FileWriter(file);
		for (List<Double> line : data) {
			String str = "";
			for (Double element : line) {
				str += element + ",";
			}
			str = str.substring(0, str.length() - 1) + "\n";
			writer.write(str);
		}
		writer.close();
	}

}
