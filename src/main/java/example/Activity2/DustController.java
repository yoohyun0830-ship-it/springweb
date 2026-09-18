package example.Activity2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController 
@CrossOrigin(origins = "http://localhost:5173")
public class DustController {
    @Autowired 
    private DustService dustService;

    @GetMapping("/api/dust") 
    public String getDustData(){
        return dustService.getDustData();
    }
}
