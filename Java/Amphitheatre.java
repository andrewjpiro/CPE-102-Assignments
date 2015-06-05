public class Amphitheatre extends Positionable {
   //0 - TopLeft
   //1 - TopRight
   //2 - BotRight
   //3 - BotLeft
   public Amphitheatre(Point pt, String name, ImageStore iStore, int corner) {
       super(pt, name, iStore.getImages("colosseum" + Integer.toString(corner)));
   }
}
