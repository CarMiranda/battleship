/**
 * 
 */
package vue;

/**
 * @author Jorge OCHOA
 *
 */


import java.awt.BorderLayout; 

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Canvas;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.JTextField;
import javax.swing.JPasswordField;

public class FenetreLogin extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public FenetreLogin(){
		super("Bataille Navale");
		
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(3,2));
		mainPanel.add(new JLabel("Entrez votre login :"));
		mainPanel.add(new JTextField(10));
		mainPanel.add(new JLabel("Entrez votre mot de passe :"));
		mainPanel.add(new JPasswordField(10));
		mainPanel.add(new JButton ("Créer un compte"),BorderLayout.SOUTH );
		mainPanel.add(new JButton ( " OK " ) , BorderLayout.SOUTH );
		
		this.setAlwaysOnTop(true);
		this.setContentPane(mainPanel);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.pack();
		this.setVisible(true);
		this.setResizable(false);
	}

}