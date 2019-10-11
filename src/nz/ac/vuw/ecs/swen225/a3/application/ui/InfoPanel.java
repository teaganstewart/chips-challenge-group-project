package nz.ac.vuw.ecs.swen225.a3.application.ui;

import nz.ac.vuw.ecs.swen225.a3.application.Game;
import nz.ac.vuw.ecs.swen225.a3.maze.Maze;
import nz.ac.vuw.ecs.swen225.a3.recnplay.ReplayUtils;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import java.awt.*;
import java.util.Date;
import java.util.Scanner;

/**
 * The InfoPanel on the side of the window, holds the game information,
 * buttons and the inventory.
 * 
 * @authors Meng Veng Taing - 300434816, Teagan Stewart - 300407769, 
 *  		Ethan Munn - 300367257.
 *
 */
public class InfoPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private Game game;
	private GUI gui;
	private JPanel inv, storage, hint, timer, chip, level, rec;

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
	
	/**
	 * The constructor for the information panel.
	 * 
	 * @param game The game used for the information.
	 * @param gui The gui that the panel is in.
	 */
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

	/**
	 * Method for rendering the inventory JPanel, and renders the 
	 * JPanels into the inventory each time something is added.
	 * 
	 * @return inv Returns the inventory panel.
	 */
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

	/**
	 * Creates the panel that displays treasures left or action 
	 * records left.
	 * 
	 * @return chip Returns treasure panel.
	 */
	public JPanel chipPanel() {
		chip = new JPanel();
		chip.setPreferredSize(new Dimension(220,50));
		chip.setMaximumSize(new Dimension(220,50));
		chip.setMinimumSize(new Dimension(220,50));
		chip.setBackground(new Color(65, 65, 73));
		
		displayChips(false, 0,0);
		return chip;
	}

	/**
	 * Creates the hint panel to help the player when they are stuck.
	 * 
	 * @return hint Returns the hint panel.
	 */
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

	/**
	 * Creates the level panel, shows level number.
	 * 
	 * @return level Returns level panel.
	 */
	public JPanel levelPanel() {
		level = new JPanel();
		level.setMinimumSize(new Dimension(220,40));
		level.setBackground(new Color(65, 65, 73));
		setLevelDisplay(false, false);
		return level;
	}

	/**
	 * Displays level if in game mode, if in replay mode will display the
	 * time that the replay started.
	 * 
	 * @param replayMode True if in a replay.
	 * @param flash Whether the circle next to play is shown or not.
	 */
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

	/**
	 * Sets the hint box to empty if in game mode, and to the controls if in 
	 * replay mode.
	 * 
	 * @param replayMode
	 */
	public void setDefaultHint(boolean replayMode) {
		try {
			hint.remove(0);
		} catch (ArrayIndexOutOfBoundsException e) {}
		// I wish I could justify this being necessary, but it's the only way to fix the refreshing of the GUI
		
		Border type = BorderFactory.createLineBorder(Color.GREEN);
		Border border = BorderFactory.createTitledBorder(type, replayMode ? "Replay Controls:" : "Current Hint:", TitledBorder.CENTER, TitledBorder.TOP, new Font("Calibri", Font.BOLD, 14), Color.white);
		hint.setBorder(border);
		
		JLabel text = new JLabel(replayMode ?
								"<html>\"space\" to play/pause, \"D\" to rewind"+
								"<br>\"F\" to fast forward, \"S\" to skip"+
								"<br>\".\" to move forward one frame"+
								"<br>\",\" to move backward one frame<html>":
								"<html>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;...&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;"+
								"<br>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;"+
								"<br>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;"+
								"<br>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;</html>");
		text.setForeground(Color.white);
		hint.add(text);
	}

	/**
	 * Sets the hint, for when the player is standing on a hint tile.
	 * 
	 * @param message The message in the hint
	 */
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
		sc.close();
		wrapped.append("</html>");
		JLabel text = new JLabel(wrapped.toString());
		text.setForeground(Color.LIGHT_GRAY);
		hint.add(text);
	}

	/**
	 * Create a time panel for displaying time left/ time used in 
	 * replay mode.
	 * 
	 * @return timer Returns the time panel.
	 */
	public JPanel timePanel() {

		timer = new JPanel();
		timer.setPreferredSize(new Dimension(220,50));
		timer.setMinimumSize(new Dimension(220,50));
		timer.setBackground(new Color(65, 65, 73));
		
		displayTime(false);
		return timer;
	}

	/**
	 * Display the time depending on the replay mode.
	 * 
	 * @param replayMode True if replaying.
	 */
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

	/**
	 * Convert time to mins in format.
	 */
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
	 * Create panel for the replay.
	 * @return
	 */
	JPanel recnplayPanel() {
	
		rec = new JPanel(new GridLayout(1,6,0,0));
		rec.setMinimumSize(new Dimension(220,40));
		rec.setBackground(new Color(65, 65, 73));
		for(int i=0; i<6;i++) {
			rec.add(new JLabel());
		}
		return rec;
	}


	/**
	 * Update the replay action on button event.
	 * @param replayMode
	 */
	void updateRec(boolean replayMode) {
		try {
			for(int i=0; i<6;i++) {
				rec.remove(0);
			}
		} catch (NullPointerException e) {}

		if(replayMode) {

			// button for rewinding
			JButton rewindButton = new JButton();
			rewindButton.setIcon(rewindIcon);
			rewindButton.addActionListener(event -> {
				if(!pause) { GUI.stopTimer();}
				GUI.setSpeed(10);
				GUI.setKeyFrame(0);
				GUI.setRecIndex(0);
				skipReset();
				if(!pause) {GUI.startTimer();}

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
	        	if(!pause) { GUI.stopTimer();}
				GUI.setRecIndex(Math.max(0,gui.getRecIndex()-1));
				GUI.setKeyFrame(ReplayUtils.roundTimeToTen(ReplayUtils.getActionRecord(gui.getRecIndex()).getTimeSinceLevelStart()));

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
				if(pause) GUI.startTimer();
				else GUI.stopTimer();
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
	        	if(!pause) { GUI.stopTimer();}
	        	GUI.setRecIndex(Math.min(gui.getRecIndex()+1,ReplayUtils.replaySize()-1));
	        	GUI.setKeyFrame(ReplayUtils.roundTimeToTen(ReplayUtils.getActionRecord(gui.getRecIndex()).getTimeSinceLevelStart()));
	        
	        	skipReset();

	        });
			rec.add(forwardOneButton);
			
			// button for speeding up recording
			JButton fastButton = new JButton();
			fastButton.setIcon(fastIcon);
			fastButton.addActionListener(event -> {
				if(!pause) {
					GUI.stopTimer();
				}
				GUI.setSpeed(gui.getSpeed() == 3 ? 10 : 3);
				gui.setupTimer();
				if(!pause) {
					GUI.startTimer();
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
				if (game.getLevelNum() < gui.getLevelCount()) {
					GUI.stopTimer();
					GUI.setReplayMode(false);
					gui.saveReplayPopup();
					GUI.setSpeed(10);
					game.loadLevel(null, game.getLevelNum()+1);
					GUI.main.setFocusable(true);
					gui.updateBoard();
				}else{
					gui.saveReplayPopup();
					JOptionPane.showMessageDialog(null, "Congratulation, You have won the game");
					gui.finishLevelWindow();
				}

			});
			skipButton.setBorderPainted(false); 
	        skipButton.setContentAreaFilled(false); 
	        skipButton.setFocusPainted(false); 
	        skipButton.setOpaque(false);
			rec.add(skipButton);

			//Make all the button not focusable so shortcut keys will work
			playButton.setFocusable(false);
			skipButton.setFocusable(false);
			fastButton.setFocusable(false);
			fastButton.setFocusable(false);
			backOneButton.setFocusable(false);
			forwardOneButton.setFocusable(false);
		}
		else {
			for(int i=0; i<6;i++) {
				rec.add(new JLabel());
			}
		}

	}

	/**
	 * Update board replay.
	 */
	public void skipReset() {

    	Maze m = ReplayUtils.getActionRecord(gui.getRecIndex()).getMaze();

    	game.getRender().setMaze(m);
    	game.setMaze(m);
    	gui.updateBoard();
    	if(!pause) {
    		GUI.startTimer();
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
		JLabel[] inventory = game.getRender().renderInventory();

		for(int i =0; i < 8;i++){
			JLabel item = inventory[i];

			item.setBorder(BorderFactory.createMatteBorder(1,1,1,1,Color.WHITE));
			storage.add(item);
		}
	}

	/**
	 * Displays the treasure count if in game mode, and the number of
	 * actions done if in replay mode.
	 * 
	 * @param replayMode
	 * @param first First number, to be displayed on left.
	 * @param secnd Second number, to be displayed on right.
	 */
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

	@Override
	public void paintComponent(Graphics g){
	
		g.drawImage(infoIcon.getImage(), 1, 30, getWidth()-30, getHeight()-60, null);
	}

	/**
	 * Creates image icons.
	 * 
	 * @param filename The file we want the image of.
	 * @return Returns imageIcon if it exists.
	 */
	public ImageIcon makeImageIcon(String filename){
		java.net.URL imageUrl = this.getClass().getResource(filename);
		try {
			if (imageUrl == null) { return null; }

			return new ImageIcon(imageUrl);
		} catch(Exception e){
			return null;
		}
	}
	
	/**
	 * Sets whether the replay is paused or not.
	 * 
	 * @param pause True if wants to be paused, false if not.
	 */
	public void setPause(boolean pause) {
		this.pause = pause;
	}

	/**
	 * Gets whether the replay is paused or not.
	 * 
	 * @return pause True if paused.
	 */
	public boolean getPause() {
		return pause;
	}
}