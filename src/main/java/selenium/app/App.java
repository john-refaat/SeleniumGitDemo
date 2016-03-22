package selenium.app;

import java.util.Scanner;

import selenium.demo.GitHubTester;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {

		Scanner scanner = new Scanner(System.in);
		System.out.println("Username: ");
		String username = scanner.nextLine();

		System.out.println("Password: ");
		String password = scanner.nextLine();

		System.out.println("Repository URL: ");
		String url = scanner.nextLine();

		GitHubTester tester = new GitHubTester(username, password, url);
		tester.testFork();
	}
}
