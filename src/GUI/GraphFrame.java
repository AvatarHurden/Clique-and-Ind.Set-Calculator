package GUI;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import main.Graph;
import GUI.DrawingPanel.DrawingState;

public class GraphFrame extends JFrame {

	private DrawingPanel drawingPanel;
	
	private JButton doneButton;
	
	private JPanel[] subGraphs;
	private JPanel subGraphsPanel;
	
	private Graph graph; 
	private List<Graph> graphClique, graphIndep;
	private long timeClique, timeIndep;
	
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
		
		drawingPanel.setState(DrawingState.CREATING);
	}
	
	private void setComponents() {
		// Layout dos componentes
		setLayout(new GridBagLayout());
		
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(5, 5, 5, 5);
		
		makeSubGraphSelectionPanel();
		
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
				
				if (doneButton.getText().equals("Calcular"))
					return;
				
				for (JPanel p : subGraphs) {
					p.setBackground(new Color(200, 200, 200));
					p.setBorder(new LineBorder(Color.DARK_GRAY));
				}
				
				source.setBackground(new Color(150, 150, 150));
				
				if (source.equals(subGraphs[0])) {
					drawingPanel.removeSubGraphs();
					drawingPanel.setMessage("Grafo");
				} else if (source.equals(subGraphs[1])) {
					if (graphClique == null)
						drawingPanel.setCalculating();
					else
						setGraphClique();
				} else if (source.equals(subGraphs[2])) {
					if (graphIndep == null)
						drawingPanel.setCalculating();
					else
						setGraphIndep();
				}
				
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
			p.setBackground(new Color(230, 230, 230));
			p.setBorder(new LineBorder(Color.LIGHT_GRAY));
			p.addMouseListener(listener);
		}
		
		panel1.setBackground(new Color(210, 210, 210));
		
		subGraphsPanel = new JPanel(new GridBagLayout());
		
		GridBagConstraints c = new GridBagConstraints();
		c.gridy = 0;
		c.gridx = 0;
		c.insets = new Insets(0, 1, 0, 0);
		
		subGraphsPanel.add(panel1, c);
		c.gridx++;
		subGraphsPanel.add(panel2, c);
		c.gridx++;
		subGraphsPanel.add(panel3, c);
	}
	
	private void setGraphClique() {
		if (graphClique == null) {
			timeClique = System.currentTimeMillis();
			graphClique = graph.getCliques();
			timeClique = System.currentTimeMillis() - timeClique;
		}
				
		if (subGraphs[1].getBackground().equals(new Color(150, 150, 150))) {
			drawingPanel.setMessage("\u03C9(G) = " + graphClique.get(0).getSize(),
					graphClique.size() + " conjuntos", timeClique/1000.0 + " segundos p/ processar");
			drawingPanel.setSubGraphs(graphClique);
		}
	}
	
	private void setGraphIndep() {
		if (graphIndep == null) {
			timeIndep = System.currentTimeMillis();
			graphIndep = graph.getMaximumIndependentSets();
			timeIndep = System.currentTimeMillis() - timeIndep;
		}
				
		if (subGraphs[2].getBackground().equals(new Color(150, 150, 150))) {
			drawingPanel.setMessage("\u03B1(G) = " + graphIndep.get(0).getSize(),
					graphIndep.size() + " conjuntos", timeIndep/1000.0 + " segundos p/ processar");
			drawingPanel.setSubGraphs(graphIndep);
		}
	}
	
	
	private JButton setDoneButton() {
		doneButton = new JButton("Calcular");
		
		doneButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				Robot bot = null;
				try {
					bot = new Robot();
				} catch (Exception exc) {
					return;
				}
				
				Point p = MouseInfo.getPointerInfo().getLocation();

				if (doneButton.getText().equals("Calcular")) {
					graph = drawingPanel.getGraph();
					
					doneButton.setText("Editar");
					
					for (JPanel panel : subGraphs) {
						panel.setBorder(new LineBorder(Color.DARK_GRAY));
						if (!panel.equals(subGraphs[0])) 
							panel.setBackground(new Color(200, 200, 200));
						else
							panel.setBackground(new Color(150, 150, 150));
					}
					
					drawingPanel.setState(DrawingState.MOVING);
					graph = drawingPanel.getGraph();
					
					new Thread(new Runnable() {
						@Override
						public void run() {
							setGraphIndep();
						}
					}).start();
					
					new Thread(new Runnable() {
						@Override
						public void run() {
							setGraphClique();
						}
					}).start();
					
				} else {
					doneButton.setText("Calcular");
					
					for (JPanel panel : subGraphs) {
						panel.setBorder(new LineBorder(Color.LIGHT_GRAY));
						if (!panel.equals(subGraphs[0])) 
							panel.setBackground(new Color(230, 230, 230));
						else 
							panel.setBackground(new Color(210, 210, 210));
					}

					drawingPanel.setState(DrawingState.CREATING);
					drawingPanel.removeSubGraphs();
					graphClique = null;
					graphIndep = null;
				}
//				
//				bot.mouseMove(subGraphs[0].getLocationOnScreen().x,
//						subGraphs[0].getLocationOnScreen().y);   
//				bot.mousePress(InputEvent.BUTTON1_MASK);
//				bot.mouseRelease(InputEvent.BUTTON1_MASK);

				bot.mouseMove(p.x - 100, p.y - 100);
//				bot.mouseMove(drawingPanel.getLocationOnScreen().x + 100, drawingPanel.getLocationOnScreen().y + 100);
//				
				bot.mouseMove(p.x, p.y);

				drawingPanel.setHovered(new Point(100, 100), false);
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
