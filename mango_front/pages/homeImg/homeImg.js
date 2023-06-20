// pages/homeImg/homeImg.js
const app = getApp()

Page({

  /**
   * 页面的初始数据
   */
  data: {
    height: app.globalData.height,
    nvabarData: {
      showCapsule: 1, //是否显示左上角图标   1表示显示    0表示不显示
      title: '轮播图管理', //导航栏 中间的标题
      height: 0
    },
    imageList:{ },
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad(options) {
    let that = this;
    wx.request({
      url: getApp().globalData.url + '/getMessage/getAllSwiperMessage',
      method:"POST",
      success(res){
          that.setData({
            imageList:res.data
          })
      }
    })
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

  changeImg: function(e) {
    var id = e.currentTarget.dataset.id;
    let that = this;
    console.log(" 选择图片");
    wx.chooseMedia({
      count: 1,
      mediaType: ['image'],
      sourceType: ['album', 'camera'],
      success (res) {
        // tempFilePath可以作为img标签的src属性显示图片
        const tempFilePaths = res.tempFiles
        // 开始上传图片
        wx.uploadFile({
          url: getApp().globalData.url+'/updateSwiperMessage?id='+id, // 你的上传图片API的地址
          filePath: tempFilePaths[0].tempFilePath,
          name: 'file',
          success (res){
            if(res.data ==401){
              wx.showToast({
                title: '文件为空',
                icon: 'error', // 可选值：success, loading, none
                duration: 2000, // 持续时间，单位为毫秒
              });
            }
            else if(res.data ==402){
              wx.showToast({
                title: '文件过大',
                icon: 'error', // 可选值：success, loading, none
                duration: 2000, // 持续时间，单位为毫秒
              });
            }
            else if(res.data == 200){
            wx.showToast({
              title: '更改成功',
              icon: 'success', // 可选值：success, loading, none
              duration: 2000, // 持续时间，单位为毫秒
            });
            that.onLoad();
           }else {
            wx.showToast({
              title: '服务器故障！',
              icon: 'error', // 可选值：success, loading, none
              duration: 2000, // 持续时间，单位为毫秒
            });
           }
          
            //do something
          }
        })
      }
    })    
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage() {

  }
})