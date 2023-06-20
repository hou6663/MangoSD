// pages/manageShopDetail/manageShopDetail.js
const app = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    height: app.globalData.height,
    nvabarData: {
      showCapsule: 1, //是否显示左上角图标   1表示显示    0表示不显示
      title: '商店管理', //导航栏 中间的标题
      height: 0
    },
    markers:{},
    shopMessage: {},
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad(options) {
    let that = this;
    const shopId = options.id;
    console.log("Received shopId:", shopId);
    // 可以根据 shopId 进行相应的操作
    wx.request({
      url: getApp().globalData.url + '/getMessage/getShopMessage/' + shopId,
      method: "post",
      success: function(e) {
        if (e.statusCode != 200) {
          wx.showModal({
            title: '错误',
            content: '出现错误啦，请联系管理员',
          })
          return;
        }        
        that.setData({
          shopMessage: e.data,
          latitude:e.data.shopLatitude,
          longitude:e.data.shopLongitude,
          markers: [{
            id: 1,
            latitude: e.data.shopLatitude,
            longitude: e.data.shopLongitude,
            name:e.data.shopName,
            width: 20,
            height: 40,
            label:{
              content:"【 " + e.data.shopName + "】芒果微校园",
            }
          }]
        })
      },})
  },


  bindDateChange: function(e) {
   var dateObject = e.detail.value + ' 12:00';
    this.setData({
      'shopMessage.shopCreatTime': dateObject
    })
  },
  chooseShopImages: function() {
    var that = this;
    var currentImages = that.data.shopMessage.shopImages;
    if (currentImages.length >= 3) {
      wx.showToast({
        title: '最多只能选择三张图片',
        icon: 'none',
        duration: 2000
      });
      return;
    }
    wx.chooseMedia({
      count: 1, 
      mediaType: ['image'], // 只允许选择图片
      success: function(r) {
        const tempFilePaths = r.tempFiles;
        wx.uploadFile({
          url: getApp().globalData.url + '/uploadImage', // 你的服务器API地址
          filePath: tempFilePaths[0].tempFilePath,
          name: 'file',
          success: function(res) {
            var data = JSON.parse(res.data);
            console.log(res.data);
            if(data.status === 'success') {
             // formData.shopAvatar = data.url; // 更新 shopAvatar 的 URL
              var newImages = r.tempFiles.map(function(file) {
                return { shopImages: data.url };
              });
              that.setData({
                'shopMessage.shopImages': currentImages.concat(newImages)
              });
            }
          }
        })

      }
    });
  },
  
  chooseShopAvatar: function() {
    var that = this;
    wx.chooseMedia({
      count: 1,
      mediaType: ['image'],
      success: function(res) {
        const tempFilePaths = res.tempFiles;
        wx.uploadFile({
          url: getApp().globalData.url + '/uploadImage', // 你的服务器API地址
          filePath: tempFilePaths[0].tempFilePath,
          name: 'file',
          success: function(res) {
            var data = JSON.parse(res.data);
            console.log(res.data);
            if(data.status === 'success') {
             // formData.shopAvatar = data.url; // 更新 shopAvatar 的 URL
              that.setData({
                'shopMessage.shopAvatar' : data.url
              })
              // 上传 shopImages
            }
          }
        })
      }
    }) 
  },
  
  chooseLocation: function() {
    var that = this;
    wx.chooseLocation({
      success: function(res) {
        that.setData({
          'shopMessage.shopLongitude': res.longitude,
          'shopMessage.shopLatitude': res.latitude
        })
      }
    })
  },


  // ...删除图片
deleteImage: function(e) {
  let that = this;
  wx.showModal({
    title: '删除',
    content: '是否删除这种图片',
    complete: (res) => {
      if (res.cancel) {
        
      }
      if (res.confirm) {
        var index = e.currentTarget.dataset.index; // 获取点击的图片的索引
        var shopImages = this.data.shopMessage.shopImages;
        var deletedImage = shopImages[index]; // 获取点击删除的图片对象
        console.log(deletedImage);
        //删除请求
        wx.request({
          url: getApp().globalData.url + '/deleteShopImg', // 你的服务器API地址
          method: 'POST',
          data: deletedImage,
          success: function(res) {
            console.log(res.data);
            if(res.data === 200) {
              wx.showToast({
                title: '更新成功',
                icon: 'success',
                duration: 2000
              })
              shopImages.splice(index, 1); // 删除点击的图片
              that.setData({
                'shopMessage.shopImages': shopImages
              })
            }
            else{
              wx.showToast({
                title: '失败',
                icon: 'error',
                duration: 2000
              })
            }
          }
      })
    }}
  })
 
},
// ... 其他代码 ...

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady() {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow() {
  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide() {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload() {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh() {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom() {

  },
/*
  formSubmit: function(e) {

    var formData = this.data.shopMessage; // 获取表单所有name的值
    console.log(formData)
    wx.request({
      url: getApp().globalData.url + '/updateShopMessage', // 你的服务器API地址
      method: 'POST',
      data: formData,
      success: function(res) {
        if(res.data.status === 'success') {
          wx.showToast({
            title: '更新成功',
            icon: 'success',
            duration: 2000
          })
        } else {
          wx.showToast({
            title: '更新失败',
            icon: 'none',
            duration: 2000
          })
        }
      }
    })
  },

  */
 formSubmit: function(e) {
 
  var that = this;
  var sendData = this.data.shopMessage; // 获取表单所有name的值
  var formData = e.detail.value;
  console.log(formData);
  sendData.shopIntro=formData.shopIntro;
  sendData.shopName=formData.shopName;
  sendData.shopPhone=formData.shopPhone;
  console.log(sendData);
  wx.request({
    url: getApp().globalData.url + '/updateShopMessage', // 你的服务器API地址
    method: 'POST',
    data: sendData,
    success: function(res) {
      if(res.data === 200) {
        wx.showToast({
          title: '更新成功',
          icon: 'success',
          duration: 2000
        })
      } else {
        wx.showToast({
          title: '更新失败',
          icon: 'none',
          duration: 2000
        })
      }
    }
  })
},

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage() {

  }
})