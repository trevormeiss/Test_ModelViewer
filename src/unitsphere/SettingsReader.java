package unitsphere;

import java.util.ArrayList;
//import com.thoughtworks.xstream.XStream;
import java.io.*;


public class SettingsReader 
{
    
    private String xmlData;
    public Settings settings;
     
    private void readFile()
    {
        File file = new File("SphereSettings.xml");
        StringBuilder contents = new StringBuilder();
        BufferedReader reader = null;
 
        try {
            reader = new BufferedReader(new FileReader(file));
            String text = null;
 
            // repeat until all lines is read
            while ((text = reader.readLine()) != null) 
            {
                contents.append(text)
                        .append(System.getProperty(
                                "line.separator"));
            }
            xmlData = contents.toString();
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        } 
        finally 
        {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    private void DeserializeXML()
    {
        //XStream xstream = new XStream();
        
        //settings = (Settings)xstream.fromXML(xmlData);
    }
    
    public void loadSettings()
    {
        readFile();
        DeserializeXML();
    }
    
    private class Emotion
    {
        String name;
        int id;
        ArrayList<Integer> vector;
    }   
    
    private class Settings
    {
        private ArrayList<Emotion> emotion;
        private ArrayList<Double> threshold;  
    }
    
    /*
    public String toString()
    {
        String s;
        s = "1";
        System.out.println(settings.emotion[0].name);
        return s;
        
    }
    */
}


/*
<settings>
    <emotions>
    <e>
        <name>engaged</name>
        <id>2</id>
        <vector>1 1 1</vector>
    </e>
    <e>
        <name>bored</name>
        <id>3</id>
        <vector>-1 -1 -1</vector>
    </e>    
    <e>
        <name>frustrated</name>
        <id>4</id>
        <vector>-1 1 -1</vector>
    </e> 
    </emotions>  
    <threshholds>
        <t>0.0</t>
        <t>0.2</t>
        <t>0.4</t>
        <t>0.6</t>
        <t>0.8</t>
    </threshholds> 
</settings>
*/