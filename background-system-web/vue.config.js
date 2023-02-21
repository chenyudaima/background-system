const { defineConfig } = require('@vue/cli-service')

module.exports = defineConfig({
  transpileDependencies: true,
  lintOnSave: false,
  devServer: {
    port: 3000,
    proxy: {
      //^表示以/system开头 ，没有这个的话 /xxx/system 也可能会进行代理
      '^/system-api': {
        target: 'http://localhost:8080',
        changeOrigin: true
      }
    },
  }

})
