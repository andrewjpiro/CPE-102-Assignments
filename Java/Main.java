import processing.core.*;
import java.util.List;

public class Main extends PApplet {
	private final int windowWidth = 640;
	private final int windowHeight = 480;
	private final int arrayWidth = 40;
	private final int arrayHeight = 30;
	private int viewWidth = 20;
	private int viewHeight = 15;
   private boolean colosseum;
	private final int tileWidth = windowWidth / viewWidth;
	private final int tileHeight = windowHeight / viewHeight;

	private ImageStore iStore;
	private WorldModel world;
	private WorldView viewPort;
	private Background defaultBgnd;
   private PImage pathImage;

	public void setup() {
      colosseum = false;
		size(windowWidth, windowHeight);
		iStore = new ImageStore(this, "imagelist", tileWidth, tileWidth);

		defaultBgnd = new Background("DefaultImageName",
				iStore.getImages("background_default"));

		world = new WorldModel(arrayHeight, arrayWidth, defaultBgnd);

		viewPort = new WorldView("What is this for???", new Point(viewWidth,
				viewHeight), new Point(windowWidth, windowHeight), new Point(
				arrayWidth, arrayHeight));

		world.loadFromSave(iStore, "gaia.sav");

      pathImage = loadImage("images/footstep.png");
	}

	public void draw() {
		viewPort.setValues(new Point(viewWidth, viewHeight),
				new Point(this.getSize().width,  this.getSize().height),
				new Point(arrayWidth, arrayHeight));

		background(color(220, 230, 245));
		world.updateOnTime(System.currentTimeMillis());
		viewPort.draw(this, world, pathImage);
	}

	public void keyPressed() {
		switch (key) {
		case 'w':
			viewPort.move(new Point(0, -1));
			break;
		case 'd':
			viewPort.move(new Point(1, 0));
			break;
		case 's':
			viewPort.move(new Point(0, 1));
			break;
		case 'a':
			viewPort.move(new Point(-1, 0));
			break;
		case 'g':
			if (viewHeight < arrayHeight - 1 && viewWidth < arrayWidth - 1) {
				viewWidth++;
				viewHeight++;
			}
			break;
		case 'h':
			if (viewHeight > 1 && viewWidth > 1){
				viewWidth--;
				viewHeight--;
			}
			break;
		}

	}

	public void mousePressed() {
      Point mousePt = new Point(viewPort.topLeft.getX() +
         (int)(mouseX / viewPort.getTileWidth()), viewPort.topLeft.getY() +
         (int)(mouseY / viewPort.getTileHeight()));
      if(!colosseum) {
         colosseum = true;
         world.addColosseum(mousePt, iStore);
         List<Miner> miners = world.getMinersNear(mousePt, /*radius=*/ 12);
         for(Miner m : miners) {
            m.setTargetColosseum();
         }
      }
	}

	public static void main(String[] args) {
		PApplet.main("Main");
	}
}
