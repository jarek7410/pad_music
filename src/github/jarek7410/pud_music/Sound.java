package github.jarek7410.pud_music;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import java.io.*;
import java.util.Random;

public class Sound implements Runnable{

    private boolean playMusic;
    private Player player;
    private Config config;
    private String[] listOfTracks;
    private String[] nameOfTracks;

    private Window window;

    Sound(Config config,Window w){
        if(config.frameRun)window =w;
        this.config=config;
        config.running=true;
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

        while(config.running) {
            try {
                Thread.sleep(100L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (tr != config.track) {
                tr = config.track;
                try {
                    listOfTracks = ListOfFiles.VtoS(ListOfFiles.listOfMP3(config.traks()[tr]));
                } catch (Exception e) {
                    System.err.println("incorrect track path ");
                    e.printStackTrace();
                    window.emptyTrackError(tr);
                    window.refreshActionButtonList();
                    Main.use("STOP");
                }finally {
                    System.out.println("track which is being played: " + config.traks()[config.track]);
                }
            }
            //System.err.println("play music " + playMusic);
            if (playMusic) {
                try {
                    //System.out.print("music is being played: ");
                    i = ((int) (Math.random() * listOfTracks.length));
                    if(config.frameRun) windowUpdate(i);
                    System.out.println("track nr."+(config.track+1)+" song: \""+listOfTracks[i]+"\"");
                    InputStream is =
                            new FileInputStream
                                    (config.traks()[config.track]
                                            +"\\"+ listOfTracks[i]);
                    player = new Player(is);
                    player.play();

                } catch (FileNotFoundException e) {
                    System.err.println("file not found");
                    e.printStackTrace();
                    playMusic=false;
                } catch (JavaLayerException e) {
                    e.printStackTrace();
                }

            }
        }
    }
    private void windowUpdate(int i) {
        if(i==-2){
            window.setTrack(config.traks()[config.track],config.getTracksNames()[config.track]);
            window.setSong("stopped");
        }else if(i==-1){
            window.setTrack(config.traks()[config.track],config.getTracksNames()[config.track]);
            window.setSong("changing");
        }else{
            window.setTrack(config.traks()[config.track],config.getTracksNames()[config.track]);
            window.setSong(listOfTracks[i]);
            }

        Main.windowPrint();
    }
    public void play(){
        playMusic=true;
    }
    public void pause(){
        //TODO
    }

    public void stop(){
        windowUpdate(-1);
        if(playMusic)   player.close();
        playMusic=false;
    }
    public void stop(int i){
        windowUpdate(i);
        if(playMusic)   player.close();
        playMusic=false;
    }

    public void close(){
        if(player!=null)   player.close();
        playMusic=false;
    }

    public boolean isPlayMusic() {
        return playMusic;
    }



}