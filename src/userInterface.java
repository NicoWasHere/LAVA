import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JToolBar;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

public class userInterface extends JFrame  {


	/**
	 * 
	 */
	private static final long serialVersionUID = -837362023929063636L;

	private JButton but1 = new JButton();
	private JButton but2 = new JButton("");
	private JButton but3 = new JButton("");
	private JButton but4 = new JButton();
	private JButton but5 = new JButton();
	private JFrame window = new JFrame("Lava");
	private Canvas drawingSpace = new Canvas();
	private JLabel sliderLabel = new JLabel("Brush Size", JLabel.RIGHT);
	private JSlider brushSlider = new JSlider(JSlider.VERTICAL, 0, 80, 10);
	private JToolBar toolbar = new JToolBar (JToolBar.VERTICAL);
	private JLabel sampleText = new JLabel("");
	private JButton colorButton = new JButton("Choose Color");
	private boolean brushEnabled = false;
	private int sliderSize = -1;
	private JPanel colorViewer = new JPanel();
	private Color c;
	private JFileChooser fc;
	private Clicklistener click = new Clicklistener();
	private Slidelistener slide = new Slidelistener();	

	public static void main(String[] args) {
		new userInterface();
	}


	public userInterface() {

		sliderLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);
		brushSlider.addChangeListener(slide);
		brushSlider.setMajorTickSpacing(10);
		brushSlider.setMinorTickSpacing(1);
		brushSlider.setPaintTicks(true);
		brushSlider.setPaintLabels(true);
		brushSlider.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
		Font font2 = new Font("Serif", Font.ITALIC, 15);
		Font font = new Font("Arial", Font.PLAIN, 30);
		Font colorFont = new Font("Arial", Font.PLAIN, 20);
		brushSlider.setFont(font2);

		but1.setFont(font);
		but1.addActionListener(click);
		BufferedImage but1Resized = null;
		try {
			BufferedImage img = ImageIO.read(getClass().getResource("arc.png"));
			but1Resized = resize(img, 60, 60); 
		} catch (Exception ex) {
			System.out.println(ex);
		}
		but1.setIcon(new ImageIcon(but1Resized));
		but2.setFont(font);
		BufferedImage but2Resized = null;
		try {
			BufferedImage img = ImageIO.read(getClass().getResource("line.png"));
			but2Resized = resize(img, 60, 60); 
		} catch (Exception ex) {
			System.out.println(ex);
		}
		but2.setIcon(new ImageIcon(but2Resized));
		but2.addActionListener(click);
		but3.setFont(font);
		BufferedImage but3Resized = null;
		try {
			BufferedImage img = ImageIO.read(getClass().getResource("brush.png"));
			but3Resized = resize(img, 60, 60); 
		} catch (Exception ex) {
			System.out.println(ex);
		}
		but3.setIcon(new ImageIcon(but3Resized));
		but3.addActionListener(click);
		but4.setFont(font);
		BufferedImage but4Resized = null;
		try {
			BufferedImage img = ImageIO.read(getClass().getResource("eraser.png"));
			but4Resized = resize(img, 60, 60); 
		} catch (Exception ex) {
			System.out.println(ex);
		}
		but4.setIcon(new ImageIcon(but4Resized));
		but4.addActionListener(click);
		BufferedImage but5Resized = null;
		try {
			BufferedImage img = ImageIO.read(getClass().getResource("X.png"));
			but5Resized = resize(img, 60, 60); 
		} catch (Exception ex) {
			System.out.println(ex);
		}
		but5.setIcon(new ImageIcon(but5Resized));
		but5.addActionListener(click);
		colorButton.setFont(colorFont);
		colorButton.addActionListener(new Buttonlistener());
		

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		double bruhWidth = (double) 8/5;
		double bruhHeight = (double) 1.4;
		//int panelWidth = (int) (screenSize.width / bruhWidth);
		int panelHeight = (int) (screenSize.height / bruhHeight);
		int panelWidth = (int) panelHeight * 1920/1080;


		drawingSpace.setPanelSize(panelWidth, panelHeight);
		//drawingSpace.setPanelSize(1080, 720);
		drawingSpace.setPanelLocation(screenSize.width/6, screenSize.height/14);
		window.add(drawingSpace.getPanel());

		colorViewer.setBackground(Color.BLACK);
		colorViewer.setSize(30, 30);
		colorViewer.setLocation(170, 494);
		window.add(colorViewer);


		toolbar.add(but1);
		for (int i=1; i<=4; i++)
			toolbar.addSeparator();
		toolbar.add(but2);
		for (int i=1; i<=4; i++)
			toolbar.addSeparator();
		toolbar.add(but3);
		for (int i=1; i<=4; i++)
			toolbar.addSeparator();
		toolbar.add(but4);
		for (int i=1; i<=4; i++)
			toolbar.addSeparator();
		toolbar.add(but5);
		for (int i=1; i<=4; i++)
			toolbar.addSeparator();
		toolbar.add(colorButton);
		toolbar.add(brushSlider);
		toolbar.setFloatable(false);

		window.setLayout(new BorderLayout());
		window.add(toolbar, BorderLayout.WEST);
		window.setSize(screenSize.width,screenSize.height);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		makeMenu();


		window.setVisible(true);
	}

	private static BufferedImage resize(BufferedImage img, int height, int width) {
		Image tmp = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = resized.createGraphics();
		g2d.drawImage(tmp, 0, 0, null);
		g2d.dispose();
		return resized;
	}


	public void makeMenu() {

		Menulistener hit = new Menulistener();
		JMenuBar menuBar;
		JMenu fileMenu;
		JMenu editMenu;
		JMenuItem save;
		JMenuItem neww;
		JMenuItem undo;
		
		menuBar = new JMenuBar();

		fileMenu = new JMenu("File");
		editMenu = new JMenu("Edit");
		menuBar.add(fileMenu);
		menuBar.add(editMenu);


		neww = new JMenuItem("New");
		fileMenu.add(neww);
		neww.setActionCommand("new");
		neww.addActionListener(hit);

		save = new JMenuItem("Save");
		fileMenu.add(save);
		save.setActionCommand("save");
		save.addActionListener(hit);
		
		undo = new JMenuItem("Undo");
		editMenu.add(undo);
		undo.setActionCommand("undo");
		undo.addActionListener(hit);

		window.setJMenuBar(menuBar);

	}

	private class Clicklistener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if (e.getSource() == but1)
			{
				drawingSpace.setBrush(0);
			}
			else if (e.getSource() == but2) {
				drawingSpace.setBrush(1);
			}
			else if (e.getSource() == but3) {
				drawingSpace.setBrush(2);
			}
			else if (e.getSource() == but4) {
				drawingSpace.setBrush(3);
			}
			else if (e.getSource() == but5) {
				drawingSpace.setBrush(4);
			}
		}
	}

	private class Slidelistener implements ChangeListener {

		@Override
		public void stateChanged(ChangeEvent e) {
			// TODO Auto-generated method stub

			JSlider source = (JSlider)e.getSource();
			int size = (int)source.getValue();
			sliderSize = size;
			drawingSpace.setSize(size);

		}

	}

	private class Buttonlistener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			c = JColorChooser.showDialog(null, "Choose a Color", sampleText.getForeground());
			if (c != null) {

				drawingSpace.setBrushColor(c);

				colorViewer.setBackground(c);
			}
		}
	}


	private class Menulistener implements ActionListener {
		public void actionPerformed(ActionEvent e) {


			if ("save".equals(e.getActionCommand())) {

				fc = new JFileChooser();
				fc.setFileFilter(new FileNameExtensionFilter("Image", "png"));

				int r = fc.showSaveDialog(null); 

				if (r == JFileChooser.APPROVE_OPTION) 

				{ 
					File file = fc.getSelectedFile();
					BufferedImage image = new BufferedImage(drawingSpace.getPanel().getWidth(), drawingSpace.getPanel().getHeight(), BufferedImage.TYPE_INT_RGB);
					Graphics g = image.getGraphics();

					drawingSpace.getPanel().print(g);

					g.dispose();


					try {
						ImageIO.write(image, "png", new File(file.getAbsolutePath() + ".png"));
					}
					catch (IOException ex) {
						System.out.print("fail");
					}
				}
				else {

				}

			}

			else if ("new".equals(e.getActionCommand())) {
				new userInterface();
			}
			else if ("undo".equals(e.getActionCommand())) {
				drawingSpace.undo();
			}
		}
	}
}
