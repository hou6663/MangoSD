// pages/proclamation/proclamation.js
const app = getApp();

Page({
  /**
   * 页面的初始数据
   */
  data: {
    nvabarData: {
      showCapsule: 1, //是否显示左上角图标   1表示显示    0表示不显示
      title: '公告管理', //导航栏 中间的标题
      height: 0
    },
    noticeMessage: { },
    height: app.globalData.height,
    showModal: false,
    noticeDetail: '',
    noticeId: 0,
    type:0,
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad(e) {
    this.setData({
      height: app.globalData.height
    })
    
    let that =this;
    wx.request({
      url: getApp().globalData.url + '/getMessage/getAllNoticeMessage',
      method: "post",
      success: function (e) {
       that.setData({
        noticeMessage:e.data,
       })
      }})      
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

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide() {

  },
  onInput: function(e){
    this.setData({
      noticeDetail: e.detail.value,
    });
  },

  cancelEdit: function(){
    this.setData({
      showModal: false,
    });
  },

  confirmEdit: function(){
    // 在这里添加修改公告的代码
    this.addNotice(this.data.noticeId,this.data.noticeDetail,this.data.type);
    this.setData({
      showModal: false,
    });
  },
  clickNotice: function(e){
    let that = this;
    const noticeId = parseInt(e.currentTarget.id);
    const noticeDetail = e.currentTarget.dataset.noticeDetail; 
    wx.showActionSheet({
      itemList: ['删除', '修改'],
      success: function(res) {
        if(res.tapIndex === 0){
          // 用户选择了删除
          wx.showModal({
            title: '确认',
            content: '你确定要删除这个公告吗？',
            success (res) {
              if (res.confirm) {
                // 在这里添加删除公告的代码
                that.deleteNotice(noticeId);
              } else if (res.cancel) {
                console.log('用户点击取消')
              }
            }
          })
        }else if(res.tapIndex === 1){
          // 用户选择了修改
          that.setData({
            showModal: true,
            noticeDetail: noticeDetail, 
            noticeId : noticeId,
            type:1,
          });
        }
      },
      fail: function(res) {
      }
    })
  },
  
  deleteNotice(noticeId){
    let that = this;
    wx.request({
      url: getApp().globalData.url + '/deleteNoticeMessageById?noticeId='+noticeId,
      method: "post",
      success: function (e) {
        if (e.data === 1) {
          wx.showToast({
            title: '删除成功',
            icon: 'success', // 可选值：success, loading, none
            duration: 2000, // 持续时间，单位为毫秒
          });
          that.onLoad();
        }else{
          wx.showToast({
            title: '删除失败',
            icon: 'error', // 可选值：success, loading, none
            duration: 2000, // 持续时间，单位为毫秒
          });
        }
      }})    

  },
  addNewNotice(){
    let that = this;
    that.setData({
      showModal: true,
      noticeDetail:'',
      type:2,
    });
  },
  addNotice(noticeId,noticeDetail,type){
    let that = this;
    if(noticeDetail === '')
    wx.showToast({
      title: '不可为空',
      icon: 'error', // 可选值：success, loading, none
      duration: 2000, // 持续时间，单位为毫秒
    });
    else{
    wx.request({
      url: getApp().globalData.url + '/addNoticeMessage?noticeId='+noticeId +'&noticeDetail='+noticeDetail+'&type='+type,
      method: "post",
      success: function (e) {
        if (e.data === 1) {
          wx.showToast({
            title: '操作成功',
            icon: 'success', // 可选值：success, loading, none
            duration: 2000, // 持续时间，单位为毫秒
          });
          that.onLoad();
        }else{
          wx.showToast({
            title: '操作失败',
            icon: 'error', // 可选值：success, loading, none
            duration: 2000, // 持续时间，单位为毫秒
          });
        }
      }})    
    }
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