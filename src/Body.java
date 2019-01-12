import java.util.Scanner;

public class Body {

	Scanner scanner;

	public Body() throws Exception {

		scanner = new Scanner(System.in);
		hello();
		Window okno = new Window();

		TServer tServer = new TServer();
		TClient tClient = new TClient(); // niepotrzebny (obsluga terminalu)

		tServer.start();
		tClient.start();
	}

	public void hello() {
		System.out.println("************");
		System.out.println("0 Conf Chat");
		System.out.println("by MS and AB");
		System.out.println("************\n");
		System.out.println("type your login:");
		while (Config.login.equals("non") || Config.login.equals("")) {
			Config.login = scanner.nextLine();
		}
		System.out.println("your login is: " + Config.login);
	}
}
