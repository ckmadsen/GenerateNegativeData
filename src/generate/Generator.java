package generate;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.CSVIO;

public class Generator {
	
	public static void main(String[] args) throws IOException {
		if (args.length == 1) {
			generate(args[0]);
		}
	}
	
	public static void generate(String directory) throws IOException {
		File dir = new File(directory);
		if (dir.isDirectory()) {
			for (File file : dir.listFiles()) {
				if (file.getAbsolutePath().endsWith(".csv")) {
					List<List<Double>> data = CSVIO.read(file);
					addNegativeData(data);
					CSVIO.write(data, file.getAbsolutePath());
				}
			}
		}
	}
	
	private static void addNegativeData(List<List<Double>> data) {
		double min = Double.MAX_VALUE;
		double max = Double.MIN_VALUE;
		for (List<Double> line : data) {
			for (Double element : line) {
				double value = element;
				if (min > value) {
					min = value;
				}
				if (max < value) {
					max = value;
				}
			}
		}
		int number = data.size();
		invert(data, number);
		constants(data, number, min, max);
		waveforms(data, number, min, max);
	}
	
	private static void waveforms(List<List<Double>> data, int number, double min, double max) {
		double step = ((max - min)/(number-1));
		List<Double> wave = new ArrayList<Double>();
		for (double i = min; i <= max - step; i += step) {
			wave.add(i);
		}
		for (double i = max; i >= min + step; i -= step) {
			wave.add(i);
		}
		for (int i = 0; i < number; i++) {
			int waveCounter = i;
			List<Double> dataLine = new ArrayList<Double>();
			for (int j = 0; j < data.get(0).size(); j++) {
				dataLine.add(wave.get(waveCounter));
				waveCounter = (waveCounter + 1) % wave.size();
			}
			data.add(dataLine);
		}
	}
	
	private static void constants(List<List<Double>> data, int number, double min, double max) {
		double step = ((max - min)/(number-1));
		for (double i = min; i <= max; i += step) {
			List<Double> dataLine = new ArrayList<Double>();
			for (int j = 0; j < data.get(0).size(); j++) {
				dataLine.add(i);			
			}
			data.add(dataLine);
		}
	}
	
	private static void invert(List<List<Double>> data, int number) {
		for (int i = 0; i < number; i++) {
			List<Double> dataLine = new ArrayList<Double>();
			for (Double element : data.get(i)) {
				dataLine.add(0, element);			
			}
			data.add(dataLine);
		}
	}
}
