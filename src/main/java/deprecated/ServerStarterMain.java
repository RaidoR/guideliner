package deprecated;

import java.io.File;
import java.io.IOException;

import org.openqa.selenium.chrome.ChromeDriverService;

public class ServerStarterMain {

	public static void main(String[] args) throws IOException {
		ChromeDriverService service = new ChromeDriverService.Builder()
        .usingDriverExecutable(new File("C:\\Users\\jevgeni.marenkov\\Desktop\\yli\\chrome\\chromedriver.exe"))
        .usingAnyFreePort()
        .build();
		System.out.println(service.getUrl().toString());
		service.start();
	}
	
}
