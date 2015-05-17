import processing.core.*;

public class Main extends PApplet
{
   private final int windowWidth = 640;
   private final int windowHeight = 480;
   private final int viewWidth = 20;
   private final int viewHeight = 15;
   private final int tileWidth = windowWidth/viewWidth;
   private final int tileHeight = windowHeight/viewHeight;

   private final int worldScale = 2;

   private ImageStore iStore;
   private WorldModel world;
   private WorldView viewPort;
   private Background defaultBgnd;
   public void setup()
   {
      size(windowWidth, windowHeight);
      iStore = new ImageStore(this, "imagelist", tileWidth, tileWidth);

      defaultBgnd = new Background("DefaultImageName",
              iStore.getImages("background_default"));

      world = new WorldModel(viewHeight*worldScale, viewWidth*worldScale,
         defaultBgnd);

      viewPort = new WorldView("What is this for???", 
    		  viewWidth, viewHeight, windowWidth, windowHeight);

      world.loadFromSave(iStore, "gaia.sav");
   }

   public void draw()
   {
      background(color(220, 230, 245));
      viewPort.draw(this, world);
   }

   public static void main(String[] args)
   {
      PApplet.main("Main");
   }
}
