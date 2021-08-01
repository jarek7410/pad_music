package com.company;
/*
** build on:
** https://www.tutorialspoint.com/how-to-get-list-of-all-files-folders-from-a-folder-in-java
*/
import java.io.File;
import java.io.IOException;
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
}