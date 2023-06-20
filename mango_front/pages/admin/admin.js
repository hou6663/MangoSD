// pages/admin/admin.js
const app = getApp()

Page({

  /**
   * 页面的初始数据
   */
  data: {
    height: app.globalData.height * 2 + 20,
    isLoading: false,
    userInfo:[],
    userId: "-1",
    userIsAdmin: -1, //是否为管理员
    userIsSuperAdmin:-1,
    nvabarData: {
      showCapsule: 1, //是否显示左上角图标   1表示显示    0表示不显示
      title: '我的', //导航栏 中间的标题
      height: 0
    },
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad(options) {
    let that= this;
    this.setData({
      height: app.globalData.height,
      userIsAdmin: getApp().globalData.userIsAdmin,
      userIsSuperAdmin: getApp().globalData.userIsSuperAdmin
    });
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

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady() {
    let that = this
    setTimeout(function() {
      that.setData({
        isLoading: true
      })
    }, 500)
  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow() {
    let that = this;
    wx.getStorage({
      key: 'userIsSuperAdmin',
      success:function(res){
        that.setData({
          userIsSuperAdmin: res.data
        })
      },
    });
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

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage() {

  }
})