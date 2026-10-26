package main;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.JFrame;

public class MainFrame extends JFrame implements KeyListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private TrackerManager tracker = new TrackerManager();
	private MainPanel mp = new MainPanel(tracker);
	public MainFrame() throws IOException, SQLException {
		super("Keyboard Tracker");
		setSize(950, 700);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		mp.addKeyListener(this);
		
		this.add(mp, BorderLayout.CENTER);
		
		setVisible(true);
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyPressed(KeyEvent e) {
		System.out.println("Key Pressed: " + KeyEvent.getKeyText(e.getKeyCode()));
		String key = KeyEvent.getKeyText(e.getKeyCode());
		try {
			mp.updateRecent(key);
			mp.updateMost();
		} catch (NumberFormatException | SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
