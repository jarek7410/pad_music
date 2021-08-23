package github.jarek7410.pud_music;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class Window extends JPanel implements ActionListener {
    private final Config config;
    private final Color myBlack = new Color(30,39,46);
    private final Color CITI_LIGHTS =new Color(223,228,234);
    private final String[] trackName;
    private JPanel nameSong;
    private JLabel titleLabel;
    private JPanel nameTrack;
    private JPanel actionButtonsPanel;

    private String nameOfSong;
    private String track;

    private JButton up, playStop,left,right;
    private JButton[] actionButtonList;

    public Window(Config config) {
        this.config=config;
        this.trackName=config.getTracksNames();
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

        setActionButtons();

        this.add(titleLabel);
        this.add(nameSong);
        this.add(nameTrack);
        this.add(actionButtonsPanel);

        sLayout.putConstraint(SpringLayout.NORTH,titleLabel,5,SpringLayout.NORTH,this);
        sLayout.putConstraint(SpringLayout.NORTH, nameSong, 5, SpringLayout.SOUTH, titleLabel);
        sLayout.putConstraint(SpringLayout.NORTH, nameTrack, 5, SpringLayout.SOUTH, nameSong);
        sLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER,nameTrack,0,SpringLayout.HORIZONTAL_CENTER,this);
        sLayout.putConstraint(SpringLayout.NORTH, actionButtonsPanel, 5, SpringLayout.SOUTH, nameTrack);
        sLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, actionButtonsPanel,0,SpringLayout.HORIZONTAL_CENTER,this);

    }
    private void setActionButtons(){
        actionButtonsPanel = new JPanel();
        actionButtonsPanel.setPreferredSize(new Dimension(200,35*config.getNumberOfTreks()));
        SpringLayout actButtonLayout = new SpringLayout();

        actionButtonsPanel.setLayout(actButtonLayout);
        Actions[] a=Actions.values();
        actionButtonList =new JButton[a.length];
        for (int i = 0; i < a.length; i++) {
            JButton jButton = new JButton(a[i].name());
            jButton.setName(a[i].name());
            jButton.setPreferredSize(new Dimension(80, 35));
            actionButtonList[i] = jButton;
            //System.err.println(a[i].name()+"\t"+i);
        }
        for(int i=3;i<config.getNumberOfTreks()+3;i++){
            actionButtonList[i].setText(trackName[i-3]);
        }
        for (int i = 0; i < config.getNumberOfTreks() + 3; i++) {
            actionButtonList[i].addActionListener(this);
        }

        actionButtonsPanel.add(actionButtonList[3]);
        actButtonLayout.putConstraint(SpringLayout.NORTH, actionButtonList[3], 0, SpringLayout.NORTH, actionButtonsPanel);
        actButtonLayout.putConstraint(SpringLayout.WEST, actionButtonList[3], 0, SpringLayout.WEST, actionButtonsPanel);
        for (int i = 4; i < config.getNumberOfTreks() + 3; i++) {
            actionButtonsPanel.add(actionButtonList[i]);
            actButtonLayout.putConstraint
                    (SpringLayout.NORTH, actionButtonList[i], 0, SpringLayout.SOUTH, actionButtonList[i - 1]);
        }
        actionButtonList[1].addActionListener(this);
        actionButtonsPanel.add(actionButtonList[1]);
        actButtonLayout.putConstraint(SpringLayout.NORTH,actionButtonList[1],5,SpringLayout.NORTH,actionButtonsPanel);
        actButtonLayout.putConstraint(SpringLayout.EAST,actionButtonList[1],-5,SpringLayout.EAST,actionButtonsPanel);
        actionButtonList[2].addActionListener(this);
        actionButtonList[2].setPreferredSize(new Dimension(100,35));
        actionButtonsPanel.add(actionButtonList[2]);
        actButtonLayout.putConstraint(SpringLayout.NORTH,actionButtonList[2],5,SpringLayout.SOUTH,actionButtonList[1]);
        actButtonLayout.putConstraint(SpringLayout.EAST,actionButtonList[2],-5,SpringLayout.EAST,actionButtonsPanel);

    }
    private void pressButtonAction(int i){

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
        Object source = e.getSource();
        if (actionButtonList[1].equals(source)) {
            Main.use("STOP");
        } else if (actionButtonList[2].equals(source)) {
            Main.use("CHANGE");
        } else if (actionButtonList[3].equals(source)) {
            JButton button= (JButton) source;
            Main.use(button.getName());
        }else if (actionButtonList[4].equals(source)) {
            JButton button= (JButton) source;
            Main.use(button.getName());
        }else if (actionButtonList[5].equals(source)) {
            JButton button= (JButton) source;
            Main.use(button.getName());
        }else if (actionButtonList[6].equals(source)) {
            JButton button= (JButton) source;
            Main.use(button.getName());
        }else{
            throw new IllegalStateException("Unexpected value: " + e.getSource());
        }

    }
}
