package github.jarek7410;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import java.io.*;
import java.net.MalformedURLException;
import java.util.Random;
import javax.sound.sampled.*;

public class Sound implements Runnable{

    private boolean running;
    private boolean playMusic;
    private Player player;
    private Config config;
    private String[] listOfTracks;
    private int track=0;

    Sound(Config config){
        this.config=config;
        running=true;
        playMusic=false;
    }
    public void reloadConfig(Config config){
        this.config=config;
    }

    @Override
    public void run() {

        int tr=-1;
        int i;
        Random r;

        while(running) {
            try {
                Thread.sleep(100L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (tr != track) {
                tr = track;
                listOfTracks = ListOfFiles.VtoS(ListOfFiles.listOfMP3(config.traks()[tr]));

            }
            //System.err.println("play music " + playMusic);
            if (playMusic) {
                System.err.println("IT IS WORKING!!!!!");
                try {
                    i = ((int) (Math.random() * listOfTracks.length)) ;
                    System.err.println(config.traks()[track] + listOfTracks[i]);
                    InputStream is = new FileInputStream(config.traks()[track] + listOfTracks[i]);
                    player = new Player(is);
                    player.play();

                } catch (FileNotFoundException e) {
                    System.err.println("file not found");
                    e.printStackTrace();
                } catch (JavaLayerException e) {
                    e.printStackTrace();
                }

            }
        }
    }
    public void play(){
        playMusic=true;
    }

    public void stop(){
        if(playMusic)   player.close();
        playMusic=false;
    }

    public void close(){
        if(playMusic)   player.close();
        playMusic=false;
        running=false;
    }

    public boolean isPlayMusic() {
        return playMusic;
    }

    public int getTrack(){
        return track;
    }
    public void setTrack(int i,Config config){
        if(i<config.getNumberOfTreks())track=i;
    }
}