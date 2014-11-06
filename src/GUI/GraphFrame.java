package GUI;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.SoftBevelBorder;

import main.Graph;
import main.Node;
import GUI.DrawingPanel.DrawingState;

public class GraphFrame extends JFrame {

	private DrawingPanel drawingPanel;
	
	private JButton doneButton;
	
	private JPanel[] subGraphs;
	private JPanel subGraphsPanel, emptyPanel;
	
	private Graph graph, graphClique, graphIndep;
	
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
		c.insets = new Insets(5, 5, 5, 5);
		
		makeSubGraphSelectionPanel();
		
		add(emptyPanel, c);
		add(subGraphsPanel, c);
		
		// Tela de desenho
		drawingPanel = new DrawingPanel();
		
		c.gridy++;
		c.fill = GridBagConstraints.BOTH;
		add(drawingPanel, c);
		
		c.fill = GridBagConstraints.NONE;
		c.gridy++;
		add(setDoneButton(), c);
	}
	
	private void makeSubGraphSelectionPanel() {
		
		MouseAdapter listener = new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent ev) {
				JPanel source = (JPanel) ev.getSource();
				
				source.setBackground(new Color(150, 150, 150));
				source.setBorder(new SoftBevelBorder(SoftBevelBorder.LOWERED));
				
				for (JPanel p : subGraphs)
					if (p != source) {
						p.setBackground(new Color(200, 200, 200));
						p.setBorder(new SoftBevelBorder(SoftBevelBorder.RAISED));
					}
				
				if (source.equals(subGraphs[0]))
					drawingPanel.enableSubGraph(graph);
				else if (source.equals(subGraphs[1]))
					drawingPanel.enableSubGraph(graphClique);
				else if (source.equals(subGraphs[2]))
					drawingPanel.enableSubGraph(graphIndep);
				
			}
		};
		
		JPanel panel1 = new JPanel();
		JPanel panel2 = new JPanel();
		JPanel panel3 = new JPanel();
		
		panel1.add(new JLabel("Grafo"));
		panel2.add(new JLabel("Clique"));
		panel3.add(new JLabel("Conjunto Independente"));
		
		subGraphs = new JPanel[] {panel1, panel2, panel3};
		for (JPanel p : subGraphs) {	
			p.setBackground(new Color(200, 200, 200));
			p.setBorder(new SoftBevelBorder(SoftBevelBorder.RAISED));
			p.addMouseListener(listener);
		}
		
		subGraphsPanel = new JPanel(new GridBagLayout());
		subGraphsPanel.setVisible(false);
		
		GridBagConstraints c = new GridBagConstraints();
		c.gridy = 0;
		c.gridx = 0;
		c.insets = new Insets(0, 1, 0, 0);
		
		subGraphsPanel.add(panel1, c);
		c.gridx++;
		subGraphsPanel.add(panel2, c);
		c.gridx++;
		subGraphsPanel.add(panel3, c);
		
		emptyPanel = new JPanel();
		emptyPanel.setPreferredSize(subGraphsPanel.getPreferredSize());
	}
	
	private JButton setDoneButton() {
		doneButton = new JButton("Calcular");
		
		doneButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (doneButton.getText().equals("Calcular")) {
					graph = drawingPanel.getGraph();
					graphClique = graph.getIndependentSet(graph.getNodes().toArray(new Node[]{}));
					graphIndep = graphClique.getIndependentSet(graphClique.getNodes().toArray(new Node[]{}));
				
					doneButton.setText("Editar");
					emptyPanel.setVisible(false);
					subGraphsPanel.setVisible(true);
					
					drawingPanel.setState(DrawingState.MOVING);
					drawingPanel.repaint();
				} else {
					doneButton.setText("Calcular");
					subGraphsPanel.setVisible(false);
					emptyPanel.setVisible(true);
					drawingPanel.setState(DrawingState.CREATING);
					drawingPanel.repaint();
				}
					
			}
		});
		
		return doneButton;
	}
	
	private void addKeyListener() {
		 // Adds listener for ctrl+tab funcionality
        KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();

        manager.addKeyEventDispatcher(new KeyEventDispatcher() {
            public boolean dispatchKeyEvent(KeyEvent e) {	
            	
            	if (doneButton.getText().equals("Editar"))
            		return false;
            	
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
