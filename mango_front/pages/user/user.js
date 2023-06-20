var app = getApp();
const api = require('../../utils/config');
const defaultAvatarUrl = 'https://mmbiz.qpic.cn/mmbiz/icTdbqWNOwNRna42FI242Lcia07jQodd2FJGIYQfG0LAJGFxM4FbnQP6yfMxBgJ0F3YRqJCJ1aPAK2dQagdusBZg/0'
Page({

  /**
   * 页面的初始数据
   */
  data: {
    nvabarData: {
        showCapsule: 1, //是否显示左上角图标   1表示显示    0表示不显示
        title: '个人信息', //导航栏 中间的标题
        height: 0
      },
    showModal: false,
    phone: "",
    confirm_phone: "",
    code: "",
    countdown: 60,
    columns: ["未知", "男", "⼥"],
    gender: 0 ,
    avatarUrl: defaultAvatarUrl,
    message: {},
    btname:true,
    btsave: 'none',
    showDialog1: false,
    userInfo: {},
    region: [],
    hasLogin: false,
    userSharedUrl: '',
    grade: "",
    major: "",
    showDialog1: false,
    height: app.globalData.height * 2 + 20,
    isLoading: false
  },

  /**
      * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.setData({
        height: app.globalData.height,
      })
    let that = this;
    
      /**页面渲染完毕 */
      setTimeout(function() {
        that.setData({
          isLoading: true
        })
      }, 500)
  },
  onModifyPhoneTap() {
    this.setData({
      showModal: true,
    });
  },

  onModalTap() {
    this.setData({
      showModal: false,
    });
  },

  onModalContentTap(e) {
    // 由于微信小程序中没有提供原生的阻止事件冒泡方法，这里不做处理
  },
  
  onPhoneInputChange(e) {
   //input修改后操作
    this.setData({
      phone: e.detail.value,
    });
  },

  onCodeInputChange(e) {
    this.setData({
      code: e.detail.value,
    });
  },
  onConfirmTap(){
    var that = this ;
    var code = this.data.code;
    var phone = this.data.phone;
    if(code == 0){
      wx.showToast({
        title: "请输入验证码",
        icon: "none",
      });
    }
    else{
      wx.request({
        url: getApp().globalData.url + "/verifyPhone" + "?code=" + code + "&userId=" +app.globalData.userId +"&userPhone=" +phone,
        method: "POST",
        success:function(result){

          if(result.data==200)
          {
            wx.showToast({
              title: '验证成功',
              icon: 'none', // 可以设置为 'success'、'loading' 或 'none'
              duration: 1000 // 提示框显示时间，单位为毫秒
            });
            that.data.confirm_phone = that.data.phone;  
            that.data.userInfo.phone = that.data.confirm_phone;
            wx.setStorageSync("userInfo",that.data.userInfo);
            that.setData({
              showModal: false,
            });
            wx.redirectTo({
              url: '/pages/me/me',
            })
          }
          else if(result.data==202)
          {
            wx.showToast({
              title: '验证码过期，请重试',
              icon: 'none', // 可以设置为 'success'、'loading' 或 'none'
              duration: 1000 // 提示框显示时间，单位为毫秒
            });
          }
          else if(result.data==400){
            wx.showToast({
              title: '验证码错误，请重试',
              icon: 'none', // 可以设置为 'success'、'loading' 或 'none'
              duration: 1000 // 提示框显示时间，单位为毫秒
            });
          }
          else{
            wx.showToast({
              title: '出现未知错误',
              icon: 'none', // 可以设置为 'success'、'loading' 或 'none'
              duration: 1000 // 提示框显示时间，单位为毫秒
            });
          }
        }
      })
    }
   
  },
  onGetCodeTap() {
    if (this.data.countdown !== 60) return;
    if (!this.checkPhoneValid()) {
      wx.showToast({
        title: "请输入正确的手机号",
        icon: "none",
      });
      return;
    }
    var phone = this.data.phone;
    // 请求后端发送验证码
    wx.request({
      url: getApp().globalData.url + "/sendMessage" + "?phoneNumber=" + phone + "&userId=" +app.globalData.userId ,
      method: "POST",
      success:function(result){
   
          if(result.data==200)
          {
            wx.showToast({
              title: '获取验证码成功',
              icon: 'none', // 可以设置为 'success'、'loading' 或 'none'
              duration: 1000 // 提示框显示时间，单位为毫秒
            });
          }
          else{
            wx.showToast({
              title: '获取验证码失败',
              icon: 'none', // 可以设置为 'success'、'loading' 或 'none'
              duration: 1000 // 提示框显示时间，单位为毫秒
          }
          )
        }
      }
    })
    // 模拟发送验证码成功，开始倒计时
    this.startCountdown();
  },

  startCountdown() {
    let countdown = this.data.countdown;
    const timer = setInterval(() => {
      countdown--;
      if (countdown === 0) {
        clearInterval(timer);
        countdown = 60;
      }
      this.setData({
        countdown,
      });
    }, 1000);
  },

  checkPhoneValid() {
    const phone = this.data.phone;
    const phoneReg = /^1[3456789]\d{9}$/;

    return phoneReg.test(phone);
  },
  bindRegionChange: function (e) {

     this.setData({
      region: e.detail.value
     })
  },
  pickSex: function(e) {
    this.setData({
    gender: e.detail.value
    });
  
  },
  editname(){
    this.setData({
      btname : false,
      btsave : 'block'
    })
    
  },
  pickGrade: function(e){
    this.setData({
        grade: e.detail.value
        });
    
  },
  formSubmit(e){
  
    var userCity = e.detail.value.city[0] + "|" + e.detail.value.city[1] + "|" + e.detail.value.city[2];
 
    var userGrade = e.detail.value.grade + "|" + e.detail.value.major;
   
    let userInfo = wx.getStorageSync('userInfo');
    userInfo.nickName=e.detail.value.nickname;
    userInfo.gender=e.detail.value.sex;
    userInfo.city=e.detail.value.city[1];
    userInfo.province=e.detail.value.city[0];
    userInfo.area=e.detail.value.city[2];
    userInfo.avatarUrl=this.data.avatarUrl;
    userInfo.grade = e.detail.value.grade;
    userInfo.major = e.detail.value.major;
    wx.setStorageSync('userInfo', userInfo);
    app.globalData.userInfo=userInfo;

    let SendUserInfo = {
        "userId": app.globalData.userId,
        "userOpenid": null,
        "userGender": userInfo.gender,
        "userAvatar":  userInfo.avatarUrl,
        "userNickname": userInfo.nickName,
        "userIsAdmin": userInfo.userIsAdmin,
        "userAllow": null,
        "userCreatTime": null,
        "userCity": userCity,
        "userGrade": userGrade,
        "userPhone": this.data.confirm_phone
    };
    let that = this;
    wx.request({
        url: getApp().globalData.url + "/updateUser",
        method: "POST",
        data: JSON.stringify(SendUserInfo),
        dataType: JSON,
        success: function(result) {
         
            wx.hideLoading(); 
            if (result.statusCode != 200) {
            wx.showModal({
              title: '提示',
              content: '出现问题啦，在试一下吧~',
            })
            return;
          }
          if (result.statusCode == 200) {
            that.setData({
              btsave: 'none'
            })
            wx.showModal({
              title: '提示',
              content: '更新成功',
            })
          }
        }
      })
    
   },
  onShow: function () {
    //获取用户的登录信息
    let userInfo = wx.getStorageSync('userInfo');
    var arr =new Array(3);
    arr[0] = userInfo.province;
    arr[1] = userInfo.city;
    arr[2] = userInfo.area;
    this.setData({
      userInfo: userInfo,
      hasLogin: true,
      avatarUrl:userInfo.avatarUrl,
      gender:userInfo.gender,
      region:arr,
      grade:userInfo.grade,
      major:userInfo.major,
      confirm_phone:userInfo.phone
    });
  },
  bindchooseavatar(e) {

     this.setData({
         avatarUrl : e.detail.avatarUrl,
       })
     this.fileUpLoad();
   },
  getPhoneNumber: function (e) {
    wx.showModal({
      title: '验证手机号',
      content: '',
      editable:true,//显示输入框
      editable:true,//显示输入框
    })
  },
  /*
  formsubmit(e){
    const nickName1 = e.detail.value.nickname
    console.log("nickname",nickName1)
    let userInfo = wx.getStorageSync('userInfo');
    userInfo.nickName = nickName1;//设置用户名
    wx.setStorageSync('userInfo', userInfo);
    console.log("userInfo",userInfo)
    // do something
  },  */
  test(){
    var that = this;
    /*
    wx.uploadFile({
      filePath: that.data.avatarUrl,
      name: "file",
      url: api.imgUrl,
      success: function (res) {
        console.log(api.imgUrl);
        console.log(that.data.avatarUrl);
        console.log("信息",JSON.parse(res.data))
      }})  */

  },
  fileUpLoad(){
    var that = this;

    wx.uploadFile({
        filePath: that.data.avatarUrl,
        name: "file",
        url: api.imgUrl,
        success: function (res) {
          const data = JSON.parse(res.data)
          if (data.success) {
            const avatarUrl = data.url;
    
            that.setData({
              avatarUrl : avatarUrl
            })
            that.data.userInfo.avatarUrl = avatarUrl;
            wx.setStorageSync("userInfo",that.data.userInfo);
            wx.showToast({
              title: '上传成功'
            })
          } else {
            wx.showToast({
              icon: 'error',
              title: '上传失败'
            })}
          }})
    },
  exitLogin: function () {
    wx.showModal({
      title: '',
      confirmColor: '#b4282d',
      content: '退出登录？',
      success: function (res) {
        if (!res.confirm) {
          return;
        }
        wx.removeStorageSync('token');
        wx.removeStorageSync('userInfo');
        getApp().globalData.userInfo = null;
        getApp().globalData.userId = -1;
        wx.setStorage({
            key: 'userId',
            data: -1,
        })
        wx.reLaunch({
          url: '/pages/me/me'
        });
      }
    })
  },
  test(){
    wx.showModal({
      title: '提示',
      content: ' 请先点击个人头像前往修改个人资料   你好新朋友，快去探索吧~',
    })
  }
})