package com.company;
/*
** build on:
** https://www.tutorialspoint.com/how-to-get-list-of-all-files-folders-from-a-folder-in-java
*/
import java.io.File;
import java.io.IOException;
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
        for(String s:contents){
            m=p.matcher(s);
            if(!m.find()) s = "";
        }
        System.out.println("List of files and directories in the specified directory:");
        for(int i=0; i<contents.length; i++) {
            System.out.println(contents[i]);
        }
    }
}