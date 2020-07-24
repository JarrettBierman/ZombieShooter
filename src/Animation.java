import java.awt.image.BufferedImage;

//Jarrett Bierman
//Program Description
//Dec 20, 2016

public class Animation
{
   private BufferedImage[] frames;
   private int currentFrame;
   private long startTime;
   private long delay;
   
   public Animation() {}

   
   public void setFrames(BufferedImage[] images) {
      frames = images;
      if(currentFrame >= frames.length) currentFrame = 0;
   }
   
   public void setDelay(long d){
      delay = d;
   }
   
   public void update() 
      {
         if(delay == -1) return;
         
         long elapse = (System.nanoTime() - startTime) / 1000000;
         if(elapse > delay) {
            currentFrame++;
            startTime = System.nanoTime();
         }
         if(currentFrame == frames.length){
            currentFrame = 0;
         }
      }
      
   public BufferedImage getImage(){
      return frames[currentFrame];
   }
}
