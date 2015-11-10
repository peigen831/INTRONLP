package assignment5ir.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import assignment5ir.InformationRetrievalModel;

public class SearchListener extends Thread implements ActionListener {
	
	private MainFrame frame;
	
	public SearchListener(MainFrame frame) {
		this.frame = frame;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		this.start();
	}

	@Override
	public void run() {
		String text = frame.getSearchText();
		boolean activity1 = frame.getActivity1();
		boolean activity2 = frame.getActivity2();
		boolean activity3 = frame.getActivity3();
		
		SearchFrame sframe = new SearchFrame();
		frame.showLoading();
		
		if (activity1) {
			try {
				sframe.addSearchResultPanel(1, InformationRetrievalModel.noRanking(text));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (activity2) {
			try {
				sframe.addSearchResultPanel(2, InformationRetrievalModel.tfRanking(text));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (activity3) {
			try {
				sframe.addSearchResultPanel(3, InformationRetrievalModel.tdIdfRanking(text));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		frame.hideLoading();
		frame.setBtnSearchListener(new SearchListener(frame));
	}
	
}
