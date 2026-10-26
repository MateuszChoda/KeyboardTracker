package main;

import java.io.IOException;
import java.sql.SQLException;

import javax.swing.SwingUtilities;

import main.MainFrame;

@SuppressWarnings("unused")
public class KeyboardTracker {
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					new MainFrame();
				} catch (IOException | SQLException e) {
					e.printStackTrace();
				}
			}
		});

	}

}