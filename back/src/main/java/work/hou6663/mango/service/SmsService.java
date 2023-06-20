package work.hou6663.mango.service;

import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.sms.v20210111.SmsClient;
import com.tencentcloudapi.sms.v20210111.models.SendSmsRequest;
import com.tencentcloudapi.sms.v20210111.models.SendSmsResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service
public class SmsService {

    @Value("${sms.appid}")
    private String appid;

    @Value("${sms.appkey}")
    private String appkey;

    @Value("${sms.sdkId}")
    private String sdkId;

    @Value("${sms.templateId}")
    private String templateId;

    @Value("${sms.sign}")
    private String sign;

    public SendSmsResponse sendVerificationCode(String phoneNumber, String code) throws Exception {
        Credential cred = new Credential(appid, appkey);
        HttpProfile httpProfile = new HttpProfile();
        httpProfile.setEndpoint("sms.tencentcloudapi.com");
        ClientProfile clientProfile = new ClientProfile();
        clientProfile.setHttpProfile(httpProfile);
        SmsClient client = new SmsClient(cred, "ap-guangzhou", clientProfile);

        String[] phoneNumberSet = {"+86" + phoneNumber};
        String[] templateParamSet = {code};
        log.info("sdk:"+sdkId);
        SendSmsRequest req = new SendSmsRequest();
        req.setPhoneNumberSet(phoneNumberSet);
        req.setTemplateId(templateId);
        req.setSignName(sign);
        req.setTemplateParamSet(templateParamSet);
        req.setSmsSdkAppId(sdkId);

        SendSmsResponse resp = client.SendSms(req);
        log.info("req:");
        log.info(String.valueOf(req));
        return resp;
    }
}
