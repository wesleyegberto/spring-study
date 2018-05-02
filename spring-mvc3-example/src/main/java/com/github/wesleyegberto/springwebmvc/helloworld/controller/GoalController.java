package com.github.wesleyegberto.springwebmvc.helloworld.controller;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.github.wesleyegberto.springwebmvc.helloworld.model.Goal;

@Controller
@SessionAttributes("goal") // store and read the goal from session
public class GoalController {

	@RequestMapping(value = "/addGoal", method = RequestMethod.GET)
	public String addGoal(Model model) {
		model.addAttribute("goal", new Goal());
		return "addGoal";
	}
	
	@RequestMapping(value = "/addGoal", method = RequestMethod.POST)
	public String updateGoal(@Valid @ModelAttribute Goal goal, BindingResult result) {
		System.out.println("Creating goal: " + goal.getDescription());
		if (result.hasErrors()) {
			return "addGoal";
		}
		return "redirect:addMinutes.html";
	}
	
	@RequestMapping(value = "/goals/current.json", method = RequestMethod.GET)
	public @ResponseBody Goal getCurrentGoal(@ModelAttribute Goal goal) {
		return goal;
	}
}
