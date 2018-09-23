package org.amy.poemdj.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import org.amy.poemdj.domain.Author;
import org.amy.poemdj.domain.Line;
import org.amy.poemdj.domain.Poem;
import org.amy.poemdj.repository.PoemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service for displaying poetry
 * 
 * @author yateam
 *
 */
@Service
public class PoemService {
	
	@Autowired
	PoemRepository poemRepository;

    Random rand = new Random(); 

	public Poem getById(Long id) {
    	return poemRepository.getOne(id);
    }
	
	//TODO: Real implementation. For now, just mix up the lines of 2 poems into stanzas of size 4.
	public Poem mixRandom() {
		// Database is well-ordered with no gaps, so get counts and get by id
		
		int numPoems = (int) poemRepository.count();
		int poem1id = rand.nextInt(numPoems-1)+1;
		int poem2id = rand.nextInt(numPoems-1)+1;
		
		Poem poem1 = poemRepository.getOne(new Long(poem1id));
		Poem poem2 = poemRepository.getOne(new Long(poem2id));
		
		System.out.println(poem1.getId());
		System.out.println(poem2.getId());
		
		List<Line> combinedLines = poem1.getLines();
		combinedLines.addAll(poem2.getLines());

		int stanzaCount = Math.floorDiv(combinedLines.size(), 5);
		Set<Integer> randomIndices = randomIndices(stanzaCount*4, combinedLines.size());
		System.out.println(randomIndices.stream().map(i -> i.toString()).collect(Collectors.joining(",")));
		Iterator<Integer> iterator = randomIndices.iterator();
		Poem returnPoem = new Poem();
		returnPoem.setTitle(poem1.getTitle() + "|" + poem2.getTitle());
		Author author = new Author();
		author.setName(poem1.getAuthor().getName() + "|" + poem2.getAuthor().getName());
		returnPoem.setAuthor(author);
		ArrayList<Line> lines = new ArrayList<>();
		for (int i=0; i < stanzaCount; i++) {
			for (int j=0;j<4;j++) {
				Integer it = iterator.next();
				Line dbLine = combinedLines.get(it);
				System.out.println(it + ":" + dbLine);
				Line line = new Line();				
				line.setLine(dbLine.getLine());
				line.setStanza(i+1);
				line.setNumber(i*4 + (j+1));
				line.setPoem(returnPoem);
				lines.add(line);
			}
		}
		returnPoem.setLines(lines);
		
		return returnPoem;
		
	}

	/**
	 * Given a num lower than bound, produce a random unique set of integers between between 0 and bound
	 * @param num
	 * @param bound
	 * @return
	 */
	private Set<Integer> randomIndices(int num, int bound) {
		
		Set<Integer> indices = new LinkedHashSet<>();
		
		//TODO: Assert num < bound
		if (num < bound) {
			while (indices.size() < num) {
				indices.add(rand.nextInt(bound));
			}
		}
		
		return indices;
		
	}
	
	public List<Poem> getPoemsByExactSearchTerm(String exact) {		
		return poemRepository.findPoemsContaining(exact);
	}

}
