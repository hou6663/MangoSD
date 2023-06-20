var fileHost = "https://腾讯云对象存储地址.cos.ap-shanghai.myqcloud.com/"; //你的云东西存储地址最后面跟上一个/   在你当前小程序的后台的uploadFile 合法域名也要配上这个域名
var Url = "https://你的域名:7778";
//var Url = "https://127.0.0.1:7778";
var wsUrl = 'wss://你的域名:7778/';
//var wsUrl = 'ws://127.0.0.1:7778/';
var imageUrl = "https://腾讯云对象存储地址.cos.ap-shanghai.myqcloud.com/";//这是你的oss地址,用来展示图片,后面加斜杠

var config = {
  //腾讯 对象存储 config
  uploadImageUrl: `${fileHost}`, // 默认存在根目录，可根据需求改
  //这两个是腾讯云或者阿里云的购买
  AccessKeySecret: '', // AccessKeySecret 去你的腾讯云上控制台上找
  OSSAccessKeyId: '', // AccessKeyId 去你的腾讯云上控制台上找
  timeout: 87600 ,//这个是上传文件时Policy的失效时间
  serverUrl: Url,
  imgUrl : Url + "/upImgs",
  wsUrl :wsUrl,
  imageUrl:imageUrl,
};
module.exports = config
