package GUI;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import main.Graph;
import main.Node;
import GUI.DrawingPanel.DrawingState;

public class GraphFrame extends JFrame implements ActionListener {

	private DrawingPanel drawingPanel;
	private JButton creatingButton, movingButton, deletingButton;
	
	// Mapa para poder colocar os botões num estado de "toggle" dependentes
	private HashMap<JButton, Boolean> buttonState;
	
	public GraphFrame() {
		buttonState = new HashMap<JButton, Boolean>();
		
		setComponents();
		
		setTitle("Grafos");
		setResizable(false);
		pack();
		// Coloca no centro da tela
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);		
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
		
		c.gridy++;
		add(makeButtonPanel(), c);
	}
	
	private JPanel makeButtonPanel() {
		JPanel panel = new JPanel();
		
		// Cria o botão para criação de componentes
		creatingButton = new JButton("Criar Componentes");
		// Quando ele é clicado, chama-se o método 'actionPerformed'
		creatingButton.addActionListener(this);
		// É passado, no clique, o comando 'create', pra decidir o que fazer no método
		creatingButton.setActionCommand("create");
		panel.add(creatingButton);
		
		// Colocando o estado atual do botão como desativado
		buttonState.put(creatingButton, false);
		
		// Igual ao anterior, mas outro comando
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
		JButton source = (JButton) ev.getSource();

		// Verificamos se o botão estava clicado anteriormente
		boolean isPressed = buttonState.get(source);
		
		// Desativamos todos os botões
		for (Entry<JButton, Boolean> entry : buttonState.entrySet()) {
			entry.setValue(false);
			entry.getKey().getModel().setPressed(false);
		}
					
		// Invertemos o estado do botão atual
		source.getModel().setPressed(!isPressed);
		buttonState.put(source, !isPressed);
		
		switch (ev.getActionCommand()) {
		// Caso o botão clicado tenha sido o de criação
		case "create":
			
			// Colocamos o painel de desenho para poder ou não criar, dependendo do estado
			// anterior
			drawingPanel.setState(isPressed ? DrawingState.NONE : DrawingState.CREATING);
			
			break;
		case "move":
			drawingPanel.setState(isPressed ? DrawingState.NONE : DrawingState.MOVING);
			break;
		case "delete":
			drawingPanel.setState(isPressed ? DrawingState.NONE : DrawingState.DELETING);
			break;
		default:
			break;
		}
		
		Graph graph = drawingPanel.getGraph();
		Graph sub = graph.getIndependentSet(graph.getNodes().toArray(new Node[]{}));
//		drawingPanel.enableSubGraph(sub);
	}
	
}
