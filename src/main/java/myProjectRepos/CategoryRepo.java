package myProjectRepos;

import org.springframework.data.jpa.repository.JpaRepository;

import myProjectEntities.Category;

public interface CategoryRepo extends JpaRepository<Category,Long>{

}
