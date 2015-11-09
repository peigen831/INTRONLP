package assignment5ir.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SearchListener implements ActionListener {
	
	private MainFrame frame;
	
	public SearchListener(MainFrame frame) {
		this.frame = frame;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		String text = frame.getSearchText();
		boolean activity1 = frame.getActivity1();
		boolean activity2 = frame.getActivity2();
		boolean activity3 = frame.getActivity3();
		
		// TODO
	}
	
}
