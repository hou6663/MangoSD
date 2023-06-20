package work.hou6663.mango.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import work.hou6663.mango.model.MangoCategory;
import work.hou6663.mango.service.MangoCategoryService;

import java.util.List;

@RestController
public class GetCategoryMessageController {

    @Autowired
    private MangoCategoryService mangoCategoryService;


    @PostMapping("/getMessage/getAllCategoryMessage")
    public List<MangoCategory> getAllCategoryMessage() {
        return mangoCategoryService.findAll();
    }
}
