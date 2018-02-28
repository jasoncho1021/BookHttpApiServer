package kr.or.connect.domain;

public class Book {
	private Integer id;
	private String title;
	private String author;
	private Integer page;
	
	public Book(){
	}

	public Book(String title, String author, Integer page) {
		this.title = title;
		this.author = author;
		this.page = page;
	}

	public Book(Integer id, String title, String author, Integer page) {
		this(title, author, page);
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

}
