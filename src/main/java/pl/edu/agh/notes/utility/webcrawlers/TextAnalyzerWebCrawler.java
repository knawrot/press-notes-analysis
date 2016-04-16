package pl.edu.agh.notes.utility.webcrawlers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class TextAnalyzerWebCrawler implements WebCrawler {
	private static final String TEXT_ANALYZER_AT_ONLINE_UTILITY_COM = 
			"http://www.online-utility.org/"
			+ "english/analyzer.jsp";

	public Map<String, Integer> runWithText(String text) {
		WebDriver driver = new FirefoxDriver();
		driver.get(TEXT_ANALYZER_AT_ONLINE_UTILITY_COM);
		
		WebElement form = driver.findElement(By.tagName("form"));
		Select excludeWordsSelect = new Select(
										form.findElement(By.name("ruleset"))
										);
		excludeWordsSelect.selectByValue("10");
		System.out.println("Using excluding mode: " 
								+ excludeWordsSelect.getFirstSelectedOption()
									.getText());
		
		WebElement textArea = form.findElement(By.name("text"));
		textArea.sendKeys(text);

		textArea.submit();
		
		WebElement tableOfResults = driver.findElements(By.tagName("table"))
											.get(1);
		List<WebElement> rows = tableOfResults.findElements(By.tagName("tr"));
		Map<String, Integer> wordFrequencyMap = new HashMap<String, Integer>();
		for (WebElement row : rows) {
			List<WebElement> cells = row.findElements(By.tagName("td"));
			wordFrequencyMap.put(cells.get(1).getText(), Integer.parseInt(cells.get(2).getText()));
		}
		
		driver.close();
		
		return wordFrequencyMap;
	}

}
