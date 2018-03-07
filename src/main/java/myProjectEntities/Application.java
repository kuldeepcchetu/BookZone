package myProjectEntities;



import java.util.List;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import myProjectEntities.Book;
import myProjectRepos.BookRepo;
import myProjectRepos.CategoryRepo;

@SpringBootApplication
@EntityScan(basePackages="myProjectEntities")
@EnableJpaRepositories("myProjectRepos")
public class Application {
	@Autowired
    private EntityManager entityManager;
	
	private static final Logger log = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) {
		SpringApplication.run(Application.class);
	}
	
	@Bean
	public CommandLineRunner demo2(BookRepo bookrepo,CategoryRepo caterepo) {
		return (args) -> {
			
			// fetch All books 
			List<Book> theList = bookrepo.findAll();
			log.info("MealTbl found with findAll():");
			log.info("-------------------------------");
			for (Book book : theList) {
				log.info(book.toString());
			} 
			 log.info("");

			// fetch All Category
			List<Category> myList = caterepo.findAll();
			log.info("MealTbl found with findAll():");
			log.info("-------------------------------");
			for (Category cate : myList) {
				log.info(cate.toString());
			} 
			 log.info(""); 
			 
		// fetch books by title
			
			log.info("Book found with findByTitle('java'):");
			log.info("--------------------------------------------");
			for (Book book : bookrepo
					.findByTitle("")) {
				log.info(book.toString());
			}
			log.info("");
		 
			 
			 
			 
		};
	} 

}
