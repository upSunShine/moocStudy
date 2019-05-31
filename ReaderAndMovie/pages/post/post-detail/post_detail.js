// pages/post/post-detail/post_detail.js
var postsData = require("../../../data/posts-data.js");
var app = getApp();
const backgroundAudioManager = wx.getBackgroundAudioManager();
Page({

  /**
   * 页面的初始数据
   */
  data: {
    postData: {},
    collected: "",
    isPlayingMusic:false,
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {
    var id = options.id;
    this.data.currentpostId = id;
    var postData = postsData.postList[id];
    // this.data.postData = postData;
    this.setData({
      postData: postData
    })

    var postsCollected = wx.getStorageSync("posts_Collected");
    if (postsCollected) {
      var postCollected = postsCollected[id];
      this.setData({
        collected: postCollected
      })
    } else {
      var postsCollected = {};
      postsCollected[id] = false;
      wx.setStorageSync("posts_Collected", postsCollected);
    }
    this.setMusicMonitor();

    if (app.globalData.g_isPlayingMusic && app.globalData.g_currentPostId==id){
      // this.data.isPlayingMusic = true;
      this.setData({
        isPlayingMusic:true
      })
    }
  },

  //监听音乐播放状态，设置与主控件状态一致
  setMusicMonitor:function(){
    var that = this;
    //音乐播放
    backgroundAudioManager.onPlay(function () {
      that.setData({
        isPlayingMusic: true
      });
      //当前音乐是否正在播放
      app.globalData.g_isPlayingMusic = true;
      //当前第几首音乐
      app.globalData.g_currentPostId = that.data.currentpostId;
    });

    //暂停音乐
    backgroundAudioManager.onPause(function () {
      that.setData({
        isPlayingMusic: false
      });
      app.globalData.g_isPlayingMusic = false;
      app.globalData.g_currentPostId = null;
    });
    
    //音乐播放完毕
    backgroundAudioManager.stop(function() {
      that.setData({
        isPlayingMusic: false
      });
      app.globalData.g_isPlayingMusic = false;
      app.globalData.g_currentPostId = null;
    })
  },
  onCollectionTap: function(event) {
    this.getCollectionSyc();
    // this.getCollectionAsy();
  },

  //异步获取
  getCollectionAsy:function () {
    var that = this;
    var postsCollected = wx.getStorage({ 
      key:"posts_Collected",
      success:function(res){
        var postsCollected = res.data;
        var postCollected = postsCollected[that.data.currentpostId];
        //取反
        postCollected = !postCollected;
        postsCollected[that.data.currentpostId] = postCollected;
        that.showToast(postsCollected, postCollected);
      }
    });
    
  },

  //同步获取
  getCollectionSyc:function(){
    var postsCollected = wx.getStorageSync("posts_Collected");
    var postCollected = postsCollected[this.data.currentpostId];
    //取反
    postCollected = !postCollected;
    postsCollected[this.data.currentpostId] = postCollected;
    this.showToast(postsCollected, postCollected);
  },

  showModal: function (postsCollected, postCollected) {
    var that = this;
    wx.showModal({
      title: '收藏',
      content: postCollected?'收藏该文章？':'取消收藏该文章？',
      showCancel: true,
      cancelText: '取消',
      cancelColor: '#333',
      confirmText: '确认',
      confirmColor: '#405f80',
      success: function (res) { 
        if(res.confirm){
          wx.setStorageSync("posts_Collected", postsCollected);
          //更新数据绑定变量,切换图片
          that.setData({
            collected: postCollected
          })
        }
      },
      fail: function (res) {

       },
      complete: function (res) {

       },
    })
  },
  showToast: function (postsCollected, postCollected) {
    //更新文章是否缓存
    wx.setStorageSync("posts_Collected", postsCollected);
    //更新数据绑定变量,切换图片
    this.setData({
      collected: postCollected
    })
    wx.showToast({
      title: postCollected?'收藏成功':'取消收藏成功',
      icon:'success',
      duration:1000
    })
  },

  onshareTap:function(event){
    var itemList = [
      "分享给微信好友",
      "分享到朋友圈",
      "分享到QQ",
      "分享到微博"
    ]
    wx.showActionSheet({
      itemList: itemList,
      itemColor:"#405f80",
      success(res){
        wx.showModal({
          title: '用户 ' + itemList[res.tapIndex],
          content: '用户是否取消？' + res.cancel + "现在无法实现分享功能",
        })
      }
    })
   
  },

  onMusicTap:function(){
    var currentpostId =this.data.currentpostId;
    var postData = postsData.postList[currentpostId];
    var isplayingMusic= this.data.isPlayingMusic;
    if (isplayingMusic){
      
      backgroundAudioManager.pause();
      // this.data.playingMusic = false;
      this.setData({
        isPlayingMusic:false
      });
    }else{
      backgroundAudioManager.title = postData.music.title;
      // backgroundAudioManager.epname = postData.music.title;;
      backgroundAudioManager.singer = postData.music.author;;
      backgroundAudioManager.coverImgUrl = postData.music.coverImg;;
      // 设置了 src 之后会自动播放
      backgroundAudioManager.src = postData.music.url;
      this.setData({
        isPlayingMusic: true
      });
    }
  },
  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function() {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function() {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function() {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function() {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function() {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function() {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function() {

  }
})