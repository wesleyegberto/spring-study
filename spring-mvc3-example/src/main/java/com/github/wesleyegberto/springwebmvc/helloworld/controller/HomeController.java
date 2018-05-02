package com.github.wesleyegberto.springwebmvc.helloworld.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {

	@RequestMapping(method = RequestMethod.GET, value = "/greeting")
	public String index(Model model) {
		model.addAttribute("greeting", "Hello World!");
		return "hello";
	}
}
