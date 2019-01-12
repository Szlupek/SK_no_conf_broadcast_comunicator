import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPServer {

	public UDPServer() throws Exception {

		int it = 0;
		int ok = 0;
		while (it < 5 && ok == 0) {
			try {
				DatagramSocket datagramSocket = new DatagramSocket(Config.PORT + it);
				datagramSocket.close();
				ok = 1;
				Config.PORT = Config.PORT + it;
			} catch (Exception e) {
				it++;
			}
		}
		if (it > 10) {
			System.out.println("błąd otwarcia portu!!");
		} else if (ok == 1) {
			DatagramSocket datagramSocket = new DatagramSocket(Config.PORT);

			System.out.println("Used port -" + Config.PORT);

			byte[] byteResponse = "otrzymano".getBytes("utf8");

			while (Config.close == 0) {

				DatagramPacket reclievedPacket = new DatagramPacket(new byte[Config.BUFFER_SIZE], Config.BUFFER_SIZE);

				datagramSocket.receive(reclievedPacket);

				int length = reclievedPacket.getLength();
				String message = new String(reclievedPacket.getData(), 0, length, "utf8");

				InetAddress address = reclievedPacket.getAddress();
				int port = reclievedPacket.getPort();
				checkMessage(message, address.getHostAddress());

				// System.out.println(message + " " + address + " " + port);
				Thread.sleep(100);
				DatagramPacket response = new DatagramPacket(byteResponse, byteResponse.length, address, port);
				try {
					datagramSocket.send(response);
				} catch (Exception e) {
				}
			}
			datagramSocket.close();
		}
	}

	static void addUser(String IP, String name, int port) {
		UsersList.addUser(IP, name, port);
	}

	void checkMessage(String messege, String IP) throws IOException {
		char firstChar = messege.charAt(0);
		String name;
		int port;
		switch (firstChar) {

		case 'a':
			name = messege.substring(5, messege.length());
			port = Integer.parseInt(messege.substring(1, 5));
			if (!UsersList.checkIfEg(IP, port)) {
				addUser(IP, name, port);
				System.out.println("Added - " + messege.substring(5, messege.length()));
				// Window.printl("Added - " + messege.substring(5, messege.length()));
			} else {
				UsersList.replaceUser(IP, name, port);
			}

			break;
		case 's':
			name = messege.substring(5, messege.length());
			port = Integer.parseInt(messege.substring(1, 5));
			if (!UsersList.checkIfEg(IP, port)) {
				addUser(IP, name, port);
				System.out.println("Added - " + messege.substring(5, messege.length()));
				// Window.printl("Added - " + messege.substring(5, messege.length()));
			} else {
				UsersList.replaceUser(IP, name, port);
			}
			UDPClient.introduce(IP, port);

			break;
		case 'm':
			System.out.println("[" + UsersList.getNameFromIPAndPort(IP, Integer.parseInt(messege.substring(1, 5)))
					+ "] " + messege.substring(5, messege.length()));
			Window.printl("[" + UsersList.getNameFromIPAndPort(IP, Integer.parseInt(messege.substring(1, 5))) + "] "
					+ messege.substring(5, messege.length()));
			break;
		default:
			break;
		}

	}

}
