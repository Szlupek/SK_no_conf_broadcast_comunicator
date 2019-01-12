import java.io.IOException;

public class TClient extends Thread {

	public TClient() {
		// TODO Auto-generated constructor stub
	}

	public void run() {

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			UDPClient client = new UDPClient();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
