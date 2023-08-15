package repository;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CSVFileReader {
	private static final int NUM_THREADS = 4; 
	
	public static List<String[]> items() {
		
		List<String[]> results = new ArrayList<>();
		
		File directory = new File("C:\\Users\\prakharsrivastava01\\eclipse-workspace\\TshirtRecommendation\\resources");
		File[] files = directory.listFiles();
		List<File> csvFiles = new ArrayList<>();

		for (File file : files) {
			if (file.isFile() && file.getName().endsWith(".csv")) {
				csvFiles.add(file);
			}
		}

		ExecutorService executor = Executors.newFixedThreadPool(NUM_THREADS);
		
		for (File file : csvFiles) {
			executor.submit(() -> {
				try {
					long startTime = System.currentTimeMillis();
					List<String[]> fileData = readCSV(file);
					synchronized (results) {
						results.addAll(fileData);
					}
					Thread.sleep(2000);
					long endTime = System.currentTimeMillis();
					long timeTaken = endTime - startTime;
					System.out.println(Thread.currentThread().getName() + " took " + timeTaken + "ms.");
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
		}

		
		executor.shutdown();
		while (!executor.isTerminated()) {}
		
		return results;
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
}