package common;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import io.appium.java_client.AppiumDriver;

public class Common_methods {

	public static void printLine() {
		System.out.println("-------------------------------------");
	}
	public static String capture(AppiumDriver driver, String screenshotName) throws IOException { //to capture screenshot
		
		TakesScreenshot ts = (TakesScreenshot)driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		String dest = System.getProperty("user.dir")+"/screenshots/"+screenshotName+".png";
		File destination = new File(dest);
		FileUtils.copyFile(source, destination);
		return dest;
		
	}

	public static String fileName() { // generating file with current time
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-YYYY_hh:mm:ss");
		Date date= new Date();
		String file_name = sdf.format(date);		
		return file_name;
		
	}
	
	public static ArrayList<String> readDataFromFile(String path) throws FileNotFoundException {
		File file = new File(path);
		ArrayList<String> values = new ArrayList<String>();
		Scanner sc = new Scanner(file);
		while(sc.hasNextLine()){
			values.add(sc.nextLine());
		}
		return values;
	}
}
