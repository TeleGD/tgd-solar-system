package app.elements;

public abstract class MenuItem {

	private String content;

	public MenuItem(String content) {
		this.setContent(content);
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getContent() {
		return this.content;
	}

	public abstract void itemSelected();

}
