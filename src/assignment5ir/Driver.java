package assignment5ir;

import assignment5ir.ui.BrowseListener;
import assignment5ir.ui.MainFrame;
import assignment5ir.ui.ScanListener;
import assignment5ir.ui.SearchListener;

public class Driver {
	
	public static void main(String[] args) {
		MainFrame frame = new MainFrame();
		frame.setBtnScanListener(new ScanListener(frame));
		frame.setBtnBrowseListener(new BrowseListener(frame));
		frame.setBtnSearchListener(new SearchListener(frame));
	}
}
