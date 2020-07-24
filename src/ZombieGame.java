import java.applet.Applet;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

//Jarrett Bierman
//A neat zombie game!
//Feb 15, 2017

public class ZombieGame extends JPanel implements KeyListener, ActionListener
{ 
private static final long serialVersionUID = 1L;
private final int PREF_W = 1400;
private final int PREF_H = 800;
private int tt, pfDX, rate, shootTimer, shootRate, score, counter, openingRate, powerTime, powerUpPacer, highScore, rateStart, rateEnd;
private boolean opening, playing, shooting, powered;
private Image backGround, platForm, logo;
private Rectangle gB, bGB;
private Rectangle[] pf;
private Sprite sprite;
private ArrayList <Zombie> zombie;
private ArrayList <PowerUp> pow;
private String highScoreFile;
private Timer timer;
private RenderingHints hints = 
      new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
 
public ZombieGame()
{
   super();
   this.addKeyListener(this);
   setFocusable(true);
   requestFocus();
   this.setBackground(Color.WHITE);
   
   JukeBox.init();
   JukeBox.load("ZombieMusic3.wav", "music");
   JukeBox.load("GunShot4.wav", "gunShot");
   JukeBox.load("GunShotFast2.wav", "gunShotFast");
   JukeBox.load("deathSound.wav", "deathSound");
   JukeBox.setVolume("deathSound", 6);
   
   JukeBox.loop("music");

   gB = new Rectangle(-100, -100, PREF_W + 100, PREF_H + 100);
   bGB = new Rectangle(-10, 0, PREF_W, PREF_H);
   
   pf = new Rectangle[6];
   pf[0] = new Rectangle(-100, 550, 400, 10);       
   pf[1] = new Rectangle(1100, 550, 500, 10); 
   pf[2] = new Rectangle(500, 375, 350, 10);
   pf[3] = new Rectangle(100, 75, 300, 10);
   pf[4] = new Rectangle(1000, 75, 300, 10);
   pf[5] = new Rectangle(0, 700, PREF_W, 100);
   
   rateStart = 50;
   rateEnd = 20;
   
   sprite = new Sprite(600, 50, 70, 70, pf);
   sprite.setrDirection(true);
   
   zombie = new ArrayList<Zombie>();
   
   pow = new ArrayList<PowerUp>();
   
   powerUpPacer = (int)(Math.random() * 400) + 1000;
   
   openingRate = 0;
   
   powered = false;
   powerTime = 0;
   
   opening = true;
   playing = false;
   
   try
   {backGround = new ImageIcon(this.getClass().getResource("backGround.png")).getImage();}
   catch (NullPointerException e){e.printStackTrace();} 
   
   try
   {platForm = new ImageIcon(this.getClass().getResource("platForm.png")).getImage();}
   catch (NullPointerException e){e.printStackTrace();}
   
   try
   {logo = new ImageIcon(this.getClass().getResource("ZombieGameLogo.png")).getImage();}
   catch (NullPointerException e){e.printStackTrace();} 
   
   highScore = 0;
   highScoreFile = "/Users/" + System.getProperty("user.name") + "/Documents/ZombieShooterHighScore.txt";
   
   pfDX = 2;  
   shootRate = 20;
   shootTimer = shootRate - 1;
   
   counter = 0;
   timer = new Timer (10, this);
   sprite.restart();
   timer.start();
   restart();
}


public void paintComponent(Graphics g)
{
   super.paintComponent(g);
   Graphics2D g2 = (Graphics2D) g;
   g2.setRenderingHints(hints);
   g2.setFont(new Font("Cambria" , Font.PLAIN, 65));

   g2.drawImage(backGround, 0, 0, PREF_W, PREF_H, this);
   
   for(int h = 0; h < pf.length; h++)
      g2.drawImage(platForm, pf[h].x, pf[h].y, pf[h].width, pf[h].height, this);
   
   for(int i = 0; i < zombie.size(); i++)
      zombie.get(i).drawZombie(g2);
   
   for(int i = 0; i < pow.size(); i ++)
      pow.get(i).drawPowerUp(g2);
   
   if(!opening)
      sprite.drawSprite(g2);  
   
   if(opening)
      g2.drawImage(logo, 200, 200, openingRate * 3, openingRate, this);  
   
   g2.setColor(Color.GREEN);
   g2.drawString(score + "", 50, 50);   
   if(powered)
      g2.drawString("ULTRA POWER-UP (lasts " + (700 - powerTime)/100 + " seconds)", 200, 760);
   if(!playing && !opening)
   {
      g2.drawString("Oh Lord! You have died!", 350, 300);
      g2.drawString("Your Score was " + score, 350, 400);
      g2.drawString("Your High Score is " + highScore, 350, 500);      
      g2.drawString("Press ENTER to restart", 350, 600);
   }
}

public Dimension getPreferredSize()
{
   return new Dimension(PREF_W, PREF_H);
}

private static void createAndShowGUI()
{
   ZombieGame gamePanel = new ZombieGame();
   
   JFrame frame = new JFrame("SUPER ULTRA ZOMBIE SLAUGHTER");
   frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   frame.getContentPane().add(gamePanel);
   frame.pack();
   frame.setLocationRelativeTo(null);
   frame.setVisible(true);
   frame.setResizable(false);
}


public static void main(String[] args)
{
   SwingUtilities.invokeLater(new Runnable(){         
      public void run(){
         createAndShowGUI();
      }        
   });
}

   @Override
   public void keyTyped(KeyEvent e)
   {
      
   }
   @Override
   public void keyPressed(KeyEvent e)
   {
      int key = e.getKeyCode();      
      if(key == KeyEvent.VK_RIGHT && playing)         
         {
            sprite.setRight(true);
            sprite.setrDirection(true);
            sprite.setlDirection(false);
         }
         
      if(key == KeyEvent.VK_LEFT && playing)
         {
            sprite.setLeft(true);
            sprite.setlDirection(true);
            sprite.setrDirection(false);
         }
      
      if(key == KeyEvent.VK_UP && sprite.getDy() == 0 && playing)
         sprite.setJumping(true);
      
      if(key == KeyEvent.VK_SPACE && playing)
      {
         shooting = true;  
         if(powered)
            JukeBox.loop("gunShotFast");
      }

      if(key == KeyEvent.VK_ENTER && !playing)
         restart();
      
      if(key == KeyEvent.VK_ENTER && opening)
      {
         playing = true;
         opening = false;
         restart();
      }     
   }
   
   @Override
   public void keyReleased(KeyEvent e)
   {
      int key = e.getKeyCode();  
      
      if(key == KeyEvent.VK_RIGHT)         
         {
            sprite.setRight(false);
         }
         
      if(key == KeyEvent.VK_LEFT)
         {
            sprite.setLeft(false);
         }   
      
      if(key == KeyEvent.VK_SPACE)
      {
         shooting = false;
         JukeBox.stop("gunShotFast");
      }       
      
   }
   
   public void readHighScore()
   {
      File filePath = new File(highScoreFile);
      try{
         Scanner dataGetter = new Scanner(filePath);
         highScore = dataGetter.nextInt();
         dataGetter.close();
      }
      catch (FileNotFoundException e){
         System.out.println("Creating high score file.");
         try
         {
            PrintWriter pw = new PrintWriter(new FileWriter(highScoreFile, false));
            pw.println(0);
            pw.close();
         } catch (IOException e1){e1.printStackTrace();}
      }
   }
   public void setScores()
   {
      if(score > highScore)
      {
         highScore = score;
         try

         {
            System.out.println("Writing high score.");
            PrintWriter pw = new PrintWriter(new FileWriter(highScoreFile, false));
            pw.println(highScore);
            pw.close();
         } catch (IOException e1){e1.printStackTrace();}
      }
   }
   
   public void addZombie()
   {
      int speed = (int)(Math.random()*3) + 3;
      int x = (int) (Math.random() * 6);
      
      if(x == 0)    zombie.add(new Zombie(100, -50, 50, 50, speed, pf));     //top left
      if(x == 1)    zombie.add(new Zombie(1300, -50, 50, 50, -speed, pf));   //top right
      if(x == 2)    zombie.add(new Zombie(-50, 450, 50, 50, speed, pf));     //middle left
      if(x == 3)    zombie.add(new Zombie(1550, 450, 50, 50, -speed, pf));   //middle right
      if(x == 4)    zombie.add(new Zombie(-50, 650, 50, 50, speed, pf));     //bottom left
      if(x == 5)    zombie.add(new Zombie(1375, 650, 50, 50, -speed, pf));   //bottom right  
      
      System.out.println(x);
   }
   
   public void autoShoot()
   {
      if(shooting)
      {
         shootTimer++;
         if(shootTimer == shootRate)
            {
               sprite.shoot();
               if(!powered)
                  JukeBox.play("gunShot");
               shootTimer = 0; 
            }
      }
      else
         shootTimer = shootRate - 1;
   }
   
   public void runPowerUp()
   {
     powerTime++;
     sprite.startPowerUp();
     shootRate = 2;
     if(powerTime >= 700)
     {
        powered = false;
        powerTime = 0;
        sprite.endPowerUp();
        shootRate = 20;
        JukeBox.stop("gunShotFast");
     }
   }
   
   public void openGame()
   {
      if(openingRate <= 300)
         openingRate++;
   }
   
//***************************UPDATE METHOD**********************************//   
   public void update()
   {
//SHOOTING
      autoShoot();
      
      for(int z = zombie.size() - 1; z >= 0 ; z--)
      {
         for(int b = sprite.getBullets().size() - 1; b >= 0; b--)
            if(zombie.get(z).getBounds().intersects(sprite.getBullets().get(b).getBounds()))
            {
               sprite.getBullets().remove(b);
               zombie.remove(z);
               JukeBox.play("deathSound");
//INCREASING SCORE
               score += 10;
//RATE OF ZOMBIES INCREASE
               if(rate > rateEnd && score % 50 == 0)
                  rate = rateStart - (score/15); 
               break;
            }            
      }
//SPAWNING ZOMBIES
      tt++;
      if(tt >= rate && (playing || opening))
      {
         addZombie();
         tt = 0;
      }
             
//REMOVING ZOMBIES AND BULLETS OFF SCREEN
      for(int z = 0; z < zombie.size(); z++)
      {
         if(!zombie.get(z).getBounds().intersects(gB))
            zombie.remove(z);
      }
//      for(int i = 0; i < pow.size(); i++)
//      {
//         if(!pow.get(i).getBounds().intersects(gB))
//            pow.remove(i);
//      }
      
      for(int b = 0; b < sprite.getBullets().size(); b++)
      {
         if(!sprite.getBullets().get(b).getBounds().intersects(bGB))
            sprite.getBullets().remove(b);
      } 
      
//DEATH OF SPRITE (CHARACTER)
      for(int z = 0; z < zombie.size(); z++)
         if(sprite.getBounds().intersects(zombie.get(z).getBounds()))
            playing = false; 
//POWER-UP
    if (powerUpPacer <= counter)
    {
       int random = (int)(Math.random()*4);
       if(random == 0)
       {
          pow.add(new PowerUp(-50, 400, 40, 40, 3, pf));
          System.out.println("LEFT POW");
       }
       if(random == 1)
       {
          pow.add(new PowerUp(50, 0, 40, 40, 3, pf));
          System.out.println("UPPER LEFT");
       }
       if(random == 2)
       {
          pow.add(new PowerUp(1300, 0, 40, 40, -3, pf));
          System.out.println("UPPER RIGHT");
       }
       if(random == 3)
       {
          pow.add(new PowerUp(1450, 400, 40, 40, -3, pf));
          System.out.println("RIGHT POW");
       }
       counter = 0;
       powerUpPacer = (int)(Math.random() * 400) + 1000;    
    }
   for(int i = 0; i < pow.size(); i ++)
      if(sprite.getBounds().intersects(pow.get(i).getBounds()))
      {
         powered = true;
         pow.remove(i);
      }
      if(powered)
         runPowerUp();            
//MOVING THE MIDDLE PLATFORM
      if(pf[2].x == 300 || pf[2].x == 750)
         pfDX = -pfDX;
      pf[2].x += pfDX;
     
//THE SPRITE ON THE PLATFORM      
      if(sprite.getBounds().intersects(pf[2]))
      {
         sprite.setDx((int) (pfDX * 1.5));
         sprite.setMoveSpeed(10);
      }
      else
         sprite.setMoveSpeed(1);     
      
//COUNTER INCREASE
      if(!opening)
         counter++;
       
//CONSOLE OUTPUTS 
   }  
   public void restart()
   {
      zombie = new ArrayList<Zombie>();
      pow = new ArrayList<PowerUp>();
      sprite.restart();
      sprite.endPowerUp();
      powered = false;
      shootRate = 20;
      powerTime = 0;
      score = 0;
      rate = rateStart;
      counter = 0;
      if(!opening)
         playing = true;
   }  
   @Override
   public void actionPerformed(ActionEvent e)
   {        
      if(playing)
         {
            sprite.update();
            
            for(int i = 0; i < pow.size(); i ++)
               pow.get(i).update();
         }   
      if(opening || playing)
         {
            update();
            for(int i = 0; i < zombie.size(); i++)
               zombie.get(i).update();
         }
      if(opening)
         {
            openGame();
         }
      if(!opening && !playing)
         {
            readHighScore();
            setScores();
            JukeBox.stop("gunShotFast");
         }
      repaint();
   }
}