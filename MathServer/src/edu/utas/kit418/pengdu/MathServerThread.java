package edu.utas.kit418.pengdu;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.List;

import javax.swing.SwingWorker;

public class MathServerThread extends SwingWorker<Void, Void>{

	private boolean running;
	private StatusListener statusLisnter;
	public interface StatusListener {
		void statusChanged(boolean state);
	}
	
	
	@Override
	protected void done() {
		MathServer.printInfo("MathServer stopped");
	}
	
	@Override
	protected void process(List<Void> chunks) {
		if(statusLisnter != null) statusLisnter.statusChanged(running);
	}

	@Override
	protected Void doInBackground() throws Exception {
		ServerSocket s = null;
		try {
			s = new ServerSocket(MathServer.listeningPort);
			running = true;
			MathServer.printInfo("Math Server is listening on port:" + MathServer.listeningPort);
			publish();
			while (running) {
				new MathServerWorker(s.accept()).execute();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally{
			publish();
			try {
				if(s!=null)
				s.close();	
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public void setServerStateListener(StatusListener listener) {
		statusLisnter = listener;
	}
	
	public void attemptStopService(){
		running = false;
	}
	
}