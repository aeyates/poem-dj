package org.amy.poemdj.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.amy.poemdj.domain.Poem;
import org.amy.poemdj.service.PoemService;
import org.amy.poemdj.view.PoemModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class PoemController {
	
	@Autowired
	PoemService poemService;
	
	@GetMapping("/poem/{id}")
	public String poem(Map<String, Object> model, HttpServletRequest request, @PathVariable Long id) {
		Poem poem = poemService.getById(id);
		model.put("poem", new PoemModel(poem));
		return "poem/show";
	}
	

}
