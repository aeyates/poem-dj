package org.amy.poemdj;

import java.util.List;

import org.amy.poemdj.domain.Author;
import org.amy.poemdj.domain.Poem;
import org.amy.poemdj.repository.AuthorRepository;
import org.amy.poemdj.repository.PoemRepository;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PoemDjApplicationTests {
	
	@Autowired
	PoemRepository poemRepository;

	@Autowired
	AuthorRepository authorRepository;

	// Only uncomment to ingest resources from files with mysql started
	@Ignore
	@Test
	public void loadDb() {
		try {
			ClassLoader classLoader = this.getClass().getClassLoader();
			ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver(classLoader);
			Resource[] resources = resolver.getResources("classpath*:/poems/**/*.json") ;
			for (Resource resource: resources){
				ObjectMapper om = new ObjectMapper();
				Poem poem = om.readValue(resource.getInputStream(), Poem.class);
				poem.setAuthor(findOrPersistAuthor(poem.getAuthor(), authorRepository));
				// Set the parents correctly
				poem.getLines().forEach(line -> {
					line.setPoem(poem);
				});
				poemRepository.save(poem);
				resource.getInputStream().close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

  private Author findOrPersistAuthor(Author author, AuthorRepository authorRepository) {
		List<Author> dbAuthors = authorRepository.findByName(author.getName());
		if (dbAuthors.size() == 1) {
			return dbAuthors.get(0);
		}
		
		if (dbAuthors.size() > 2) {
			throw new RuntimeException("Unique constraint should prevent this.");
		}
		
		return authorRepository.save(author);
	}

}
