package unitsphere;

import java.util.Scanner;

public class UnitSphere {

    private int emotion = 0;
    private double projection = 0;
    private int avatarVector = 0;
    private final Vector[] preDef_emot = {new Vector(1, 1, 1), new Vector(-1, -1, -1), new Vector(-1, 1, -1)};  // E,B,F
    private Vector userVector; // = new Vector(-.234, .66, -.1);
  
    
    //public double getAvatarVector() { return avatarVector; }
    public void updateUserVector(double[] points)
    {
        userVector = new Vector(points[0], points[1], points[2]);
    }
    
    public int updateAndGetAvatarVector()
    {
        updateAvatarVector();
        
        return avatarVector;        
    }
    
    private void updateAvatarVector() 
    {
        getUserVector();
        calculateAvatarVector();
    }
    
    private void getUserVector()
    {    
        /*
        Scanner keyboard = new Scanner(System.in);
        System.out.println("Pressure: ");
        double p = keyboard.nextDouble();
        
        System.out.println("Arousal: ");
        double a = keyboard.nextDouble();       

        System.out.println("Dominance: ");
        double d = keyboard.nextDouble();    
        
        userVector = new Vector(p, a, d);
        */
    }
     
    private void calculateAvatarVector()
    {
        int closest_emot = -1;
        double minDistance = 3;
        double distance = 0;
        int emotiveForce = 0;

                
        double press = userVector.getPressure();
        double arous = userVector.getArousal();
        double domin = userVector.getDominance();
         
        for (int emote = 0; emote < 3; emote++)
        {         
            distance = magnitude_3D(preDef_emot[emote].getPressure()  - press,
                                    preDef_emot[emote].getArousal()   - arous,
                                    preDef_emot[emote].getDominance() - domin);
            if (distance < minDistance)
            {
                minDistance = distance;
                closest_emot = emote;
            }
        }
                 
        emotion = closest_emot + 1;
        
        projection = findScalarProjection(userVector, preDef_emot[closest_emot]);
        
        // Normalize the magnitude
        projection /= Math.sqrt(3);
        
        if (0 <= projection && projection < .2)
            emotiveForce = 0;
        else if (.20 <= projection && projection < .4)
            emotiveForce = 1;
        else if (.40 <= projection && projection < .6)
            emotiveForce = 2;
        else if (.60 <= projection && projection < .8)
            emotiveForce = 3;
        else if (.80 <= projection && projection < 1.1)
            emotiveForce = 4;
        else
            emotiveForce = 9;
        // Attach the emotion to the avatarVector:  
        //  Magnitude is values [0-4,9],
        //  Closest emotion is [10-30],
        //  Add offset to closest emotion then add the Magnitude
        // This allows for a single number to represent emotion and projection.
        avatarVector = (emotion * 10) + emotiveForce;     
    }
    
    private double findScalarProjection(Vector user, Vector base)
    {    
        double dotProduct = dot_3D(user, base);
        
        double magnitude = magnitude_3D(base.getPressure(), 
                                        base.getArousal(), 
                                        base.getDominance());
        
        return  dotProduct / magnitude;
        
        
        /*
            a DOT b
            -------
             mag(a)
        */
    }
    
    private double dot_3D(Vector user, Vector base)
    {
        return  user.getPressure()  * base.getPressure() +
                user.getArousal()   * base.getArousal()  +
                user.getDominance() * base.getDominance();

    }
        
    private double magnitude_3D(double x_diff, double y_diff, double z_diff)
    {
        return Math.sqrt(Math.pow(x_diff, 2) + 
                         Math.pow(y_diff, 2) + 
                         Math.pow(z_diff, 2));
    }
    
    public void initUnitSphere()
    {
        
        
    }
    
    private class Vector 
    {
        private double _pressure;
        private double _arousal;
        private double _dominance;
        
        public Vector(double _p, double _a, double _d)
        {
            _pressure  = _p;
            _arousal   = _a;
            _dominance = _d;
        }
        
        public double getPressure()  { return _pressure;  }
        public double getArousal()   { return _arousal;   }
        public double getDominance() { return _dominance; }
    }
}

