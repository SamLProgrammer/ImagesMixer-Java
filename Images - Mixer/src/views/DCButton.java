package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;

import javax.swing.JButton;

public class DCButton extends JButton{
	
	public DCButton(String string) {
		setForeground(Color.decode(string));
		setLayout(new BorderLayout());
//		setContentAreaFilled(false);
		setFont(new Font("Oswald", Font.PLAIN, 20));
		setFocusPainted(false);
		setCursor(new Cursor(Cursor.HAND_CURSOR));
	}
}
