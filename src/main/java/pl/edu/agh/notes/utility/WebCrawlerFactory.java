package pl.edu.agh.notes.utility;

import pl.edu.agh.notes.utility.webcrawlers.TextAnalyzerWebCrawler;
import pl.edu.agh.notes.utility.webcrawlers.WebCrawler;
import pl.edu.agh.notes.utility.webcrawlers.WordCounterWebCrawler;
import pl.edu.agh.notes.utility.webcrawlers.WordFrequencyWebCrawler;

public class WebCrawlerFactory {

	public static WebCrawler getWebCrawler(WebCrawlers webCrawler) {
		switch (webCrawler) {
			case WORD_COUNTER:
				return new WordCounterWebCrawler();
			case WORD_FREQUENCY:
				return new WordFrequencyWebCrawler();
			case TEXT_ANALYZER:
				return new TextAnalyzerWebCrawler();
			default:
				throw new RuntimeException("Bad webcrawler!");
		}
	}
}
