package example.EcoCloset;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClothesController {
    
    @Autowired
    private ClothesService clothesService;

    // 의류전체 조회
    @GetMapping("/clothes")
    public ArrayList<ClothesDto> clothesPrintAll(){
        return clothesService.clothesPrintAll();
    }

      // 의류 등록(C)
    @PostMapping("/closet")
    public boolean clothesAdd(@RequestBody ClothesDto closetDto, @RequestParam(name = "mno")int mno){
        return clothesService.clothesAdd(closetDto, mno);
    }


}
