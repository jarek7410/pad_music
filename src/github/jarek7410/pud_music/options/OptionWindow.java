package github.jarek7410.pud_music.options;

import com.studiohartman.jamepad.ControllerButton;
import github.jarek7410.pud_music.Actions;
import github.jarek7410.pud_music.Config;
import github.jarek7410.pud_music.ListOfFiles;
import github.jarek7410.pud_music.Window;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Objects;
import java.util.Vector;

public class OptionWindow extends JFrame{
    private Config config;
    private Window window;
    public OptionWindow(Config config,Window window){
        this.config=config;
        this.window=window;
    }
    private Vector<CTrack> tracks;


    public void run() {
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setSize(1000,500);
        this.setLayout(new CardLayout());

        SpringLayout layout = new SpringLayout();
        this.setLayout(layout);

        JPanel actionButtons=new JPanel();
        actionButtons.setLayout(new FlowLayout());
        actionButtons.setPreferredSize(new Dimension(240,410));
        actionButtons.setBackground(Color.PINK);

        actionButtons.add(new JLabel("Action to buttons assignment:"));

        HashMap<String, String> hash =config.getActionButtons();
        actionButtonView a;
        JButton options=new JButton("tekst");
        options.setPreferredSize(new Dimension(150,40));
        options.addActionListener(e -> {
            Vector<String> traks=new Vector<>(),
                    nameTracks=new Vector<>();
            for(CTrack b:tracks){
                if(!Objects.equals(b.name.getText(), "")&&!Objects.equals(b.path.getText(),""))
                {
                    System.out.println(b.id+b.name.getText()+b.path.getText());
                    traks.add(b.path.getText());
                    nameTracks.add(b.name.getText());
                }
            }
            config.changeTracks(ListOfFiles.VtoS(traks),ListOfFiles.VtoS(nameTracks),ListOfFiles.VtoS(traks).length);


            config.saveConfigs();
            window.refreshChaneInNames();
        });
        options.setText("actions and buttons list");
        Vector<String> listButtons=new Vector<>();
        for(ControllerButton b:ControllerButton.values()){
            listButtons.addElement(b.name());
        }
        String[] listOfButtons= ListOfFiles.VtoS(listButtons);
        for(Actions actions:Actions.values()){
            a=new actionButtonView();
            a.set(actions.name(),hash.get(actions.name()),listOfButtons);
            actionButtons.add(a);
        }
        JPanel trackList=new JPanel();
        trackList.add(new JLabel("tracks:"));
        tracks=new Vector<>();
        for(int i =0;i<8;i++){
            if(i<config.getNumberOfTreks())
                tracks.add(new CTrack(config.getTracksNames()[i],config.getTraks()[i],i));
            else{
                tracks.add(new CTrack("","",i));
            }
            trackList.add(tracks.get(i));
        }
        trackList.setBackground(Color.GREEN);
        trackList.setPreferredSize(new Dimension(270,305));



        this.add(trackList);
        this.add(actionButtons);
        this.add(options);
        layout.putConstraint(SpringLayout.NORTH, actionButtons, 0,SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.WEST,actionButtons,0,SpringLayout.WEST,this);
        layout.putConstraint(SpringLayout.SOUTH,options,0,SpringLayout.SOUTH,actionButtons);
        layout.putConstraint(SpringLayout.WEST,options,0,SpringLayout.EAST,actionButtons);
        layout.putConstraint(SpringLayout.NORTH,trackList,0,SpringLayout.NORTH,this);
        layout.putConstraint(SpringLayout.WEST,trackList,0,SpringLayout.EAST,actionButtons);
    }
    class CTrack extends JPanel{
        int id;
        JLabel text;
        JTextField  name,
                    path;
        CTrack(String name,String path,int id){
            this.id=id;
            this.name=new JTextField(name);
            this.path=new JTextField(path);
            this.text=new JLabel(Actions.values()[3+id].name()+":");
            this.name.setPreferredSize(new Dimension(100,20));
            this.path.setPreferredSize(new Dimension(100,20));
            this.text.setPreferredSize(new Dimension(45,20));
            this.add(this.text);
            this.add(this.name);
            this.add(this.path);
        }
    }

    class actionButtonView extends JPanel{
        public void set(String action,String button,String[] list){
            JLabel label=new JLabel(action);
            label.setPreferredSize(new Dimension(60,20));
            this.add(label);
            this.add(new JLabel(":"));
            JComboBox<String> field=new JComboBox(list);
            field.setSelectedItem(button);
            field.addActionListener(e -> {
                JComboBox<String> box= (JComboBox) e.getSource();
                config.changeOptionsBAAB((String) box.getSelectedItem(),box.getName());

            });
            field.setName(action);
            field.setPreferredSize(new Dimension(150,20));
            this.add(field);
        }
    }
}
