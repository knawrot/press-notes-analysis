package pl.edu.agh.notes.utility.webcrawlers;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;



public class WordFrequencyWebCrawler implements WebCrawler {
	private static final int MAX_ENTRIES_NUM = 100;
	private static final String WORD_FREQ_AT_ONLINE_UTILITY_COM = 
										"http://www.online-utility.org/"
										+ "english/filtered_word_frequency.jsp";


	public Map<String, Integer> runWithText(String text) {
		if(text.isEmpty()){
			return Collections.emptyMap();
		}
		System.setProperty("webdriver.chrome.driver", "drive/chromedriver");
		WebDriver driver = new ChromeDriver(); //FirefoxDriver() or SafariDriver();
		
		driver.get(WORD_FREQ_AT_ONLINE_UTILITY_COM);
		
		WebElement form = driver.findElement(By.tagName("form"));
		Select excludeWordsSelect = new Select(
										form.findElement(By.name("ruleset"))
										);
		excludeWordsSelect.selectByValue("10");
		
		WebElement textArea = form.findElement(By.name("text"));
		textArea.sendKeys(text);

		textArea.submit();
		
		WebElement tableOfResults = driver.findElements(By.tagName("table"))
											.get(1);
		List<WebElement> rows = tableOfResults.findElements(By.tagName("tr"));
		Map<String, Integer> wordFrequencyMap = new HashMap<String, Integer>();
		for (int i = 1; i < rows.size() && i < MAX_ENTRIES_NUM; i++) {
			List<WebElement> cells = rows.get(i).findElements(By.tagName("td"));
			wordFrequencyMap.put(cells.get(1).getText(), Integer.parseInt(cells.get(2).getText()));
		}
		
		driver.close();
		
		return wordFrequencyMap;
	}

}
