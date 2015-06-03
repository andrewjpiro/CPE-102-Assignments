import java.util.ArrayList;
import java.util.List;

import processing.core.PImage;

public class OreBlob extends DynamicMover {
   private final int deathAnimation = 1;
   private boolean dead;

	public OreBlob(Point position, String name, int animationRate, int rate,
			ImageStore iStore) {
		super(position, name, animationRate, rate, iStore, "blob");
      dead = false;
	}

	public boolean toTarget(WorldModel world, Positionable destination) {
		if (destination == null) {
			return false;
		} else if (adjacent(this.getPosition(), destination.getPosition())) {
			return true;
		} else {
			Point nextpt = this.nextPosition(world, destination.getPosition());
			if (!nextpt.equals(getPosition())) {
				Entity ocupant = world.getTileOccupant(nextpt);
				if (ocupant != null && ocupant instanceof Positionable)
					world.removeEntity((Positionable)ocupant);
			}
			world.moveEntity(this, nextpt);
			return false;
		}
	}

	public boolean canMove(WorldModel world, Point pt) {
		return (!world.isOccupied(pt) || world.getTileOccupant(pt) instanceof Ore);
	}

   public void die(WorldModel world, ImageStore iStore,long ticks) {
      if(!(dead)) {
         dead = true;
         animationRate = 200;
         setAnimation(deathAnimation);
         Action deathAction = createDeathAction(world, iStore);
         world.scheduleAction(this, deathAction, ticks +
            animationRate * (allImgs.get(deathAnimation).size() - 1));
      }
   }

   protected Action createDeathAction(WorldModel world, ImageStore iStore) {
      Action[] actions = {null};
      actions[0] = (long ticks)-> {
         world.removeEntity(this);
         Vein vein = Vein.createVein(world, ticks, " ", getPosition(), iStore);
         world.addEntity(vein);

         return this.getPosition();
      };
      return actions[0];
   }

	protected Action createAction(WorldModel world, ImageStore iStore) {
		Action[] actions = { null };
		actions[0] = (long ticks)-> {
         if(dead) {
            return this.getPosition();
         }
			removePendingAction(actions[0]);

			Vein vein = world.findNearestVein(getPosition());

			super.updatePath(world, vein);

			boolean atVein = toTarget(world, vein);

			long delay = this.rate;
			if (atVein) {
				world.removeEntity(vein);
				Quake quake = Quake.createQuake(world, vein.getPosition(),
						ticks, iStore);
				world.addEntity(quake);
				delay *= 2;
			}

			this.scheduleAction(world, ticks, iStore, delay);

			return this.getPosition();
		};
		return actions[0];
	}

   protected void loadImages(ImageStore iStore) {
      allImgs = new ArrayList<List<PImage>>();

      allImgs.add(iStore.getImages("blob"));
      allImgs.add(iStore.getImages("blobDeath"));
   }

	public static OreBlob createBlob(WorldModel world, String name, Point pt,
			int rate, long ticks, ImageStore iStore) {
		// TODO: change to random rate generation

		OreBlob blob = new OreBlob(pt, name, 100, rate,
				iStore);
		blob.schedule(world, ticks, iStore);

		return blob;
	}
}
