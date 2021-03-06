package com.github.wesleyegberto.springwebmvc.helloworld.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.wesleyegberto.springwebmvc.helloworld.model.TrackedMinute;

@Controller
public class MinutesController {

	@RequestMapping("/addMinutes") // will handle any http method
	public String showMinutesPage(@ModelAttribute("trackedMinute") TrackedMinute trackedMinute) {
		System.out.println("Minutes: " + trackedMinute.getMinutes());
		return "addMinutes";
	}
}
