package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import io.appium.java_client.MobileElement;

public class MainPage {
	
	private static MobileElement element= null;
	
	public static MobileElement tabParent(WebDriver driver) {
	
		element =(MobileElement) driver.findElement(By.id("com.test.gambit:id/tab_pager"));
		
		return element;
	}

	
	public static String eachTab(WebDriver driver){
		
		return "com.test.gambit:id/tab_title";
		
	}
	
	public static String firstName(WebDriver driver){
		
		return "com.test.gambit:id/tv_first_name";
		
	}
	
	public static MobileElement playerCount(WebDriver driver) {
		
		element =(MobileElement) driver.findElement(By.id("com.test.gambit:id/tv_count"));
		
		return element;
	}
	
	public static MobileElement firstNameElement(WebDriver driver) {
		
		element =(MobileElement) driver.findElement(By.id("com.test.gambit:id/tv_first_name"));
		
		return element;
	}
	
	public static MobileElement playersAndGamesRoot(WebDriver driver) {
		
		element = (MobileElement) driver.findElement(By.id("com.test.gambit:id/rv_players"));
		
		return element;
	}
	

	public static String lastName(WebDriver driver){
		
		return "com.test.gambit:id/tv_last_name";
		
	}
	

	public static String teamId(WebDriver driver){
		
		return "com.test.gambit:id/tv_team_id";
		
	}

	public static String vs(WebDriver driver){
		
		return "com.test.gambit:id/vs";
		
	}
	
	public static MobileElement vsElement(WebDriver driver) {
		
		element =(MobileElement) driver.findElement(By.id("com.test.gambit:id/vs"));
		
		return element;
	}
	
	public static String team1(WebDriver driver){
		
		return "com.test.gambit:id/tv_abbr_1";
		
	}
	
	public static String team2(WebDriver driver){
		
		return "com.test.gambit:id/tv_abbr_2";
		
	}
	
	
	public static MobileElement team1Element(WebDriver driver) {
		
		element =(MobileElement) driver.findElement(By.id("com.test.gambit:id/tv_abbr_1"));
		
		return element;
	}
	
	public static MobileElement team2Element(WebDriver driver) {
		
		element =(MobileElement) driver.findElement(By.id("com.test.gambit:id/tv_abbr_2"));
		
		return element;
	}
	
	public static MobileElement expandedHeader(WebDriver driver) {
		
		element =(MobileElement) driver.findElement(By.xpath("//android.widget.FrameLayout[contains(@resource-id,'toolbar_layout') and @content-desc=' ']"));
		
		return element;
	}
	
	public static MobileElement CollapsedHeader(WebDriver driver) {
		
		element =(MobileElement) driver.findElement(By.xpath("//android.widget.FrameLayout[contains(@resource-id,'toolbar_layout') and @content-desc='The NBA Scout']"));
		
		return element;
	}
	
	public static MobileElement progressBar(WebDriver driver) {
		
		element =(MobileElement) driver.findElement(By.id("com.test.gambit:id/progress_bar"));
		
		return element;
	}
	
	public static MobileElement searchInputField(WebDriver driver) {
		
		element =(MobileElement) driver.findElement(By.id("com.test.gambit:id/et_search"));
		
		return element;
	}
	
	public static MobileElement removeSearchKeyword(WebDriver driver) {
		
		element =(MobileElement) driver.findElement(By.id("com.test.gambit:id/iv_close"));
		
		return element;
	}
	
	
}

