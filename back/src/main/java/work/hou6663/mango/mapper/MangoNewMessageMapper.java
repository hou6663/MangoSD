package work.hou6663.mango.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import work.hou6663.mango.model.MangoNewMessage;

import java.util.List;

public interface MangoNewMessageMapper extends BaseMapper<MangoNewMessage> {

    List<MangoNewMessage> getAllNewMessage(Integer id);


    MangoNewMessage getLastNewMessage(Integer id);
}