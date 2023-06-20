package work.hou6663.mango.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import work.hou6663.mango.model.MangoChatMessage;

import java.util.List;


public interface MessageMapper extends BaseMapper<MangoChatMessage> {
    List<MangoChatMessage> getMessageList(@Param("sender") Long sender, @Param("receiver") Long receiver);
}
