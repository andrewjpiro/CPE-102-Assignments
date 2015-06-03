import java.util.List;

import processing.core.PImage;

public abstract class DynamicMover extends Mover {
   protected List<List<PImage>> allImgs;
   private int currentAnimation;

   public DynamicMover(Point position, String name, int animationRate,
      int rate, ImageStore iStore, String imageKey) {

      super(position, name, animationRate, rate, iStore.getImages(imageKey));
      loadImages(iStore);
   }

   public PImage getImage() {
      images = allImgs.get(currentAnimation);
      return super.getImage();
   }

   public void setAnimation(int animation) {
      if(animation >= allImgs.size()) {
         throw new IndexOutOfBoundsException();
      }
      currentAnimation = animation;
   }

   protected void loadImages(ImageStore iStore){};
}
