package assignment5ir.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import assignment5ir.DatabaseConnector5;
import assignment5ir.IrParser;
import common.NlpFileReader;
import common.Parser;

public class ScanListener extends Thread implements ActionListener {
	
	private MainFrame frame;
	
	public ScanListener(MainFrame frame) {
		this.frame = frame;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		this.start();
	}

	@Override
	public void run() {
		String packageName = "assignment5ir";
		
		File file = new File(packageName + ".db");
		if (file.exists()) {
			file.delete();
		}
		try {
			file.createNewFile();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		DatabaseConnector5 db = new DatabaseConnector5(packageName);
		try {
			db.openConnection();
			db.recreateDatabase();
			db.closeConnection();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		Parser parser = new IrParser(packageName);
		NlpFileReader reader = new NlpFileReader(parser, frame.getFilepath());
		reader.setNotifier(frame);
		frame.showLoading();
		reader.start();
		synchronized(frame) {
			try {
				frame.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		frame.hideLoading();
		frame.setBtnScanListener(new ScanListener(frame));
	}
	
}
