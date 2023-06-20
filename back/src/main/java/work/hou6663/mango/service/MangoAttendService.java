package work.hou6663.mango.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import work.hou6663.mango.mapper.MangoAttendMapper;
import work.hou6663.mango.model.MangoAttend;
import work.hou6663.mango.util.SameService;

import java.util.List;

@Service
public class MangoAttendService extends SameService<MangoAttendMapper,MangoAttend> {

    @Autowired
    private MangoAttendMapper mangoAttendMapper;

    public List<MangoAttend> getAllAttendMessageByUserId(Integer id) {
        return mangoAttendMapper.getAllAttendMessageByUserId(id);
    }
}
