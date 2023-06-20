// pages/manageShop/mangageShop.js
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
    shopMessage: [],
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

  addNewShop(){
    var that = this;
    wx.request({
      url:  getApp().globalData.url  + '/addShopMessage',
      method: "post",
      success: function (e) {
       if(e.data === 200){
          wx.showToast({
            title: '添加',
            content:'添加成功，请点击管理修改信息'
          })
          wx.request({
            url:  getApp().globalData.url  + '/getMessage/getAllShop',
            method: "post",
            success: function (e) {
              that.setData({
                shopMessage:e.data,
              })
            }
          })
       }
      }
    })
  },

  deleteShop:function(e){
    var id = e.currentTarget.dataset.shopid;
    let that = this;
    wx.showModal({
      title: '危险',
      content: '是否这个商店',
      complete: (res) => {
        if (res.cancel) {
          
        }
        if (res.confirm) {
          wx.request({
            url:  getApp().globalData.url  + '/deleteShopMessage/'+id,
            method: "post",
            success: function (e) {
             if(e.data === 200){
                wx.showToast({
                  title: '删除',
                  content:'删除成功'
                })
                wx.request({
                  url:  getApp().globalData.url  + '/getMessage/getAllShop',
                  method: "post",
                  success: function (e) {
                    that.setData({
                      shopMessage:e.data,
                    })
                  }
                })
             }
            }
          })
        }
      }
    })
  },
  /**
   * 生命周期函数--监听页面显示
   */
  onShow() {
    let that = this;
    wx.request({
      url:  getApp().globalData.url  + '/getMessage/getAllShop',
      method: "post",
      success: function (e) {
        that.setData({
          shopMessage:e.data,
        })
      }
    })
  },

  goShop:function(e){
    const shopId = e.currentTarget.dataset.shopid;
    wx.navigateTo({
      url: '/pages/manageShopDetail/manageShopDetail?id=' + shopId
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