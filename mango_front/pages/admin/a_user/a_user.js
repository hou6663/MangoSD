// pages/admin/a_user/a_user.js
const app = getApp()
import Dialog from '@vant/weapp/dialog/dialog';

Page({

  /**
   * 页面的初始数据
   */
  data: {
    nvabarData: {
      showCapsule: 1, //是否显示左上角图标   1表示显示    0表示不显示
      title: '所有用户', //导航栏 中间的标题
      height: 0
    },
    showModal: false,
    getUsersInfo:[],
    height: app.globalData.height,
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad(options) {
  },
  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady() {

  },

  /**
   * 加载所有的用户信息
   */
  loadUsersInfo(){
    let that =this;
    wx.request({
      url: getApp().globalData.url + '/selectAllUsers',
      method: "post",
      success: function(res) {
          that.setData({
            getUsersInfo: res.data
          })
      }
    }) //一个request

  },

  /**
   * 点击用户事件
   */
  toUserDetail: function(e){
    const userId = parseInt(e.currentTarget.id);
    const user = this.data.getUsersInfo.find(u => u.userId === userId); // 通过find方法查找对应的用户对象
    wx.navigateTo({
      url: '/pages/userDetailPage/userDetailPage?userId=' + user.userId
    });
  },
  
  /**
   * 生命周期函数--监听页面显示
   */
  onShow(){
    this.loadUsersInfo();
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
