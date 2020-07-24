import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

//Jarrett Bierman
//Sprite Class For the zombie game

public class Sprite
{
   private double x, y, width, height, moveSpeed, maxSpeed, stopSpeed, dx, dy, jumpHeight, gravity;
   private boolean up, down, left, right, jumping, falling, rDirection, lDirection;
   private Rectangle[] rect;
   private Animation a;
   private BufferedImage[] walking, look, jump;
   private ArrayList <Bullet> bullet = new ArrayList<Bullet>();
   
   public Sprite(double x, double y, double width, double height, Rectangle[] rect)
   {
      this.x = x;
      this.y = y;
      this.width = width;
      this.height = height;
      this.rect = rect;
      jumpHeight = 17;
      gravity = 0.5;
      moveSpeed = 1;
      maxSpeed = 7;
      stopSpeed = 1;
      
      walking = new BufferedImage[8];
      look = new BufferedImage[1];
      jump = new BufferedImage[1];
      
      try
      {
         BufferedImage sheet = ImageIO.read(ClassLoader.getSystemResource("sprite777.png"));          
         for(int i = 0; i < 8; i++)
         {
            walking[i] = sheet.getSubimage(i*50 + i, 51, 50, 50);
         }
      }catch (IOException e){e.printStackTrace();}
      
      try
      {
         BufferedImage sheet = ImageIO.read(ClassLoader.getSystemResource("sprite777.png"));          
            look[0] = sheet.getSubimage(0, 0, 50, 50);
      }catch (IOException e){e.printStackTrace();}
      
      try
      {
         BufferedImage sheet = ImageIO.read(ClassLoader.getSystemResource("sprite777.png"));          
         {
            jump[0] = sheet.getSubimage(200, 0, 50, 50);
         }
      }catch (IOException e){e.printStackTrace();}      
      
      a = new Animation();
   }
   
   public double getStopSpeed()
   {
      return stopSpeed;
   }

   public void setStopSpeed(int stopSpeed)
   {
      this.stopSpeed = stopSpeed;
   }

   public boolean isJumping()
   {
      return jumping;
   }

   public void setJumping(boolean jumping)
   {
      this.jumping = jumping;
   }

   public boolean isUp()
   {
      return up;
   }

   public void setUp(boolean up)
   {
      this.up = up;
   }

   public boolean isDown()
   {
      return down;
   }

   public void setDown(boolean down)
   {
      this.down = down;
   }

   public boolean isLeft()
   {
      return left;
   }

   public void setLeft(boolean left)
   {
      this.left = left;
   }

   public boolean isRight()
   {
      return right;
   }

   public void setRight(boolean right)
   {
      this.right = right;
   }

   public double getMoveSpeed()
   {
      return moveSpeed;
   }

   public void setMoveSpeed(int moveSpeed)
   {
      this.moveSpeed = moveSpeed;
   }

   public double getMaxSpeed()
   {
      return maxSpeed;
   }

   public void setMaxSpeed(int maxSpeed)
   {
      this.maxSpeed = maxSpeed;
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

   public boolean isFalling()
   {
      return falling;
   }

   public void setFalling(boolean falling)
   {
      this.falling = falling;
   }

   public double getJumpHeight()
   {
      return jumpHeight;
   }

   public void setJumpHeight(int jumpHeight)
   {
      this.jumpHeight = jumpHeight;
   }

   public double getGravity()
   {
      return gravity;
   }

   public void setGravity(int gravity)
   {
      this.gravity = gravity;
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

   public boolean isrDirection()
   {
      return rDirection;
   }

   public void setrDirection(boolean rDirection)
   {
      this.rDirection = rDirection;
   }

   public boolean islDirection()
   {
      return lDirection;
   }

   public void setlDirection(boolean lDirection)
   {
      this.lDirection = lDirection;
   }
   
   public ArrayList <Bullet> getBullets()
   {
      return bullet;
   }

   public Rectangle getBounds()
   {
      return new Rectangle((int)x + 20, (int)y, (int)width - 20, (int)height);
   }
   
   
   public void shoot()
   {
      int direction = 1;
      if(islDirection())
         direction = -1;
      bullet.add(new Bullet(x + (width/2) + 5, y + (height/2) - 10 , 10 , 10 , 25 * direction));
   }
   
   public void restart()
   {
      x = 500;
      y = 600;
      bullet = new ArrayList<Bullet>();
   }
   
   public void drawSprite(Graphics2D g2)
   {
      for(int i = 0; i < bullet.size(); i++)
         bullet.get(i).drawBullet(g2);
     
      if(isrDirection())
         g2.drawImage(a.getImage(), (int)x - 15, (int)y - 15, (int)width + 45, (int)height + 15, null);
      else
         g2.drawImage(a.getImage(), (int)x + (int)width + 30, (int)y - 15, (int)-width - 45, (int)height + 15, null);
   }
   
   public void startPowerUp()
   {
      jumpHeight = 25;
      moveSpeed = 2;
      maxSpeed = 10;
   }
   
   public void endPowerUp()
   {
      jumpHeight = 15;
      moveSpeed = 1;
      maxSpeed = 7;
   }

public void update()
{ 
   for(int i = 0; i < bullet.size(); i++)
      bullet.get(i).update();
   falling = true;
      if (left)
      {
         dx -= moveSpeed;
         if (dx < -maxSpeed)
            dx = -maxSpeed;
      } else if (right)
      {
         dx += moveSpeed;
         if (dx > maxSpeed)
            dx = maxSpeed;
      } else if (dx > 0)
      {
         dx -= stopSpeed;
         if (dx < 0)
            dx = 0;
      } 
      else if (dx < 0)         
      {
         dx += stopSpeed;
         if (dx > 0)
            dx = 0;
      }
      if(jumping)
      {
         dy = -jumpHeight;
         falling = true;
         jumping = false;
      }
      if(falling)
      {
         dy += gravity;
      } 
      else
      {
         dy = 0;
      }
      
      for(int i = 0; i < rect.length; i++)
         if(getBounds().intersects(rect[i]) && dy > 0){
            dy = 0;
            y = rect[i].getY() - height + 1;
         }
      x += dx;
      y += dy;
      
      if(x <= 0)
         x = 0;
      if(x >= 1400 - width)
         x = 1400 - width;
      
      if(dy != 0)
      {
         a.setFrames(jump);
         a.setDelay(-1);        
      }
      else if(left||right)
      {
         a.setFrames(walking);
         a.setDelay(60);
      }
     
      else
      {
         a.setFrames(look);
         a.setDelay(-1);
      }     
      a.update(); 
   }
}
