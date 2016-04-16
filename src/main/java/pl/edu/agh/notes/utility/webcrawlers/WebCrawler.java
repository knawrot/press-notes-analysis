package pl.edu.agh.notes.utility.webcrawlers;

import java.util.Map;

public interface WebCrawler {
	public Map<String, Integer> runWithText(String text);

}
