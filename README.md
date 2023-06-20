# MangoSD
# 微信小程序|校园|表白墙|失物招领|二手交易|论坛|兼职|拼车|活动等
# 2023-06-20所有源码公开
## 基于Mango [直达链接](https://github.com/Xin-Felix/Mango) 的二次开发，修改了一些内容，适用于当前日期较新的测试环境
## 小型项目，可以学习交流使用
项目使用技术
1.前台微信小程序(Vue)
  原生小程序开发，直接用微信开发者工具打开。
2.后台(springBoot+mybatis-plus+mysql)
  本人使用idea开发。
### 程序本身的一些功能
 1.发布帖子

 2.收藏

 3.转发
 
 4.帖子的增删改查
 
 5.管理员可以前台管理帖子，留言，有管理员页面
 
 6.匿名发布,封禁用户
 
 7.一对一聊天等

 8.管理员以及禁用的功能(数据库表mango_user中的user_is_admin为2是管理员、3为超级管理员,user_allow为1正常使用,其他数字禁止用户使用程序功能)
 
 9.....其他功能自己发现

 # 如何使用
 ## 本地部署
 ### 前台
 在 util目录下的 config.js修改
 1. imageUrl和fileHost为个人腾讯云的对象存储cos的访问域名，**所以，你得买个cos，或者使用阿里云的oss，我就是oss改成cos的，怎么改自己学习。** 最后是这样：https://example.cos.ap-shanghai.myqcloud.com/ ,记得后面有斜杠，
 2. Url 为你服务器的地址，本地部署环境，就设置为127.0.0.1即完事了，由于服务器开的端口为7778，所以你得写成这样：http://127.0.0.1:7778
 3. wsUrl 为一对一聊天的地址，本地为： ws://127.0.0.1:7778/ 后面有斜杠。
 4. AccessKeySecret: 去腾讯云的访问密钥里面找 SecretId: 就是这个东西，没有你就新建一个。 **cos的密钥，注意你的权限问题，存储桶的权限应该放开一点，不然应该无法访问。**
 5. OSSAccessKeyId: 去腾讯云的访问密钥里面找 SecretKey  和上面一样位置找。
 6. 你使用腾讯的地图要去申请地图权限使用
 **好了前台本地部署完了**
 ### 后台
 在 resources目录下的application.yml 修改
 1.  把server 的整个ssl注释掉，因为本地测试，这个负责线上的wss
 2.  修改spring下的url，username，password ，分别为数据库地址，用户名，密码，本系统用的jdbc驱动可以直接连接MySQL5.7和MySQL8.0
 3.  到下面的oa下的wx，里面是微信小程序的app-id和app-secret，自己去微信找。
 4.  sms是发送信息模板，需要去腾讯云开通短信，appid和appkey就是上面腾讯云访问密钥的SecretId和SecretKey。
 5.  sms中的templateId是正文模板id，我的是腾讯云的国内短信的正文模板管理里面的id， sign为签名管理的签名内容。
 6.  sdkId是短信下的应用管理的应用列表的SDK AppID：是短信应用的唯一标识，调用短信API接口时，需要提供该参数。
 7.  bucketName是cos对象存储的，偷个懒都放一块了，就是存储桶列表的存储桶名称。
 8.  img 下的为文件存储的地址，我的操作是服务器本地存储，这样其他设备就能云端通过http访问。本地你就写本地地址，然后本地访问路径就好了。
 9.  avatar:头像存储地址，avatarHttpImg：头像访问路径 ；headImg：主页轮播图存储地址；chatImg:聊天图片存储地址；shopImg商店图片存储地址。
 ### 数据库
 测试使用MySQL8.0和MySQL5.7都正常，sql文件是back文件夹下的sql文件。
 ## 云端部署
 ### 本人使用的腾讯云服务器，宝塔面板，添加了wss协议不知道为什么就要把域名加端口才能访问，很烦，不知道咋搞的。
 ### 我的后台是idea直接打包为jar让宝塔直接运行的。
 ### 前台
 主要步骤和本地部署的一样，只说差异处
 1. Url :设置为云端域名，用http还是https自己看。
 2. wsUrl:也是云端的wss协议需要服务器去配置，小程序的合法域名必需要是wss和https。
 ### 后台
主要步骤和本地部署的一样，只说差异处
1. ssl为wss协议的解析，如果可以使用wss就不需要注释，不会用wss就注释掉。
2. 下面说ssl 的各项：key-store就是服务器存放证书文件的地址，本地也能用，测试过
3. key-store-password和key-password就是设置的密码，key-store-type是文件的类型jks
4. 首先你要有3个pem ： chain.pem 和 fullchain.pem 和 privkey.pem 。然后设置密码给转成.jks文件
5. 下面是服务器脚本，记得自己先去路径看看自己有没有这个文件，所有文件根据真实路径来，下面是演示路径,下面脚本是ChatGPT写的，我也看不懂
openssl pkcs12 -export -in /etc/letsencrypt/live/(你的域名)/fullchain.pem -inkey /etc/letsencrypt/live/(你的域名)/privkey.pem -out keystore.p12 -name wssMango -CAfile /etc/letsencrypt/live/(你的域名)/chain.pem -caname root
keytool -importkeystore -deststorepass 123456 -destkeypass 123456 -destkeystore keystore.jks -srckeystore keystore.p12 -srcstoretype PKCS12 -srcstorepass 123456 -alias wssMango
最后会生成一个keystore.jks，然后你把路径填进去就完事了。

## 如果对你有帮助，可以捐助一点给作者哦。
![baipiao](9150e4e5gy1gb5dqmlb21j204k04kmx0.jpg)
![weixin](1687227952779.png)
![zhibufbao](1687227920696.jpg)
