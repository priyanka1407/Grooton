package grooton;

import org.testng.annotations.Test;

public class HomePageValidation extends ConfigurationSettings {
	private String BaseUrl="https://www.grootan.com";
	private static int counter=1;
	
  @Test(invocationCount = 2)
  public void GrootanTechnologyMenuValidation() throws Exception {
	  LaunchBrowser(BaseUrl);
	  CaptureScreen("HomePage",counter);
	  GrootanTechnology_HomePage_Validation();
	  ExecuteclickAndCapture(counter);
	  if(counter==2)
		  SnapshotComparing();
	  counter++;
  }
}