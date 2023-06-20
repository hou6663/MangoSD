
const App = getApp();
const formatTime = date => {
  const year = date.getFullYear()
  const month = date.getMonth() + 1
  const day = date.getDate()
  const hour = date.getHours()
  const minute = date.getMinutes()
  const second = date.getSeconds()

  return `${[year, month, day].map(formatNumber).join('/')} ${[hour, minute, second].map(formatNumber).join(':')}`
}

const formatNumber = n => {
  n = n.toString()
  return n[1] ? n : `0${n}`
}

const tsFormatTime = function(timestamp, format){
  const formateArr = ['Y', 'M', 'D', 'h', 'm', 's'];
  let returnArr = [];
  let date = new Date(timestamp);
  let year = date.getFullYear()
  let month = date.getMonth() + 1
  let day = date.getDate()
  let hour = date.getHours()
  let minute = date.getMinutes()
  let second = date.getSeconds()
  returnArr.push(year, month, day, hour, minute, second);

  returnArr = returnArr.map(formatNumber);

  for (var i in returnArr) {
      format = format.replace(formateArr[i], returnArr[i]);
  }
  return format;
}

const formatTimeYMD = date => {
  const year = date.getFullYear()
  const month = date.getMonth() + 1
  const day = date.getDate()
  return [year, month, day].map(formatNumber).join('-')
}


const wxuuid = function () {
  var s = [];
  var hexDigits = "0123456789abcdef";
  for (var i = 0; i < 36; i++) {
    s[i] = hexDigits.substr(Math.floor(Math.random() * 0x10), 1);
  }
  s[14] = "4"; // bits 12-15 of the time_hi_and_version field to 0010
  s[19] = hexDigits.substr((s[19] & 0x3) | 0x8, 1); // bits 6-7 of the clock_seq_hi_and_reserved to 01
  s[8] = s[13] = s[18] = s[23] = "-";
 
  var uuid = s.join("");
  return uuid
}

const get=function(url,params){
  return request('GET',url,params)
}

const post=function(url,params){
  return request('POST',url,params,{'Content-Type': 'application/json'})
}
const postNoLoading=function(url,params){
  return request('POST',url,params,{'Content-Type': 'application/json'},false)
}
// const loadLocation=function(){
//   return new Promise((resolve, reject)=>{
//     let address=wx.getStorageSync('ly-user-address')
//     let isLoad=true
//     if(address){
//       isLoad=(Date.now()-address.time)>=720000
//     }
//     if(!isLoad){
//       resolve(address)
//     }else{
//       wx.getLocation({
//         type: 'wgs84',
//         success (res) {
//           const latitude = res.latitude
//           const longitude = res.longitude
//          let addressUrl=`https://apis.map.qq.com/ws/geocoder/v1/?location=${latitude},${longitude}&key=7MLBZ-BRLWV-KIFPM-UTP3B-MMUR6-MUFCO`
//          wx.request({
//           url: addressUrl,
//           success: function (result) {
//             if(result&&result.statusCode===200){
//               let city=result.data.result.address_component.city
//               let addressName=result.data.result.address
//               let address={lat:latitude,lon:longitude,city:city,addressName:addressName,time:Date.now()}
//               wx.setStorageSync('ly-user-address',address)
//               resolve(address)
//             }
//           }
//         })
//         }
//        })
//     }
//   })
// }

const request=function(method,url,params,header,showLoading=true){
  return new Promise((resolve, reject) => {
    if(showLoading){
      wx.showLoading({
        title: '加载中...',
      })
    }
    wx.request({
      url:  `${App.globalData.baseAPI}${url}`,
      method: method,
      data:params,
      header: header,
      success: data => {
        if(data.data){
          if(data.data.code===0){
            resolve(data.data)
          }else if(data.data.code===401){
            wx.redirectTo({url: 'pages/chat/login/login'})
          }else{
            wx.showToast({
              title: data.data.msg,
              icon: 'waring',
              duration: 1500
            })
          }
        }
      },
      fail: err => {
        console.err(err);
        reject(err)
      },complete:com=>{
        if(showLoading){
          wx.hideLoading()
        }
      }
    })
  })
}

const getNowAddress=function(){
  return new Promise((resolve, reject) => {
    let selectAddress=wx.getStorageSync('select-address')
    if(selectAddress){
      resolve(selectAddress)
    }else{
      let lyUserAddress=wx.getStorageSync('ly-user-address')
      resolve(lyUserAddress)
    }
  })
}


module.exports = {
  formatTime,
  wxuuid,
  tsFormatTime,
  formatTimeYMD,
  get:get,
  post:post,
  postNoLoading:postNoLoading,
  // loadLocation:loadLocation,
  getNowAddress:getNowAddress
}
