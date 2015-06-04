import java.util.List;
import processing.core.*;

public class WorldView {
	public Point topLeft;
	private Point bottomRight;
	private Point tileArraySize;
	private float tileWidth;
	private float tileHeight;
	private Point tilesToDisplay;
   private  Mover tracked;

	public WorldView(String string, Point viewGrid, Point window,
			Point worldSize) {
		topLeft = new Point(0, 0);
		this.setValues(viewGrid, window, worldSize);
		tracked = null;
	}

	public void setValues(Point tilesToDisplay, Point pixelDimensions, Point tileArraySize){
		this.tileArraySize = new Point(tileArraySize.getX(), tileArraySize.getY());
		bottomRight = new Point(tilesToDisplay.getX() + topLeft.getX(), tilesToDisplay.getY() + topLeft.getY());
		tileWidth = pixelDimensions.getX() / tilesToDisplay.getX();
		tileHeight = pixelDimensions.getY() / tilesToDisplay.getY();
	}

	public void draw(Main main, WorldModel world, PImage pathImage) {
		drawBackground(main, world);
		drawEntities(main, world);
		drawPath(main, world, pathImage);
	}

	public void move(Point delta) {
		if (topLeft.getX() + delta.getX() >= 0
				&& bottomRight.getX() + delta.getX() <= tileArraySize.getX()
				&& topLeft.getY() + delta.getY() >= 0
				&& bottomRight.getY() + delta.getY() <= tileArraySize.getY()) {

			topLeft.setX(topLeft.getX() + delta.getX());
			bottomRight.setX(bottomRight.getX() + delta.getX());
			topLeft.setY(topLeft.getY() + delta.getY());
			bottomRight.setY(bottomRight.getY() + delta.getY());
		}
	}

	private void drawEntities(Main main, WorldModel world) {
		List<Positionable> entities = world.getEntities();

		for (Positionable a : entities) {
			if (inView(a)) {
				main.image(a.getImage(),
						(a.getPosition().getX() - topLeft.getX()) * tileWidth,
						(a.getPosition().getY() - topLeft.getY()) * tileHeight,
						tileWidth, tileHeight);
			}
		}

	}

	private void drawBackground(PApplet main, WorldModel world) {
		int height = tileArraySize.getY();
		int width = tileArraySize.getX();
		boolean farDown =  bottomRight.getY() >= height;
		boolean farRight =  bottomRight.getX() >= width;

		for (int i = topLeft.getY(); farDown ? i < height : i <= bottomRight.getY() ; i++) {
			for (int j = topLeft.getX(); farRight ? j < width : j <=  bottomRight.getX(); j++) {
				main.image(world.getBackground(new Point(j, i)).getImage(),
						(j - topLeft.getX()) * tileWidth, (i - topLeft.getY())
								* tileHeight, tileWidth, tileHeight);
			}
		}
	}

   private void drawPath(PApplet main, WorldModel world, PImage pathImage) {
      Point mousePt = new Point(topLeft.getX() +
         (int)(main.mouseX / tileWidth), topLeft.getY() +
         (int)(main.mouseY / tileHeight));

      if(world.getTileOccupant(mousePt) instanceof Mover) {
         tracked = (Mover)world.getTileOccupant(mousePt);
      }
      if(tracked != null) {
         drawList(main, tracked.getPath(), pathImage);
      }
   }

   private void drawList(PApplet main, List<Point> path, PImage pathImage) {
      for(Point p : path) {
         main.image(pathImage,
               (p.getX() - topLeft.getX()) * tileWidth,
               (p.getY() - topLeft.getY()) * tileHeight,
               tileWidth, tileHeight);
      }
   }

	private boolean inView(Positionable entity) {
		Point pos = entity.getPosition();
		return pos.getX() >= topLeft.getX() && pos.getX() <= bottomRight.getX()
				&& pos.getY() >= topLeft.getY()
				&& pos.getY() <= bottomRight.getY();
	}
}
