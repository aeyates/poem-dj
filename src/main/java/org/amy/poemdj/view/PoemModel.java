package org.amy.poemdj.view;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.amy.poemdj.domain.Author;
import org.amy.poemdj.domain.Line;
import org.amy.poemdj.domain.Poem;

public class PoemModel {
	
	Author author;
	String title;
	
	ArrayList<StanzaModel> stanzas;
	
	public PoemModel(Poem poem) {		
		this.setAuthor(poem.getAuthor());
		this.setTitle(poem.getTitle());
		this.stanzas = new ArrayList<>();
		Map<Integer, List<Line>> linesGroupedByStanza = poem.getLines().stream().collect(Collectors.groupingBy(line -> line.getStanza()));
		for (Integer stanzaNum : linesGroupedByStanza.keySet()) {
			StanzaModel stanza = new StanzaModel();
			stanza.setStanza(stanzaNum);
			List<Line> stanzaLines = linesGroupedByStanza.get(stanzaNum);
			Collections.sort(stanzaLines, (l1, l2) -> l1.getNumber().compareTo(l2.getNumber()));
			stanza.setLines(new ArrayList<>(stanzaLines));
			stanzas.add(stanza);
		}
		Collections.sort(stanzas, (s1, s2) -> s1.getStanza().compareTo(s2.getStanza()));
	}

	public Author getAuthor() {
		return author;
	}

	public void setAuthor(Author author) {
		this.author = author;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public ArrayList<StanzaModel> getStanzas() {
		return stanzas;
	}

	public void setStanzas(ArrayList<StanzaModel> stanzas) {
		this.stanzas = stanzas;
	}
	
	

}
