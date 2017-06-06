package kr.or.connect;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
	@GetMapping("/{str}")
	String hello(@PathVariable String str) {
		return str+" Hello!";
	}
}
