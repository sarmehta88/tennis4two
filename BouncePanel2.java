import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.WindowAdapter;
import java.awt.*;
import java.awt.event.*;




import java.awt.event.*;

public class BouncePanel2 extends JPanel implements Runnable, MouseListener {
    Image court;
    float current = 0F;// zero in float point
    Thread runner;
    
    double xPosition = 0;
    float time=0F;
    double yPosition = 0;
    
   
    
    int period= 7;
    double temp;
    int speed= 30; //mph
    
    
    int rise= 20; // - BACKHAND or + FOREHAND

    public BouncePanel2() {     // similar to start()
        super(); //create a panel thread is on top
        Toolkit kit = Toolkit.getDefaultToolkit();
        court = kit.getImage("tennis_diagram.gif");
	
        runner = new Thread(this);
        addMouseListener( this );

        runner.start();
            
    }
    // If mouse-player pressed on location of the ball, stop the ball, else out
    //call popup window
    // if ball drops on second bounce, out
    //if ball is clicked on out of boundaries, then out

    public void mousePressed(MouseEvent event)
    {
        System.out.println("Mouse pressed. x = "
                           + event.getX() + " y = " + event.getY());
            }
    public void mouseReleased(MouseEvent event)
    {
        System.out.println("Mouse released. x = "
                           + event.getX() + " y = " + event.getY());
    }
    public void mouseClicked(MouseEvent event)
    {
        if(xPosition>=265){ // ONLY ALLOW CLICK IN PLAYER2 SQUARE
        System.out.println("Mouse clicked. x = "
                           + event.getX() + " y = " + event.getY());
        System.out.println("Xposition = "
                           + xPosition + " y = " + yPosition);
        int mouseX= event.getX();
        int mouseY= event.getY();
        
        if((Math.abs(xPosition- mouseX)<=40 && Math.abs(yPosition- mouseY)<=40 )){
            
            popUpBox();
            runner.interrupt();
            
            
        }
        }
    }
        
        
    
    public void mouseEntered(MouseEvent event)
    {
        System.out.println("Mouse entered. x = "
                           + event.getX() + " y = " + event.getY());
    } 
    public void mouseExited(MouseEvent event)
    { 
        System.out.println("Mouse exited. x = "
                           + event.getX() + " y = " + event.getY());
    }
    
// When mouse catches the ball, popUP box appears, stops animation, enter input, switch to player 1(Computer)
    
    
    public void paintComponent(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;

	        
        if ((court != null)) {
            g2D.drawImage(court, 0, 0, this);
            g2D.setColor(Color.WHITE);
            g2D.fillOval((int) xPosition,(int) yPosition,10,10);
        }
            
    }
    
    /* rise is the angle= direction of hit use up/down up is left or +
     * frequency can be changed = power of the shot how far it is hit
     * add boundaries
     * add comp player response and mouse detection of ball
     */
    
    public void run() {
        Thread thisThread = Thread.currentThread();
        
        boolean a= false; //player 2 off, player 1 on
                      
        
        while (runner == thisThread) {
            
            repaint();
            time= time+ .1F; //sec
            // Make these into different methods and access them in mousevent clicked
            if(a){ //player 2 ON
                xPosition= (510- 100*time);
                
            }else{ //player 1
                xPosition= 100*time/Math.exp(.05*time); // Wave Time*speed, speed= 100 pix/sec, Scales the sin wave to the image
                
            }
             yPosition= 100*-(Math.abs(Math.sin(time*2*Math.PI/period)))/Math.exp(.25*time)-rise*time+217;
                        
            //217 is the current y position of the player
            // 50 is amplitude depends on angle
            // frequency depends on the power intensity speed
            
            //Bounds where the player 1/2 boxes start, MOUSE HANDLER
            // ADD Mouselistener to this thread
            
            if (xPosition > 503){
                time=0F;
                a= true; // turn player 2 on, player 1 off
                popUpBox();
                pause(); // pause 6 secs
                
                
            }
            if(xPosition<0){ // turn player 1 on, player 2 off
                time=0F;
                a=false; // reset y post to starting point of next shot
                
                
            }
                
            try {
                Thread.sleep(100); // affects speed of ball in realtime, find math eq btw period and speed
            } catch (InterruptedException e) {
               //  pause(); mouse event
                pause();
            
            }
        }
    }
    public void pause(){
        try {
            Thread.sleep(6000); // affects speed of ball in realtime
            
        } catch (InterruptedException e) {
            // Return back to main thread
            
        }
    }

    public void popUpBox() {
    
        
        // creates the actual frame with title and dimensions
        final JFrame frame =new JFrame("Tennis Swing");
        
        
        frame.setSize(200,210);
        
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        
        WindowListener exitListener = new WindowAdapter() {
            
            @Override
            public void windowClosing(WindowEvent e) {
                runner.interrupt();
                frame.dispose();
            }
        };
        frame.addWindowListener(exitListener);
        final JPanel panel = new JPanel(); //Create a JPanel
        final JLabel label3= new JLabel("You have 6 seconds..GO!");
        panel.add(label3);
        final JLabel label4= new JLabel("1");
        panel.add(label4);
        final JProgressBar bar= new JProgressBar(JProgressBar.HORIZONTAL, 0, 6);
        panel.add(bar);
        bar.setValue(1);
        
        
        
        
        final Timer timer = new Timer(1000, new ActionListener() {
            int counter= 1;
            
            @Override
            public void actionPerformed(ActionEvent e) {
                counter++;
                label4.setText(String.valueOf(counter));
                bar.setValue(counter);
                if(counter>6){
                    frame.setVisible(false);
                    frame.dispose();
                }
                
                
            }
        });
        timer.start();
    
        
        
        JLabel label=new JLabel("Enter Frequency of Swing");
        panel.add(label); // Add the label to the panel
        
        final JTextField jtf = new JTextField(6);
        panel.add(jtf); // Add the JTextField to the panel
        
        JLabel label2=new JLabel("Enter Rise of Swing");
        panel.add(label2); // Add the label to the panel
        
        final JTextField jtf2 = new JTextField(6);
        panel.add(jtf2); // Add the JTextField to the panel
        
        
        
        final JButton submit= new JButton("Recalculating...");
        submit.setEnabled(false);
        
        //CHANGEEEEEE
        
        panel.add(submit);
        
        
        
        
        
        
        
                       
        frame.getContentPane().add(panel); // Add the panel to the JFrame
        frame.setVisible(true);
        //Enter text and update datafields period and rise
        submit.addActionListener(new ActionListener() {
            

            
            @Override
            public void actionPerformed(ActionEvent event) {
                // Set rise and period with the input string from the textfield
               
                period = Integer.parseInt(jtf.getText().trim());
                rise= Integer.parseInt(jtf2.getText().trim());
                

                frame.setVisible(false);
                runner.interrupt();
                
                
            }
        });
        // Disable submit button if NO text entered
        jtf.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                if(jtf.getText().length() > 0){
                    submit.setEnabled(true);
                }}
            });
        
        jtf2.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                if(jtf2.getText().length() > 0){
                    submit.setEnabled(true);
                }}
        });
        
}
    
}