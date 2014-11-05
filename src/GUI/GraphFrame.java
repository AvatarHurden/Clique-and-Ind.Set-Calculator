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
	private JButton creatingButton, movingButton, deletingButton;
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
		
		creatingButton = new JButton("Criar Componentes");
		creatingButton.addActionListener(this);
		creatingButton.setActionCommand("create");
		panel.add(creatingButton);
		
		buttonState.put(creatingButton, false);
		
		movingButton = new JButton("Mover Componentes");
		movingButton.addActionListener(this);
		movingButton.setActionCommand("move");
		panel.add(movingButton);
		
		buttonState.put(movingButton, false);
		
		deletingButton = new JButton("Deletar Componentes");
		deletingButton.addActionListener(this);
		deletingButton.setActionCommand("delete");
		panel.add(deletingButton);
		
		buttonState.put(deletingButton, false);
		
		return panel;
	}

	@Override
	public void actionPerformed(ActionEvent ev) {
		boolean isPressed;
		
		switch (ev.getActionCommand()) {
		case "create":
			isPressed = buttonState.get(creatingButton);
			
			panel.setCreating(!isPressed);
			
			creatingButton.getModel().setPressed(!isPressed);
			buttonState.put(creatingButton, !isPressed);
			
			movingButton.getModel().setPressed(false);
			buttonState.put(movingButton, false);
			break;
		case "move":
			isPressed = buttonState.get(movingButton);
			
			movingButton.getModel().setPressed(!isPressed);
			buttonState.put(movingButton, !isPressed);
			
			creatingButton.getModel().setPressed(false);
			buttonState.put(creatingButton, false);
		
			break;
		default:
			break;
		}
		
	}
	
}
