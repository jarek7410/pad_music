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

        this.nameOfSong = "songName";
        titleLabel = new JLabel("song info");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        this.add(titleLabel);
        this.add(nameSong);
        this.add(nameTrack);
    }
    public void updatePanel(){
        this.nameSong.removeAll();
        this.nameSong.add(new Label("song name: "));
        this.nameSong.add(new Label(nameOfSong));

        this.nameTrack.removeAll();
        this.nameTrack.add(new Label("track localization: "));
        this.nameTrack.add(new Label(track));
    }
    public void setpause(){
        this.nameSong.removeAll();
        this.nameTrack.removeAll();
        this.nameSong.add(new JLabel("music is stopped"));

    }
    public void setSong(String name){
        this.nameOfSong=name;
    }

    public void setTrack(String path){
        this.track=path;
    }



    public void setAsDisconnected() {
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
