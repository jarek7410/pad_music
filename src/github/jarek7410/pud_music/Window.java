package github.jarek7410.pud_music;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Window extends JPanel implements ActionListener {
    private final Config config;
    private Color myBlack = new Color(30,39,46);
    private JPanel nameSong;
    private JLabel titleLabel;
    private JPanel nameTrack;

    private String nameOfSong;
    private String track;

    public Window(Config config) {
        this.config=config;
        //this.setBackground(Color.GREEN);
        GridLayout layout = new GridLayout(3,1);
        this.setLayout(layout);
        this.nameSong = new JPanel();
        this.nameTrack = new JPanel();
        this.setBackground(Color.blue);
        this.nameOfSong = "songName";
        titleLabel = new JLabel("song info");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        this.add(titleLabel);
        this.add(nameSong);
        this.add(nameTrack);
    }
     synchronized public void updatePanel(){
        this.nameSong.removeAll();
        this.nameSong.add(new JLabel("song name: "));
        JTextField textField=new JTextField();
        textField.setText(nameOfSong);
        textField.setVisible(true);
        this.nameSong.add( textField);

        this.nameTrack.removeAll();
        this.nameTrack.add(new JLabel("track localization: "));
        textField=new JTextField();
        textField.setText(track);
        textField.setVisible(true);
        this.nameTrack.add(textField);
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
