package org.simorion.ui.view;

import java.awt.BorderLayout;
import java.awt.Dialog.ModalityType;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;

/**
 * Simple Splash Screen for displaying while loading
 * 
 * @author Edmund Smith
 */
public class Splash {

	private final String fileName;
	private final JDialog dialog;
	
	public Splash(String fileName) {
		this.fileName = fileName;
		dialog = new JDialog();
		dialog.setModalityType(ModalityType.MODELESS);
		dialog.setUndecorated(true);
		JLabel image = new JLabel(new ImageIcon(fileName));
		image.setLayout(new BorderLayout());
		dialog.add(image);
				
		dialog.pack();
		dialog.setLocationRelativeTo(null);
	}
	
	public void open() {
		dialog.setVisible(true);
	}
	
	public void close() {
		dialog.setVisible(false);
	}
	
}
