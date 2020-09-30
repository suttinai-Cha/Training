package cs.example.csdemo.controler;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController()
@RequestMapping("/cs")
public class HelloController {

	@RequestMapping("")
	public String index() {
		return "Oh...you touch my tra la la";
	}

}