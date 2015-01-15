/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unitsphere;
/**
 *
 * @author Tim
 */
public class Main {
    
    SettingsReader s;
    
    public static void main(String [] args)
    {
        /*
        SettingsReader s = new SettingsReader();
        s.loadSettings();
        
        s.toString();
        
        */
        
        
        UnitSphere us = new UnitSphere();
        double projection = us.updateAndGetAvatarVector();
         System.out.println("Projection: " + projection);       
        String emotion = "";
        int emot = -1;
        
        int switchVal = (int)projection;
        //System.out.println("Switch Val: " + switchVal);
        switch(switchVal)
        {
            case 1:
                emotion = "Engaged";
                emot = 0;
                break;
            case 2:
                emotion = "Bored";
                emot = 1;
                break;
            case 3:
                emot = 2;
                emotion = "Frustrated";
                break;
            default:
                emotion = "ERROR";
                break;
        }
        
        
        int mag = (int) ((projection - Math.floor(projection)) * 10);

        String magnitude = "";
        
        switch(mag)
        {
            case 0:
                magnitude = "No Repsonse";
                break;
            case 1:
                magnitude = "Light Repsonse";
                break;
            case 2:
                magnitude = "Moderate Repsonse";
                break;
            case 3:
                magnitude = "Strong Repsonse";
                break;
            case 4:
                magnitude = "Full Repsonse";
                break;
            default:
                magnitude = "ERROR" ;
                System.out.println("MAG: " + mag);
                break;
        }
        System.out.println("Emotion: " + emotion + "\tMagnitude: " + magnitude);
    }
} 