import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Window extends JFrame {

	static JTextField textField;
	static JTextArea textArea;
	static JComboBox<String> adresses;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Window() {
		setTitle("0_Conf");
		setSize(300, 400);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		setMinimumSize(new Dimension(200, 100));
		this.setLayout(new BorderLayout());
		JPanel NPanel = new JPanel();
		JPanel SPanel = new JPanel();
		JPanel CPanel = new JPanel();
		this.add(NPanel, BorderLayout.NORTH);
		this.add(SPanel, BorderLayout.SOUTH);
		this.add(CPanel, BorderLayout.CENTER);

		adresses = new JComboBox<String>();
		JButton rButton = new JButton("Refresh");

		JLabel label = new JLabel("Type your messege:");
		textField = new JTextField();
		textField.setBackground(Color.black);
		textField.setForeground(Color.white);
		textField.setSelectionColor(Color.PINK);
		textField.setSize(4000, 50);
		textArea = new JTextArea("***Witaj " + Config.login+"***");
		textArea.setBackground(Color.PINK);

		NPanel.setLayout(new GridLayout(1, 2));
		SPanel.setLayout(new GridLayout(1, 2));
		CPanel.setLayout(new GridLayout(1, 1));

		textArea.setEditable(false);
		textField.addActionListener(write);

		JScrollPane scroll = new JScrollPane(textArea);
		CPanel.add(scroll);
		rButton.addActionListener(refresh);
		NPanel.add(rButton);
		NPanel.add(adresses);
		SPanel.add(label);
		SPanel.add(textField);

	}

	ActionListener write = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			// textArea.append("\n" + textField.getText());

			try {
				sendMessage(UsersList.list.get(adresses.getSelectedIndex()).IP,
						UsersList.list.get(adresses.getSelectedIndex()).port, textField.getText());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			textField.setText("");

		}
	};
	ActionListener refresh = new ActionListener() {

	
		@Override
		public void actionPerformed(ActionEvent e) {
			adresses.removeAllItems();
			try {
				UsersList.removeAll();
			} catch (IOException e2) {
				e2.printStackTrace();
			}
			for (int ii = 0; ii < 5; ii++) {
				try {
					scream(9000 + ii);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}

		}
	};

	static void printl(String text) {
		textArea.append("\n" + text);
	}

	static void refreshCombo() {
		adresses.removeAllItems();
		for (int ii = 0; ii < UsersList.list.size(); ii++) {
			adresses.addItem(UsersList.list.get(ii).name);
		}
	}

	void sendMessage(String IP, int port, String text) throws IOException {
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
				Window.printl("***wiadomość nie dotarła***");

			}
			socket.close();
		}
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

}
