import java.io.IOException;
import java.util.ArrayList;

public class UsersList {

	static ArrayList<User> list = new ArrayList<User>();

	public UsersList() {
	}

	static void printList() {
		for (int ii = 0; ii < list.size(); ii++) {
			System.out.println(ii + " " +list.get(ii).name + " " + list.get(ii).IP + " " + list.get(ii).port);
		}
	}
	
	static void removeAll() throws IOException {
		list = new ArrayList<User>();
	}

	static String getNameFromIP(String gIP) {
		String name = "unknown";
		for (int ii = 0; ii < list.size(); ii++) {
			if (gIP.equals(list.get(ii).IP))
				name = list.get(ii).name;
		}
		return name;
	}

	static String getNameFromIPAndPort(String gIP, int port) {
		String name = "unknown";
		for (int ii = 0; ii < list.size(); ii++) {
			if (gIP.equals(list.get(ii).IP) && port == list.get(ii).port)
				name = list.get(ii).name;
		}
		return name;
	}

	static boolean checkIfEg(String gIP, int gPort) {
		boolean eg = false;
		for (int ii = 0; ii < list.size(); ii++) {
			if (gIP.equals(list.get(ii).IP) && gPort == list.get(ii).port)
				eg = true;
		}
		return eg;
	}

	static void addUser(String gIP, String gName, int gPort) {
		int it = 1;
		String name = gName;
		boolean ok = false;
		while (!ok) {
			ok = true;
			for (int ii = 0; ii < list.size(); ii++) {
				if (name.equals(list.get(ii).name)) {
					name = gName + it;
					ok = false;
				}
			}
			it++;
		}
		list.add(new User(gIP, name, gPort));
		Window.adresses.addItem(name);
	}

	static void replaceUser(String gIP, String gName, int gPort) {
		for (int ii = 0; ii < list.size(); ii++) {
			if (gIP.equals(list.get(ii).IP) && gPort == list.get(ii).port && !gName.equals(list.get(ii).name)) {
				System.out.println("User - " + list.get(ii).name + " changed name to - " + gName);
				Window.printl("User - " + list.get(ii).name + " changed name to - " + gName);
				list.set(ii, new User(gIP, gName, gPort));
				Window.refreshCombo();
			}
		}
	}

}
