module.exports={
  lintOnSave: false,
  devServer:{
    open : true,
    host : 'hospital',//设置域名
    port : '80',//设置端口号
    https : false,//是否启动https的网络协议
    hotOnly : false,//热模块更新，自带热更新，热更新可以对新添加的模块进行配置
    proxy: {
      '/api': {
        target: 'http://localhost:8001',
        ws: true,
        changeOrigin: true,
        pathRewrite: {
          '^/api': 'http://localhost:8001'  //通过pathRewrite重写地址，将前缀/api转为/
        }
      }
    }
  }
}

