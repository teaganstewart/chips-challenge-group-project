package nz.ac.vuw.ecs.swen225.a3.application.ui;

import nz.ac.vuw.ecs.swen225.a3.application.Game;
import nz.ac.vuw.ecs.swen225.a3.maze.Maze;
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

	private boolean pause = false;
	private ImageIcon infoIcon = makeImageIcon("icons/InfoBackground2.png");
	private ImageIcon slotIcon = makeImageIcon("icons/Slot.png");
	private ImageIcon playIcon = makeImageIcon("icons/play.png");
	private ImageIcon pauseIcon = makeImageIcon("icons/pause.png");
	private ImageIcon forwardOne = makeImageIcon("icons/forwardOne.png");
	private ImageIcon backOne = makeImageIcon("icons/backOne.png");
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

		JLabel text = new JLabel("Inventory:");
		text.setForeground(Color.white);
		inv.add(text,c);
		inv.setBackground(new Color(65, 65, 73));

		c.gridy = 1;

		storage = new JPanel(new GridLayout(2,4));
		storage.setBorder(BorderFactory.createEmptyBorder(10,0,0,0));
		storage.setBackground(new Color(65, 65, 73));
		JLabel[] inventory = game.getRender().renderInventory();

		for(int i =0; i < 8;i++){
			JLabel item = inventory[i];
			if(item==null) {
				item = new JLabel();
				item.setIcon(slotIcon);
			}
			item.setBorder(BorderFactory.createMatteBorder(1,1,1,1,Color.WHITE));
			storage.add(item);
		}
		storage.setVisible(true);

		inv.add(storage, c);
		return inv;
	}

	public JPanel chipPanel() {
		chip = new JPanel();
		chip.setPreferredSize(new Dimension(220,50));
		chip.setMaximumSize(new Dimension(220,50));
		chip.setMinimumSize(new Dimension(220,50));
		chip.setBackground(new Color(65, 65, 73));
		
		displayChips(false, 0,0);
		return chip;
	}

	public JPanel hintPanel() {
		hint = new JPanel();
		hint.setPreferredSize(new Dimension(220,100));
		hint.setMaximumSize(new Dimension(220,100));
		hint.setMinimumSize(new Dimension(220,100));
		hint.setBackground(new Color(65, 65, 73));
		
		Border type = BorderFactory.createLineBorder(Color.GREEN);
		Border border = BorderFactory.createTitledBorder(type, "Current Hint:", TitledBorder.CENTER, TitledBorder.TOP, new Font ("Calibri", Font.BOLD, 14), Color.WHITE);
		hint.setBorder(border);
		setDefaultHint(false);
		return hint;
	}

	public JPanel levelPanel() {
		level = new JPanel();
		level.setMinimumSize(new Dimension(220,40));
		level.setBackground(new Color(65, 65, 73));
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
		JLabel text = new JLabel(!replayMode ?
				"<html>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;Level: " + Integer.toString(num) + "&emsp;&emsp;"+
				"<br>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;</html>":
					(pause ? "<html>&emsp;&emsp;&emsp;&emsp;&emsp;PAUSED \u23F3 " :
					"<html>&emsp;&emsp;&emsp;&emsp;     PLAYBACK  " + (flash ? "\u25CF  " : "     " ))+
					"<br>" + new Date(ReplayUtils.getStartTime()).toString() + "</html>");
		text.setForeground(Color.WHITE);
		level.add(text);
	}

	public void setDefaultHint(boolean replayMode) {
		try {
			hint.remove(0);
		} catch (ArrayIndexOutOfBoundsException e) {}
		// I wish I could justify this being necessary, but it's the only way to fix the refreshing of the GUI
		
		Border type = BorderFactory.createLineBorder(Color.GREEN);
		Border border = BorderFactory.createTitledBorder(type, replayMode ? "Replay Controls:" : "Current Hint:", TitledBorder.CENTER, TitledBorder.TOP, new Font("Calibri", Font.BOLD, 14), Color.white);
		hint.setBorder(border);
		
		JLabel text = new JLabel(replayMode ?
								"<html>## to play, ## to pause"+
								"<br>## to fast forward"+
								"<br>## to move forward one frame"+
								"<br>## to move backward one frame<html>":
								"<html>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;...&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;"+
								"<br>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;"+
								"<br>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;"+
								"<br>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;</html>");
		text.setForeground(Color.white);
		hint.add(text);
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
		JLabel text = new JLabel(wrapped.toString());
		text.setForeground(Color.LIGHT_GRAY);
		hint.add(text);
	}

	public JPanel timePanel() {

		timer = new JPanel();
		timer.setPreferredSize(new Dimension(220,50));
		timer.setMinimumSize(new Dimension(220,50));
		timer.setBackground(new Color(65, 65, 73));
		
		displayTime(false);
		return timer;
	}

	public void displayTime(boolean replayMode) {
		try {
			timer.remove(0);
			timer.remove(0);
		} catch (ArrayIndexOutOfBoundsException e) {}

		Border type = BorderFactory.createLineBorder(Color.GREEN);
		Border border = BorderFactory.createTitledBorder(type, !replayMode ? "Total Time:" : "Clear Time:", TitledBorder.CENTER, TitledBorder.TOP, new Font ("Calibri", Font.BOLD, 14), Color.WHITE);
		
		timer.setBorder(border);

		JLabel text = new JLabel(!replayMode ? timeToMins(game.getTime()) : timeToMins(game.getLevel().getTimeAllowed() - game.getTime()));
		text.setForeground(Color.WHITE);
		timer.add(text);
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
	
		rec = new JPanel(new GridLayout(1,6,0,0));
		rec.setMinimumSize(new Dimension(220,40));
		for(int i=0; i<6;i++) {
			rec.add(new JLabel());
		}
		return rec;
	}

	void updateRec(boolean replayMode) {
		try {
			for(int i=0; i<6;i++) {
				rec.remove(0);
			}
		} catch (ArrayIndexOutOfBoundsException e) {}

		if(replayMode) {

			// button for rewinding
			JButton rewindButton = new JButton();
			rewindButton.setIcon(rewindIcon);
			rewindButton.addActionListener(event -> {
				if(!pause) { gui.stopTimer();}
				gui.setSpeed(10);
				gui.setKeyFrame(0);
				gui.setRecIndex(0);
				skipReset();
				if(!pause) {gui.startTimer();}
				gui.main.setFocusable(true);
			});
			
			rewindButton.setBorderPainted(false); 
	        rewindButton.setContentAreaFilled(false); 
	        rewindButton.setFocusPainted(false); 
	        rewindButton.setOpaque(false);
			rec.add(rewindButton);
		
			// button for moving back one frame
			JButton backOneButton = new JButton();
			
			backOneButton.setBorderPainted(false); 
			backOneButton.setContentAreaFilled(false); 
	        backOneButton.setFocusPainted(false); 
	        backOneButton.setOpaque(false);
	        backOneButton.setIcon(backOne);
	        backOneButton.addActionListener(event -> {
	        	if(!pause) { gui.stopTimer();}
	        	gui.setRecIndex(Math.max(0,gui.getRecIndex()-1));
	        	gui.setKeyFrame(ReplayUtils.roundTimeToTen(ReplayUtils.getActionRecord(gui.getRecIndex()).getTimeSinceLevelStart()));
	        	
	        	skipReset();
	        });
			rec.add(backOneButton);
			
			// button for toggling between play and pause
			JButton playButton = new JButton();
			if(pause) playButton.setIcon(playIcon);
			else playButton.setIcon(pauseIcon);
					
			playButton.setBorderPainted(false); 
	        playButton.setContentAreaFilled(false); 
	        playButton.setFocusPainted(false); 
	        playButton.setOpaque(false);
	        rec.add(playButton);
	        
	        playButton.addActionListener(event -> { 
				if(pause) gui.startTimer();
				else gui.stopTimer();
				pause = !(pause);
				gui.updateBoard();
			});
			
	        // button for moving forward one frame
	        JButton forwardOneButton = new JButton();
	        
	        forwardOneButton.setBorderPainted(false); 
			forwardOneButton.setContentAreaFilled(false); 
	        forwardOneButton.setFocusPainted(false); 
	        forwardOneButton.setOpaque(false);
	        forwardOneButton.setIcon(forwardOne);
	        forwardOneButton.addActionListener(event -> {
	        	if(!pause) { gui.stopTimer();}
	        	gui.setRecIndex(Math.min(gui.getRecIndex()+1,ReplayUtils.replaySize()-1));
	        	gui.setKeyFrame(ReplayUtils.roundTimeToTen(ReplayUtils.getActionRecord(gui.getRecIndex()).getTimeSinceLevelStart()));
	        
	        	skipReset();

	        });
			rec.add(forwardOneButton);
			
			// button for speeding up recording
			JButton fastButton = new JButton();
			fastButton.setIcon(fastIcon);
			fastButton.addActionListener(event -> {
				if(!pause) {
					gui.stopTimer();
				}
				gui.setSpeed(gui.getSpeed() == 3 ? 10 : 3);
				gui.setupTimer();
				if(!pause) {
					gui.startTimer();
				}

			});
			fastButton.setBorderPainted(false); 
	        fastButton.setContentAreaFilled(false); 
	        fastButton.setFocusPainted(false); 
	        fastButton.setOpaque(false);
			rec.add(fastButton);
			
			
			JButton skipButton = new JButton();
			skipButton.setIcon(skipIcon);
			skipButton.addActionListener(event -> {
				if (game.getLevelNum() < 2) {
					gui.stopTimer();
					gui.setReplayMode(false);
					gui.setSpeed(10);
					game.loadLevel(null, game.getLevelNum()+1);
				}
			});
			skipButton.setBorderPainted(false); 
	        skipButton.setContentAreaFilled(false); 
	        skipButton.setFocusPainted(false); 
	        skipButton.setOpaque(false);
			rec.add(skipButton);
		}
		else {
			for(int i=0; i<6;i++) {
				rec.add(new JLabel());
			}
		}
	}

	private void skipReset() {

    	Maze m = ReplayUtils.getActionRecord(gui.getRecIndex()).getMaze();
    	int t = ReplayUtils.getActionRecord(gui.getRecIndex()).getTimeSinceLevelStart();

    	game.getRender().setMaze(m);
    	game.setMaze(m);
    	gui.updateBoard();
    	if(!pause) {
    		gui.startTimer();
    	}
	}
	
	/**
	 * Clears the game panel so it can be redrawn.
	 */
	public void clearInventory() {
		try{
			for (int y = 0; y < 8; y++) {
				storage.remove(0);
			}
		}catch(IndexOutOfBoundsException e){

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

			item.setBorder(BorderFactory.createMatteBorder(1,1,1,1,Color.WHITE));
			storage.add(item);
		}
	}

	public void displayChips(boolean replayMode, int first, int secnd) {
		try {
			chip.remove(0);
		} catch (ArrayIndexOutOfBoundsException e) {}

		Border type = BorderFactory.createLineBorder(Color.GREEN);
		Border border = BorderFactory.createTitledBorder(type, !replayMode ? "Treasures Collected:" : "Total Board Events:",
				TitledBorder.CENTER, TitledBorder.TOP, new Font ("Calibri", Font.BOLD, 14), Color.WHITE);
		chip.setBorder(border);

		String form = String.format("%d / %d", first, secnd);
		JLabel text = new JLabel(form);
		text.setForeground(Color.WHITE);
		chip.add(text);
	}

	public void paintComponent(Graphics g){
	
		g.drawImage(infoIcon.getImage(), 1, 30, getWidth()-30, getHeight()-60, null);
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
	
	public void setPause(boolean pause) {
		this.pause = pause;
	}

	public boolean getPause() {
		return pause;
	}
}