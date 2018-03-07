package myProjectEntities;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the book database table.
 * 
 */
@Entity
@NamedQuery(name="Book.findAll", query="SELECT b FROM Book b")
public class Book implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue//(strategy=GenerationType.AUTO)
	private Long id;
	private String title;

	private String author;

	@Lob
	private String description;

	@Column(name="existing_quantity")
	private int quantity;

	private String isbn;

	@Column(name="last_update")
	private Timestamp lastUpdate;

	private BigDecimal price;

	private String publisher;

	@Column(name="release_year")
	private String year;
	//bi-directional many-to-one association to Category
	@ManyToOne
	private Category category;

	//bi-directional many-to-many association to CustomerOrder
	@ManyToMany
	@JoinTable(
		name="ordered_book"
		, joinColumns={
			@JoinColumn(name="book_id")
			}
		, inverseJoinColumns={@JoinColumn(name = "customer_order_id")
			}
		)
	private List<CustomerOrder> customerOrders;

	protected Book() {
	}
	public Book( String title, String author, String description, String quantity, String isbn, 
			String price, String publisher ) {
		this.title = title;
		this.author = author;
		this.description = description;
		this.quantity = Integer.parseInt(quantity);
		this.isbn = isbn;
		this.price = new BigDecimal(price);
		this.publisher = publisher;	
	}
	public Book(Long id , String title, String author, String description, int quantity, String isbn, 
			BigDecimal price, String publisher, String year,Timestamp lastUpdate,Category catego) {
		super();
		this.id = id;
		this.title = title;
		this.author = author;
		this.description = description;
		this.quantity = quantity;
		this.isbn = isbn;
		this.price = price;
		this.publisher = publisher;
		this.year = year;
		this.lastUpdate = lastUpdate;
		this.category=catego;

	}
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAuthor() {
		return this.author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getQuantity() {
		return this.quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getIsbn() {
		return this.isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public Timestamp getLastUpdate() {
		return this.lastUpdate;
	}

	public void setLastUpdate(Timestamp lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public BigDecimal getPrice() {
		return this.price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getPublisher() {
		return this.publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getYear() {
		return this.year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Category getCategory() {
		return this.category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public List<CustomerOrder> getCustomerOrders() {
		return this.customerOrders;
	}

	public void setCustomerOrders(List<CustomerOrder> customerOrders) {
		this.customerOrders = customerOrders;
	}
	@Override
	public String toString() {
		return "Book [id=" + id + ", title=" + title + ", author=" + author + ", description=" + description + ", existingQuantity="
				+quantity + ", isbn=" + isbn + ", price=" + price
				+ ", publisher=" + publisher + ", year=" + year + ", lastUpdate=" + lastUpdate+ "]";
	}

}