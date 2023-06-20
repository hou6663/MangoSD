package work.hou6663.mango.controller;

import com.tencentcloudapi.sms.v20210111.models.SendSmsResponse;
import lombok.extern.slf4j.Slf4j;
import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import work.hou6663.mango.model.MangoMessage;
import work.hou6663.mango.model.MangoUser;
import work.hou6663.mango.service.MangoMessageDetailService;
import work.hou6663.mango.service.MangoUserService;
import work.hou6663.mango.service.SmsService;
import work.hou6663.mango.util.GetRandom;
import work.hou6663.mango.util.login.IsLogin;


import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;


@Slf4j
@RestController
public class LoginController {
    @Autowired
    private WxMaService wxService;

    @Autowired
    private SmsService smsService;

    @Autowired
    private MangoUserService mangoUserService;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private MangoMessageDetailService mangoMessageDetailService;
    //头像存储路径
    @Value("${img.avatar}")
    private String avatar;
    @Value("${img.avatarHttpImg}")
    private String avatarHttp;
    private final String UPLOAD_DIR = avatar;

    /**
     * 登录功能
     *
     * @param code
     * @param mangoUser
     * @return
     */
    @Transactional
    @PostMapping("/Login")

    public IsLogin Login(String code, @RequestBody MangoUser mangoUser) {

        //String wxResult = HttpClientUtil.doGet(url, param);
        //log.info(wxResult);
        //WXSessionModel wxSessionModel = JsonUtils.jsonToPojo(wxResult, WXSessionModel.class);
        WxMaJscode2SessionResult result=null;
        try {
            result = this.wxService.getUserService().getSessionInfo(code);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        log.info("result");
        log.info(String.valueOf(result));

        String openid = result.getOpenid();
        mangoUser.setUserOpenid(openid);
        log.info("mangoUser:");
        log.info(String.valueOf(mangoUser));
        log.info("openid");
        log.info(openid);
        log.info("mangoUserService");
        log.info(String.valueOf(mangoUserService));

        return new IsLogin().isTrue(mangoUser, openid, mangoUserService);
    }

    @PostMapping("/loadUserInfo")
    public MangoUser LoadUserInfo(String code){
        MangoUser mangoUser = null;
        WxMaJscode2SessionResult result=null;
        try {
            result = this.wxService.getUserService().getSessionInfo(code);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        String userOpenid = result.getOpenid();
        mangoUser = mangoUserService.selectUserByOpenid(userOpenid);
        return mangoUser;
    }
    @PostMapping("/checkAdmin")
    public List<MangoUser> checkAdmin(Integer id) {
        MangoUser mangoUser = new MangoUser();
        mangoUser.setUserId(id);
        return mangoUserService.getUserMessageByOtherMessage(mangoUser);
    }

    @PostMapping("/upImgs")
    public Map<String, Object> upload(@RequestParam("file") MultipartFile file) throws IOException {
        Map<String, Object> result = new HashMap<>();
        if (file.isEmpty()) {
            result.put("success", false);
            result.put("message", "请选择要上传的文件");
            return result;
        }
        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        File dest = new File(UPLOAD_DIR + "/" + System.currentTimeMillis() + extension);
        file.transferTo(dest);
        result.put("success", true);
        result.put("url", avatarHttp + dest.getName());
        log.info("result:");
        log.info(String.valueOf(result));
        return result;
    }
    @PostMapping("/updateUser")
    public String UpdateUserInfo(@RequestBody MangoUser mangoUser){
        log.info(String.valueOf(mangoUser));
        Map<String,String> map = new HashMap<>();
        map.put("Grade",mangoUser.getUserGrade());
        map.put("Area",mangoUser.getUserCity());
        log.info(String.valueOf(map));
        String statusCode="500";
        try {
            mangoUserService.UpdateUserInfo(mangoUser);
            statusCode="200";
        }
        catch (Exception e){

        }
        return statusCode;
    }
    @PostMapping("/sendMessage")
    public  String SendMessage(String phoneNumber,int userId){
        String statusCode = "400";
        String code = GetRandom.generateVerificationCode(6);
        SendSmsResponse response = null;
        try {
            response = smsService.sendVerificationCode(phoneNumber, code);
            statusCode = "200";
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 处理发送短信的响应，例如：保存验证码到数据库等
        log.info("code"+code);
        MangoUser mangoUser = mangoUserService.SelectUserById(userId);
        mangoUser.setUserPhone_Code(code);
        ZonedDateTime currentTimeInChina = ZonedDateTime.now(ZoneId.of("Asia/Shanghai"));
        // 将 ZonedDateTime 转换为 java.sql.Timestamp
        Timestamp timestamp = Timestamp.valueOf(currentTimeInChina.toLocalDateTime());
        mangoUser.setUserPhone_Code_Time(timestamp);
        mangoUserService.UpdateUserInfo(mangoUser);
        return statusCode;
    }
    @PostMapping("/verifyPhone")
    public  String VerifyPhone(String code,int userId,String userPhone){
        String statusCode = "400";
        MangoUser mangoUser = mangoUserService.SelectUserById(userId);
        String systemCode = mangoUser.getUserPhone_Code();
        ZonedDateTime currentTimeInChina = ZonedDateTime.now(ZoneId.of("Asia/Shanghai"));
        // 将 ZonedDateTime 转换为 java.sql.Timestamp
        Timestamp nowTimestamp = Timestamp.valueOf(currentTimeInChina.toLocalDateTime());
        Date systemDate  =  mangoUser.getUserPhone_Code_Time();
        Timestamp systemTimestamp = new Timestamp(systemDate.getTime());
        if (code.equals( systemCode) )
        {
            statusCode = "202";
            long differenceInMillis = Math.abs(nowTimestamp.getTime() - systemTimestamp.getTime());
            long differenceInMinutes = differenceInMillis / (60 * 1000);
            // 判断时间差距是否小于 5 分钟
            if (differenceInMinutes < 5) {
                log.info("两个时间对象的差距小于5分钟");
                statusCode = "200";
                mangoUser.setUserPhone(userPhone);
                mangoUserService.UpdateUserInfo(mangoUser);
                updateMessage(mangoUser);
            } else {
                log.info("两个时间对象的差距大于或等于5分钟");
            }
            log.info(String.valueOf(differenceInMinutes));
        }
        return statusCode;
    }

    //更新用户的所有帖子信息
    private void updateMessage(MangoUser mangoUser){
        List<MangoMessage> list = new ArrayList<>(mangoMessageDetailService.getMessageDetailByUserId(mangoUser.getUserId()));
        for (MangoMessage mangoMessage : list) {
            mangoMessage.setUserPhone(mangoUser.getUserPhone());
            mangoMessageDetailService.update(mangoMessage);
        }

    }

}
