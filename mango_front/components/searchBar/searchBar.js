// components/searchBar/searchBar.js
// 本组件为搜索组件
// 需要传入addflag   值为true / false （搜索框右侧部分）
// 若显示搜索框右侧部分   需传入右侧图标url以及addhandle函数
const app = getApp();
Component({
  /*
  properties: {
    addflag: { //显示搜索框右侧部分
      type: Boolean,
      value: false,

      observer(newVal, oldVal, changedPath) {

      }
    },
    addimg: { //显示搜索框右侧部分icon
      type: String,
      value: ''
    },
    searchstr: { //input  值
      type: String,
      value: '值'
    },
    searchflag: {
      type: Boolean,
      value: false,
    }

  },
  
*/
  /**
   * 组件的初始数据
   */
    data: {
      value: '',
    },
    onChange(e) {
      this.setData({
        value: e.detail,
      });
    },
    onSearch() {
      Toast('搜索' + this.data.value);
    },
    onClick() {
      Toast('搜索' + this.data.value);
    },

})