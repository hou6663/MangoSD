package work.hou6663.mango.service.inter;

import com.baomidou.mybatisplus.extension.service.IService;
import work.hou6663.mango.model.MangoChatMessage;

public interface MessageService extends IService<MangoChatMessage> {

    int sendMessage(MangoChatMessage mangoChatMessage);
}
