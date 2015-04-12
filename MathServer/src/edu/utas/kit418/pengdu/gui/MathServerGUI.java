package edu.utas.kit418.pengdu.gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.GroupLayout.SequentialGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import edu.utas.kit418.pengdu.MathServer;
import edu.utas.kit418.pengdu.MathServerThread.StatusListener;

public class MathServerGUI extends JFrame implements StatusListener {

	private static final long serialVersionUID = 1L;
	private JTextArea logPane;
	private JScrollPane jScrollPane;
	private JPanel controlPane;
	private JButton jBtnClearLog;
	private JButton jBtnStartServer;
	private JButton jBtnStopServer;
	private JSplitPane jSplit;
	private JTextField jTxtPort;
	private JLabel jLblPort;

	public MathServerGUI() {
		setTitle("MathServer GUI");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		createComponents();
		performLayout();
	}

	private void createComponents() {

		logPane = new JTextArea();
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		logPane.setLineWrap(true);
		logPane.setWrapStyleWord(true);
		logPane.setEditable(false);

		jScrollPane = new JScrollPane(logPane);
		jScrollPane.setBorder(BorderFactory
				.createTitledBorder("Math Server Log"));

		controlPane = new JPanel();
		controlPane.setBorder(BorderFactory.createTitledBorder("Options"));

		jBtnClearLog = new JButton("Clear Log");
		jBtnStartServer = new JButton("Start Server");
		jBtnStopServer = new JButton("Stop Server");
		jLblPort = new JLabel("Port:");
		jTxtPort = new JTextField();

		jBtnClearLog.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				logPane.setText("");
			}
		});

		jBtnStartServer.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (jTxtPort.getText().matches(MathServer.PORT_PATTERN)) {
					MathServer.startServer(Integer.parseInt(jTxtPort.getText()));
				} else {
					JOptionPane.showMessageDialog(MathServerGUI.this,
						    "Error: <port> must be a integer ranging from 1024 to 65535",
						    "Invalid port",
						    JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		jBtnStopServer.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				MathServer.stopServer();
			}
		});

		jSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, jScrollPane,
				controlPane);
		jSplit.setOneTouchExpandable(true);
		jSplit.setDividerLocation(1000);
	}

	private void performLayout() {
		Dimension screenSize = java.awt.Toolkit.getDefaultToolkit()
				.getScreenSize();
		setLocation((int) screenSize.getWidth() / 4,
				(int) screenSize.getHeight() / 4);

		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setAutoCreateContainerGaps(true);
		layout.setAutoCreateGaps(true);

		SequentialGroup h1 = layout.createSequentialGroup();
		h1.addComponent(jSplit, GroupLayout.DEFAULT_SIZE,
				(int) screenSize.getWidth() / 2, Short.MAX_VALUE);
		layout.setHorizontalGroup(h1);

		ParallelGroup vGroup = layout
				.createParallelGroup(GroupLayout.Alignment.LEADING);
		vGroup.addComponent(jSplit, GroupLayout.DEFAULT_SIZE,
				(int) screenSize.getHeight() / 2, Short.MAX_VALUE);
		layout.setVerticalGroup(vGroup);

		GroupLayout optLayout = new GroupLayout(controlPane);
		controlPane.setLayout(optLayout);
		optLayout.setAutoCreateContainerGaps(true);
		optLayout.setAutoCreateGaps(true);
		optLayout.setHorizontalGroup(optLayout
				.createParallelGroup(GroupLayout.Alignment.CENTER)
				.addComponent(jBtnClearLog, GroupLayout.DEFAULT_SIZE,
						GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE)
				.addComponent(jBtnStartServer, GroupLayout.DEFAULT_SIZE,
						GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE)
				.addComponent(jBtnStopServer, GroupLayout.DEFAULT_SIZE,
						GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE)
				.addGroup(
						optLayout
								.createSequentialGroup()
								.addComponent(jLblPort,
										GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE,
										Short.MAX_VALUE)
								.addComponent(jTxtPort,
										GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE,
										Short.MAX_VALUE)));
		optLayout.setVerticalGroup(optLayout
				.createSequentialGroup()
				.addComponent(jBtnClearLog)
				.addComponent(jBtnStartServer)
				.addComponent(jBtnStopServer)
				.addGroup(
						optLayout
								.createParallelGroup(
										GroupLayout.Alignment.BASELINE)
								.addComponent(jLblPort,
										GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE,
										Short.MAX_VALUE)
								.addComponent(jTxtPort,
										GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE,
										Short.MAX_VALUE)));

		pack();
	}

	public static void main(String[] args) {
		JFrame mathServer = new MathServerGUI();
		mathServer.setVisible(true);
	}

	public void log(String info) {
		logPane.append(info);
	}

	@Override
	public void statusChanged(boolean state) {
		jBtnStartServer.setEnabled(!state);
		jBtnStopServer.setEnabled(state);
		jTxtPort.setText(String.valueOf(MathServer.listeningPort));
		jTxtPort.setEditable(!state);
	}
}
