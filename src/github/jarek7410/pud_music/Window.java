package github.jarek7410.pud_music;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
    private String track="no selected";
    private String name="-";

    private JButton up, playStop,left,right;
    private JButton[] actionButtonList;
    private Boolean[] actionButtonState;
    protected int lastPressedActionButton=1;
    protected int lastTrackPlayed;

    private JLabel[] infoLabel;
    private JTextField[] infoField;

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

        setTextField();
    }


    private void setTextField() {
        infoLabel=new JLabel[3];

        infoLabel[0]=new JLabel("song name: ");
        infoLabel[1]=new JLabel("track Name: ");
        infoLabel[2]=new JLabel("track localization: ");

        infoField=new JTextField[3];

        infoField[0]=nameOf(nameOfSong);
        infoField[1]=nameOf(name);
        infoField[2]=nameOf(track);

    }

    private void setActionButtons(){
        actionButtonsPanel = new JPanel();
        if(track.length()<=4)actionButtonsPanel.setPreferredSize(new Dimension(200,35*4));
        else actionButtonsPanel.setPreferredSize(new Dimension(280,35*4));
        SpringLayout actButtonLayout = new SpringLayout();

        actionButtonsPanel.setLayout(actButtonLayout);
        Actions[] a=Actions.values();
        actionButtonList =new JButton[a.length];
        actionButtonState = new Boolean[a.length];
        for (int i = 0; i < a.length; i++) {
            JButton jButton = new JButton(a[i].name());
            jButton.setName(a[i].name());
            jButton.setPreferredSize(new Dimension(90, 35));
            actionButtonList[i] = jButton;
            actionButtonState[i]=true;
            //System.err.println(a[i].name()+"\t"+i);
        }
        lastButtonPressed(1);
        actionButtonList[2].setEnabled(false);
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
            if(i<=6)
                actButtonLayout.putConstraint
                    (SpringLayout.NORTH, actionButtonList[i], 0, SpringLayout.SOUTH, actionButtonList[i - 1]);
            else {
                //System.err.println(i);
                actButtonLayout.putConstraint
                        (SpringLayout.WEST, actionButtonList[i], 0, SpringLayout.EAST, actionButtonList[i - 4]);
                if(i!=7)actButtonLayout.putConstraint
                        (SpringLayout.NORTH, actionButtonList[i], 0, SpringLayout.SOUTH, actionButtonList[i - 1]);

            }
        }
        actionButtonList[1].addActionListener(this);
        actionButtonList[1].setPreferredSize(new Dimension(90,40));
        actionButtonsPanel.add(actionButtonList[1]);
        actButtonLayout.putConstraint(SpringLayout.NORTH,actionButtonList[1],5,SpringLayout.NORTH,actionButtonsPanel);
        actButtonLayout.putConstraint(SpringLayout.EAST,actionButtonList[1],-5,SpringLayout.EAST,actionButtonsPanel);
        actionButtonList[2].addActionListener(this);
        actionButtonList[2].setPreferredSize(new Dimension(90,40));
        actionButtonsPanel.add(actionButtonList[2]);
        actButtonLayout.putConstraint(SpringLayout.NORTH,actionButtonList[2],5,SpringLayout.SOUTH,actionButtonList[1]);
        actButtonLayout.putConstraint(SpringLayout.EAST,actionButtonList[2],-5,SpringLayout.EAST,actionButtonsPanel);

    }
    private void pressButtonAction(int i){

    }
     synchronized public void updatePanel(){

         if(!nameOfSong.equals(infoField[0].getText())){
             infoField[0]=nameOf(nameOfSong);
             this.nameSong.removeAll();
             this.nameSong.add(infoLabel[0]);
             this.nameSong.add(infoField[0]);
         }
         if(!nameOfSong.equals(infoField[1].getText())){
             infoField[1]=nameOf(name);
             infoField[2]=nameOf(track);
             this.nameTrack.removeAll();
             this.nameTrack.add(infoLabel[1]);
             this.nameTrack.add(infoField[1]);
             this.nameTrack.add(infoLabel[2]);
             this.nameTrack.add(infoField[2]);
         }

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

    synchronized public void setTrack(String path,String name){
        this.track=path;
        this.name=name;
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
        }else if (actionButtonList[7].equals(source)) {
            JButton button= (JButton) source;
            Main.use(button.getName());
        }else if (actionButtonList[8].equals(source)) {
            JButton button= (JButton) source;
            Main.use(button.getName());
        }else if (actionButtonList[9].equals(source)) {
            JButton button= (JButton) source;
            Main.use(button.getName());
        }else if (actionButtonList[10].equals(source)) {
            JButton button= (JButton) source;
            Main.use(button.getName());
        }else if (actionButtonList[11].equals(source)) {
            JButton button= (JButton) source;
            Main.use(button.getName());
        }else{
            throw new IllegalStateException("Unexpected value: " + e.getSource());
        }

    }
    public void lastButtonPressed(int number){
        if(!(actionButtonState[lastPressedActionButton])){
            System.err.println("new button was pressed or one before  was false");
        }else
            actionButtonList[lastPressedActionButton].setEnabled(true);
        lastPressedActionButton=number;
        actionButtonList[lastPressedActionButton].setEnabled(false);
        refreshActionButtonList();
    }

    public void setActionButtonsList(int number,boolean state){
        actionButtonState[number]=state;
    }
    public void refreshActionButtonList(){
        for(int i=0;i<actionButtonList.length;i++){
            actionButtonList[i].setEnabled(actionButtonState[i]);
        }
        actionButtonList[lastPressedActionButton].setEnabled(false);
    }
    public void emptyTrackError(int track){
        setActionButtonsList(track+3,false);
        actionButtonList[track+3].setText("\"EMPTY\"");
    }

}
