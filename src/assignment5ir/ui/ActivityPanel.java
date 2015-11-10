package assignment5ir.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.text.html.HTMLEditorKit;

@SuppressWarnings("serial")
public class ActivityPanel extends JPanel {
	private JLabel lblActivity;
	private JScrollPane scrollPane;
	private JEditorPane txtpnResult;
	
	public ActivityPanel() {
		setLayout(new BorderLayout(0, 0));
		setSize(new Dimension(450, 600));
		setPreferredSize(new Dimension(450, 600));
		
		lblActivity = new JLabel("Activity");
		lblActivity.setFont(new Font("Tahoma", Font.PLAIN, 14));
		add(lblActivity, BorderLayout.NORTH);
		
		JPanel panel = new JPanel();
		add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_1 = new JPanel();
		panel_1.setSize(new Dimension(1, 3));
		panel_1.setPreferredSize(new Dimension(1, 3));
		panel.add(panel_1, BorderLayout.NORTH);
		
		scrollPane = new JScrollPane();
		panel.add(scrollPane, BorderLayout.CENTER);
		
		txtpnResult = new JEditorPane();
		txtpnResult.setEditable(false);
		HTMLEditorKit kit = new HTMLEditorKit();
		txtpnResult.setEditorKit(kit);
		scrollPane.setViewportView(txtpnResult);

	}
	
	public int getActivityNumber() {
		return Integer.parseInt(lblActivity.getText().split(" ")[1]);
	}
	
	public void setActivityNumber(int number) {
		lblActivity.setText("Activity " + number);
	}
	
	public void setResult(ArrayList<String> fileList) throws IOException {
		String html = "<html><body>";
		for (String filepath : fileList) {
			File file = new File(filepath);
			String filename = file.getName();
			String filetext = "";
			
			BufferedReader reader = new BufferedReader(new FileReader(file));
			for (int i = 0; i < 11; i++) {
				filetext += reader.readLine();
				System.out.println(filetext);
			}
			reader.close();
			
			filetext = filetext.replaceAll("<(/)*[A-Za-z0-9 ?\".=-]+>", " ");
			filetext = filetext.replaceAll("(\t)+|(  )+", " ");
			filetext = filetext.replaceAll("(\t)+|(  )+", " ");
			filetext = filetext.replaceAll("(\t)+|(  )+", " ");
			filetext = filetext.trim();
			String[] textArray = filetext.split(" ");
			
			String finaltext = "";
			for (int i = 0; i < 40 && i < textArray.length; i++) {
				finaltext += textArray[i] + " ";
			}
			System.out.println(filetext);
			
			html += "<p><strong>" + filename + "</strong><br />";
			html += "<u>" + filepath + "</u><br />";
			html += finaltext.trim() + "...</p>";
		}
		html += "</body></html>";
		
		txtpnResult.setText(html);
	}
}
