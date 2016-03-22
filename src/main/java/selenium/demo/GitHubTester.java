package selenium.demo;

import org.openqa.selenium.firefox.FirefoxDriver;

import selenium.pages.GitHubPage;

public class GitHubTester {

	private String repositoryURL;
	private String username;
	private String password;

	private GitHubPage gitHubPage;
	
	public GitHubTester(String username, String password, String repositoryName) {
		super();
		this.repositoryURL = repositoryName;
		this.username = username;
		this.password = password;
	}

	
	public void testFork() {
		if(!repositoryURL.contains("github.com")) {
			throw new IllegalArgumentException("Invalid URL");
		}
		FirefoxDriver driver = new FirefoxDriver();
		driver.get(repositoryURL);
		gitHubPage  = new GitHubPage(driver);
		
		gitHubPage.signIn(username, password);
		gitHubPage.fork();
		gitHubPage.getStructure();
		
		// Close the driver
		driver.quit();
	}
	
}
