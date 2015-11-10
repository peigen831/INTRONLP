package assignment5ir.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
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
	private JPanel panelArea1;
	private JPanel panelArea2;
	private JPanel panelArea3;

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
					array.add("C:\\Users\\USER\\Desktop\\NLP\\assign6\\TagalogNews\\Tagalog News - 1.txt");
					frame.addSearchResultPanel(1, array);
					frame.addSearchResultPanel(2, array);
					frame.addSearchResultPanel(3, array);
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
		
		panelArea1 = new JPanel();
		contentPane.add(panelArea1, BorderLayout.WEST);
		panelArea1.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));
		
		panelArea2 = new JPanel();
		panel.add(panelArea2, BorderLayout.WEST);
		panelArea2.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_1 = new JPanel();
		panel.add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		panelArea3 = new JPanel();
		panel_1.add(panelArea3, BorderLayout.WEST);
		panelArea3.setLayout(new BorderLayout(0, 0));
		
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
		
		if (activityPanels.size() <= 0) {
			panelArea2.add(panel, BorderLayout.CENTER);
		}
		else {
			ActivityPanel oldPanel = (ActivityPanel) panelArea2.getComponent(0);
			if (oldPanel.getActivityNumber() == 1) {
				panelArea1.add(oldPanel, BorderLayout.CENTER);
				panelArea2.removeAll();
				panelArea2.add(panel, BorderLayout.CENTER);
			}
			else if (oldPanel.getActivityNumber() == 3) {
				panelArea3.add(oldPanel, BorderLayout.CENTER);
				panelArea2.removeAll();
				panelArea2.add(panel, BorderLayout.CENTER);
			}
			else if (panel.getActivityNumber() == 1) {
				panelArea1.add(panel, BorderLayout.CENTER);
			}
			else if (panel.getActivityNumber() == 3) {
				panelArea3.add(panel, BorderLayout.CENTER);
			}
		}
		
		activityPanels.add(panel);
		
		this.setMinimumSize(new Dimension(450 * activityPanels.size() + 25, 600));
		this.setPreferredSize(new Dimension(450 * activityPanels.size() + 25, 600));
		this.setSize(new Dimension(450 * activityPanels.size() + 25, 600));
	}
	
	public void setSearch(String search) {
		txtSearch.setText("Results for \"" + search + "\"");
	}
}
