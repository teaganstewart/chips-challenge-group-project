package nz.ac.vuw.ecs.swen225.a3.application.ui;

import nz.ac.vuw.ecs.swen225.a3.application.Game;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class InfoPanel extends JPanel {

    private Game game;
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
        this.setBackground(Color.RED);


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


    }

    public JPanel inventory(){
        JPanel storage = new JPanel();
        storage.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.gridx = 0;
        c.gridy = 0;
        //c.insets = new Insets(5,5,5,5);

        for(int i =0; i < 8;i++){
            JLabel item = new JLabel();
            item.setIcon(slotIcon);
            item.setBorder(BorderFactory.createMatteBorder(2,2,2,2,Color.black));
            storage.add(item,c);
            c.gridx ++;
            if(i == 3){
                c.gridx=0;
                c.gridy++;
            }
        }
        storage.setVisible(true);
        return storage;
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
