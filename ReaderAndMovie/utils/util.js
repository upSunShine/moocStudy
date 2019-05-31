const formatTime = date => {
  const year = date.getFullYear()
  const month = date.getMonth() + 1
  const day = date.getDate()
  const hour = date.getHours()
  const minute = date.getMinutes()
  const second = date.getSeconds()

  return [year, month, day].map(formatNumber).join('/') + ' ' + [hour, minute, second].map(formatNumber).join(':')
}

const formatNumber = n => {
  n = n.toString()
  return n[1] ? n : '0' + n
}

function convertToStarArray(stars){
  var num = stars.substring(0,1);
  var array = [];
  for(var i=0;i<5;i++){
    if(i<=num){
      array.push(1);
    }else{
      array.push(0);
    }
  }
  return array;
}


function http(url,callback) {
  wx.request({
    url: url,
    method: 'GET',
    header: {
      "Content-Type": "application/json",
    },
    success: function (res) {
      callback(res.data);
    },
    fail: function () {

    }

  })
}

function convertToCastString(casts){
  var castsjoin = "";
  for (var idx = 0; idx < casts.length; idx++) {
    castsjoin = castsjoin + casts[idx].name + " / ";
  }
  return castsjoin.substring(0, castsjoin.length - 2);
 
}

function convertToCastInfos(casts){
  var castsArray = [];
  for (var i = 0; i < casts.length; i++) {
    var cast = {
      img: casts[i].avatars ? casts[i].avatars.large : "",
      name:casts[i].name
    }
    castsArray.push(cast);
  }
  return castsArray;
}
module.exports = {
  convertToStarArray: convertToStarArray,
  http:http,
  convertToCastString:convertToCastString,
  convertToCastInfos: convertToCastInfos,
  formatTime: formatTime
}
