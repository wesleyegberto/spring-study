package com.github.wesleyegberto.customsecurity;

import java.util.Arrays;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class SpringCustomSecurityApplication {
	public static void main(String[] args) {
		SpringApplication.run(SpringCustomSecurityApplication.class, args);
	}
}

@RestController
@RequestMapping("/api/heroes")
class HeroesController {
	@GetMapping
	public List<String> findHeroes(@AuthenticationPrincipal Authentication user) {
		return Arrays.asList("Hulk", "Wolverine", user.getPrincipal().toString());
	}
}


@RestController
@RequestMapping("/public/heroes")
class PublicHeroesController {
	@GetMapping
	public List<String> findHeroes() {
		return Arrays.asList("Professor X");
	}
}

