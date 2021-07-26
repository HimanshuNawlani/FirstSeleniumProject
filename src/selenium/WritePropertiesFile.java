package selenium;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

public class WritePropertiesFile {

	public static void main(String[] args) {

		Properties prop = new Properties();
		OutputStream writeFile = null;

		try {
			writeFile = new FileOutputStream("configure.properties");

			prop.setProperty("chrome_driver", "webdriver.chrome.driver");
			prop.setProperty("chromeDriverLocation", "chromedriver.exe");

			prop.setProperty("gecko_driver", "webdriver.gecko.driver");
			prop.setProperty("geckoDriverLocation", "geckodriver.exe");

			prop.setProperty("url", "https://www.snapdeal.com/");
			

			prop.store(writeFile, "Web Driver Properties and URL");
			System.out.println("Created Properties Successfully");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (writeFile != null) {
				try {
					writeFile.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

}
