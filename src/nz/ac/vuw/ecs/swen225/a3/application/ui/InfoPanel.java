package nz.ac.vuw.ecs.swen225.a3.application.ui;

import nz.ac.vuw.ecs.swen225.a3.application.Game;
import nz.ac.vuw.ecs.swen225.a3.maze.Treasure;
import nz.ac.vuw.ecs.swen225.a3.recnplay.ReplayUtils;
import nz.ac.vuw.ecs.swen225.a3.render.Render;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import java.awt.*;
import java.util.Date;
import java.util.Scanner;

public class InfoPanel extends JPanel {

	private Game game;
	private GUI gui;
	private JPanel inv;
	private JPanel storage;
	private JPanel hint;
	private JPanel timer;
	private JPanel chip;
	private JPanel level;
	private JPanel rec;

	private ImageIcon infoIcon = makeImageIcon("icons/InfoBackground.png");
	private ImageIcon slotIcon = makeImageIcon("icons/Slot.png");
	private ImageIcon playIcon = makeImageIcon("icons/play.png");
	private ImageIcon pauseIcon = makeImageIcon("icons/pause.png");
	private ImageIcon fastIcon = makeImageIcon("icons/fast.png");
	private ImageIcon rewindIcon = makeImageIcon("icons/rewind.png");
	private ImageIcon skipIcon = makeImageIcon("icons/skip.png");

	public InfoPanel (Game game, GUI gui){

		this.game = game;
		this.gui = gui;
		this.setLayout(new GridBagLayout());
		this.setPreferredSize(new Dimension(300,200));

		this.setBorder(BorderFactory.createEmptyBorder(30,0,30,30));

		GridBagConstraints c = new GridBagConstraints();

		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth =1;
		c.insets = new Insets(0,0,15,0);  //top padding

		this.add(levelPanel(),c);
		c.gridy = 1;
		this.add(timePanel(),c);
		c.gridy = 2;
		this.add(chipPanel(),c);
		c.gridy = 3;
		this.add(hintPanel(),c);
		c.gridy = 5;
		this.add(inventory(),c);
		c.gridy = 6;
		this.add(recnplayPanel(),c);

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

		displayChips(false, 0,0);
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
		level.setMinimumSize(new Dimension(200,40));
		level.setBackground(Color.white);

		setLevelDisplay(false, false);
		return level;
	}

	public void setLevelDisplay(boolean replayMode, boolean flash) {
		try {
			level.remove(0);
		} catch (ArrayIndexOutOfBoundsException e) {}

		int num = 0;
		try {
			num = game.getLevelNum();
		} catch (NullPointerException e) {}
		level.add(new JLabel(!replayMode ?
				"<html>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;Level: " + Integer.toString(num) + "&emsp;&emsp;"+
				"<br>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;</html>":

					"<html>&emsp;&emsp;&emsp;&emsp;&emsp;PLAYBACK  " + (flash ? "\u25CF  " : "     " )+
					"<br>" + new Date(ReplayUtils.getStartTime()).toString() + "</html>"));
	}

	public void setDefaultHint() {
		try {
			hint.remove(0);
		} catch (ArrayIndexOutOfBoundsException e) {}
		// I wish I could justify this being necessary, but it's the only way to fix the refreshing of the GUI
		hint.add(new JLabel("<html>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;...&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;"+
							"<br>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;"+
							"<br>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;"+
							"<br>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;</html>"));
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
		timer.setMinimumSize(new Dimension(200,50));
		timer.setBackground(Color.white);

		displayTime(false);
		return timer;
	}

	public void displayTime(boolean replayMode) {
		try {
			timer.remove(0);
			timer.remove(0);
		} catch (ArrayIndexOutOfBoundsException e) {}

		Border type = BorderFactory.createLineBorder(Color.white);
		Border border = BorderFactory.createTitledBorder(type, !replayMode ? "Total Time:" : "Clear Time:", TitledBorder.CENTER, TitledBorder.TOP);
		timer.setBorder(border);

		timer.add(new JLabel(!replayMode ? timeToMins(game.getTime()) : timeToMins(game.getLevel().getTimeAllowed() - game.getTime())));
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

	JPanel recnplayPanel() {

		rec = new JPanel(new GridLayout(1,5,5,0));
		rec.setMinimumSize(new Dimension(220,40));
		for(int i=0; i<5;i++) {
			rec.add(new JLabel());
		}
		return rec;
	}

	void updateRec(boolean replayMode) {
		try {
			for(int i=0; i<5;i++) {
				rec.remove(0);
			}
		} catch (NullPointerException e) {}

		if(replayMode) {

			JButton playButton = new JButton();
			playButton.setIcon(playIcon);
			playButton.setOpaque(false);
			playButton.addActionListener(event -> gui.startTimer());
			rec.add(playButton);

			JButton pauseButton = new JButton();
			pauseButton.setIcon(pauseIcon);
			pauseButton.setOpaque(false);
			pauseButton.addActionListener(event -> gui.stopTimer());
			rec.add(pauseButton);

			JButton fastButton = new JButton();
			fastButton.setIcon(fastIcon);
			fastButton.setOpaque(false);
			fastButton.addActionListener(event -> {
				gui.stopTimer();
				gui.setSpeed(3);
				gui.setupTimer();
				gui.startTimer();
			});
			rec.add(fastButton);

			JButton rewindButton = new JButton();
			rewindButton.setIcon(rewindIcon);
			rewindButton.setOpaque(false);
			rewindButton.addActionListener(event -> {
				gui.stopTimer();
				gui.setSpeed(10);
				gui.setKeyFrame(0);
				gui.setRecIndex(0);
				gui.startTimer();
			});
			rec.add(rewindButton);

			JButton skipButton = new JButton();
			skipButton.setIcon(skipIcon);
			skipButton.setOpaque(false);

			skipButton.addActionListener(event -> {
				if (game.getLevelNum() < 3) {
					gui.stopTimer();
					gui.setReplayMode(false);
					gui.setSpeed(10);
					game.loadLevel(null, game.getLevelNum()+1);
				}
			});
			rec.add(skipButton);
		}
		else {
			for(int i=0; i<5;i++) {
				rec.add(new JLabel());
			}
		}
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

	public void displayChips(boolean replayMode, int first, int secnd) {
		try {
			chip.remove(0);
		} catch (ArrayIndexOutOfBoundsException e) {}

		Border type = BorderFactory.createLineBorder(Color.white);
		Border border = BorderFactory.createTitledBorder(type, !replayMode ? "Treasures Remaining:" : "Total Board Events:",
				TitledBorder.CENTER, TitledBorder.TOP);
		chip.setBorder(border);

		String form = String.format("%d / %d", first, secnd);
		chip.add(new JLabel(form));
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

	public boolean getPause() {
		return pause;
	}
}
