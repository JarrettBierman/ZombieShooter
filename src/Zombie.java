import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

//Jarrett Bierman
//Program Description
//Feb 16, 2017

public class Zombie
{
   private double x, y, width, height, dx, dy, gravity;
   private boolean falling;
   private Rectangle[] rect;
   private BufferedImage[] walking;
   private Animation a;

   public Zombie(double x, double y, double width, double height, double dx, Rectangle[] rect)
   {
      super();
      this.x = x;
      this.y = y;
      this.width = width;
      this.height = height;
      this.dx = dx;
      this.rect = rect;
      gravity = 0.5;
      
      
      walking = new BufferedImage[6];
      
      try
      {
         BufferedImage sheet = ImageIO.read(ClassLoader.getSystemResource("kirbyzomb.png"));          
         for(int i = 0; i < 6; i++)
         {
            walking[i] = sheet.getSubimage(i*22 + i, 0, 22, 22);
         }
      }catch (IOException e){e.printStackTrace();}
      
      a = new Animation();
      
   }

   public double getX()
   {
      return x;
   }

   public void setX(int x)
   {
      this.x = x;
   }

   public double getY()
   {
      return y;
   }

   public void setY(int y)
   {
      this.y = y;
   }

   public double getWidth()
   {
      return width;
   }

   public void setWidth(int width)
   {
      this.width = width;
   }

   public double getHeight()
   {
      return height;
   }

   public void setHeight(int height)
   {
      this.height = height;
   }

   public double getDx()
   {
      return dx;
   }

   public void setDx(int dx)
   {
      this.dx = dx;
   }

   public double getDy()
   {
      return dy;
   }

   public void setDy(int dy)
   {
      this.dy = dy;
   }
   
   public double getGravity()
   {
      return gravity;
   }

   public void setGravity(int gravity)
   {
      this.gravity = gravity;
   }

   public boolean isFalling()
   {
      return falling;
   }

   public void setFalling(boolean falling)
   {
      this.falling = falling;
   } 
///////////////////////////////////////////////
   public void drawZombie(Graphics2D g2)
   {
//      g2.setColor(Color.GREEN);
//      g2.fillRect(x, y, width, height);
//      g2.setColor(Color.BLACK);
//      g2.drawRect(x, y, width, height);
      if(dx < 0)
         g2.drawImage(a.getImage(), (int)x, (int)y, (int)width, (int)height, null);
      else
         g2.drawImage(a.getImage(), (int)x + (int)width, (int)y, (int)-width, (int)height, null);
         
   }
   
   public Rectangle getBounds()
   {
      return new Rectangle((int)x, (int)y, (int)width, (int)height);
   }
   
   public void update()
   {
      dy += gravity;
      for(int i = 0; i < rect.length; i++)
         if(getBounds().intersects(rect[i])){
            dy = 0;
            y = rect[i].getY() - height + 1;
         }
      x += dx;
      y += dy;
      
      a.setFrames(walking);
      a.setDelay(70);   
      
      a.update(); 
   }   
}
