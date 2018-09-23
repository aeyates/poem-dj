package org.amy.poemdj.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.amy.poemdj.domain.Poem;
import org.amy.poemdj.service.PoemService;
import org.amy.poemdj.view.PoemModel;
import org.amy.poemdj.view.ThemeSearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * TODO: mix endpoint, match endpoint, navigation, database instead of resources
 * @author yateam
 *
 */
@Controller
public class MainController {
	
	@Autowired
	PoemService poemService;
	
	@GetMapping("/")
	public String home(Map<String, Object> model, HttpServletRequest request) {
		return "home";
	}

	@GetMapping("/mix")
	public String mix(Map<String, Object> model, HttpServletRequest request) {
		try {
			Poem poem = poemService.mixRandom();
			model.put("poem", new PoemModel(poem));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "mix";
	}

	@GetMapping("/match")
	public String match(Map<String, Object> model, HttpServletRequest request) {
		model.put("searchForm", new ThemeSearch());
		return "match";
	}

	@PostMapping("/match")
	public String match(Map<String, Object> model, HttpServletRequest request, @ModelAttribute ThemeSearch form) {
		List<Poem> poems = poemService.getPoemsByExactSearchTerm(form.getExactTheme());
		model.put("searchForm", form);
		model.put("results", true);
		model.put("poems", poems);
		return "match";
	}

	@GetMapping("/spin")
	public String spin(Map<String, Object> model, HttpServletRequest request) {
		return "spin";
	}

}
