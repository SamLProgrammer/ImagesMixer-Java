package views;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import controller.Actions;
import models.FilterManager;

public class FiltersChooserDialog extends JDialog{

	JComboBox<String> filtersBox;
	FilterManager filterManager;
	Dimension screenDimensions;
	
	public FiltersChooserDialog(ActionListener listener) {
		getContentPane().setBackground(Color.decode("#2A3132"));
		setLayout(new GridBagLayout());
		screenDimensions = Toolkit.getDefaultToolkit().getScreenSize();
		initComponents(listener);
		locate();
	}
	
	public void initComponents(ActionListener listener) {
		setUIManager();
		GridBagConstraints gbc = new GridBagConstraints();
		setBackground(Color.decode("#2A3132"));
		filterManager = new FilterManager();
		filtersBox = new JComboBox<>();
		filtersBox.setBackground(Color.WHITE);
		((JLabel)filtersBox.getRenderer()).setHorizontalAlignment(JLabel.CENTER);
		filtersBox.getComponent(0).setBackground(Color.decode("#336B87"));
		filtersBox.setFont(new Font("Oswald",Font.PLAIN,15));
		filtersBox.setBorder(BorderFactory.createEmptyBorder());
		filtersBox.setAlignmentX(CENTER_ALIGNMENT);
		filtersBox.setAlignmentX(SwingConstants.CENTER);
		for (int i = 0; i < filterManager.getFiltersNames().length; i++) {
			filtersBox.addItem(filterManager.getFiltersNames()[i]);
		}
		DCButton okButton = new DCButton("#336B87");
		okButton.setText("DONE");
		okButton.setBackground(Color.decode("#171b1c"));
		okButton.setActionCommand(Actions.OK_FILTER_CHOOSER_BTN.name());
		okButton.addActionListener(listener);
//		JButton cancelButton = new JButton("CANCEL");
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setLayout(new GridBagLayout());
		buttonsPanel.setBackground(Color.decode("#2A3132"));
		buttonsPanel.setOpaque(false);

		
		int inset = (int)((screenDimensions.getHeight() + screenDimensions.getWidth())/500);
		gbc.insets = new Insets(inset, inset, inset, inset);
		gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
//		gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.5;
        
        buttonsPanel.add(okButton,gbc);
//        gbc.gridx = 1;
//        gbc.gridy = 0;
//        buttonsPanel.add(cancelButton,gbc);
        gbc.weighty = 1;
        
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.insets = new Insets(20, 10, 10, 10);
		
		add(filtersBox, gbc);
		
		gbc.insets = new Insets(0, 0, 10, 0);
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.fill = GridBagConstraints.REMAINDER;
		gbc.weightx = 1;
		gbc.weighty = 1;
		add(buttonsPanel, gbc);
		pack();
	}
	
	public void showUp() {
		filtersBox.setSelectedIndex(0);
		setVisible(true);
	}
	
	public String getSelectedFilter() {
		return (String)filtersBox.getSelectedItem();
	}
	
	public void locate() {
		setSize((int)(screenDimensions.getWidth()/4), (int)(screenDimensions.getHeight()/5));
		setLocation((int)(screenDimensions.getWidth()/2-this.getWidth()/2),
				(int)(screenDimensions.getHeight()/2 - this.getHeight()/2));
	}
	
	private void setUIManager() {
		UIManager.put("Panel.background", Color.decode("#2A3132"));
		UIManager.put("ComboBox.selectionBackground", Color.decode("#90AFC5"));
	}
}
