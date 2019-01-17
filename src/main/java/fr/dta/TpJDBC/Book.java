package fr.dta.TpJDBC;

public class Book {

	private int id;
	private String title;
	private String author;

	public Book(String title, String author) {
		this.title = title;
		this.author = author;
		// Book.books.add(this);
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
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

	@Override
	public String toString() {
		return "Book [id=" + id + ", title=" + title + ", author=" + author + "]";
	}
}
