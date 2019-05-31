// pages/movies/movies.js
var util = require("../../utils/util.js");
var app = getApp();
Page({

  /**
   * 页面的初始数据
   */
  data: {
    inTheaters:{},
    comingsoon:{},
    top250:{},
    searchResult: {},
    containerShow:true,
    searchPanelShow:false,
  },


  onMoreTap: function (event) {
    var type = event.currentTarget.dataset.type;
    wx.navigateTo({
      url: 'more-movie/more-movie?type='+type,
    })
  },

  onMovieTap:function(event){
    var movieId = event.currentTarget.dataset.movieid;
    wx.navigateTo({
      url: 'movie-detail/movie-detail?id=' + movieId,
    })
  },

  onBindFocus:function(){
    this.setData({
      containerShow:false,
      searchPanelShow:true
    })
  },

  onCancelTap:function(){
    this.setData({
      containerShow: true,
      searchPanelShow: false,
      //searchResult:{}
    })
  },
  onBindConfirm:function(e){
    var text = e.detail.value;
    var searchUrl = app.globalData.g_doubanBase + "/v2/movie/search?q="+text;  
    this.getMovieListData(searchUrl, "","searchResult");
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var inTheatersUrl = app.globalData.g_doubanBase + "/v2/movie/in_theaters?start=0&count=3";
    var comingsoonUrl = app.globalData.g_doubanBase + "/v2/movie/coming_soon?start=0&count=3";
    var top250Url = app.globalData.g_doubanBase + "/v2/movie/top250?start=0&count=3";

    this.getMovieListData(inTheatersUrl,"正在热映","inTheaters");
    this.getMovieListData(comingsoonUrl,"即将上映","comingsoon");
    this.getMovieListData(top250Url,"豆瓣Top250","top250");
    
  },

  getMovieListData:function(url,type,settedkey){
    var that = this;
    wx.request({
      url: url,
      method: 'GET',
      header: {
        "Content-Type": "application/json",
      },
      success: function (res) {
        that.processDoubanData(res.data,type,settedkey);
      },
      fail: function () {

      }

    })
  },

  processDoubanData: function (moviesDouban,type,settedkey){
    var movies = [];
    for (var i = 0; i < moviesDouban.subjects.length;i++){
        var subject = moviesDouban.subjects[i];
        var title = subject.original_title;
        if(title.length>6){
          title = title.substring(0,6)+"...";
        }
        var temp={
          stars: util.convertToStarArray(subject.rating.stars),
          title:title,
          average:subject.rating.average,
          coverImg:subject.images.large,
          movieId:subject.id,
          
        };
        movies.push(temp);
        var readyData = [];
        readyData[settedkey] = {
          movies:movies,
          type: type,
        };
        this.setData(readyData);
    }
  },
  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  }
})