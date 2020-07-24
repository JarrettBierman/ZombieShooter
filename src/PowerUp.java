import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

import javax.swing.ImageIcon;

//Jarrett Bierman
//Program Description
//Apr 20, 2017

public class PowerUp
{
   private double x, y, width, height, dx, dy, gravity;
   private boolean falling;
   private Image pic;
   private Rectangle[] rect;

   public PowerUp(double x, double y, double width, double height, double dx, Rectangle[] rect)
   {
      super();
      this.x = x;
      this.y = y;
      this.width = width;
      this.height = height;
      this.dx = dx;
      this.rect = rect;
      gravity = 0.5;
      
      try
      {pic = new ImageIcon(this.getClass().getResource("mushroom.png")).getImage();}
      catch (NullPointerException e){e.printStackTrace();} 
   }

   public double getX()
   {
      return x;
   }

   public void setX(double x)
   {
      this.x = x;
   }

   public double getY()
   {
      return y;
   }

   public void setY(double y)
   {
      this.y = y;
   }

   public double getWidth()
   {
      return width;
   }

   public void setWidth(double width)
   {
      this.width = width;
   }

   public double getHeight()
   {
      return height;
   }

   public void setHeight(double height)
   {
      this.height = height;
   }

   public double getDx()
   {
      return dx;
   }

   public void setDx(double dx)
   {
      this.dx = dx;
   }

   public double getDy()
   {
      return dy;
   }

   public void setDy(double dy)
   {
      this.dy = dy;
   }

   public double getGravity()
   {
      return gravity;
   }

   public void setGravity(double gravity)
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

   public Rectangle[] getRect()
   {
      return rect;
   }

   public void setRect(Rectangle[] rect)
   {
      this.rect = rect;
   }
   
   public void drawPowerUp(Graphics2D g2)
   {
      g2.setColor(Color.RED);
//      g2.fillRect((int)x, (int)y, (int)width, (int)height);
      g2.drawImage(pic, (int)x, (int)y, (int)width, (int)height, null);
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
   }
   

}
