package assignment5ir.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import assignment5ir.IrParser;
import common.Parser;
import common.XmlReader;

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
		Parser parser = new IrParser();
		XmlReader reader = new XmlReader(parser, frame.getFilepath());
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
