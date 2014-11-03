package GUI;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GraphFrame extends JFrame implements ActionListener {

	private DrawingPanel panel;
	private JButton vertexButton, edgeButton;
	private HashMap<JButton, Boolean> buttonState;
	
	public GraphFrame() {

		buttonState = new HashMap<JButton, Boolean>();
		
		setComponents();
		
		setTitle("Grafos");
		setResizable(false);
		pack();
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);		
	}
	
	private void setComponents() {
		setLayout(new GridBagLayout());
		
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.fill = GridBagConstraints.BOTH;
		
		panel = new DrawingPanel();
		add(panel, c);
		
		c.gridy++;
		add(makeButtonPanel(), c);
	}
	
	private JPanel makeButtonPanel() {
		JPanel panel = new JPanel();
		
		vertexButton = new JButton("Novo Vértice");
		vertexButton.addActionListener(this);
		vertexButton.setActionCommand("vertex");
		panel.add(vertexButton);
		
		buttonState.put(vertexButton, false);
		
		edgeButton = new JButton("Nova Aresta");
		edgeButton.addActionListener(this);
		edgeButton.setActionCommand("edge");
		panel.add(edgeButton);
		
		buttonState.put(edgeButton, false);
		
		return panel;
	}

	@Override
	public void actionPerformed(ActionEvent ev) {
		boolean isPressed;
		
		switch (ev.getActionCommand()) {
		case "vertex":
			isPressed = buttonState.get(vertexButton);
			
			panel.setPlaceVertex(!isPressed);
			panel.setPlaceEdge(false);
			
			vertexButton.getModel().setPressed(!isPressed);
			buttonState.put(vertexButton, !isPressed);
			
			edgeButton.getModel().setPressed(false);
			buttonState.put(edgeButton, false);
			break;
		case "edge":
			isPressed = buttonState.get(vertexButton);
			
			panel.setPlaceVertex(false);
			panel.setPlaceEdge(!isPressed);
			
			edgeButton.getModel().setPressed(!isPressed);
			buttonState.put(edgeButton, !isPressed);
			
			vertexButton.getModel().setPressed(false);
			buttonState.put(vertexButton, false);
		
			break;
		default:
			break;
		}
		
	}
	
}
