package pl.edu.agh.notes;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import pl.edu.agh.notes.utility.FileSplitter;
import pl.edu.agh.notes.utility.WebCrawlerFactory;
import pl.edu.agh.notes.utility.WebCrawlers;
import pl.edu.agh.notes.utility.webcrawlers.WebCrawler;

public class StatisticsGenerator {
	private static final Logger logger = Logger.getLogger(StatisticsGenerator.class);
	private static final String RESULTS_FILE_PREFIX = "results";
	private static final String INPUT_FILE = "litwa.txt";
	private static final String WORKING_DIRECTORY = 
									"notes/";
	private static final String RESULTS_DIRECTORY = 
									"statistics/";
	
	static {
		BasicConfigurator.configure();
		Logger.getLogger("org.apache.http").setLevel(org.apache.log4j.Level.OFF);
	}
	
	public static void main(String[] args) throws IOException {
		logger.info("Splitting file " + INPUT_FILE + " into smaller files...");
		FileSplitter.splitFile(WORKING_DIRECTORY + INPUT_FILE);
		logger.info("Done");

		final WebCrawler webCrawler = WebCrawlerFactory
										.getWebCrawler(WebCrawlers.WORD_FREQUENCY);
				
		logger.info("Running webcrawlers on splits...");
		Files.list(Paths.get(WORKING_DIRECTORY))
				.filter(new Predicate<Path>() {

					public boolean test(Path path) {
						return path.getFileName().toString()
									.startsWith(FileSplitter.SPLIT_FILE_PREFIX);
					}		
				})
				.forEach(new Consumer<Path>() {

					public void accept(Path path) {
						try {
							logger.info("Running webcrawler on file " + path.getFileName());
							Map<String, Integer> map = webCrawler.runWithText(
													Files.lines(path)
														.collect(Collectors.joining()));					
							String fileName = RESULTS_FILE_PREFIX + "." + path.getFileName();
							logger.info("Saving temporary results to file " + fileName);
							ObjectOutputStream outputStream = new ObjectOutputStream(
													Files.newOutputStream(
															Paths.get(RESULTS_DIRECTORY + fileName),
															StandardOpenOption.CREATE_NEW));
							outputStream.writeObject(map);
							logger.info("Done webcrawling with file " + path.getFileName());
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				});
		logger.info("Done");
		
		final Map<String, Integer> entityFrequency = new HashMap<String, Integer>();
		logger.info("Merging local results...");
		Files.list(Paths.get(RESULTS_DIRECTORY))
				.filter(new Predicate<Path>() {

					public boolean test(Path path) {
						return path.getFileName().toString()
									.startsWith(RESULTS_FILE_PREFIX + "." 
												+ FileSplitter.SPLIT_FILE_PREFIX);
					}		
				})
				.forEach(new Consumer<Path>() {

					@SuppressWarnings("unchecked")
					public void accept(Path t) {
						try {
							ObjectInputStream inputStream = new ObjectInputStream(Files
																					.newInputStream(t));
							Map<String, Integer> map = (Map<String, Integer>) inputStream.readObject();
							for (Entry<String, Integer> entry : map.entrySet()) {
								Integer wordCount = entityFrequency.get(entry.getKey());
								if (wordCount != null) {
									entityFrequency.put(entry.getKey(), wordCount + entry.getValue());
								} else {
									entityFrequency.put(entry.getKey(), entry.getValue());
								}
							}
						} catch (IOException e) {
							e.printStackTrace();
						} catch (ClassNotFoundException e) {
							e.printStackTrace();
						}
						
					}
				});
		logger.info("Done");
		
		logger.info("Sorting generated statistics...");
		Map<String, Integer> sortedEntityFrequency = sortMapByValue(entityFrequency);
		logger.info("Done");
		
		StringBuilder sb = new StringBuilder();
		for (Entry<String, Integer> entry : sortedEntityFrequency.entrySet()) {
			sb.append(entry.getKey());
			sb.append(" ");
			sb.append(entry.getValue());
			sb.append("\n");
		}
		
		String fileName = RESULTS_FILE_PREFIX + INPUT_FILE.substring(0, (INPUT_FILE.contains(".") ?
																		INPUT_FILE.lastIndexOf(".")
																		: INPUT_FILE.length()-1));
		logger.info("Saving results to file " + fileName);
		Files.write(Paths.get(RESULTS_DIRECTORY + fileName),
					sb.toString().getBytes(), StandardOpenOption.CREATE,
					StandardOpenOption.TRUNCATE_EXISTING);
		logger.info("Done");
	}

	private static Map<String, Integer> sortMapByValue(Map<String, Integer> entityFrequency) {
		List<Entry<String, Integer>> list = new LinkedList<Entry<String, Integer>>
														(entityFrequency.entrySet());
		Collections.sort(list, new Comparator<Entry<String, Integer>>() {

			public int compare(Entry<String, Integer> o1,
								Entry<String, Integer> o2) {
				return o2.getValue().compareTo(o1.getValue());
			}
		});
		Map<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
		for (Entry<String, Integer> entry : list) {
			sortedMap.put(entry.getKey(), entry.getValue());
		}
		
		return sortedMap;
	}
}
