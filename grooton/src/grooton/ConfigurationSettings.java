package grooton;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;




import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.comparison.ImageDiff;
import ru.yandex.qatools.ashot.comparison.ImageDiffer;
public class ConfigurationSettings {
	WebDriver driver;
	//LinkedHashMap<String,String> valuetoStore;
	List<String> JEngineers=new ArrayList<String>();
	List<String> Emptylist=new ArrayList<String>();
	Map<String, Object[]> TestNGResults;
	public static List<String> menuItems=null;
//	String MethodName="";
	public static final By JEng=By.xpath("//h5[contains(.,'Junior Engineer')]/preceding-sibling::h3[contains(@class,'member-name')]");
	public static final By GROOTAN_LOGO=By.xpath("//a/img[contains(@class,'logo')]");
	public static final By CTO_IMG=By.xpath("//h5[contains(text(),'CTO')]/preceding-sibling::img");
	public static final By HR_IMG=By.xpath("//h5[contains(text(),'HR')]/preceding-sibling::img");
	boolean launchflag;
	/**
	 * This Method used to fetch the names of Junior Engineers
	 * @throws Exception
	 */
	static String FileName="testvalidation";
	static String Username=System.getProperty("user.name");
	public final String Path = "C:\\Users\\"+Username+"\\"+FileName+".xls";
	Map<String, Object[]> articleMapOne;
	String sheet="first";
	public void StoreinExcel_JuniorEngineers() throws Exception {
		String MethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
		try {
			
			int JuniorEnineers_size=driver.findElements(JEng).size();
			for(int i=1;i<=JuniorEnineers_size;i++)
			{
				String JEng_Name=driver.findElement(By.xpath("(//h5[contains(.,'Junior Engineer')]/preceding-sibling::h3[contains(@class,'member-name')])["+i+"]")).getText();
				JEngineers.add(JEng_Name);
			}
			launchflag=true;
			TestNGResults.put(MethodName, new Object[] { MethodName, "Fetch & Write Junior Engineer Names", "Junior Engineer Names written successfully in excel", launchflag });
		}catch(Exception e) {
			launchflag=false;
			TestNGResults.put(MethodName, new Object[] { MethodName, "Fetch & Write Junior Engineer Names", "Junior Engineer Names written successfully in excel", launchflag });
			System.out.println(e);
		}
		
	}
	
	public void LaunchBrowser(String Url) throws InterruptedException
	{
		String MethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
		try {
			TestNGResults = new LinkedHashMap<String, Object[]>();
			articleMapOne= new LinkedHashMap<String, Object[]>();
			TestNGResults.put("", new Object[] { "Test Step", "Action", "Expected Output", "Actual Output" });
			System.setProperty("webdriver.chrome.driver", "C:\\Users\\Murugavel-pc\\Desktop\\eclipse project\\chromedriver_win32_88\\chromedriver.exe");  
			driver=new ChromeDriver();  
			Url="window.open(\""+Url+"\",\"_self\")";
			closeTabs();
			ClearBrowserCache();
			((JavascriptExecutor)driver).executeScript(Url);
			driver.manage().timeouts().pageLoadTimeout(80, TimeUnit.SECONDS);
			driver.manage().window().maximize(); 
			launchflag=true;
			TestNGResults.put(MethodName, new Object[] { MethodName, "Launch Grootan Application", "Grootan Application Launched in Chrome", launchflag });
		}catch(Exception e)
		{
			launchflag=false;
			System.out.println(e);
			TestNGResults.put(MethodName, new Object[] { MethodName, "Launch Grootan Application", "Grootan Application Launched in Chrome", launchflag });
		}
	}
	/**
	 * This Method used to close all other tabs except the current tab.
	 */
	public void closeTabs()
	{
		String HomePage = driver.getWindowHandle();
	    for(String handle : driver.getWindowHandles()) {
	        if (!handle.equals(HomePage)) {
	            driver.switchTo().window(handle);
	            driver.close();
	        }
	    }
	    driver.switchTo().window(HomePage);
	}
	/**
	 * This Method used to clear cookies.
	 */
	public void ClearBrowserCache() throws InterruptedException
	{
		driver.manage().deleteAllCookies();
		Thread.sleep(7000);
	}
	/**
	 * This Method used to capture screenshot and place the same in local.
	 * eg: with respect to Current System User and  current Invocation Count.
	 * if folder is not exist then new folder will be created w.r.t current Invocation Count.
	 */
	public void CaptureScreen(String imagename, int iteration) throws Exception
	{
		String Username=System.getProperty("user.name");
		String FilePath="C:\\Users\\"+Username+"\\folder"+iteration;
		boolean filecreated=false;
		File file=new File(FilePath);
		if(!file.isDirectory())
		{
			filecreated=file.mkdir();
			System.out.println("Folder"+iteration+" Created");
		}else
			filecreated=true;
		if(filecreated) {
			File screenshot=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			try {
				FileUtils.copyFile(screenshot, new File(FilePath+"\\"+imagename+".png"));
				Thread.sleep(2000);
			}catch(Exception e)
			{
				System.out.println(e.getMessage());
			}
		}
	}
	
	public void imageComparison() {
		String MethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
		 try {
//			 driver.findElement(By.xpath("//a[contains(text(),'Team')]")).click();
			 Thread.sleep(5000);
			 WebElement CTO_Image = driver.findElement(CTO_IMG);
			 WebElement HR_Image = driver.findElement(HR_IMG);
			 WebDriverWait wait = new WebDriverWait(driver, 10);
				wait.until(ExpectedConditions.visibilityOf(CTO_Image));
			//BufferedImage ctoImage = ImageIO.read(new File(FileName));
	        Screenshot CTOImageScreenshot = new AShot().takeScreenshot(driver, CTO_Image);
	        BufferedImage actualImage = CTOImageScreenshot.getImage();
	        Screenshot HRImageScreenshot = new AShot().takeScreenshot(driver, HR_Image);
	        BufferedImage expectedImage = HRImageScreenshot.getImage();
	        ImageDiffer imgDiff = new ImageDiffer();
	        ImageDiff diff = imgDiff.makeDiff(actualImage, expectedImage);
//	        Assert.assertTrue(diff.hasDiff(), "Images are Different");
	        if(diff.hasDiff())
	        	TestNGResults.put(MethodName, new Object[] { MethodName, " Compare CTO image & HR Image", "CTO image & HR Image are different", diff.hasDiff() });
	        else
	        	TestNGResults.put(MethodName, new Object[] { MethodName, " Compare CTO image & HR Image", "CTO image & HR Image are different", diff.hasDiff() });
//	        Assert.assertFalse(diff.hasDiff(),"Images are Same");

		 }
		 catch(Exception e) {
			 TestNGResults.put(MethodName, new Object[] { MethodName, " Compare CTO image & HR Image", "CTO image & HR Image are different", e.getMessage() });
			 System.out.println(e.getMessage());
		 }
		}

	/**
	 * This Method used to highlight the element
	 */
	public void HighlightElement(By elementLocator) throws InterruptedException{
		WebElement elementToView=driver.findElement(elementLocator);
		((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();",elementToView);
		((JavascriptExecutor)driver).executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');",elementToView);
		Thread.sleep(2000);
	}
	public void HighlightElement(By.ByXPath elementLocator) throws InterruptedException{
		WebElement elementToView=driver.findElement(elementLocator);
		((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();",elementToView);
		((JavascriptExecutor)driver).executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');",elementToView);
		Thread.sleep(2000);
	}
	public void GrootanTechnology_HomePage_Validation() throws Exception {
		try {
			HighlightElement(GROOTAN_LOGO);
			Thread.sleep(2000);
		}catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
	/**
	 * This method used to click the element and capture the page new loaded.
	 */

	public void ExecuteclickAndCapture(int j) throws Exception {
		boolean staleElement=true,clickFlag=false;
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		String name="";
		String MethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
		try {
			String HomePage = driver.getWindowHandle();
			List<WebElement> MenuOptions = driver.findElements(By.xpath("//a[contains(@class,'st-root-link nav-link')]"));
			menuItems=new ArrayList<String>();
			for(int i=0;i<MenuOptions.size();i++) {
					name = MenuOptions.get(i).getText();
//					System.out.println(name);
					menuItems.add(name);
			}	
			for(String menu:menuItems) {
					
					if(driver.findElements(By.xpath("//a[contains(text(),'"+menu+"')]")).size()>0) 
						
					{
						clickFlag=true;
						HighlightElement(By.xpath("//a[contains(text(),'"+menu+"')]"));
						WebDriverWait wait=new WebDriverWait(driver, 20);
						wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(),'"+menu+"')]")));
						driver.findElement(By.xpath("//a[contains(text(),'"+menu+"')]")).click();
						TestNGResults.put(MethodName +" " + menu, new Object[] { MethodName, menu+ " Menu to be clicked", "Menu Clicked Successfully", clickFlag });
						Set<String> pagearray=driver.getWindowHandles();
						if(pagearray.size()>0) {
							String newwindow=null;
						    for(String window : pagearray) {
						    	newwindow=window;
						    }
						    driver.switchTo().window(newwindow);
						}
					    if(menu.equalsIgnoreCase("TEAM")) {
					    	StoreinExcel_JuniorEngineers();
					    	imageComparison();
					    }
					    	
					    CaptureScreen(menu, j);
					    driver.switchTo().window(HomePage);
					    closeTabs();
					}
					
					}
					staleElement=false;
			}
//			catch(StaleElementReferenceException e) {
//				TestNGResults.put(MethodName, new Object[] { MethodName, name+ " Menu to be clicked", "Menu Clicked Successfully", clickFlag });
//				 staleElement = true;
//				 driver.findElement(By.xpath("//a[contains(.,'"+name+"')]")).click();
//
//			}
			catch(Exception e)
			{
				TestNGResults.put(MethodName, new Object[] { MethodName, name+ " Menu to be clicked", "Menu Clicked Successfully", clickFlag });
				System.out.println(e.getMessage());
			}
		}
	/**
	 * This method used to write user defined output in excel. 
	 * @param FileName
	 * @param SheetName
	 * @param JuniorEng
	 * @param TestReport
	 * @throws Exception
	 */
	public void writeExcel(String FileName,String SheetName,List<String> JuniorEng,Map<String, Object[]> TestReport) throws Exception {
		try {
		File file = new File(Path);
		HSSFWorkbook workbook;
		boolean sheetFlag=false;
	    if (file.exists() == false) {
	        workbook = new HSSFWorkbook();
	    } else {
	        try ( 
	            InputStream is = new FileInputStream(file)) {
	                workbook = new HSSFWorkbook(is);
	            }
	    }
	    HSSFSheet spreadsheet = workbook.getSheet(SheetName);
	    if (workbook.getNumberOfSheets() != 0) {
	        for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
	        
	           if (workbook.getSheetName(i).equals(SheetName)) {
	                spreadsheet = workbook.getSheet(SheetName);
	                sheetFlag=true;
	                break;
	            } 
	           }
	        if(!sheetFlag) {
	        	 spreadsheet = workbook.createSheet(SheetName);
	        }
	    }
	    else {
	        spreadsheet = workbook.createSheet(SheetName);
	    }
//	    spreadsheet = workbook.getSheet(SheetName);

	    if(TestReport==null||TestReport.isEmpty())
		for(int s=0;s<JuniorEng.size();s++)
        {
        	HSSFRow rowhead = spreadsheet.createRow((short)s);
        	rowhead.createCell(0).setCellValue(JuniorEng.get(s));
        	spreadsheet.autoSizeColumn(0);
        }
		if(JuniorEng.size()==0) {
			Set<String> keyset = TestReport.keySet();
			int rownum = 0;
			for (String key : keyset) {
				Row row = spreadsheet.createRow(rownum++);
				Object[] objArr = TestReport.get(key);
				int cellnum = 0;
				for (Object obj : objArr) {
					Cell cell = row.createCell(cellnum++);
					if (obj instanceof Date)
						cell.setCellValue((Date) obj);
					else if (obj instanceof Boolean)
						cell.setCellValue((Boolean) obj);
					else if (obj instanceof String)
						cell.setCellValue((String) obj);
					else if (obj instanceof Double)
						cell.setCellValue((Double) obj);
				}
			}
			spreadsheet.autoSizeColumn(0);
		}
		FileOutputStream  fileOut = new FileOutputStream(file);
            workbook.write(fileOut);
            fileOut.close();
            workbook.close();
        } catch (Exception ex ) {
            System.out.println(ex);
        }
	}
	public void SnapshotComparing() throws Exception {
		String MethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
		try {
			for(int i=0;i<menuItems.size();i++){

		        File fileInput = new File("C:\\Users\\"+Username+"\\folder1\\"+menuItems.get(i)+".png");
		        File fileOutPut = new File("C:\\Users\\"+Username+"\\folder2\\"+menuItems.get(i)+".png");

		        BufferedImage bufferfileInput = ImageIO.read(fileInput);
		        DataBuffer bufferfileInput1 = bufferfileInput.getData().getDataBuffer();
		        int sizefileInput = bufferfileInput1.getSize();                    
		        BufferedImage bufferfileOutPut = ImageIO.read(fileOutPut);
		        DataBuffer datafileOutPut = bufferfileOutPut.getData().getDataBuffer();
		        int sizefileOutPut = datafileOutPut.getSize();
		        Boolean matchFlag = false;
		        if(sizefileInput == sizefileOutPut) {                        
		           for(int j=0; j<sizefileInput; j++) {
		                 if(bufferfileInput1.getElem(j) != datafileOutPut.getElem(j)) {
		                       matchFlag = true;
		                       break;
		                 }
		            }
		        }
		        else {                          
		           matchFlag = true;
		        }
//		        Assert.assertTrue(!matchFlag);   
		        TestNGResults.put(MethodName + "" + "\\folder1\\"+menuItems.get(i)+".png" + " and " + "\\folder2\\"+menuItems.get(i)+".png", new Object[] { MethodName, "\\folder1\\"+menuItems.get(i)+".png" + " and " + "\\folder2\\"+menuItems.get(i)+".png", "Snapshot Comparison matches", matchFlag });
		     }
		}catch(Exception e) {
			 TestNGResults.put(MethodName , new Object[] { MethodName, "Snapshot Comparison", "Snapshot Comparison matches", false });
		}
		
	}
	public ConfigurationSettings() {
		// TODO Auto-generated constructor stub‪
	}
	@AfterTest
	public void Report() throws Exception 
	{
		writeExcel(Path,"Junior Engineers",JEngineers,articleMapOne);
		writeExcel(Path,"TSR",Emptylist,TestNGResults);
//		SnapshotComparing();
		driver.quit();
	}
	@AfterMethod
	public void QuitBrowser() throws Exception 
	{
//		writeExcel(Path,"Junior Engineers",JEngineers,articleMapOne);
//		writeExcel(Path,"TSR",Emptylist,TestNGResults);
		driver.quit();
	}

	
}
