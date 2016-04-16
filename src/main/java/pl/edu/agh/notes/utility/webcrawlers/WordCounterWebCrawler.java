package pl.edu.agh.notes.utility.webcrawlers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class WordCounterWebCrawler implements WebCrawler {
	private static final String WORD_COUNTER_COM = "http://www.wordcounter.com/";

	public Map<String, Integer> runWithText(String text) {
		WebDriver driver = new FirefoxDriver();
		driver.get(WORD_COUNTER_COM);
		
		WebElement form = driver.findElement(By.tagName("form"));	
		WebElement textArea = form.findElement(By.name("string"));
		textArea.sendKeys(text);
		textArea.submit();
		
		WebElement tableOfResults = driver.findElements(By.tagName("table"))
											.get(0);
		List<WebElement> rows = tableOfResults.findElements(By.tagName("tr"));
		rows.remove(0);
		Map<String, Integer> wordFrequencyMap = new HashMap<String, Integer>();
		for (WebElement row : rows) {
			List<WebElement> cells = row.findElements(By.tagName("td"));
			wordFrequencyMap.put(cells.get(0).getText(), Integer.parseInt(cells.get(1).getText()));
		}
		
		driver.close();
		
		return wordFrequencyMap;
	}

}
