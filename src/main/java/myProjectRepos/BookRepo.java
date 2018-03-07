package myProjectRepos;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import myProjectEntities.Book;

public interface BookRepo extends JpaRepository<Book,Long> {
	List<Book> findByTitle(String title);

}
