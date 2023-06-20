package work.hou6663.mango.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import work.hou6663.mango.model.MangoNotice;
import work.hou6663.mango.service.MangoNoticeService;

import java.util.HashSet;
import java.util.List;

@RestController
public class GetNoticeController {


    @Autowired
    private MangoNoticeService mangoNoticeService;

    @PostMapping("/getMessage/getAllNoticeMessage")
    public List<MangoNotice> getAllNoticeMessage() {
        return mangoNoticeService.findAll();
    }


    //根据id删除公告
    @PostMapping("/deleteNoticeMessageById")
    public int deleteNoticeMessageById(int noticeId) {
        try {
            mangoNoticeService.deleteById(noticeId);
            return 1;
        }catch (Exception e){
            return 0;
        }
    }

    //添加一个新的公告
    @PostMapping("/addNoticeMessage")
    public int addNoticeMessage(int noticeId,String noticeDetail,int type) {
        if (type==1) {
            MangoNotice mangoNotice = new MangoNotice();
            mangoNotice.setNoticeId(noticeId);
            mangoNotice.setNoticeDetail(noticeDetail);
            System.out.println(noticeDetail);
            try {
                mangoNoticeService.update(mangoNotice);
                return 1;
            } catch (Exception e) {
                return 0;
            }
        }
        else if (type == 2){
            int id = 1;
            HashSet<Integer> list = new HashSet<>();
            for (MangoNotice mangoNotice : mangoNoticeService.findAll()) {
                list.add(mangoNotice.getNoticeId());
            }
            while (true){
                if (list.contains(id))
                    id++;
                else break;
            }
            try {
                MangoNotice mangoNotice = new MangoNotice();
                mangoNotice.setNoticeId(id);
                mangoNotice.setNoticeDetail(noticeDetail);
                mangoNoticeService.add(mangoNotice);
                return 1;
            }catch (Exception e){
                return 0;
            }
        }
        else return 0;
    }
}
