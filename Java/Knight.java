import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

import processing.core.PImage;


public class Knight extends DynamicMover {
   private static final int defaultAnim = 0;
   private static final int upAnim = 1;
   private static final int leftAnim = 2;
   private static final int rightAnim = 3;
   private static final int leftSlashAnim = 4;
   private static final int rightSlashAnim = 5;

	public Knight(Point position, String name, int animationRate,
      int rate, ImageStore iStore) {

		super(position, name, animationRate, rate, iStore, "knight");
	}

	public boolean canMove(WorldModel world, Point pt) {
		return !world.isOccupied(pt);
	}

   public boolean toTarget(WorldModel world, Positionable destination) {
      Point oldPt = getPosition();
      boolean found = super.toTarget(world, destination);
      Point newPt = getPosition();

      int xDiff = newPt.getX() - oldPt.getX();
      int yDiff = newPt.getY() - oldPt.getY();
      if(xDiff > 0) {
         setAnimation(rightAnim);
      }
      else if(xDiff < 0) {
         setAnimation(leftAnim);
      }
      else if(yDiff < 0) {
         setAnimation(upAnim);
      }
      else if(yDiff > 0) {
         setAnimation(defaultAnim);
      }
      else if(found){
         slash(destination.getPosition().getX() - newPt.getX(),
               destination.getPosition().getY() - newPt.getY());
      }
      else {
         setAnimation(defaultAnim);
      }

      return found;
   }

	protected Action createAction(WorldModel world, ImageStore iStore) {
		Action[] actions = { null };
		actions[0] = (long ticks)-> {
			removePendingAction(actions[0]);

			OreBlob blob = world.findNearestOreBlob(getPosition());

			super.updatePath(world, blob);

			boolean atBlob = toTarget(world, blob);

			long delay = this.rate;
			if (atBlob) {
            blob.die(world, iStore, ticks);
            delay *= 2;
			}

			this.scheduleAction(world, ticks, iStore, delay);

			return this.getPosition();
		};
		return actions[0];
	}


   protected void loadImages(ImageStore iStore) {
      List<String> keys = new ArrayList< >(Arrays.asList(new String[] {
         "knight",
         "knightUp",
         "knightLeft",
         "knightRight",
         "knightSlashLeft",
         "knightSlashRight"
      }));

      allImgs = new ArrayList<List<PImage>>();
      for(String key : keys) {
         allImgs.add(iStore.getImages(key));
      }
   }

   private void slash(int xDiff, int yDiff) {
      if(xDiff > 0 || yDiff < 0) {
         setAnimation(rightSlashAnim);
      }
      else {
         setAnimation(leftSlashAnim);
      }
   }
}
