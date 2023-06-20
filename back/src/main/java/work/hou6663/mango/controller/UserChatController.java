package work.hou6663.mango.controller;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import work.hou6663.mango.config.OSSFactory;
import work.hou6663.mango.model.MangoChatMessage;
import work.hou6663.mango.model.MangoUser;
import work.hou6663.mango.service.MangoUserService;
import work.hou6663.mango.service.inter.MessageService;
import work.hou6663.mango.util.ChatUserInfo;
import work.hou6663.mango.util.R;

import cn.hutool.core.io.file.FileWriter;
import cn.hutool.core.util.IdUtil;
import java.io.IOException;
import java.util.*;


@Slf4j
@RestController
public class UserChatController {

    @Autowired
    private MessageService messageService;
    @Autowired
    private MangoUserService mangoUserService;

    //聊天图片路径
    @Value("${img.chatImg}")
    private String chatImg;
    @Value("${img.chatHttp}")
    private String chatHttp;
    private final String filePath = chatImg;
    private final String urlHeadPath = chatHttp;
    //获取聊天的人的列表
    @PostMapping("/getAllChatUser")
    public List<ChatUserInfo> getAllChatUser(int userId) {
        List<ChatUserInfo> userList = new ArrayList<>();
        List<MangoChatMessage> list1 = messageService.list();
        list1.sort(Comparator.comparing(MangoChatMessage::getCreatedTime).reversed());
        Set<Integer> addedUserIds = new HashSet<>(); // 用于去重已添加的用户ID
        for (MangoChatMessage mangoChatMessage : list1) {
            if (mangoChatMessage.getSender() == userId) {
                long receiverIdInt = mangoChatMessage.getReceiver();
                int receiverId = (int) receiverIdInt; // 将Long类型转换为int类型
                if (!addedUserIds.contains(receiverId)) {
                    ChatUserInfo chatUserInfo = new ChatUserInfo();
                    MangoUser user = mangoUserService.getById(receiverId);
                    if (user != null) {
                        chatUserInfo.setMangoUser(user);
                        chatUserInfo.setIsNoRead(0);
                        userList.add(chatUserInfo);
                        addedUserIds.add(receiverId);
                    }
                }
            } else if (mangoChatMessage.getReceiver() == userId) {
                long senderIdInt = mangoChatMessage.getSender();
                int senderId = (int) senderIdInt; // 将Long类型转换为int类型
                if (!addedUserIds.contains(senderId)) {
                    ChatUserInfo chatUserInfo = new ChatUserInfo();
                    MangoUser user = mangoUserService.getById(senderId);
                    if (user != null) {
                        chatUserInfo.setMangoUser(user);
                        if (mangoChatMessage.getIsRead().equals("0") && mangoChatMessage.getIsRead().equals("0"))
                            chatUserInfo.setIsNoRead(1);
                        userList.add(chatUserInfo);
                        addedUserIds.add(senderId);
                    }
                }
            }
        }
        MangoUser adminUser = mangoUserService.getById(24);
        ChatUserInfo userInfo = new ChatUserInfo();
        userInfo.setMangoUser(adminUser);
        int isNotRead=0;

        // 获取管理员
        if (addedUserIds.contains(24)) {
            Iterator<ChatUserInfo> iterator = userList.iterator();
            while (iterator.hasNext()) {
                ChatUserInfo user = iterator.next();
                if (user.getMangoUser().getUserId() == 24) {
                    isNotRead = user.getIsNoRead();
                    iterator.remove();
                    break;
                }
            }
        }
        userInfo.setIsNoRead(isNotRead);
        if (adminUser != null) {
            userList.add(0,userInfo);
        }
        return userList;
    }


    @GetMapping("/getMessageHistory")
    public R getMessageHistory(Long myMemberId, Long youMemberId, int pageNo, int pageSize){
        Page page = new Page<>(pageNo,pageSize);
        //查询历史聊天记录
        QueryWrapper<MangoChatMessage> queryWrapper = new QueryWrapper();
        queryWrapper.eq("del_flag", 0);
        queryWrapper.and(wrapper -> wrapper.and(item -> item.eq("sender",myMemberId).eq("receiver",youMemberId))
                .or(item -> item.eq("receiver",myMemberId).eq("sender",youMemberId))
        );
        queryWrapper.orderByDesc("created_time");
        Page pageData = messageService.page(page, queryWrapper);
        //第一次获取，将此人与对方的聊天消息，全部置为已读
        if(pageNo==1){
            UpdateWrapper updateWrapper = new UpdateWrapper();
            updateWrapper.eq("del_flag", 0);
            updateWrapper.eq("sender",youMemberId);
            updateWrapper.eq("receiver",myMemberId);
            updateWrapper.set("is_read",1);
            messageService.update(updateWrapper);
        }
        return R.ok().put("datas",pageData);
    }

    //上传聊天图片
    @RequestMapping(value = "/uploadChatImg")
    public R upload(@RequestParam("file") MultipartFile multipartFile, Integer source, String openId
    ) {
        //上传文件
        String suffix = Objects.requireNonNull(multipartFile.getOriginalFilename()).substring(multipartFile.getOriginalFilename().lastIndexOf("."));
        try {
            String url = "";
            //上传到本地服务器
            String uuid = IdUtil.simpleUUID()+suffix;
            FileWriter writer = new FileWriter(filePath+uuid);
            writer.writeFromStream(multipartFile.getInputStream());
            url = urlHeadPath+uuid;
            return R.ok().put("url", url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return R.error();
    }


}
