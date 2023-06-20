package work.hou6663.mango.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import work.hou6663.mango.model.MangoShop;
import work.hou6663.mango.model.MangoShopBusiness;
import work.hou6663.mango.model.MangoShopImages;
import work.hou6663.mango.service.MangoShopBusinessService;
import work.hou6663.mango.service.MangoShopImagesService;
import work.hou6663.mango.service.MangoShopService;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@RestController
@Slf4j
public class GetShopMessageController {
    @Autowired
    private MangoShopService mangoShopService;

    @Autowired
    private MangoShopImagesService mangoShopImagesService;
    @Autowired
    private MangoShopBusinessService mangoShopBusinessService;
    //商店图片存储路径
    @Value("${img.shopImg}")
    private String shopImg;
    @Value("${img.shopHttpImg}")
    private String shopHttpImg;
    @PostMapping("/getMessage/getAllShop")
    public List<MangoShop> getAllShopMessage() {
        return mangoShopService.findAll();
    }

    @PostMapping("/getMessage/getShopMessage/{id}")
    public MangoShop getShopMessageById(@PathVariable Integer id) {
        MangoShop mangoShop = mangoShopService.getById(id);
        MangoShopImages mangoShopImages = new MangoShopImages();
        mangoShopImages.setShopId(id);
        mangoShop.setShopImages(mangoShopImagesService.findList(mangoShopImages));
        MangoShopBusiness mangoShopBusiness = new MangoShopBusiness();
        mangoShopBusiness.setShopId(id);
        mangoShop.setShopBusinesses(mangoShopBusinessService.findList(mangoShopBusiness));
        return mangoShop;
    }
    //新增一个对象
    @PostMapping("/addShopMessage")
    public int addShopMessage() throws IOException {
        MangoShop mangoShop = new MangoShop();
        mangoShop.setShopName("请修改");
        mangoShop.setShopIntro("请修改");
        mangoShop.setShopAvatar("");
        mangoShop.setShopLongitude("117.259265");
        mangoShop.setShopLatitude("31.758259");
        mangoShop.setShopCreateTime(new Date());
        mangoShop.setShopPhone("请修改");
        mangoShopService.add(mangoShop);
        return 200;
    }
    //删除一个对象
    @PostMapping("/deleteShopMessage/{id}")
    public int deleteShopMessage(@PathVariable Integer id) {
        mangoShopService.deleteById(id);
        return 200;
    }

    @PostMapping("/updateShopMessage")
    public int updateMessage(@RequestBody MangoShop mangoShop) {
        //新增加图片判断，如果直接将图片数组加入，会将以前的图片也添加进去，所以先判断图片是否已经存在
        HashMap<Integer, MangoShopImages> imgMap = new HashMap<>();
        for (MangoShopImages mangoShopImages : mangoShopImagesService.selectByShopId(mangoShop.getShopId())) {
            imgMap.put(mangoShopImages.getShopDetailId(), mangoShopImages);
        }
        for (MangoShopImages shopImage : mangoShop.getShopImages()) {
            if (imgMap.containsKey(shopImage.getShopDetailId()))
                continue;
            shopImage.setShopId(mangoShop.getShopId());
            mangoShopImagesService.add(shopImage);
        }
        mangoShopService.update(mangoShop);
        return 200;
    }


    //删除某个图片
    @PostMapping("/deleteShopImg")
    public int deleteShopImg(@RequestBody MangoShopImages mangoShopImages) throws IOException {
        mangoShopImagesService.delete(mangoShopImages);
        return 200;
    }

    @PostMapping("/uploadImage")
    @ResponseBody
    public String uploadImage(@RequestParam("file") MultipartFile file) {
        String uploadPath = shopImg;
        try {
            // 检查文件是否为空
            if (file.isEmpty()) {
                return "{\"status\": \"error\", \"message\": \"文件为空\"}";
            }
            long maxSize = 5 * 1024 * 1024; // 5MB的字节数
            System.out.println(file.getSize());
            if (file.getSize() > maxSize) {
                return "{\"status\": \"error\", \"message\": \"文件太大\"}";
            }
            String originalFilename = file.getOriginalFilename();
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            File dest = new File(uploadPath + "/" + System.currentTimeMillis() + extension);
            file.transferTo(dest);
            // 返回上传成功的 URL
            String imageUrl = shopHttpImg + dest.getName();
            return "{\"status\": \"success\", \"url\": \"" + imageUrl + "\"}";
        } catch (Exception e) {
            e.printStackTrace();
            return "{\"status\": \"error\", \"message\": \"文件上传失败\"}";
        }
    }

    // 生成唯一的文件名，可以根据需要自定义
    private String generateUniqueFileName(String originalFileName) {
        String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
        return UUID.randomUUID().toString() + fileExtension;
    }
}

