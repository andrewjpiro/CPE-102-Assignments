import java.util.List;

import processing.core.PImage;

public class Ore extends Actor {
	private static final int NAME = 1;
	private static final int COL = 2;
	private static final int ROW = 3;
	private static final int RATE = 4;

	private int rate;

	public Ore(Point position, String name, int rate, List<PImage> images) {
		super(position, name, images);
		this.rate = rate;
	}

	public String entityString() {
		return String.format("ore %1$s %2$s %3$s", super.getName(), super
				.getPosition().toString(), this.rate);
	}

	public void schedule(WorldModel world, long ticks, ImageStore iStore) {
		scheduleAction(world, ticks, iStore, rate);
	}

	public static Positionable createFromProperties(String[] prop,
			ImageStore iStore) {
		Point pos = new Point(Integer.parseInt(prop[COL]),
				Integer.parseInt(prop[ROW]));
		int rate = Integer.parseInt(prop[RATE]);

		return new Ore(pos, prop[NAME], rate, iStore.getImages("ore"));
	}

	protected Action createAction(WorldModel world, ImageStore iStore) {
		Action[] actions = { null };
		actions[0] = (long ticks) -> {
			removePendingAction(actions[0]);
			OreBlob blob = OreBlob.createBlob(world, getName(), getPosition(),
					1000, ticks, iStore);
			world.removeEntity(this);
			world.addEntity(blob);
			return blob.getPosition();
		};

		return actions[0];
	}

	public static Ore createOre(WorldModel world, String name, Point pos,
			long ticks, ImageStore iStore) {
		// TODO: make rate random
		Ore ore = new Ore(pos, name, 15000, iStore.getImages("ore"));
		ore.schedule(world, ticks, iStore);
		return ore;
	}
}
