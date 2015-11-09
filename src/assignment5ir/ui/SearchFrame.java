package assignment5ir.ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class SearchFrame extends JFrame {

	private JPanel contentPane;
	private JTextField txtSearch;
	private ArrayList<ActivityPanel> activityPanels;
	private JPanel panelArea;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SearchFrame frame = new SearchFrame();
					frame.setVisible(true);
					
					ArrayList<String> array = new ArrayList<>();
					array.add("C:\\Users\\user1\\Downloads\\INTRONLP\\assignment4\\TheThreeArticles.xml");
					frame.addSearchResultPanel(1, array);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public SearchFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		panelArea = new JPanel();
		contentPane.add(panelArea, BorderLayout.CENTER);
		panelArea.setLayout(new BorderLayout(0, 0));
		
		txtSearch = new JTextField();
		txtSearch.setHorizontalAlignment(SwingConstants.CENTER);
		txtSearch.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtSearch.setEnabled(false);
		contentPane.add(txtSearch, BorderLayout.NORTH);
		txtSearch.setColumns(10);
		setVisible(true);
		
		activityPanels = new ArrayList<>();
	}
	
	public void addSearchResultPanel(int activityNumber, ArrayList<String> filepaths) throws IOException {
		ActivityPanel panel = new ActivityPanel();
		panel.setActivityNumber(activityNumber);
		panel.setResult(filepaths);
		activityPanels.add(panel);
		panelArea.add(panel);
	}
	
	public void setSearch(String search) {
		txtSearch.setText("Results for \"" + search + "\"");
	}
}
