
public class TServer extends Thread {

	public TServer() {
	}

	public void run() {
		try {
			UDPServer server = new UDPServer();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
