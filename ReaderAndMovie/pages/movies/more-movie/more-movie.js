// pages/movies/more-movie/more-movie.js
var util = require("../../../utils/util.js");
var app = getApp();
Page({

  /**
   * 页面的初始数据
   */
  data: {
    movies:[],
    navigateTitle:"",
    requestUrl:"",
    totalCount:0,
    isEmpty:false,
  },

  onMovieTap: function (event) {
    var movieId = event.currentTarget.dataset.movieid;
    wx.navigateTo({
      url: '../movie-detail/movie-detail?id=' + movieId,
    })
  },
  
  /**
   * 1、生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var type = options.type;
    this.data.navigateTitle = type;

    var dataUrl = '';
    switch (type) {
      case "正在热映":
        dataUrl = app.globalData.g_doubanBase + "/v2/movie/in_theaters";
        break;
      case "即将上映":
        dataUrl = app.globalData.g_doubanBase + "/v2/movie/coming_soon";
        break;
      case "豆瓣Top250":
        dataUrl = app.globalData.g_doubanBase + "/v2/movie/top250";
        break;
    }
    this.data.requestUrl = dataUrl;
    util.http(dataUrl, this.processDoubanData);
  },

  // onScollLower: function (event) {
  //   var nextUrl = this.data.requestUrl + "?start=" + this.data.totalCount +"&count=20";
  //   util.http(nextUrl, this.processDoubanData);
  //   wx.showNavigationBarLoading();
  // },

  /**
  * 页面上拉触底事件的处理函数
    上拉加载
  */
  onReachBottom: function () {
    var nextUrl = this.data.requestUrl + "?start=" + this.data.totalCount + "&count=20";
    util.http(nextUrl, this.processDoubanData);
    wx.showNavigationBarLoading();
  },

  /**
  * 页面相关事件处理函数--监听用户下拉动作
   下拉刷新
  */
  onPullDownRefresh: function () {
    var refreshUrl = this.data.requestUrl + "?start=0&count=20";
    this.data.movies={};
    this.data.isEmpty=true;
    this.data.totalCount = 0;
    util.http(refreshUrl, this.processDoubanData);
    wx.showNavigationBarLoading();
  },

  //加载豆瓣数据
  processDoubanData: function (moviesDouban){
  var movies = [];
  for (var i = 0; i < moviesDouban.subjects.length; i++) {
    //[1,1,1,0,0]
    var subject = moviesDouban.subjects[i];
    var title = subject.original_title;
    if (title.length > 6) {
      title = title.substring(0, 6) + "...";
    }
    var temp = {
      stars: util.convertToStarArray(subject.rating.stars),
      title: title,
      average: subject.rating.average,
      coverImg: subject.images.large,
      movieId: subject.id,
    };
    movies.push(temp);
  }
  var totalMovies = {};
  //新加载的数据加入原有数据
  if (!this.data.isEmpty) {
    totalMovies = this.data.movies.concat(movies);
  } else {
    totalMovies = movies;
    this.data.isEmpty = false;
  }
  this.setData({
    movies: totalMovies
  });
  this.data.totalCount += 20;
  wx.hideNavigationBarLoading();
  wx.stopPullDownRefresh();
},
  /**
   * 3、生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {
    wx.setNavigationBarTitle({
      title: this.data.navigateTitle,
      success:function(res){
      }

    })
  },

  /**
   * 2、生命周期函数--监听页面显示
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
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  }
})