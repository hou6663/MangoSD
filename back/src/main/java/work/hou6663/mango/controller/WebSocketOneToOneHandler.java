package work.hou6663.mango.controller;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import work.hou6663.mango.model.MangoChatMessage;
import work.hou6663.mango.service.MessageServiceImpl;
import work.hou6663.mango.util.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import work.hou6663.mango.util.SpringContextUtils;

import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Controller
@Scope("singleton")
@Lazy
public class WebSocketOneToOneHandler extends TextWebSocketHandler {

    private static final Logger log = LoggerFactory.getLogger(WebSocketOneToOneHandler.class);

    private static final HashMap<Long, WebSocketSession> connections = new HashMap<>();

    private static int onlineCount;
    private WebSocketSession session; // 使用 WebSocketSession 替代 Session
    private Long sendId;
    private String roomId;

   // @Autowired
    private static final MessageServiceImpl businessMessageService = SpringContextUtils.getBean(MessageServiceImpl.class);

    @Override
    public synchronized void handleTextMessage(@NotNull WebSocketSession session, TextMessage message) {
        // onMessage 你的代码...
        System.out.println("来自客户端的消息:" + message);
        JSONObject json= JSON.parseObject(message.getPayload());
        String msg =json.getString("content");  //需要发送的信息
        String requestId = json.getString("requestId");
        int msgType = json.getIntValue("messageType");
        String lastMessageTime = null;
        if(json.containsKey("lastMessageTime")){
            lastMessageTime = json.getString("lastMessageTime");
        }
        Long giftId =null;
        Long receiveId = json.getLong("receiveId");      //发送对象的用户标识(接收者)
        Long sendId = json.getLong("sendId");
        send(msg,sendId,receiveId,roomId,msgType,requestId,lastMessageTime,giftId);
    }
    @Override
    public synchronized void afterConnectionEstablished(WebSocketSession session) {
        // onOpen 你的代码...
        this.session = session;
        // 解析WebSocket连接的URL
        URI uri = session.getUri();
        String path = uri.getPath();
        log.info("WebSocket连接的URL:"+path);
        String[] parts = path.split("/");
        log.info(Arrays.toString(parts));
        if (parts.length >= 4) {
            this.sendId = Long.valueOf(parts[2]);  // 获取sendId
            this.roomId = parts[3];  // 获取roomId
        }
        connections.put(sendId, this.session);     //添加到map中
        //addOnlineCount();               // 在线数加
        setOnlineCount(connections.size());
        System.out.println("用户加入连接：连接数：");
        for (WebSocketSession con : connections.values()) {
            System.out.println(con);
        }
        System.out.println("有新连接加入！新用户："+sendId+",当前在线人数为" + getOnlineCount());
    }


    @Override
    public synchronized void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        // onClose 你的代码...
        if (sendId != null) {
            connections.remove(getSendId(session)); // 从map中移除
            //subOnlineCount(); // 在线数减
            setOnlineCount(connections.size());
            System.out.println("有一连接关闭！当前在线人数为" + getOnlineCount());
            System.out.println("用户连接关闭：连接数：");
            for (WebSocketSession con : connections.values()) {
                System.out.println(con);
            }
        }
    }
    @Override
    public synchronized void handleTransportError(WebSocketSession session, Throwable exception) {
        //onError 你的代码...
        System.out.println("发生错误");
        exception.printStackTrace();
    }
    /**
     *
     * @param msg 消息内容
     * @param sendId 发送人
     * @param receiveId 接收人
     * @param roomId 房间ID
     * @param msgType 消息类型
     * @param requestId 消息请求ID
     * @param lastMessageTime 最后一次的消息时间
     * @param giftId 礼物ID
     */


    public synchronized void send(String msg,Long sendId,Long receiveId,String roomId,int msgType,String requestId,String lastMessageTime,Long giftId){
        MangoChatMessage message = new MangoChatMessage();
        message.setContent(msg);
        Date now = new Date();
        message.setCreatedTime(now);
        message.setReceiver(receiveId);
        message.setSender(sendId);
        message.setContentType(msgType);
        message.setIsRead("0");
        message.setRequestId(requestId);
        message.setType(0);
        if(StringUtils.isNotBlank(lastMessageTime)){
            Date lastTime = DateUtils.stringToDate(lastMessageTime,"yyyy-MM-dd HH:mm");
            long minute = (now.getTime() - lastTime.getTime()) / 1000 / 60;
            log.info("此二人聊天，距离上一次聊天时间相差分钟数："+minute);
            if(minute > 5 ){
                message.setType(1);
            }
        }
        try {
            int res =businessMessageService.sendMessage(message);
            if(res==1){
                message.setStatus("-1");
            }
            System.out.println("用户发送信息：连接数：");
            for (WebSocketSession con : connections.values()) {
                System.out.println(con);
            }
            //发送
            WebSocketSession con = connections.get(receiveId);
            if(con!=null){
                log.info(con.toString());
                if(roomId.equals(getRoomId(con))){
                    con.sendMessage(new TextMessage(JSON.toJSONString(message)));
                    UpdateWrapper updateWrapper = new UpdateWrapper();
                    updateWrapper.set("is_read",1);
                    updateWrapper.eq("id",message.getId());
                    businessMessageService.update(updateWrapper);
                }
            }
            //通知发送消息的狗逼，消息已经发送成功
            WebSocketSession confrom = connections.get(sendId);
            if(confrom!=null) {
                log.info(confrom.toString());
                if (roomId.equals(getRoomId(confrom))) {
                    confrom.sendMessage(new TextMessage(JSON.toJSONString(message)));
                }
            }
            } catch (IOException ex) {
            throw new RuntimeException(ex);
        }


    }


    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSocketOneToOneHandler.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSocketOneToOneHandler.onlineCount--;
    }
    public static synchronized void setOnlineCount(int count) {
        WebSocketOneToOneHandler.onlineCount = count;
    }
    // 你的其他代码...
    public synchronized String getRoomId(WebSocketSession session){
        URI uri = session.getUri();
        String path = uri.getPath();
        log.info("WebSocket连接的URL:"+path);
        String[] parts = path.split("/");
        log.info(Arrays.toString(parts));
        if (parts.length >= 4) {
            return parts[3];  // 获取roomId
        }
        return null;
    }

    public synchronized Long getSendId(WebSocketSession session){
        URI uri = session.getUri();
        String path = uri.getPath();
        log.info("WebSocket连接的URL:"+path);
        String[] parts = path.split("/");
        log.info(Arrays.toString(parts));
        if (parts.length >= 4) {
            return Long.valueOf(parts[2]);  // 获取roomId
        }
        return null;
    }
}

