package assignment5ir.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class MainFrame extends JFrame {

	private JPanel contentPane;
	private JTextField txtFilepath;
	private JTextField txtSearch;
	private JButton btnBrowse;
	private JButton btnScan;
	private JButton btnSearch;
	private JCheckBox chckbxActivity1;
	private JCheckBox chckbxActivity2;
	private JCheckBox chckbxActivity3;
	private JPanel panel_6;
	private JPanel panel_7;
	private JPanel panel_8;
	private JLabel loading;
	
	public MainFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 225, 250);
		setMinimumSize(new Dimension(225, 250));
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		panel_1.add(panel, BorderLayout.NORTH);
		panel.setLayout(new BorderLayout(0, 0));
		
		txtFilepath = new JTextField();
		txtFilepath.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel.add(txtFilepath);
		txtFilepath.setColumns(10);
		
		btnBrowse = new JButton("Browse");
		btnBrowse.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel.add(btnBrowse, BorderLayout.EAST);
		
		panel_7 = new JPanel();
		panel_7.setSize(new Dimension(1, 1));
		panel_7.setPreferredSize(new Dimension(1, 3));
		panel.add(panel_7, BorderLayout.SOUTH);
		
		JPanel panel_2 = new JPanel();
		panel_1.add(panel_2, BorderLayout.CENTER);
		panel_2.setLayout(new BorderLayout(0, 0));
		
		btnScan = new JButton("Scan");
		btnScan.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_2.add(btnScan, BorderLayout.NORTH);
		
		JPanel panel_3 = new JPanel();
		panel_2.add(panel_3, BorderLayout.SOUTH);
		panel_3.setLayout(new BorderLayout(0, 0));
		
		txtSearch = new JTextField();
		txtSearch.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtSearch.setSize(new Dimension(1, 25));
		txtSearch.setPreferredSize(new Dimension(1, 25));
		panel_3.add(txtSearch, BorderLayout.NORTH);
		txtSearch.setColumns(10);
		
		JPanel panel_4 = new JPanel();
		panel_3.add(panel_4, BorderLayout.CENTER);
		panel_4.setLayout(new BorderLayout(0, 0));
		
		btnSearch = new JButton("Search");
		btnSearch.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_4.add(btnSearch, BorderLayout.EAST);
		
		JPanel panel_5 = new JPanel();
		panel_4.add(panel_5, BorderLayout.WEST);
		panel_5.setLayout(new BorderLayout(0, 0));
		
		chckbxActivity1 = new JCheckBox("Activity 1");
		chckbxActivity1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_5.add(chckbxActivity1, BorderLayout.NORTH);
		
		chckbxActivity2 = new JCheckBox("Activity 2");
		chckbxActivity2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_5.add(chckbxActivity2);
		
		chckbxActivity3 = new JCheckBox("Activity 3");
		chckbxActivity3.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_5.add(chckbxActivity3, BorderLayout.SOUTH);
		
		panel_6 = new JPanel();
		panel_6.setSize(new Dimension(1, 1));
		panel_6.setPreferredSize(new Dimension(1, 3));
		panel_4.add(panel_6, BorderLayout.NORTH);
		
		panel_8 = new JPanel();
		panel_2.add(panel_8, BorderLayout.CENTER);
		
		ImageIcon loadingIcon = new ImageIcon(getClass().getResource("loading-icon.gif"));
		loading = new JLabel("Loading...");//new ImageIcon(MainFrame.class.getResource("/assignment5ir/ui/loading-icon.gif")), JLabel.CENTER);
		loading.setSize(50, 50);
		loading.setVisible(false);
		panel_8.add(loading);
		
		JLabel lblSelectAFolder = new JLabel("Select a folder / directory:");
		lblSelectAFolder.setFont(new Font("Tahoma", Font.PLAIN, 12));
		contentPane.add(lblSelectAFolder, BorderLayout.NORTH);
		lblSelectAFolder.setHorizontalAlignment(SwingConstants.LEFT);
		
		setVisible(true);
	}

	public String getFilepath() {
		return txtFilepath.getText();
	}
	
	public void setFilepath(String filepath) {
		txtFilepath.setText(filepath);
	}
	
	public void setBtnBrowseListener(ActionListener l) {
		while (btnBrowse.getActionListeners().length > 0) {
			btnBrowse.removeActionListener(btnBrowse.getActionListeners()[0]);
		}
		btnBrowse.addActionListener(l);
	}
	
	public void setBtnScanListener(ActionListener l) {
		while (btnScan.getActionListeners().length > 0) {
			btnScan.removeActionListener(btnScan.getActionListeners()[0]);
		}
		btnScan.addActionListener(l);
	}
	
	public String getSearchText() {
		return txtSearch.getText();
	}
	
	public void setBtnSearchListener(ActionListener l) {
		while (btnSearch.getActionListeners().length > 0) {
			btnSearch.removeActionListener(btnSearch.getActionListeners()[0]);
		}
		btnSearch.addActionListener(l);
	}
	
	public boolean getActivity1() {
		return chckbxActivity1.isSelected();
	}
	
	public boolean getActivity2() {
		return chckbxActivity2.isSelected();
	}
	
	public boolean getActivity3() {
		return chckbxActivity3.isSelected();
	}
	
	public void showLoading() {
		loading.setVisible(true);
	}
	
	public void hideLoading() {
		loading.setVisible(false);
	}
}
