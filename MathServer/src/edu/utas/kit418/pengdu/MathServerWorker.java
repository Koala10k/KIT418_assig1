package edu.utas.kit418.pengdu;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.util.List;

import javax.swing.SwingWorker;

public class MathServerWorker extends SwingWorker<Void, String> {

	private Socket client;
	private DataInputStream din;
	private DataOutputStream dout;
	private Functionality calculator;
	private boolean stopping = false;
	private String clientIP;

	public MathServerWorker(Socket s) throws IOException {
		client = s;
		calculator = new Functionality();
		din = new DataInputStream(client.getInputStream());
		dout = new DataOutputStream(client.getOutputStream());
		clientIP = "Client("+client.getRemoteSocketAddress().toString()+")";
	}

	@Override 
	protected Void doInBackground() throws Exception {
		String query;
		try {
			while (true) {
				query = din.readUTF();
				if (query.equals(MathServer.stopCode)) {
					stopping = true;
					break;
				}
				String[] parts = query.split(",");
				if (parts.length != 2) {
					dout.writeUTF("server: query parsing failed...");
				} else {
					publish("Processing: <operation> is " + parts[0]
							+ ", <value> is " + parts[1]);
					String resp = calculator.calculate(parts[0], parts[1]);
					publish("Responsing: <result> is " + resp);
					dout.writeUTF(resp);
				}
			}
		} catch (EOFException eof) {
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (client != null) {
				try {
					client.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	@Override
	protected void done() {
		if (stopping)
			return;
		StringBuilder sb = new StringBuilder();
		sb.append(clientIP + " disconnected...");
		sb.append(MathServer.newline);
		MathServer.printInfo(sb.toString());
	}

	@Override
	protected void process(List<String> chunks) {
		for (String msg : chunks) {
			MathServer.printInfo(clientIP+": "+msg);
		}
	}

}
