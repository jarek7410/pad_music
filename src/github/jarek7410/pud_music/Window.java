package github.jarek7410.pud_music;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Window extends JPanel implements ActionListener {
    private final Config config;
    private final Color myBlack = new Color(30,39,46);
    private final Color CITI_LIGHTS =new Color(223,228,234);
    private JPanel nameSong;
    private JLabel titleLabel;
    private JPanel nameTrack;
    private JPanel actionButtons;

    private String nameOfSong;
    private String track;

    private JButton up, playStop,left,right;

    public Window(Config config) {
        this.config=config;
        //this.setBackground(Color.GREEN);
        SpringLayout sLayout = new SpringLayout();
        this.setLayout(sLayout);
        this.nameSong = new JPanel();
        this.nameTrack = new JPanel();
        this.setBackground(CITI_LIGHTS);
        this.nameOfSong = "songName";
        this.setPreferredSize(config.winDim);
        titleLabel = new JLabel("song info");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        this.nameSong.setPreferredSize
                (new Dimension((int) config.winDim.getWidth(),30));
        this.titleLabel.setPreferredSize
                (new Dimension((int) config.winDim.getWidth(),30));

        actionButtons = new JPanel();
        actionButtons.setPreferredSize(new Dimension(190,50));
        SpringLayout actButtonLayout = new SpringLayout();

        actionButtons.setLayout(actButtonLayout);

        up = new JButton("up");
        playStop = new JButton("down");
        left = new JButton("left");
        right = new JButton("right");

        actionButtons.add(up);
        actionButtons.add(left);
        actionButtons.add(playStop);
        actionButtons.add(right);

        actButtonLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER,up,0,SpringLayout.HORIZONTAL_CENTER,actionButtons);
        actButtonLayout.putConstraint(SpringLayout.NORTH,up,0,SpringLayout.NORTH,actionButtons);
        actButtonLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, playStop,0,SpringLayout.HORIZONTAL_CENTER,actionButtons);
        actButtonLayout.putConstraint(SpringLayout.NORTH,left,0,SpringLayout.SOUTH,up);
        actButtonLayout.putConstraint(SpringLayout.NORTH,right,0,SpringLayout.SOUTH,up);
        actButtonLayout.putConstraint(SpringLayout.NORTH, playStop,0,SpringLayout.SOUTH,up);
        actButtonLayout.putConstraint(SpringLayout.EAST,left,0,SpringLayout.WEST, playStop);
        actButtonLayout.putConstraint(SpringLayout.WEST,left,0,SpringLayout.WEST,actionButtons);
        actButtonLayout.putConstraint(SpringLayout.WEST,right,0,SpringLayout.EAST, playStop);
        actButtonLayout.putConstraint(SpringLayout.EAST,right,0,SpringLayout.EAST,actionButtons);


        this.add(titleLabel);
        this.add(nameSong);
        this.add(nameTrack);
        this.add(actionButtons);

        sLayout.putConstraint(SpringLayout.NORTH,titleLabel,5,SpringLayout.NORTH,this);
        sLayout.putConstraint(SpringLayout.NORTH, nameSong, 5, SpringLayout.SOUTH, titleLabel);
        sLayout.putConstraint(SpringLayout.NORTH, nameTrack, 5, SpringLayout.SOUTH, nameSong);
        sLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER,nameTrack,0,SpringLayout.HORIZONTAL_CENTER,this);
        sLayout.putConstraint(SpringLayout.NORTH, actionButtons, 5, SpringLayout.SOUTH, nameTrack);
        sLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER,actionButtons,0,SpringLayout.HORIZONTAL_CENTER,this);


    }
     synchronized public void updatePanel(){
        this.nameSong.removeAll();
        this.nameSong.add(new JLabel("song name: "));

        this.nameSong.add(nameOf(nameOfSong));

        this.nameTrack.removeAll();
        this.nameTrack.add(new JLabel("track localization: "));

        this.nameTrack.add(nameOf(track));
    }
    private JTextField nameOf(String s){
        JTextField textField = new JTextField();
        textField.setText(s);
        textField.setBorder(BorderFactory.createEmptyBorder());
        textField.setVisible(true);
        return textField;
    }
    synchronized public void setPause(){
        this.nameSong.removeAll();
        this.nameTrack.removeAll();
        this.nameSong.add(new JLabel("music is stopped"));

    }
    synchronized public void setSong(String name){
        this.nameOfSong=name;
    }

    synchronized public void setTrack(String path){
        this.track=path;
    }



    public void setAsDisconnected() {
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
