package github.jarek7410.pud_music;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class ListOfFiles {
    public static void main(String[] args) {
        //Creating a File object for directory
        File directoryPath = new File("C:\\Users\\jarek\\Downloads");
        //List of all files and directories
        String[] contents = directoryPath.list();

        Pattern p= Pattern.compile("zip$");
        Matcher m;
        Vector<String> lis = new Vector<>();
        for(int i = 0; i< Objects.requireNonNull(contents).length; i++){
            m=p.matcher(contents[i]);
            if(m.find()) lis.addElement(contents[i]);
        }
        System.out.println("List of files and directories in the specified directory:");
        for (String content : contents) {
            System.out.println(content);
        }
    }
    public static Vector<String> listOfFiles(String regex,String path) {

            File directoryPath = new File(path);
            String[] contents = directoryPath.list();

            Pattern p = Pattern.compile(regex);
            Matcher m;
            Vector<String> lis = new Vector<>();

            for (int i = 0; i < Objects.requireNonNull(contents).length; i++) {
                m = p.matcher(contents[i]);
                if (m.find()) lis.addElement(contents[i]);
            }
            return lis;

    }
    public static Vector<String> listOfMP3(String path) {
        return listOfFiles("mp3$",path);
    }
    public static String[] VtoS(Vector<String> list){
        String[] s =new String[list.size()];
        for(int i=0;i<s.length;i++){
            s[i]=list.get(i);
        }
        return s;
    }
    public static void writeToFile(File file,String s){
        try {
            FileWriter writer=new FileWriter(file);
            writer.write(s);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}