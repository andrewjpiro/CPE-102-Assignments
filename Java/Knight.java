import java.util.List;

import processing.core.PImage;


public class Knight extends Mover {

	public Knight(Point position, String name, int animationRate, int rate,
			List<PImage> images) {
		super(position, name, animationRate, rate, images);
		// TODO Auto-generated constructor stub
	}
	
	public boolean canMove(WorldModel world, Point pt) {
		return !world.isOccupied(pt);
	}

	protected Action createAction(WorldModel world, ImageStore iStore) {
		Action[] actions = { null };
		actions[0] = (long ticks) -> {
			removePendingAction(actions[0]);

			OreBlob blob = world.findNearestOreBlob(getPosition());
			
			super.updatePath(world, blob);
			
			boolean atBlob = toTarget(world, blob);

			long delay = this.rate;
			if (atBlob) {
				world.removeEntity(blob);
				Vein vein = Vein.createVein(world, ticks, " ", blob.getPosition(), iStore);
				world.addEntity(vein);
				delay *= 2;
			}

			this.scheduleAction(world, ticks, iStore, delay);

			return this.getPosition();
		};
		return actions[0];
	}

}
