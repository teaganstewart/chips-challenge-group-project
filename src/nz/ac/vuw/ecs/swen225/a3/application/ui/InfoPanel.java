package nz.ac.vuw.ecs.swen225.a3.application.ui;

import nz.ac.vuw.ecs.swen225.a3.application.Game;
import nz.ac.vuw.ecs.swen225.a3.render.Render;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import java.awt.*;
import java.util.Scanner;

public class InfoPanel extends JPanel {

	private Game game;
	private JPanel storage;
	private JPanel hint;

	private int time;
	private int level;
	private int treasures;
	private ImageIcon infoIcon = makeImageIcon("icons/InfoBackground.png");
	private ImageIcon slotIcon = makeImageIcon("icons/Slot.png");

	public InfoPanel (Game game){
		this.game = game;
		this.time = game.getTime();
		this.level = game.getLevel();
		this.treasures = game.getTreasures();
		this.setLayout(new GridBagLayout());
		this.setPreferredSize(new Dimension(300,200));

		this.setBorder(BorderFactory.createEmptyBorder(30,0,30,30));

		GridBagConstraints c = new GridBagConstraints();

		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth =1;
		c.insets = new Insets(0,0,50,0);  //top padding
		JLabel level = new JLabel("Level:");
		JLabel displayLevel = new JLabel("Display Level");
		//displayLevel.setBackground();
		JLabel time = new JLabel("Time:");
		JLabel displayTime = new JLabel("Display Time");
		JLabel chips = new JLabel("Chips:");
		JLabel displayChip = new JLabel("Display Chip");

		this.add(level,c);
		c.gridy = 1;
		this.add(time,c);
		c.gridy = 2;
		this.add(chips,c);
		c.gridy = 3;
		c.gridheight = 2;
		this.add(inventory(),c);
		c.gridy = 5;
		this.add(hintPanel(),c);


	}

	public JPanel inventory(){
		storage = new JPanel(new GridLayout(2,4));
		JLabel[] inventory = game.getRender().renderInventory();

		for(int i =0; i < 8;i++){
			JLabel item = inventory[i];
			if(item==null) {
				item = new JLabel();
				item.setIcon(slotIcon);
			}
			item.setBorder(BorderFactory.createMatteBorder(1,1,1,1,Color.gray));
			storage.add(item);
		}
		storage.setVisible(true);
		return storage;
	}

	public JPanel hintPanel() {
		hint = new JPanel();
		hint.setPreferredSize(new Dimension(200,100));
		hint.setBackground(Color.white);
		
		Border type = BorderFactory.createLineBorder(Color.black);
		Border border = BorderFactory.createTitledBorder(type, "Current Hint:", TitledBorder.CENTER, TitledBorder.TOP);
		hint.setBorder(border);
		setDefaultHint();
		return hint;
	}
	
	public void setDefaultHint() {
		try {
			hint.remove(0);
		} catch (ArrayIndexOutOfBoundsException e) {}
		// I wish I could justify this being necessary, but it's the only way to fix the refreshing of the GUI
		hint.add(new JLabel("<html>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;...&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;"+
							"<br>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;"+
							"<br>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;"+
							"<br>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;</html>"));
	}
	
	public void setHint(String message) {
		try {
			hint.remove(0);
		} catch (ArrayIndexOutOfBoundsException e) {}
		
		int length = 0;
		StringBuilder wrapped = new StringBuilder();
		Scanner sc = new Scanner(message);
		wrapped.append("<html>");
		while (sc.hasNext()) {
			if (length > 25) {
				wrapped.append("<br>");
				length = 0;
			}
			String phrase = sc.next();
			wrapped.append(phrase + " ");
			length += phrase.length() + 1;
		}
		wrapped.append("</html>");
		hint.add(new JLabel(wrapped.toString()));
	}
	
	/**
	 * Clears the game panel so it can be redrawn.
	 */
	public void clearInventory() {
		for (int y = 0; y < 8; y++) {
			storage.remove(0);
		}
	}

	/** 
	 *  Draws the inventory.
	 */
	public void drawInventory() {
		Render render = game.getRender();

		JLabel[] inventory = game.getRender().renderInventory();

		for(int i =0; i < 8;i++){
			JLabel item = inventory[i];
	
			item.setBorder(BorderFactory.createMatteBorder(1,1,1,1,Color.gray));
			storage.add(item);
		}
	}

	public void paintComponent(Graphics g){
		g.drawImage(infoIcon.getImage(), 0, 30, getWidth()-30, getHeight()-60, null);
	}

	public ImageIcon makeImageIcon(String filename){
		java.net.URL imageUrl = this.getClass().getResource(filename);
		try {
			if (imageUrl == null) { return null; }

			return new ImageIcon(imageUrl);
		} catch(Exception e){
			return null;
		}
	}


}
