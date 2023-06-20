//index.js
//获取应用实例
const app = getApp()

Page({
  data: {
    // 组件所需的参数
    nvabarData: {
      showCapsule: 0, //是否显示左上角图标   1表示显示    0表示不显示
      title: '我的', //导航栏 中间的标题
      height: 0
    },
    userId: "-1",
    message: {},
    userInfo: {},
    showDialog1: false,
    // 此页面 页面内容距最顶部的距离
    height: app.globalData.height * 2 + 20,
    isLoading: false,
    loginFlag:true,
  },
  onLoad() {
    this.setData({
      height: app.globalData.height
    })
    let that = this
    wx.getStorage({
      key: 'userInfo',
      success: function(res) {
        that.setData({
          userInfo: res.data
        })
      },
      fail: function() {
        if (that.data.userId == -1) {
          that.setData({
            showDialog1: true
          })
        }
      }
    })
    that.setData({
      userInfo: getApp().globalData.userInfo,
      userId: getApp().globalData.userId
    })
    wx.getStorage({
      key: 'userId',
      success: function(res) {
        that.setData({
          userId: res.data
        })
      },
      fail: function() {
        if (that.data.userId == -1) {
          that.setData({
            showDialog1: true
          })
        }
      }
    })
  },
  checkAdmin() {
    let that = this
    wx.request({
      url: getApp().globalData.url + '/checkAdmin?id=' + new Number(that.data.userId),
      method: "post",
      success: function(res) {
        if (res.data[0].userIsAdmin != 2 && res.data[0].userIsAdmin != 3) {
          wx.showModal({
            title: '提示',
            content: '还未获得权限，请联系管理员',
          })
        }
        if (res.data[0].userIsAdmin == 2 || res.data[0].userIsAdmin == 3) {
          if(res.data[0].userIsAdmin == 3){
            wx.setStorage({
              key: 'userIsSuperAdmin',
              data: 1,
            })
            res.data[0].userIsAdmin=2;
          }
          wx.showModal({
            title: '提示',
            content: '已获得管理员权限\r\n是否前往管理界面？',
            success (res) {
              if (res.confirm) {
                wx.navigateTo({
                  url: '/pages/admin/admin',
                })
              } else if (res.cancel) {
              }
            }
          })
        }
        wx.setStorage({
          key: 'userIsAdmin',
          data: res.data[0].userIsAdmin,
        })

        getApp().globalData.userIsAdmin = res.data[0].userIsAdmin
      }
    })
  },
  doLogin: function(e) {

    let that = this

    var listMessage = {
      "userAvatar": e.detail.userInfo.avatarUrl,
      "userNickname": e.detail.userInfo.nickName,
      "userGender": e.detail.userInfo.gender,
    }
    wx.login({
      success: function(res) {
        wx.showLoading({
          title: '登陆中~',
        })
        // console.log(that.data.openid)
        // 获取登录的临时凭证
        var code = res.code;
        // 调用后端，获取微信的session_key, secret
        wx.request({
          url: getApp().globalData.url + "/Login?code=" + code,
          method: "POST",
          data: JSON.stringify(listMessage),
          dataType: JSON,
          success: function(result) {
            var flag = true;
           // console.log(getApp().globalData.url + "/Login?code=" + code)
            wx.hideLoading();
            let data = JSON.parse(result.data);
            if (result.statusCode != 200 || data.code == 500) {
              flag = false;
              that.setData({
                loginFlag :false
              })
              wx.showModal({
                title: '提示',
                content: '出现问题啦，在试一下吧~',
              })
              //return;
            }
            if (data.code == 200) {
              wx.showModal({
                title: '提示',
                content: '欢迎回来，老朋友~',
              })
            }
            if (data.code == 300) {
              wx.showModal({
                title: '提示',
                content: ' 请先点击个人头像前往修改个人资料   你好新朋友，快去探索吧~',
              })
            }
            // that.setData({
            //   showDialog1: false
            // })
            if(flag){
              that.secondLogin();
            }
          }
        })

        //第二次获取数据

      }
    })
    /*
    wx.login({
      success: function(res) {
        wx.showLoading({
          title: '登陆中~',
        })
        // console.log(that.data.openid)
        // 获取登录的临时凭证
        var code = res.code;
        console.log(that.data.loginFlag+"secondlogin");
    if(that.data.loginFlag){
    wx.request({
      url: getApp().globalData.url + "/loadUserInfo?code=" + code,
      method: "POST",
      success: function(result) {

        var userInfo ={};
        that.data.userInfo=userInfo;
        //需要新建一个userInfo对象，否则复制that.data.userInfo成员为空
        that.data.userInfo.nickName= result.data.userNickname;
        that.data.userInfo.avatarUrl =result.data.userAvatar;
        that.data.userInfo.gender = result.data.userGender;
        that.data.userInfo.phone = result.data.userPhone;
        var str1=result.data.userCity;
        var str2=result.data.userGrade;
        if(str1 !=null ){
          var arr=str1.split("|");
          that.data.userInfo.city= arr[1];
          that.data.userInfo.province=arr[0];
          that.data.userInfo.area=arr[2];
        }
        if(str2 !=null){
          var arr2=str2.split("|");
          that.data.userInfo.grade=arr2[0];
          that.data.userInfo.major=arr2[1];
        }
   
   
     
        that.data.userInfo.userIsAdmin=result.data.userIsAdmin;

     
        getApp().globalData.userId = result.data.userId;
        getApp().globalData.userInfo = that.data.userInfo;
        wx.setStorageSync('userInfo', that.data.userInfo);
        wx.setStorageSync('userId', result.data.userId);
        wx.setStorageSync('userIsAdmin',result.data.userIsAdmin);
        that.setData({
          showDialog1: false
        })
        that.onLoad();
      }})
    }}})
    */
  },
  secondLogin(){
    let that = this;
    wx.login({
      success: function(res) {
        
        // console.log(that.data.openid)
        // 获取登录的临时凭证
        var code = res.code;
    if(that.data.loginFlag){
    wx.request({
      url: getApp().globalData.url + "/loadUserInfo?code=" + code,
      method: "POST",
      success: function(result) {

        var userInfo ={};
        that.data.userInfo=userInfo;
        //需要新建一个userInfo对象，否则复制that.data.userInfo成员为空
        that.data.userInfo.nickName= result.data.userNickname;
        that.data.userInfo.avatarUrl =result.data.userAvatar;
        that.data.userInfo.gender = result.data.userGender;
        that.data.userInfo.phone = result.data.userPhone;
        var str1=result.data.userCity;
        var str2=result.data.userGrade;
        if(str1 !=null ){
          var arr=str1.split("|");
          that.data.userInfo.city= arr[1];
          that.data.userInfo.province=arr[0];
          that.data.userInfo.area=arr[2];
        }
        if(str2 !=null){
          var arr2=str2.split("|");
          that.data.userInfo.grade=arr2[0];
          that.data.userInfo.major=arr2[1];
        }
   
   
     
        that.data.userInfo.userIsAdmin=result.data.userIsAdmin;

     
        getApp().globalData.userId = result.data.userId;
        getApp().globalData.userInfo = that.data.userInfo;
        wx.setStorageSync('userInfo', that.data.userInfo);
        wx.setStorageSync('userId', result.data.userId);
        wx.setStorageSync('userIsAdmin',result.data.userIsAdmin);
        that.setData({
          showDialog1: false
        })
        that.onLoad();
      }})
    }}})
  },

  onReady() {
    let that = this
    setTimeout(function() {
      that.setData({
        isLoading: true
      })
    }, 500)
  },

  attention() {
    wx.showModal({
      title: '提示',
      content: '公众号名称  芒果微校园',
      confirmText: "复制",
      showCancel: false,
      success: function() {
        wx.setClipboardData({
          data: '芒果微校园',
        })
      }
    })
  },
  call() {
    wx.showModal({
      title: '提示',
      content: '微信联系/手机联系',
      confirmText: "手机联系",
      confirmColor: "#3cc",
      cancelColor: "#3cc",
      cancelText: "微信联系",
      success: function(e) {
        if (e.confirm) {
          wx.showModal({
            title: '提示',
            content: '是否联系(17318567890)',
            success: function(e) {
              if (e.confirm) {
                wx.makePhoneCall({
                  phoneNumber: '17318567890',
                })
              }
            }
          })
        } else {
          wx.showModal({
            title: '提示',
            content: '微信号：17318567890',
            confirmText: "复制",
            success: function(e) {
              if (e.confirm) {
                wx.setClipboardData({
                  data: '17318567890',
                })
              }
            }
          })
        }
      }
    })
  },
  onShow() {
    let that = this
    wx.getStorage({
      key: 'userInfo',
      success: function(res) {
        that.setData({
          userInfo: res.data
        })
      },
      fail: function() {
        if (that.data.userId == -1) {
          that.setData({
            showDialog1: true
          })
        }
      }
    })
    that.setData({
      userInfo: getApp().globalData.userInfo,
      userId: getApp().globalData.userId
    })
    this.setData({
      userId: getApp().globalData.userId
    })
    wx.getStorage({
      key: 'userId',
      success: function(res) {
        that.setData({
          userId: res.data
        })
      },
    })
  }
})