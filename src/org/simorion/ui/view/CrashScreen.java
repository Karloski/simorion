package org.simorion.ui.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextPane;

import org.simorion.common.stream.FileSongWriter;
import org.simorion.common.stream.SongFormats;
import org.simorion.ui.model.MutableModel;

/**
 * Fatal error handler, displaying the error and offering a recovery option
 * 
 * @author Edmund Smith
 */
public class CrashScreen {

	/**
	 * @wbp.parser.entryPoint
	 */
	public static void displayCrash(Throwable thrown, final MutableModel model) {
		
		JFrame errorFrame = new JFrame("Simori-ON Crashed!");
		errorFrame.setSize(640, 480);
		errorFrame.setResizable(false);
		errorFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		errorFrame.setLocationRelativeTo(null);
		GUI.getInstance().setVisible(false);
		
		JPanel mainPanel = new JPanel(new FlowLayout());
		JTextPane textPane = new JTextPane();
		textPane.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		String text = thrown.getMessage()+"\n";
		for(StackTraceElement element : thrown.getStackTrace()) {
			text += element.toString() + "\n";
		}
		textPane.setText(text);
		textPane.setEditable(false);
		mainPanel.add(textPane, BorderLayout.CENTER);
		
		JButton emergencySaveButton = new JButton("Emergency Save!");
		emergencySaveButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String filename = "crash";
				FileSongWriter fsw = new FileSongWriter(new File(filename + ".song"));
				
				int crashNumber = 0;
				File toBeWritten = new File(filename + ".song");
				while(toBeWritten.exists()) {
					crashNumber++;
					toBeWritten = new File(filename + crashNumber + ".song");
				}
				
				try {
					while(crashNumber-- > 0) {
						File moveFrom = new File(filename + (crashNumber != 0 ? crashNumber : "") + ".song");
						File moveTo = new File(filename + (crashNumber + 1) + ".song");
						if(!moveFrom.renameTo(moveTo)) {
							System.err.println("Failed to move file from " + moveFrom.getCanonicalPath() + " to " + moveTo.getCanonicalPath() );
						}
					}
				} catch (IOException ex) {
					System.err.println("Your system does not support canonical paths for these files.");
					ex.printStackTrace();
				}
				
				try {
					toBeWritten = new File(filename + ".song");
					fsw.write(SongFormats.PREFERRED_FORMAT, model.getSong());
					JOptionPane.showMessageDialog(null, "Successfully saved song to \""+toBeWritten.getCanonicalPath()+"\"");
				} catch (Exception ex) {
					ex.printStackTrace();
					JOptionPane.showMessageDialog(null, "Failed to save song");
				}
			}
			
		});
		errorFrame.add(emergencySaveButton, BorderLayout.SOUTH);
		
		errorFrame.add(mainPanel);
		
		errorFrame.pack();
		errorFrame.setVisible(true);
	}
	
}
