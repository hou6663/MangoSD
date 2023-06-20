package work.hou6663.mango.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import work.hou6663.mango.mapper.MangoNewMessageMapper;
import work.hou6663.mango.model.MangoNewMessage;
import work.hou6663.mango.util.SameService;

import java.util.List;

@Service
public class MangoNewMessageService extends SameService<MangoNewMessageMapper,MangoNewMessage> {


    @Autowired
    private MangoNewMessageMapper mangoNewMessageMapper;

    public List<MangoNewMessage> getAllNewMessage(Integer userId) {
        return mangoNewMessageMapper.getAllNewMessage(userId);
    }

    public MangoNewMessage getLastNewMessage(Integer id) {
        return mangoNewMessageMapper.getLastNewMessage(id);
    }
}
