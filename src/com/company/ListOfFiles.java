package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class ListOfFiles {
    public static void main(String args[]) throws IOException {
        //Creating a File object for directory
        File directoryPath = new File("C:\\Users\\jarek\\Downloads");
        //List of all files and directories
        String contents[] = directoryPath.list();

        Pattern p= Pattern.compile("zip$");
        Matcher m;
        Vector<String> lis = new Vector<String>();
        for(int i =0;i<contents.length;i++){
            m=p.matcher(contents[i]);
            if(m.find()) lis.addElement(contents[i]);
        }
        System.out.println("List of files and directories in the specified directory:");
        for(int i=0; i<contents.length; i++) {
            System.out.println(contents[i]);
        }
    }
    public static Vector<String> listOfFiles(String regex,String path){

            File directoryPath = new File(path);
            String contents[] = directoryPath.list();

            Pattern p = Pattern.compile(regex);
            Matcher m;
            Vector<String> lis = new Vector<String>();

            for (int i = 0; i < contents.length; i++) {
                m = p.matcher(contents[i]);
                if (m.find()) lis.addElement(contents[i]);
            }
            return lis;

    }
    public static Vector<String> listOfMP3(String path){
        return listOfFiles("mp3$",path);
    }
    public static String[] VtoS(Vector<String> list){
        String s[]=new String[list.size()];
        for(int i=0;i<s.length;i++){
            s[i]=list.get(i);
        }
        return s;
    }
    protected static String[] getFirstConfig(){
        String ss[]=new String[8];
        String s;
        int i =0;

        File config=new File("config.txt");
        try {
            Scanner scanner = new Scanner(config);
            while(scanner.hasNextLine()){
                s=scanner.nextLine()+"\\";
                if(s.charAt(0) != '#'){
                    ss[i]=s;
                    i++;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return ss;
    }
}