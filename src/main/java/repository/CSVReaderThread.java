package repository;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

public class CSVReaderThread extends Thread {
	private static final int NUM_THREADS = 4; 
	private List<String[]> csvData;
	private  static final int INTERVAL = 1000;

	public CSVReaderThread(List<String[]> csvData) {		
		this.csvData = csvData;
	}

	public void run() {
		while (true) {
			try {
				readCSV();
				Thread.sleep(INTERVAL);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}



	private void readCSV() {
		List<String[]> newData = new ArrayList<>();
		ExecutorService executor = Executors.newFixedThreadPool(NUM_THREADS);
		List<File> csvFiles =  CSVFileList();
		for (File file : csvFiles) {
			executor.submit(() -> {
				try {
					List<String[]> fileData = readCSV(file);
					synchronized (newData) {
						newData.addAll(fileData);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
		}
		
		executor.shutdown();
		while (!executor.isTerminated()) {}
		synchronized (csvData) {
			csvData.clear();
			csvData.addAll(newData);
		}
	}



	private static List<String[]> readCSV(File file) throws Exception {
		List<String[]> lines = new ArrayList<>();
		FileReader filereader = new FileReader(file);
		CSVParser parser = new CSVParserBuilder().withSeparator('|').build();
		CSVReader csvReader = new CSVReaderBuilder(filereader).withCSVParser(parser).build();
		csvReader.skip(1);
		String[] nextLine;
		while ((nextLine = csvReader.readNext()) != null) {
			lines.add(nextLine);
		}
		csvReader.close();
		filereader.close();
		return lines;
	}



	private static List<File> CSVFileList() {
		final String loc="C:\\Users\\prakharsrivastava01\\eclipse-workspace\\TshirtRecommendation\\resources";
		File directory = new File(loc);
		File[] files = directory.listFiles();
		List<File> csvFiles = new ArrayList<>();
		for (File file : files) {
			if (file.isFile() && file.getName().endsWith(".csv")) {
				csvFiles.add(file);
			}
		}
		return csvFiles; 
	}
}
