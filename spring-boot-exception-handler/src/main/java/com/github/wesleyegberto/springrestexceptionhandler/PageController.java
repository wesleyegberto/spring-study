package com.github.wesleyegberto.springrestexceptionhandler;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/web")
public class PageController {
	@GetMapping("customerrorpage")
	public String customErrorPage() {
		throw new NumberFormatException("We can't format your number");
	}
}