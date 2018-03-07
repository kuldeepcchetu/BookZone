package myProjectEntities;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.data.Binder;
import com.vaadin.data.converter.StringToBigDecimalConverter;
import com.vaadin.data.converter.StringToIntegerConverter;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.event.ShortcutAction;
import com.vaadin.server.FontAwesome;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import myProjectRepos.BookRepo;
import myProjectRepos.CategoryRepo;


@SpringComponent
@UIScope
public class BookEditor extends VerticalLayout {
	private Book book;
	private final BookRepo bookRepo;
	
	private Category catego;
	private final CategoryRepo categoRepo;
	/* Fields to edit properties in Customer entity */
	TextField titleTF = new TextField("Title");
	TextField authorTF = new TextField("Author");
	TextField isbnTF = new TextField("ISBN");
	TextField publisherTF = new TextField("Publisher");
	TextField yearTF = new TextField("Year");
    TextField priceTF = new TextField("Price");
    TextField quantityTF = new TextField("Quantity");
	TextArea descriptionTA=new TextArea("Description");
	ComboBox<Category> comboBox=new ComboBox<>();
	/* Action buttons */
	Button save = new Button("Save", FontAwesome.SAVE);
	Button cancel = new Button("Cancel");
	Button delete = new Button("Delete", FontAwesome.TRASH_O);
	CssLayout actions = new CssLayout(save, cancel, delete);

	Binder<Book> bookbinder = new Binder<>(Book.class);
    Binder<Category> categobinder = new Binder<>(Category.class);
	@Autowired
	public BookEditor(BookRepo bookRepo,CategoryRepo categoRepo) {
		this.bookRepo = bookRepo;
		this.categoRepo=categoRepo;
		
		addComponents( titleTF, authorTF,isbnTF,publisherTF,yearTF,priceTF,quantityTF,descriptionTA,comboBox,actions);	

		//bookbinder.bindInstanceFields ( this) ;
        bookbinder.forField(this.titleTF).asRequired("required").bind(Book:: getTitle, Book:: setTitle);
        bookbinder.forField(this.authorTF).asRequired("required").bind(Book:: getAuthor, Book:: setAuthor);
        bookbinder.forField(this.isbnTF).asRequired("required").bind(Book:: getIsbn, Book:: setIsbn);
        bookbinder.forField(this.publisherTF).asRequired("required").bind(Book:: getPublisher, Book:: setPublisher);
        bookbinder.forField(this.yearTF).asRequired("required").bind(Book:: getYear, Book:: setYear);
        bookbinder.forField ( this.priceTF ) 
	  	  .withNullRepresentation( "" )
	        .withConverter ( new StringToBigDecimalConverter(BigDecimal.valueOf(00.00), "Price must be in ##.## format"))
	        .asRequired("required").bind ( Book:: getPrice, Book:: setPrice );
		bookbinder.forField(this.descriptionTA).bind(Book:: getDescription, Book:: setDescription);
        bookbinder.forField(this.quantityTF).withNullRepresentation( "" )
        .withConverter ( new StringToIntegerConverter(Integer.valueOf(0), "Only Integer"))
        .asRequired("required").bind( Book:: getQuantity, Book:: setQuantity);
		bookbinder.forField(this.comboBox).bind(Book::getCategory, Book::setCategory);
	    this.comboBox.setItems(categoRepo.findAll());
        // Configure and style components
		setSpacing(true);
		actions.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
		save.setStyleName(ValoTheme.BUTTON_PRIMARY);
		save.setClickShortcut(ShortcutAction.KeyCode.ENTER);

		// wire action buttons to save, delete and reset
		save.addClickListener(e -> {bookRepo.save(book); /*categoRepo.save(category);*/});
		delete.addClickListener(e -> bookRepo.delete(book));
		cancel.addClickListener(e -> editbook(book));
		setVisible(false);
	}

	public interface ChangeHandler {

		void onChange();
	}

	public final void editbook(Book c) {
		if (c == null) {
			setVisible(false);
			return;
		}
		final boolean persisted = c.getId() != null;
		if (persisted) {
			// Find fresh entity for editing
			book = bookRepo.findOne(c.getId());
		}
		else {
			book = c;
		}
		cancel.setVisible(persisted);

		// Bind customer properties to similarly named fields
		// Could also use annotation or "manual binding" or programmatically
		// moving values from fields to entities before saving
		bookbinder.setBean(book);

		setVisible(true);

		// A hack to ensure the whole form is visible
		save.focus();
		// Select all text in firstName field automatically
		titleTF.selectAll();
	}

	public void setChangeHandler(ChangeHandler h) {
		// ChangeHandler is notified when either save or delete
		// is clicked
		save.addClickListener(e -> h.onChange());
		delete.addClickListener(e -> h.onChange());
	}

}
