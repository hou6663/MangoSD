//index.js
//获取应用实例
const app = getApp()
var bmap = require('../../utils/bmap-wx.min.js');
var util = require('../../utils/util.js');
Page({
  data: {
    imageUrl:"",
    // 此页面 页面内容距最顶部的距离
    height: app.globalData.height * 2 + 20,
    currentIndex: 0,
    swiper_images: [],
    allCategoryMessage: [],
    weatherData: null,
    floorstatus: "none",
    category_first: [],
    category_second: [],
    notice: [],
    lost_new: {},
    takeout: [],
    ad_bottom: ["../../images/other/ad_bottom.jpg"],
    user_message: [],
    activeIndex: 1,
    isLastPage: false, //是否最后一页
    isUpdate: -1,
    nowTime: 0,
    isLoading: false //页面是否渲染完毕
  },
  onReady() {
    let that = this
    setTimeout(function() {
      that.setData({
        isLoading: true
      })
    }, 1000)

  },

  /**
   * 联系我
   */
  me_call() {
    wx.showModal({
      title: '提示',
      content: '如有需要请联系我',
      confirmText: "联系我",
      success: function(e) {
        if (e.confirm) {
          wx.makePhoneCall({
            phoneNumber: '17318567890',
          })
        }
      }
    })
  },


  //查看失物招领详情
  lookLostMessage(e) {
    wx.showModal({
      title: '失物招领',
      content: e.target.id,
      showCancel: false,
      confirmText: '已查阅'
    })
   
  },
  //商家查看
  to_shop(e) {
    wx.navigateTo({
      url: "/pages/shop_detail/shop_detail?shopId=" + e.target.id
    })
  },
  //查看最新的失物招领
  new_lost_look(e) {
    wx.navigateTo({
      url: "/pages/message_detail/message_detail?messageId=" + e.currentTarget.id
    })
  },
  //点击查看图片
  look_image(e) {
    wx.previewImage({
      urls: [e.target.id],
    });
  },
  //查看公告
  checkNotice(e) {
    wx.showModal({
      title: '公告',
      content: e.target.id,
      showCancel: false,
      confirmText: '已查阅'
    })
  },
  

  //下方下拉刷新
  onPullDownRefresh: function () {
    // 在标题栏中显示加载
    wx.showNavigationBarLoading()
    // 开始刷新数据
    this.refreshData(() => {
      // 停止下拉刷新
      wx.stopPullDownRefresh()
      // 隐藏导航栏加载动画
      wx.hideNavigationBarLoading()
    })
  },

  refreshData: function (callback) {
    var that = this;
    // 这里模拟请求数据的操作
    setTimeout(() => {
      wx.showLoading({
        title: '更新主页信息中~',
    })
      wx.redirectTo({
        url: '/pages/index/index',
      })
      that.onLoad()
    }, 100)
  },

  //上方下拉刷新

  onPageScroll: function(e) { //判断滚轮位置
    if (e.scrollTop > 200) {
      this.setData({
        floorstatus: "block"
      });
    } else {
      this.setData({
        floorstatus: "none"
      });
    }
  },
  goTop: function(e) { // 一键回到顶部
    if (wx.pageScrollTo) {
      wx.pageScrollTo({
        scrollTop: 0
      })
    } else {
      wx.showModal({
        title: '提示',
        content: '当前微信版本过低，无法使用该功能，请升级到最新微信版本后重试。'
      })
    }
  },
  //跳转到搜索页
  search: function() {
    wx.navigateTo({
      url: '/pages/search/search',
    })
  },
  //跳转到详情页
  to_message_detail: function(e) {
    wx.navigateTo({
      url: '/pages/message_detail/message_detail?messageId=' + e.currentTarget.id,
    })
  },

  //授权天气
  getWeather() {
    var that = this;
    //高德地图
    wx.request({
      url: 'https://restapi.amap.com/v3/weather/weatherInfo?city=340100&key=4103b054f9a3f48489a9ce406697a0d7' ,
      method:'GET',
      success:(res)=>{
      
        if(res.data.status==1){
          var weatherData = res.data.lives[0];
          this.setData({
            weatherData : weatherData
          })
        }
        else{
          console.error("请求失败")
        }
      }
    })
  },

  onLoad: function(options) {

    let that = this;
    var time = util.formatTime(new Date());
    var nowTime = time.substring(0,11);
    this.getWeather();
    /**
     * 商家服务
     */
    this.setData({
      takeout: getApp().globalData.shopMessage,
      imageUrl:getApp().globalData.imageUrl,
      nowTime:nowTime,
    })

    /**
     * 轮播图
     */
    this.setData({
      swiper_images: getApp().globalData.swiperImages
    })
    /**
     * 分类信息
     */
    this.setData({
      allCategoryMessage: getApp().globalData.categoryMessage
    })
    var get_first = getApp().globalData.categoryMessage.slice(0, 8);
    var tem = [{}, {}, {}, {}, {}]
    var get_second = getApp().globalData.categoryMessage.slice(8, 14).concat(tem)
    this.setData({
      category_first: get_first,
      category_second: get_second,
      category_second: get_second
    })

  
    /**
     * 公告信息
     */
    this.setData({
      notice: getApp().globalData.noticeMessage
    })
    /**
     *第一页最新信息
     */
    this.loadMessage(this.data.activeIndex++);
    this.setData({
      //user_message: getApp().globalData.messageDetail
    })

    /**
     * 获取最新失物招领
     */
    this.setData({
      lost_new: getApp().globalData.lost_new
    })
   

  },
  handleImgChange: function(e) {
    this.setData({
      currentIndex: e.detail.current
    })
  },


  /**
   * 下拉
   */
  /**
   * 页面上拉触底事件的处理函数
   */
  
  onReachBottom: function() {
    // 最后一页了，取消下拉功能
    if (this.data.isLastPage) {
      return
    }

    this.loadMessage(++this.data.activeIndex)
  },

  loadMessage(index) {
    wx.showLoading({
      title: '加载中~',
    })
    var that = this;
    var app = getApp()
    wx.request({
      url: getApp().globalData.url + '/getMessage/getAllMessageDetail/14/' + index,
      method: "POST",
      success: (res) => {
        if (res.data == 200) {
          that.setData({
            isLastPage: true
          })
          return;
        }
        that.setData({
          user_message: that.data.user_message.concat(res.data)
        })


      },
      complete: function(res) {
        wx.hideLoading();
      },
    })

  },
  onShow() {
    let that = this;
    wx.request({
      url: getApp().globalData.url + '/getMessage/getAllSwiperMessage',
      method:"POST",
      success(res){
          that.setData({
            swiper_images:res.data
          })
      }
    })
    /*
    wx.request({
      url: getApp().globalData.url + '/getMessage/getLastNewMessage/' + getApp().globalData.userId,
      method: 'post',
      complete: function(e) {

        if (e.statusCode != 200) {
          return
        }

        wx.getStorage({
          key: 'lastNewMessage',
          success: function(res) {
            if (res.data != e.data.newMessageId) {
              wx.getBackgroundAudioManager({
                dataUrl: 'http://downsc.chinaz.net/Files/DownLoad/sound1/201609/7824.wav',
                title: '提示',
              })
              wx.showModal({
                title: '新消息',
                content: '内容：' + e.data.newMessageDetail,
                confirmText: "去查看",
                cancelText: "稍后去看",
                success: function(e) {
                  if (e.confirm) {
                    wx.navigateTo({
                      url: '/pages/me_xiaoxi/me_xiaoxi',
                    })
                  }
                }
              })
            }
          },
        })

        wx.setStorage({
          key: 'lastNewMessage',
          data: e.data.newMessageId,
        })
      }

    })
    */
    /**
     * 判断是否更新
     */
    
    if (getApp().globalData.isUpdate == 1) {
      this.onLoad(),
        this.setData({
          activeIndex: 1,
          isLastPage: false, //是否最后一页
        })
      getApp().globalData.isUpdate = -1
    }
    
  }

})