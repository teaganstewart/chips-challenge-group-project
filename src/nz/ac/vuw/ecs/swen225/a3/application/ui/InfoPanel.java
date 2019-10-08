package nz.ac.vuw.ecs.swen225.a3.application.ui;

import nz.ac.vuw.ecs.swen225.a3.application.Game;
import nz.ac.vuw.ecs.swen225.a3.maze.Treasure;
import nz.ac.vuw.ecs.swen225.a3.render.Render;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import java.awt.*;
import java.util.Scanner;

public class InfoPanel extends JPanel {

	private Game game;
	private JPanel inv;
	private JPanel storage;
	private JPanel hint;
	private JPanel timer;
	private JPanel chip;
	private JPanel level;
	
	private ImageIcon infoIcon = makeImageIcon("icons/InfoBackground.png");
	private ImageIcon slotIcon = makeImageIcon("icons/Slot.png");

	public InfoPanel (Game game){
		
		this.game = game;
		this.setLayout(new GridBagLayout());
		this.setPreferredSize(new Dimension(300,200));

		this.setBorder(BorderFactory.createEmptyBorder(30,0,30,30));

		GridBagConstraints c = new GridBagConstraints();

		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth =1;
		c.insets = new Insets(0,0,30,0);  //top padding

		this.add(levelPanel(),c);
		c.gridy = 1;
		this.add(timePanel(),c);
		c.gridy = 2;
		this.add(chipPanel(),c);
		c.gridy = 3;
		c.gridheight = 2;
		this.add(inventory(),c);
		c.gridy = 5;
		this.add(hintPanel(),c);


	}

	public JPanel inventory(){
		inv = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 0;
		c.gridwidth = 1;
		
		inv.add(new JLabel("Inventory:"),c);
		inv.setBackground(Color.white);
		
		c.gridy = 1;
		
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
		
		inv.add(storage, c);
		return inv;
	}

	public JPanel chipPanel() {
		chip = new JPanel();
		chip.setPreferredSize(new Dimension(200,50));
		chip.setMaximumSize(new Dimension(200,50));
		chip.setMinimumSize(new Dimension(200,50));
		chip.setBackground(Color.white);

		Border type = BorderFactory.createLineBorder(Color.white);
		Border border = BorderFactory.createTitledBorder(type, "Treasures Left:", TitledBorder.CENTER, TitledBorder.TOP);
		chip.setBorder(border);
		displayChips();
		return chip;
	}
	
	public JPanel hintPanel() {
		hint = new JPanel();
		hint.setPreferredSize(new Dimension(200,100));
		hint.setMaximumSize(new Dimension(200,100));
		hint.setMinimumSize(new Dimension(200,100));
		hint.setBackground(Color.white);
		
		Border type = BorderFactory.createLineBorder(Color.black);
		Border border = BorderFactory.createTitledBorder(type, "Current Hint:", TitledBorder.CENTER, TitledBorder.TOP);
		hint.setBorder(border);
		setDefaultHint();
		return hint;
	}
	
	public JPanel levelPanel() {
		level = new JPanel();
		level.setPreferredSize(new Dimension(200,20));
		level.setMaximumSize(new Dimension(200,20));
		level.setMinimumSize(new Dimension(200,20));
		level.setBackground(Color.white);
		
		setLevelDisplay();
		return level;
	}
	
	public void setLevelDisplay() {
		try {
			level.remove(0);
		} catch (ArrayIndexOutOfBoundsException e) {}
		
		
		int num = 0;
		try {
			num = game.getLevelNum();
		} catch (NullPointerException e) {}
		level.add(new JLabel("Level: " + Integer.toString(num)));
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

	
	public JPanel timePanel() {
		timer = new JPanel();
		timer.setPreferredSize(new Dimension(200,50));
		timer.setMaximumSize(new Dimension(200,50));
		timer.setMinimumSize(new Dimension(200,50));
		timer.setBackground(Color.white);
		
		Border type = BorderFactory.createLineBorder(Color.white);
		Border border = BorderFactory.createTitledBorder(type, "Total Time:", TitledBorder.CENTER, TitledBorder.TOP);
		timer.setBorder(border);
		displayTime();
		return timer;
	}
	
	public void displayTime() {
		try {
			timer.remove(0);
		} catch (ArrayIndexOutOfBoundsException e) {}
		
		timer.add(new JLabel(timeToMins(game.getTime())));
	}
	
	private String timeToMins(int time) {
		int first = 0;
		int secnd = 0;
		
		while (time >= 60) {
			first++;
			time-=60;
		}
		secnd = time;
		
		return (first <= 9 ? "0" : "") + Integer.toString(first) + ":" + (secnd <= 9 ? "0" : "") + Integer.toString(secnd);
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
	
	public void displayChips() {
		try {
			chip.remove(0);
			chip.remove(0);
		} catch (ArrayIndexOutOfBoundsException e) {}
		
		int left = Treasure.getTotalInLevel() - Treasure.getTotalCollected();
		chip.add(new JLabel((left <= 9 ? "0" : "") + Integer.toString(left)));
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
