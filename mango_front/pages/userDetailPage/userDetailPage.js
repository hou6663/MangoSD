// pages/admin/a_user/userDetailPage/userDetailPage.js
const app = getApp();

Page({

  /**
   * 页面的初始数据
   */
  data: {
    userInfo:{ },
    userIsSuperAdmin : app.globalData.userIsSuperAdmin,
    userIsAdmin : app.globalData.userIsAdmin,
    nvabarData: {
      showCapsule: 1, //是否显示左上角图标   1表示显示    0表示不显示
      title: '个人信息', //导航栏 中间的标题
      height: 0
    },
    height: app.globalData.height,
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad(options) {
    const userId = options.userId;
    let that = this;
    wx.request({
      url: getApp().globalData.url + '/selectUserByUserId?userId=' + userId,
      method: "POST",
      success: function(result) {
        that.setData({
          userInfo: result.data,
          userIsAdmin : app.globalData.userIsAdmin,
          userIsSuperAdmin : app.globalData.userIsSuperAdmin,
        }, function() {
          // 在这里进行后续操作
        });
      }
    });
  },
  


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

  toggleBanStatus() {
    // 切换封禁状态的逻辑
    const status = this.data.userInfo.userAllow === 1 ? "封禁" : "解封";
    if(this.data.userInfo.userIsAdmin === 2 && this.data.userIsSuperAdmin !== 1){
      wx.showToast({
        title: '无权限',
        icon: 'error', // 可选值：success, loading, none
        duration: 2000, // 持续时间，单位为毫秒
      });
      
    }
    else if (this.data.userInfo.userIsAdmin ===3) {
      wx.showToast({
        title: '违规操作',
        icon: 'error', // 可选值：success, loading, none
        duration: 2000, // 持续时间，单位为毫秒
      });
    }
    else{
    wx.showModal({
      title: status+'用户',
      content: '是否'+status+'用户?',
      complete: (res) => {
        if (res.cancel) {
        }
        if (res.confirm) {
          const newStatus = this.data.userInfo.userAllow === 1 ? 0 : 1;
          // 发起请求，更新封禁状态
          wx.request({
            url: getApp().globalData.url + '/updateBanStatus?userId=' + this.data.userInfo.userId + '&status=' + newStatus,
            method: "POST",
            success: (res) => {
              const userAllow = res.data;
              this.setData({
                'userInfo.userAllow': userAllow
              });
            },
            fail: (error) => {
              console.error('更新封禁状态失败：', error);
            }
          });
        }
      }
    })
  }
    
  },

  promoteToAdmin() {
    // 提升为管理员的逻辑
    const status = this.data.userInfo.userIsAdmin === 1 ? "提升" : "降级";
    if(this.data.userIsSuperAdmin !== 1){
      wx.showToast({
        title: '无权限',
        icon: 'error', // 可选值：success, loading, none
        duration: 2000, // 持续时间，单位为毫秒
      });
      
    }
    else if (this.data.userInfo.userIsAdmin ===3) {
      wx.showToast({
        title: '违规操作',
        icon: 'error', // 可选值：success, loading, none
        duration: 2000, // 持续时间，单位为毫秒
      });
    }
    else{
    const newRole = this.data.userInfo.userIsAdmin === 1 ? 2 : 1;
    // 发起请求，更新用户角色
    wx.showModal({
      title: status+'用户',
      content: '是否'+status+'用户?',
      complete: (res) => {
        if (res.cancel) {
        }
        if (res.confirm) {
            wx.request({
              url: getApp().globalData.url + '/updateUserRole?userId=' + this.data.userInfo.userId + '&role=' + newRole,
              method: "POST",
              success: (res) => {
                const userIsAdmin = res.data;
                this.setData({
                  'userInfo.userIsAdmin': userIsAdmin
                });
              },
              fail: (error) => {
                console.error('更新用户角色失败：', error);
              }
            });
        }}
      })
    }
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