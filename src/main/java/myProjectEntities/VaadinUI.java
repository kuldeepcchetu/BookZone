package myProjectEntities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.vaadin.annotations.Theme;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import myProjectRepos.BookRepo;
import myProjectRepos.CategoryRepo;
@SpringUI
public class VaadinUI extends UI {

	private final BookRepo repo;
	private final CategoryRepo categoRepo;
	private final BookEditor editor;

	final Grid<Book> grid;

	final TextField filter;
	final Label lable; 
	
    
	private final Button addNewBtn;

	@Autowired
	public VaadinUI(BookRepo repo, BookEditor editor,CategoryRepo categoRepo) {
		this.repo = repo;
		this.categoRepo=categoRepo;
		this.editor = editor;
		this.grid = new Grid<>(Book.class);
		this.filter = new TextField();
		this.addNewBtn = new Button("New Book", FontAwesome.PLUS);
		this.lable=new Label("Welcome to Book Zone.Com");
		
	}

	@Override
	protected void init(VaadinRequest request) {
		// build layout
		HorizontalLayout actions = new HorizontalLayout(filter, addNewBtn);
		VerticalLayout firstLayout = new VerticalLayout(lable,actions, grid);
		HorizontalSplitPanel hsp = new HorizontalSplitPanel (firstLayout,editor);
		hsp.setSplitPosition(75);
		lable.addStyleName ( ValoTheme.LABEL_H2);
		grid.setSizeFull();
		setContent(hsp);
		
		grid.setColumns("title","author","price", "quantity", "publisher", "isbn","year","description","category");
		filter.setPlaceholder("Filter by title");

		// Hook logic to components

		// Replace listing with filtered content when user changes filter
		filter.setValueChangeMode(ValueChangeMode.LAZY);
		filter.addValueChangeListener(e -> listbooks(e.getValue()));

		// Connect selected book to editor or hide if none is selected
		grid.asSingleSelect().addValueChangeListener(e -> {
			editor.editbook(e.getValue());
		});

		// Instantiate and edit new Book the new button is clicked
		addNewBtn.addClickListener(e -> editor.editbook(new Book(" "," "," ","0"," ","0.0"," ")));

		// Listen changes made by the editor, refresh data from backend
		editor.setChangeHandler(() -> {
			editor.setVisible(false);
			listbooks(filter.getValue());
		});

		// Initialize listing
		listbooks(filter.getValue());
	}

	// tag::listBooks[]
	void listbooks(String filterText) {
		if (StringUtils.isEmpty(filterText)) {
			grid.setItems(repo.findAll());
		}
		else {
			grid.setItems(repo.findByTitle(filterText));
		}
	}
	// end::listBooks[]

}
