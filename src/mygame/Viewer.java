package mygame;

import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.animation.AnimEventListener;
import unitsphere.UnitSphere;

import com.jme3.app.SimpleApplication;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.jme3.system.AppSettings;
import com.jme3.system.JmeCanvasContext;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * test
 * @author normenhansen
 */
public class Viewer extends SimpleApplication implements ActionListener {
    
    static String hostName = "localhost";
    static int portNumber = 5555;
    
    static Socket connectionSocket;
    static ServerSocket welcomeSocket;
    static DataInputStream sin;
    static Timer timer;
    double[] vectorPoints = new double[3];
    UnitSphere us = new UnitSphere();
    
    public Viewer() {
        initComponents();
        timer = new Timer(1000, (ActionListener) this);
    }
    
    private static JFrame window;
    
    private static JLabel pVal;
    private static JLabel aVal;
    private static JLabel dVal;
    
    private static JLabel jLabel1;
    private static JLabel jLabel2;
    
    private static JPanel modelPanel;
    
    private static JButton openServ;
    private static JButton closeServ;
    
    private Node companion;
    private AnimControl companionControl;
    private AnimChannel companionChannel;

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                AppSettings settings = new AppSettings(true);
                settings.setWidth(640);
                settings.setHeight(480);
                
                new padData().setVisible(true);
                Viewer canvasApplication = new Viewer();
                canvasApplication.setSettings(settings);
                canvasApplication.createCanvas(); // create canvas!
                JmeCanvasContext ctx = (JmeCanvasContext) canvasApplication.getContext();
                ctx.setSystemListener(canvasApplication);
                Dimension dim = new Dimension(640, 480);
                ctx.getCanvas().setPreferredSize(dim);
                
                window = new JFrame("Swing Application");
                window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                
                JPanel panel = new JPanel(new FlowLayout()); // a panel
                // add all your Swing components ...
                JPanel subPanel = new JPanel(new GridLayout(9,1));
                             
                subPanel.add(openServ);
                subPanel.add(closeServ);
                subPanel.add(new JLabel());
                
                subPanel.add(pVal);
                subPanel.add(aVal);
                subPanel.add(dVal);
                subPanel.add(new JLabel());
                
                subPanel.add(jLabel1);
                subPanel.add(jLabel2);
                
                panel.add(subPanel);
                // add the JME canvas
                panel.add(ctx.getCanvas());
                
                window.add(panel);
                window.pack();
                window.setVisible(true);
                
                canvasApplication.startCanvas();
            }
        });
    }
    
    private static void initComponents() {
        modelPanel = new javax.swing.JPanel();
        pVal = new javax.swing.JLabel();
        aVal = new javax.swing.JLabel();
        dVal = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        openServ = new javax.swing.JButton();
        closeServ = new javax.swing.JButton();

        modelPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("ModelViewer"));

        pVal.setText("pVal: 0.67");
        aVal.setText("aVal: 0.83");
        dVal.setText("dVal: -0.12");
        jLabel1.setText("Emotion: Engaged");
        jLabel2.setText("Magnitude: 2");
        
        openServ.setText("Open socket " + portNumber);
        openServ.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openServActionPerformed(evt);
            }
        });

        closeServ.setText("Close socket " + portNumber);
        closeServ.setEnabled(false);
        closeServ.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeServActionPerformed(evt);
            }
        });
    }
    
    private static void openServActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openServActionPerformed
        openServ.setEnabled(false);
        closeServ.setEnabled(true);
        startServer();
    }
    
    private static void closeServActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeServActionPerformed
        stopServer();
        openServ.setEnabled(true);
        closeServ.setEnabled(false);
    }
    
    public static void startServer() {
        try {
            welcomeSocket = new ServerSocket(portNumber);
            welcomeSocket.setSoTimeout(50);
        } catch (IOException ex) {
            //Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        timer.restart();
    }
    
    public static void stopServer() {
      try {
        sin.close();
        connectionSocket.close();
        welcomeSocket.close();
        timer.stop();
      } catch (IOException ex) {
        // System.err.println("stopServer() Exception:  " + e);
      }
      //connectionSocket = null;
    }   
    
    @Override
    public void simpleInitApp() {
        flyCam.setDragToRotate(true);
        cam.setLocation(new Vector3f(0, 12, 20));
        
        companion = (Node)assetManager.loadModel("Textures/texturetest2.j3o");
        //companionControl = companion.getChild("MH_Male_Suit_Game").getControl(AnimControl.class);
        
        // Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        // mat.setColor("Color", ColorRGBA.Blue);
        // test.setMaterial(mat);
        
        rootNode.attachChild(companion);
        //companionChannel = companionControl.createChannel();
        //companionChannel.setAnim("MH_Metc-09-scratch-takiguchi");
        
        /* Make the background transparent? */
        //viewPort.setClearColor(false);
        //viewPort.setBackgroundColor(new ColorRGBA(1.0f,0.0f,0.0f,0.0f));
        
        DirectionalLight sun = new DirectionalLight();
        sun.setDirection(new Vector3f(-0.1f, -0.7f, -1.0f));
        rootNode.addLight(sun);
    }
    
    public void pollServer() {
        
       try {
            connectionSocket = welcomeSocket.accept();
            if(connectionSocket != null) {
                sin = new DataInputStream(connectionSocket.getInputStream());
               
                String text;
                for (int i = 0; i < vectorPoints.length; i++)
                {
                    vectorPoints[i] = sin.readDouble();
                    text = "Val: " + vectorPoints[i];
                    switch(i)
                    {
                        case 0:
                            pVal.setText(text);
                            break;
                        case 1:
                            aVal.setText(text);
                            break;                                
                        case 2:
                            dVal.setText(text);
                            break;                        
                    }
                }
                
                us.updateUserVector(vectorPoints);
                int avatarResponse = us.updateAndGetAvatarVector();
                
                System.out.println("Calling Animation Function Code: " + avatarResponse);
                
                text = "Emotion: ";
                switch(avatarResponse / 10)
                {
                    case 1:
                        text += "Engaged";
                        break;
                    case 2:
                        text += "Bored";
                        break;
                    case 3:
                        text += "Frustrated";
                        break;    
                    default:
                        text += "error";
                        break;
                }
                jLabel1.setText(text);
                
                text = "Magnitude Level: ";
                switch(avatarResponse % 10)
                {
                    case 0:
                        text += "0";
                        break;                    
                    case 1:
                        text += "1";
                        break;
                    case 2:
                        text += "2";
                        break;
                    case 3:
                        text += "3";
                        break;   
                    case 4:
                        text += "4";
                        break;                           
                    default:
                        text += "error";
                        break;
                }
                jLabel2.setText(text);              
               
                
                timer.stop();
            }    
       } catch (IOException ex) {
          // System.err.println("pollServer() Exception:  " + e);
       }
    }

    @Override
    public void simpleUpdate(float tpf) {
        //TODO: add update code
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }

    public void actionPerformed(ActionEvent e) {
        pollServer();
        timer.restart();
    }
}
