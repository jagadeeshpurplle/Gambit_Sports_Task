package test_scenarios;

import java.awt.AWTException;
import java.awt.Robot;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.google.common.collect.ImmutableMap;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidKeyCode;
import io.appium.java_client.touch.offset.PointOption;
import net.sourceforge.tess4j.TesseractException;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;


import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import net.sourceforge.tess4j.util.LoadLibs;


public class GambitTestScenarios {

	ExtentReports extent;
	ExtentTest logger;
	public AppiumDriver driver;
	Properties prop = new Properties();
	int count = 0,check = 0;
	
	
	
	@BeforeTest
    public void setup() throws InterruptedException, IOException {
		PropertyConfigurator.configure(System.getProperty("user.dir")+"/src/test/resources/log4j.properties");
		
		extent = new ExtentReports(System.getProperty("user.dir")+"/HTMLOutput/STMExtentReport.html",true); //giving path where to save report and true for override
		extent.addSystemInfo("Host Name", "Jagadeesh_Automation")
		.addSystemInfo("Environment", "AngelList Production")
		.addSystemInfo("Author", "Mr. Jagadeesh");
		extent.loadConfig(new File(System.getProperty("user.dir")+"/src/test/resources/extent-config.xml"));

		FileInputStream file = new FileInputStream(System.getProperty("user.dir")+"/envProperties/env.properties");
		prop.load(file);
		
        DesiredCapabilities capabilities= new DesiredCapabilities();
        capabilities.setCapability("deviceName", prop.getProperty("deviceName"));
        capabilities.setCapability(CapabilityType.PLATFORM, "Android");
        capabilities.setCapability("platformVersion",prop.getProperty("platform_version"));
        capabilities.setCapability("appPackage", prop.getProperty("app_package"));
        capabilities.setCapability("appActivity", prop.getProperty("app_activity"));
        capabilities.setCapability("noReset", "true");
        capabilities.setCapability("ignoreUnimportantViews", true);
        capabilities.setCapability("disableAndroidWatchers", true);
        capabilities.setCapability("autoAcceptAlerts", true);
        capabilities.setCapability("autoDismissAlerts", true);
        capabilities.setCapability("resetKeyboard", true);
        capabilities.setCapability("unicodeKeyboard", true);

        try {
			driver= new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("Make sure appium server is up and running");
			
			System.exit(0);
		}
 
	}
	
	@AfterTest	
	public void iAmDone() {
		hasAppCrashed(); // it will check whether app crashed or not
		extent.flush();
		extent.close();
		driver.quit();
		
		
	}
    private void hasAppCrashed() {
        By alertTitle = By.id("android:id/alertTitle"); // taking id which unique for alert to check app crash text
        Boolean crashTextAppeared = driver.findElements(alertTitle).size() > 0;
        if (crashTextAppeared) {
            if (driver.findElement(alertTitle).getAttribute("text").contains("has stopped")) {
                System.out.println("app crashed");
                logger.log(LogStatus.FAIL, "App crashed");
            }
        }
    }
    
    @AfterMethod
	public void iAmDoneMethod(ITestResult result) throws IOException {
    	if (driver != null) {
    		hasAppCrashed();

    		if(result.getStatus() == ITestResult.FAILURE) { // if the any action gets failure then cause will be thrown to extent report
    			logger.log(LogStatus.FAIL, " Test case failed : "+result.getName());
    			logger.log(LogStatus.FAIL, "Reason is : "+result.getThrowable());
    			String path = common.Common_methods.capture(driver, common.Common_methods.fileName());
    			logger.log(LogStatus.FAIL, "Screenshot--->"+logger.addScreenCapture(path));
    		}else if(result.getStatus() == ITestResult.SUCCESS) { // if result gets pass status, hence this will show test case as passed
    			logger.log(LogStatus.PASS, "Test case passed");
    		}
    		
    		extent.endTest(logger);		
    	}
	}
	
	@Test(priority=2)
	public void Player_And_Games_Tabs() throws InterruptedException, IOException { // to check whether players tab & games tab data coming or not
		logger = extent.startTest("Player_And_Games_Tabs");
	
		try {
			new WebDriverWait(driver, 60).until(ExpectedConditions.visibilityOfElementLocated(By.id(pages.MainPage.firstName(driver))));
		} catch (Exception e) {
			exitOnCountZero();
		}
		MobileElement parentTab =  (MobileElement) pages.MainPage.tabParent(driver);
		List<MobileElement> noOfTabs = parentTab.findElements(By.id(pages.MainPage.eachTab(driver)));
		
		System.out.println("No of TABs : "+noOfTabs.size());
		logger.log(LogStatus.INFO, "No of TABs : "+noOfTabs.size());
		for(int i =0;i<noOfTabs.size();i++) {
			String tabName = noOfTabs.get(i).getText();
			System.out.println("\nTab Name : "+tabName);
			if(tabName.equalsIgnoreCase(prop.getProperty("players_tab_name"))) {
				noOfTabs.get(i).click();
				System.out.println("Clicked on "+tabName);
				logger.log(LogStatus.PASS, "Clicked on "+tabName);
				MobileElement countOfPlayers = (MobileElement) pages.MainPage.playerCount(driver);
				if(Integer.parseInt(countOfPlayers.getText()) <= 0) { // if players count is 0, this block will execute
					if(pages.MainPage.firstNameElement(driver).isDisplayed()) { // if data showing fine, then as per count is showing 0, this block will execute
						System.out.println("Player count not showing even players data loaded");
//						logger.log(LogStatus.FAIL, "Player count not showing even players data loaded");
						String path = common.Common_methods.capture(driver, common.Common_methods.fileName());
						logger.log(LogStatus.FAIL, prop.getProperty("Player count not showing even players data loaded")+"</br> Screenshot--->"+logger.addScreenCapture(path));
						
					}
				}else {
					if(!pages.MainPage.firstNameElement(driver).isDisplayed()) { // if data not displays even when count was displaying more than 0, this block will execute
						System.out.println("Players data not showing even players count is been shown");
//						logger.log(LogStatus.FAIL, "Players data not showing even players count is been shown");
						String path = common.Common_methods.capture(driver, common.Common_methods.fileName());
						logger.log(LogStatus.FAIL, prop.getProperty("Players data not showing even players count is been shown")+"</br> Screenshot--->"+logger.addScreenCapture(path));
						
					}else { // if both count & data showing, this block will execute
						System.out.println(prop.getProperty("success_msg_players_with_count"));
						String success = prop.getProperty("success_msg_players_with_count")+"</br>";
						List<MobileElement> lastName = pages.MainPage.playersAndGamesRoot(driver).findElements(By.id(pages.MainPage.lastName(driver)));
						List<MobileElement> teamsIds = pages.MainPage.playersAndGamesRoot(driver).findElements(By.id(pages.MainPage.teamId(driver)));
//						System.out.println(lastName.size()+","+teamsIds.size());
						System.out.println("Players data on current screen : -->");
						String data = "";
						for(int t= 0;t<lastName.size();t++) { // printing the data of current screen
							System.out.println(lastName.get(t).getText()+", "+teamsIds.get(t).getText());
							data = data + lastName.get(t).getText()+", "+teamsIds.get(t).getText()+"</br>";
						}
						
						logger.log(LogStatus.PASS, success + data);
					}
				}
				
			}else if(tabName.equalsIgnoreCase(prop.getProperty("Games_tab_name"))) { // games tab
				noOfTabs.get(i).click();
				System.out.println("Clicked on "+tabName);
				logger.log(LogStatus.PASS, "Clicked on "+tabName);
				new WebDriverWait(driver, 50).until(ExpectedConditions.visibilityOfElementLocated(By.id(pages.MainPage.vs(driver))));
				if(pages.MainPage.team1Element(driver).isDisplayed() && pages.MainPage.vsElement(driver).isDisplayed() && pages.MainPage.team2Element(driver).isDisplayed()) { // checking two teams and VS showing or not. On successful showing of these data, this block will execute
					System.out.println(prop.getProperty("success_msg_games_list"));
					String success = prop.getProperty("success_msg_games_list")+"</br>";
					List<MobileElement> leftSide  = pages.MainPage.playersAndGamesRoot(driver).findElements(By.id(pages.MainPage.team1(driver)));
					List<MobileElement> RightSide = pages.MainPage.playersAndGamesRoot(driver).findElements(By.id(pages.MainPage.team2(driver)));
					System.out.println("Games that are showing in current screen : ");
					String data = "";
					for(int l=0;l<leftSide.size();l++) { // print the games data
						System.out.println(leftSide.get(l).getText()+" Vs "+RightSide.get(l).getText());
						data = data + leftSide.get(l).getText()+" Vs "+RightSide.get(l).getText()+"</br>";

					}
					
					logger.log(LogStatus.PASS, success + data);
				}else {
					System.out.println("Error while showing games data");
//					logger.log(LogStatus.FAIL,"Error while showing games data");
					String path = common.Common_methods.capture(driver, common.Common_methods.fileName());
					logger.log(LogStatus.FAIL, prop.getProperty("Error while showing games data")+"</br> Screenshot--->"+logger.addScreenCapture(path));
					
				}
				
			}
			
		}
		
		common.Common_methods.printLine();

	}
	
	@Test(priority=3)
	public void scroll_player_tab() throws InterruptedException, IOException {
		logger = extent.startTest("scroll_player_tab");
/*		try {
			new WebDriverWait(driver, 60).until(ExpectedConditions.visibilityOfElementLocated(By.id(pages.MainPage.firstName(driver))));
		} catch (Exception e) {
			exitOnCountZero();
		}*/
		
		MobileElement parentTab =  (MobileElement) pages.MainPage.tabParent(driver); // parent element of two tabs
		
		List<MobileElement> noOfTabs = parentTab.findElements(By.id(pages.MainPage.eachTab(driver))); // each tab
		
		System.out.println("No of TABs : "+noOfTabs.size());
		logger.log(LogStatus.INFO, "No of TABs : "+noOfTabs.size());
		parent : for(int i =0;i<noOfTabs.size();i++) {
			String tabName = noOfTabs.get(i).getText();
			System.out.println("\nTab Name : "+tabName);
			if(tabName.equalsIgnoreCase(prop.getProperty("players_tab_name"))) { // matched with players tab
				noOfTabs.get(i).click();
				System.out.println("Clicked on "+tabName);
				logger.log(LogStatus.PASS, "Clicked on "+tabName);
				MobileElement count = (MobileElement) pages.MainPage.playerCount(driver); // taking count of players
				int countOfPlayers = Integer.parseInt(count.getText());
				int updateCountOfPlayers = 0;
				int scrollDownMore = 0;
				int Notupdatingcheck = 0;
				scrollDown();
				while(countOfPlayers>=updateCountOfPlayers) { //scroll till players count changes
//					System.out.println("Present players : "+countOfPlayers);
					
					scrollDownByElementLocation(pages.MainPage.playersAndGamesRoot(driver)); // scrolls at root layout of players
					loaderCheckAndWait(); // if progress loader comes, waits till it completely loaded
					Boolean Notupdating =checkListIsUpdatingOrNot(); // checks if list updating or not
					if(Notupdating) { // if list not updated, this block will execute
						Notupdatingcheck++; // keeping this count just for buffer and if 2 times same result comes, hence no data updating
						if(Notupdatingcheck==2) {
							System.out.println(prop.getProperty("Erro_list_not_updating"));
							String path = common.Common_methods.capture(driver, common.Common_methods.fileName());
							logger.log(LogStatus.INFO, prop.getProperty("Erro_list_not_updating")+"</br> Screenshot--->"+logger.addScreenCapture(path));
							
							continue parent; // continuing to next tab
						}
					}else { // if data updating, this block will executes
						MobileElement Updatecount = (MobileElement) pages.MainPage.playerCount(driver);
						updateCountOfPlayers = Integer.parseInt(Updatecount.getText());
//						System.out.println("Updated players : "+updateCountOfPlayers);
						if(updateCountOfPlayers>countOfPlayers) {
							System.out.println(prop.getProperty("list_updating_on_scroll"));
							logger.log(LogStatus.PASS, prop.getProperty("list_updating_on_scroll"));
							if(scrollDownMore!=1) {
								count = (MobileElement) pages.MainPage.playerCount(driver);
								countOfPlayers = Integer.parseInt(count.getText());
							}
							scrollDownMore++;// scrolling more down
						}	
					}			
				}
				check =0; 
			}else if(tabName.equalsIgnoreCase(prop.getProperty("Games_tab_name"))) {	 // games tab			
				noOfTabs.get(i).click();
				System.out.println("Clicked on "+tabName);
				logger.log(LogStatus.PASS, "Clicked on "+tabName);
//				scrollIsHappeningOrNot(4);
				int notUpdatingcheck=0;
				for(int scroll=0;scroll<5;scroll++) { // scrolling for 5 times
					boolean notUpdating = checkListIsUpdatingOrNot(); // checking list is updating or not on every scroll
					
					if(notUpdating) { // not scrolls, this block will execute
						notUpdatingcheck++;
						if(notUpdatingcheck==2) {
							System.out.println(prop.getProperty("Erro_list_not_updating_games"));
							String path = common.Common_methods.capture(driver, common.Common_methods.fileName());
							logger.log(LogStatus.INFO, prop.getProperty("Erro_list_not_updating_games")+"</br> Screenshot--->"+logger.addScreenCapture(path));
						}
					}else {
						logger.log(LogStatus.PASS, prop.getProperty("list_updating_on_scroll_games"));
					}
				}
				
			}
			
		}
		common.Common_methods.printLine(); // to print line
		check=0;
	}
	
	@Test(priority = 1)
	public void headerCollapseExpand() throws IOException {
		logger = extent.startTest("headerCollapseExpand");
		try {
			new WebDriverWait(driver, 60).until(ExpectedConditions.visibilityOfElementLocated(By.id(pages.MainPage.firstName(driver))));
		} catch (Exception e) {
			exitOnCountZero();
		}
		
		try {
			//initially checking for expanded header
			if(pages.MainPage.expandedHeader(driver).isDisplayed()) {  //if expanded header element displayed then this block will be executed
				System.out.println("Header in expanded state");
				logger.log(LogStatus.PASS, "Header in expanded state");
			}
			
			
			scrollDown(); // performing scroll down to make header to go in closed state
			
			if(pages.MainPage.CollapsedHeader(driver).isDisplayed()) { //if closed state header displayed this block will be executed
				System.out.println(prop.getProperty("success_collapse"));
				logger.log(LogStatus.PASS, prop.getProperty("success_collapse"));
			}
			
			//as per above we scrolled down only one time, here i'm just doing 5 times iteration not more than that.
			for(int i=0;i<5;i++) { //iterating 5 times(i.e scrolling 5 times till header comes to expand state, once it comes loop will break
				try {
					if(pages.MainPage.expandedHeader(driver).isDisplayed()) { //definetly it comes to expanded state as per we scroll down only once, if not comes that means some error exist
						System.out.println(prop.getProperty("success_expand"));
						logger.log(LogStatus.PASS, prop.getProperty("success_expand"));
						break;
					}
				} catch (Exception e) {
					scrollUp();
				}	
			}
		} catch (Exception e) {
			System.out.println(e.getMessage()); // throws reason for error if any on above implementation
		}		

		common.Common_methods.printLine();
		
	}
	
	
	
	@Test(priority=4)
	public void search() throws InterruptedException, TesseractException, IOException {
		logger = extent.startTest("search");
		driver.findElement(By.xpath("//android.widget.TextView[@text='Players']")).click();
		
		try {
			new WebDriverWait(driver, 60).until(ExpectedConditions.visibilityOfElementLocated(By.id(pages.MainPage.firstName(driver))));
		} catch (Exception e) {
			exitOnCountZero();
		}

		pages.MainPage.searchInputField(driver).click(); // clicking on search input field
		
		ArrayList<String> values = common.Common_methods.readDataFromFile(System.getProperty("user.dir")+"/inputs/searchKeywords.txt"); // `searchKeywords` file contains the keywords that going to be perform search

		for(int data =0 ;data<values.size();data++) { // iterate over all the keywords and search one by one
			if(values.get(data).equals("")) {
				System.out.println("Empty seach keyword not accepted");
				logger.log(LogStatus.WARNING, "Empty search keyword not accepted");
			}
			int initialCountOfPlayers = Integer.parseInt(pages.MainPage.playerCount(driver).getText());
			List<MobileElement> initialresultsFirstName = pages.MainPage.playersAndGamesRoot(driver).findElements(By.id(pages.MainPage.firstName(driver)));
			List<MobileElement> initialresultsLastName = pages.MainPage.playersAndGamesRoot(driver).findElements(By.id(pages.MainPage.lastName(driver)));
			ArrayList<String> initialresultantDataCombined = addData(initialresultsFirstName,initialresultsLastName); // taking firstname & lastname data and combining as per search only applies on these two fields
			
			System.out.println(values.get(data));
			pages.MainPage.searchInputField(driver).sendKeys(values.get(data)); // passing keyword into search input
			((AndroidDriver<MobileElement>) driver).pressKey(new KeyEvent(AndroidKey.ENTER)); // performing keyboard enter
			loaderCheckAndWait();
			logger.log(LogStatus.INFO,"Passed "+values.get(data)+" keyword to search ");
//			String s = readToastMessage(des);
			/*System.out.println("imge data");
			System.out.println(s);
			System.out.println("end image data");*/
			new WebDriverWait(driver, 50).until(ExpectedConditions.presenceOfElementLocated(By.id(pages.MainPage.firstName(driver))));
			
			int countOfPlayers = Integer.parseInt(pages.MainPage.playerCount(driver).getText());
			if(countOfPlayers==0) { // if count of players 0, then something erro came and this block will execute
				pages.MainPage.removeSearchKeyword(driver);
				System.out.println("No result for the search");
				String path = common.Common_methods.capture(driver, common.Common_methods.fileName());
				logger.log(LogStatus.FAIL, "No result for the search </br>"+"Screenshot--->"+logger.addScreenCapture(path));
				
				continue;
			}else if(initialCountOfPlayers==countOfPlayers) { // if nitial count & resulantant count becomes same, this block will execute
				List<MobileElement> resultsFirstName = pages.MainPage.playersAndGamesRoot(driver).findElements(By.id(pages.MainPage.firstName(driver)));
				List<MobileElement> resultsLastName = pages.MainPage.playersAndGamesRoot(driver).findElements(By.id(pages.MainPage.lastName(driver)));
				ArrayList<String> resultantDataCombined = addData(resultsFirstName,resultsLastName);
				if(initialresultantDataCombined.equals(resultantDataCombined)) { // if both count same, my next condition would be this block = if initial data and resultant data looks same this block will execute
					System.out.println("No more data loaded");
					String path = common.Common_methods.capture(driver, common.Common_methods.fileName());
					logger.log(LogStatus.FAIL, "No more data loaded </br>"+"Screenshot--->"+logger.addScreenCapture(path));	
				}
				
			}else { // if data successfully loaded, this block will execute
				List<MobileElement> resultsFirstName = pages.MainPage.playersAndGamesRoot(driver).findElements(By.id(pages.MainPage.firstName(driver)));
				List<MobileElement> resultsLastName = pages.MainPage.playersAndGamesRoot(driver).findElements(By.id(pages.MainPage.lastName(driver)));
				System.out.println(resultsFirstName.size());
				System.out.println(resultsLastName.size());
				ArrayList<String> resultantDataCombined = addData(resultsFirstName,resultsLastName);
				for(int check =0;check<resultantDataCombined.size();check++) { // print resultant data
					System.out.println(resultantDataCombined.get(check));
					if(resultantDataCombined.get(check).toLowerCase().contains(values.get(data).toLowerCase())) {
						System.out.println("resultant data came as per provided search keyword");
						logger.log(LogStatus.PASS, "resultant data came as per provided search keyword");
					}
				}
				
			}
			Thread.sleep(2000);
			scrollUp();
			pages.MainPage.searchInputField(driver).clear();
			
		}
	
		common.Common_methods.printLine();
		
	}
	
	
	public String readToastMessage(String path) throws TesseractException { // this is to read text from image, but unluckily toast message text not able to get  
		String data = null;
		File image = new File(path);
		System.out.println("Image name is :" + image.toString());
		ITesseract instance = new Tesseract();

		File tessDataFolder = LoadLibs.extractTessResources("tessdata");
		instance.setDatapath(tessDataFolder.getAbsolutePath());
		
		data = instance.doOCR(image);
		System.out.println(data);
		return data;
	
	}
	
	
	
	public ArrayList<String> addData(List<MobileElement> resultsFirstName,List<MobileElement> resultsLastName) {
		ArrayList<String> data = new ArrayList<String>();
		for(int i=0;i<resultsFirstName.size();i++) {
			data.add(resultsFirstName.get(i).getText());
		}
		for(int j=0;j<resultsLastName.size();j++) {
			data.add(resultsLastName.get(j).getText());
		}
		
		return data;
	}
	
	public void loaderCheckAndWait() { // wait till progress bar loads completely
		try {
			System.out.println(driver.findElement(By.id("com.test.gambit:id/progress_bar")).isDisplayed());
			if(pages.MainPage.progressBar(driver).isDisplayed()) {
				System.out.println("came to wait for load list");
				System.out.println(prop.getProperty("wait_load"));
				logger.log(LogStatus.INFO, prop.getProperty("wait_load"));
				new WebDriverWait(driver, 50).until(ExpectedConditions.invisibilityOfElementLocated(By.id("com.test.gambit:id/progress_bar")));
				try {
					if(pages.MainPage.progressBar(driver).isDisplayed()) { // this condition is for, if progress bar keep on loading without ending, this block will execute
						count++;
					}
					if(count==3) { // upto 3 times if progress bar keep on loading, this block will execute
						System.out.println(prop.getProperty("loader_keep_on_loading"));
						String path = common.Common_methods.capture(driver, common.Common_methods.fileName());
						logger.log(LogStatus.FAIL, "loader_keep_on_loading </br>"+"Screenshot--->"+logger.addScreenCapture(path));
						extent.flush();
						extent.close();
						System.exit(0);
						
					}
				} catch (Exception e) {
				}
			}
		} catch (Exception e) {
			
		}
	}
	
	public void scrollIsHappeningOrNot(int n) {
		List<MobileElement> previous=null;
		List<MobileElement> current = null;
		
		for(int i=0;i<n;i++) {
//			System.out.println(i);
			try {
				driver.findElement(By.id("dfs"));
			}catch(Exception e) {
				previous=driver.findElements(By.xpath("//*"));
				scrollDownByElementLocation(pages.MainPage.playersAndGamesRoot(driver));			
				loaderCheckAndWait();
				current=driver.findElements(By.xpath("//*"));
				if(current.size() != previous.size()) {
					System.out.println(prop.getProperty("scroll_success"));
					logger.log(LogStatus.PASS, prop.getProperty("scroll_success"));
				}
			}		
		}
	}
	
	
	public boolean checkListIsUpdatingOrNot() { // on every scroll it takes all the xpaths and check before and present elements to decide scroll is happening or not
		System.out.println("checking whether list is updating or not");
		List<MobileElement> previous=null;
		List<MobileElement> current = null;
	
		try {
			driver.findElement(By.id("dfs"));
		}catch(Exception e) {
			previous=driver.findElements(By.xpath("//*"));
			scrollDownFast();
			current=driver.findElements(By.xpath("//*"));
			if(current.size() != previous.size()) {
				check=0;
			}
		}
			
		List<String> previousStrings = new ArrayList<String>();
		List<String> CurrentStrings = new ArrayList<String>();
		
		for(MobileElement e : previous) {
			previousStrings.add(e.toString());
//				System.out.println(e.toString());
		}
		for(MobileElement e1: current) {
			CurrentStrings.add(e1.toString());
		}

		boolean status=equalLists(previousStrings,CurrentStrings);
			
		if(status && check==4) {
			System.out.println("List is not updating");
			return true;
		}
		return false;
		
	}
	
	public  boolean equalLists(List<String> one, List<String> two){      // returns two lists are equal or not
	    if (one == null && two == null){
	        check++;
	        return true;
	    }
	    if((one == null && two != null) 
	      || one != null && two == null
	      || one.size() != two.size()){
	        return false;
	    }
	    one = new ArrayList<String>(one); 
	    two = new ArrayList<String>(two);   

	    Collections.sort(one);
	    Collections.sort(two);  
	    check++;
	    return one.equals(two);
	}

	
	
	
	public void exitOnCountZero() throws IOException { // if count is 0, as it is not correct move to next testcase terming the program
		MobileElement count = (MobileElement) pages.MainPage.playerCount(driver);
		if(Integer.parseInt(count.getText())==0) {
			System.out.println(prop.getProperty("0_no_of_players"));
			String path = common.Common_methods.capture(driver, common.Common_methods.fileName());
			logger.log(LogStatus.FAIL, prop.getProperty("0_no_of_players")+"</br> Screenshot--->"+logger.addScreenCapture(path));
			extent.flush();
			driver.quit();
			System.exit(0);
		}
	}
	
	
 	public void scrollDownByElementLocation(MobileElement element) {   // scrolls by element coordinates
// 		System.out.println("scrolling by element location");
 		int x = element.getLocation().getX();
		int y = element.getLocation().getY();
		int startY=(int)(x*0.90);
 		int endY=(int)(y*0.20);
		new TouchAction(driver).press(PointOption.point(x, startY)).waitAction().moveTo(PointOption.point(x, endY)).release().perform();	

	}
 	
	
 	public void scrollDown() {  // scroll down by window coordinates
// 		System.out.println("scroll down by window");
 		Dimension dim=driver.manage().window().getSize();
		int height=dim.getHeight();
		int width=dim.getWidth();
		int x=width/2;
		int startY=(int)(height*0.80);
		int endY=(int)(height*0.50);
		new TouchAction(driver).press(PointOption.point(x, startY)).waitAction().moveTo(PointOption.point(x, endY)).release().perform();	

 	}
 	
 	public void scrollDownFast() { // scroll down by window coordinates
// 		System.out.println("scroll down by window");
 		Dimension dim=driver.manage().window().getSize();
		int height=dim.getHeight();
		int width=dim.getWidth();
		int x=width/2;
		int startY=(int)(height*0.80);
		int endY=(int)(height*0.20);
		new TouchAction(driver).press(PointOption.point(x, startY)).waitAction().moveTo(PointOption.point(x, endY)).release().perform();	

 	}
 	
 	
 	public void scrollUp() { // scroll up by window coordinates
		Dimension dim=driver.manage().window().getSize();
 		int height=dim.getHeight();
 		int width=dim.getWidth();
 		int x=width/2;
 		int startY=(int)(height*0.40);
 		int endY=(int)(height*0.80);
		new TouchAction(driver).press(PointOption.point(x, startY)).waitAction().moveTo(PointOption.point(x, endY)).release().perform();	
 
		
	}
}
