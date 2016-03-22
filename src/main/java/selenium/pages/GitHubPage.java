package selenium.pages;

import java.text.MessageFormat;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;

public class GitHubPage {

	private WebDriver driver;

	private By signInLinkLocator = new By.ByLinkText("Sign in");
	private By loginFieldLocator = By.id("login_field");
	private By passwordFieldLocator = By.id("password");
	private By forkButtonLocator = By.xpath("//ul/li[3]/form/button");
	// private By folderLocator =
	// By.xpath("//tr[@class='js-navigation-item']//td[@class='content']//a");

	private String folderXPath = "//tr[contains(@class,\"js-navigation-item\")][{0}]//td[@class=\"content\"]//a";
	private String iconXPath = "//tr[contains(@class,\"js-navigation-item\")][{0}]//td[@class=\"icon\"]//*[name() = \"svg\"]";

	private By signInButtonLocator = By.name("commit");

	public GitHubPage(WebDriver driver) {
		this.driver = driver;
	}

	public void signIn(String username, String password) {
		driver.findElement(signInLinkLocator).click();
		driver.findElement(loginFieldLocator).sendKeys(username);
		driver.findElement(passwordFieldLocator).sendKeys(password);
		driver.findElement(signInButtonLocator).click();
		driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);

	}

	public void fork() {
		driver.findElement(forkButtonLocator).click();
		driver.manage().timeouts().pageLoadTimeout(10000, TimeUnit.MILLISECONDS);
	}

	public void getStructure() {
		MessageFormat mf = new MessageFormat(folderXPath);
		MessageFormat mfIcon = new MessageFormat(iconXPath);

		int i = 0;
		while (isElementAvailable(By.xpath(mf.format(new Object[] { ++i })))) {
			String iconClass = driver.findElement(By.xpath(mfIcon.format(new Object[] { i }))).getAttribute("class");
			getStructure(iconClass, By.xpath(mf.format(new Object[] { i })), 0);

		}

	}

	private void getStructure(String iconClass, By locator, int level) {

		for (int i = 0; i < level; i++) {
			System.out.print("  ");
		}
		System.out.println(((level==0)?"":"-->") + driver.findElement(locator).getText());
		if (iconClass.contains("directory")) {
			driver.findElement(locator).click();

			waiting(3);
			// driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

			MessageFormat mf = new MessageFormat(folderXPath);
			MessageFormat mfIcon = new MessageFormat(iconXPath);

			int i = 0;
			while (isElementAvailable(By.xpath(mf.format(new Object[] { ++i })))) {
				String icon = driver.findElement(By.xpath(mfIcon.format(new Object[] { i }))).getAttribute("class");
				getStructure(icon, By.xpath(mf.format(new Object[] { i })), level + 1);
			}

			driver.navigate().back();
		}

	}

	public boolean isElementAvailable(By locator) {
		boolean available = true;
		try {
			driver.findElement(locator);
		} catch (NoSuchElementException e) {
			available = false;
		}
		return available;
	}

	private void waiting(int seconds) {
		try {
			Thread.sleep(seconds * 1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
