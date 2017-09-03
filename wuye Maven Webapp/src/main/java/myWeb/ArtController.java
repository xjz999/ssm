package myWeb;

import entity.Art;
import service.ArtService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Arts")
public class ArtController {
	@Autowired
	private ArtService artService;
	
	@RequestMapping(value = "/hello/{oid}",method=RequestMethod.GET)
	public Art greetingByGet(@PathVariable("oid") String oid) {
//        return new Greeting(counter.incrementAndGet(),
//                            String.format(template, name));
		return artService.selectById(oid);//.toString();
    }
}
