package github.jarek7410;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Vector;

public class Config {
    private String[] traks;
    public String[] traks() {
        return traks;
    }

    private int numberOfTreks;
    public int getNumberOfTreks(){
        return numberOfTreks;
    }

    File config;
    public Config(){
        this.config=new File("config.txt");
    }

    protected void getConfigs(){
        try {
            Vector<String> vv=new Vector<>();
            Scanner scanner = new Scanner(config);
            String s;
            while(scanner.hasNextLine()){
                s = scanner.nextLine();
                if(s.charAt(0) != '#'){
                    vv.add(s);
                }
            }
            numberOfTreks= Integer.parseInt(vv.get(0));
            vv.remove(0);
            traks=ListOfFiles.VtoS(vv);
            for(int i =0;i<traks.length;i++){
                traks[i]+="\\";
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}
