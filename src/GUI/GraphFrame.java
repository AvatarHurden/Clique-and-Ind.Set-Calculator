package GUI;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;

import GUI.DrawingPanel.DrawingState;

public class GraphFrame extends JFrame {

	private DrawingPanel drawingPanel;
	
	public GraphFrame() {
		setComponents();
		
		setTitle("Grafos");
		setResizable(false);
		pack();
		// Coloca no centro da tela
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);	
		
		addKeyListener();
	}
	
	private void setComponents() {
		// Layout dos componentes
		setLayout(new GridBagLayout());
		
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.fill = GridBagConstraints.BOTH;
		
		// Tela de desenho
		drawingPanel = new DrawingPanel();
		add(drawingPanel, c);
	}
	
	private void addKeyListener() {
		 // Adds listener for ctrl+tab funcionality
        KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();

        manager.addKeyEventDispatcher(new KeyEventDispatcher() {
            public boolean dispatchKeyEvent(KeyEvent e) {	
            	
            	if (e.isShiftDown() && e.isControlDown())
            		drawingPanel.setState(DrawingState.CREATING);
            	else if (e.isControlDown())
            		drawingPanel.setState(DrawingState.DELETING);
            	else if (e.isShiftDown())
            		drawingPanel.setState(DrawingState.MOVING);
            	else
            		drawingPanel.setState(DrawingState.CREATING);
            	
            	return false;
            	}
        });
	}
}
