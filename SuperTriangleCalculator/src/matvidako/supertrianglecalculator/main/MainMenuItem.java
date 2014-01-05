package matvidako.supertrianglecalculator.main;


public class MainMenuItem {

	private int id;
	private String title;
	private int imageId;
	
	public MainMenuItem(int id, String title, int imageId) {
		this.id = id;
		this.title = title;
		this.imageId = imageId;
	}

	public int getImageId() {
		return imageId;
	}

	public void setImageId(int imageId) {
		this.imageId = imageId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
