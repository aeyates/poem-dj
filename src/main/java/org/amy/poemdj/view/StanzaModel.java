package org.amy.poemdj.view;

import java.util.ArrayList;

import org.amy.poemdj.domain.Line;

public class StanzaModel {

	Integer stanza;
	ArrayList<Line> lines;

	public Integer getStanza() {
		return stanza;
	}

	public void setStanza(Integer stanza) {
		this.stanza = stanza;
	}

	public void setLines(ArrayList<Line> lines) {
		this.lines = lines;
	}

	public ArrayList<Line> getLines() {
		return lines;
	}

}
