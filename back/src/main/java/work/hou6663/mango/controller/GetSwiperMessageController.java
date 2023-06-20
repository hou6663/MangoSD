package work.hou6663.mango.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import work.hou6663.mango.model.MangoSwiper;
import work.hou6663.mango.service.MangoSwiperService;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
public class GetSwiperMessageController {

    //主页轮播图图片存储路径
    @Value("${img.headImg}")
    private String shopImg;
    //主页轮播图图片访问路径
    @Value("${img.headHttpImg}")
    private String shopHttpImg;
    @Autowired
    private MangoSwiperService mangoSwiperService;

    @PostMapping("/getMessage/getAllSwiperMessage")
    public List<MangoSwiper> getAllSwiperMessage() {
        return mangoSwiperService.findAll();
    }

    @PostMapping("/updateSwiperMessage")
    public int updateSwiperMessage(@RequestParam("file") MultipartFile file,int id) throws IOException {
        MangoSwiper mangoSwiper  = new MangoSwiper();
        mangoSwiper.setSwiperId(id);
        //上传图片代码
        if (file.isEmpty()) {
            return 401; //空资源
        }
        long maxSize = 5 * 1024 * 1024; // 5MB的字节数
        System.out.println(file.getSize());
        if (file.getSize() > maxSize) {
            return 402; // 文件大小超过1MB的错误代码
        }
        String UPLOAD_DIR = shopImg;
        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        File dest = new File(UPLOAD_DIR + "/" + System.currentTimeMillis() + extension);
        file.transferTo(dest);
        mangoSwiper.setSwiperImageUrl(shopHttpImg + dest.getName());
        mangoSwiperService.update(mangoSwiper);
        return 200;
    }
}
