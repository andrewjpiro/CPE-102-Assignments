import java.util.List;
import processing.core.PImage;

public class Entity {

	protected List<PImage> images;
	private String name;
	private int currentImage;

	public Entity(String name, List<PImage> images) {
		this.name = name;
		this.images = images;
		this.currentImage = 0;
	}

	public String getName() {
		return this.name;
	}

	public String entityString() {
		return "unknown";
	}

	public List<PImage> getImages() {
		return images;
	}

	public PImage getImage() {
		return images.get(currentImage % images.size());
	}

	public void nextImage() {
		currentImage = (currentImage + 1);
	}
}
