import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Scanner;

public class UDPClient {

	public UDPClient() throws IOException {
		Scanner scan = new Scanner(System.in);
		help();
		scream(Config.PORT);
		for(int ii =9000;ii<9005;ii++)scream(ii);
		while (Config.close == 0) {
			String typ = scan.nextLine();
			switch (typ) {
			case "s":
				for (int ii = 0; ii < 5; ii++) {
					scream(9000 + ii);
				}
				break;
			case "m":
				System.out.println("To? (number)");
				UsersList.printList();
				try {
					int num = Integer.parseInt(scan.nextLine());
					sendMessage(UsersList.list.get(num).IP, UsersList.list.get(num).port);
				} catch (Exception e) {
				}
				break;
			case "h":
				help();
				break;
			case "l":
				UsersList.printList();
				break;
			case "r":
				refresh();
				break;
			case "q":
				Config.close = 1;
				break;
			}
		}
		scan.close();

	}

	void introduce() throws IOException {
		String option = "a";

		String message = option + Config.PORT + Config.login;
		InetAddress serverAddress = InetAddress.getByName("localhost");
		DatagramSocket socket = new DatagramSocket();
		byte[] stringContents = message.getBytes("utf8");
		DatagramPacket sentPacket = new DatagramPacket(stringContents, stringContents.length);
		sentPacket.setAddress(serverAddress);
		sentPacket.setPort(Config.PORT);
		socket.send(sentPacket);

		socket.close();
	}

	static void introduce(String IP, int Port) throws IOException {
		String option = "a";

		String message = option + Config.PORT + Config.login;
		InetAddress serverAddress = InetAddress.getByName(IP);
		DatagramSocket socket = new DatagramSocket();
		byte[] stringContents = message.getBytes("utf8");
		DatagramPacket sentPacket = new DatagramPacket(stringContents, stringContents.length);
		sentPacket.setAddress(serverAddress);
		sentPacket.setPort(Port);
		socket.send(sentPacket);
		socket.close();
	}

	void scream() throws IOException {
		String option = "s";
		String message = option + Config.PORT + Config.login;
		InetAddress serverAddress = InetAddress.getByName("255.255.255.255");
		DatagramSocket socket = new DatagramSocket();
		byte[] stringContents = message.getBytes("utf8");
		DatagramPacket sentPacket = new DatagramPacket(stringContents, stringContents.length);
		sentPacket.setAddress(serverAddress);
		sentPacket.setPort(Config.PORT);
		socket.send(sentPacket);
		socket.close();
	}

	void scream(int port) throws IOException {
		String option = "s";
		String message = option + Config.PORT + Config.login;
		InetAddress serverAddress = InetAddress.getByName("255.255.255.255");
		DatagramSocket socket = new DatagramSocket();
		byte[] stringContents = message.getBytes("utf8");
		DatagramPacket sentPacket = new DatagramPacket(stringContents, stringContents.length);
		sentPacket.setAddress(serverAddress);
		sentPacket.setPort(port);
		socket.send(sentPacket);
		socket.close();
	}

	void sendMessage() throws IOException {
		Scanner scan1 = new Scanner(System.in);
		String text = scan1.nextLine();
		if (!text.equals("")) {
			Window.printl(text);
			String message = "m" + Config.PORT + text;
			InetAddress serverAddress = InetAddress.getByName("localhost");
			DatagramSocket socket = new DatagramSocket();
			byte[] stringContents = message.getBytes("utf8");
			DatagramPacket sentPacket = new DatagramPacket(stringContents, stringContents.length);
			sentPacket.setAddress(serverAddress);
			sentPacket.setPort(Config.PORT);
			socket.send(sentPacket);
			socket.close();
		}
	}

	 void sendMessage(String IP, int port) throws IOException {
		Scanner scan1 = new Scanner(System.in);
		String text = scan1.nextLine();
		if (!text.equals("")) {
			Window.printl(text);
			String message = "m" + Config.PORT + text;
			InetAddress serverAddress = InetAddress.getByName(IP);
			DatagramSocket socket = new DatagramSocket();
			byte[] stringContents = message.getBytes("utf8");
			DatagramPacket sentPacket = new DatagramPacket(stringContents, stringContents.length);
			sentPacket.setAddress(serverAddress);
			sentPacket.setPort(port);
			socket.send(sentPacket);
			DatagramPacket recievePacket = new DatagramPacket(new byte[Config.BUFFER_SIZE], Config.BUFFER_SIZE);
			socket.setSoTimeout(1010);

			try {
				socket.receive(recievePacket);
				// System.out.println("Serwer otrzymał wiadomość");
			} catch (SocketTimeoutException ste) {
				System.out.println("wiadomość nie dotarła");

			}
			socket.close();
		}
	}

	 
	static void sendMessage(String IP, int port, String text) throws IOException {
		if (!text.equals("")) {
			Window.printl(text);
			String message = "m" + Config.PORT + text;
			InetAddress serverAddress = InetAddress.getByName(IP);
			DatagramSocket socket = new DatagramSocket();
			byte[] stringContents = message.getBytes("utf8");
			DatagramPacket sentPacket = new DatagramPacket(stringContents, stringContents.length);
			sentPacket.setAddress(serverAddress);
			sentPacket.setPort(port);
			socket.send(sentPacket);
			DatagramPacket recievePacket = new DatagramPacket(new byte[Config.BUFFER_SIZE], Config.BUFFER_SIZE);
			socket.setSoTimeout(1010);

			try {
				socket.receive(recievePacket);
				// System.out.println("Serwer otrzymał wiadomość");
			} catch (SocketTimeoutException ste) {
				System.out.println("wiadomość nie dotarła");

			}
			socket.close();
		}
	}
	 
	 
	 
	void refresh() throws IOException {
		UsersList.removeAll();
		for (int ii = 0; ii < 5; ii++) {
			scream(9000 + ii);
		}
	}

	void help() {

		
		System.out.println("Type: - s for serch");
		System.out.println("      - m for messege");
		System.out.println("      - l for list of users");
		System.out.println("      - r for refresh list");
		System.out.println("      - h for help");

	}
}