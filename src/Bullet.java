import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.ArrayList;

import javax.swing.ImageIcon;

//Jarrett Bierman
//Program Description
//Feb 16, 2017

public class Bullet
{
   private double x, y, width, height, dx;
   private Image pic;
   
   public Bullet(double x, double y, double width, double height, double dx)
   {
      this.x = x;
      this.y = y;
      this.width = width;
      this.height = height;
      this.dx = dx;     
      try
      {pic = new ImageIcon(this.getClass().getResource("newBullet.png")).getImage();}
      catch (NullPointerException e){e.printStackTrace();} 
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
   
   public void drawBullet(Graphics2D g2)
   {
      g2.drawImage(pic, (int)x, (int)y, (int)width, (int)height, null);
   }
   
   public Rectangle getBounds()
   {
      return new Rectangle((int)x, (int)y, (int)width, (int)height);
   }
   
   public void update()
   {
      x += dx;
      
   }
}
