import SparkMD5 from 'spark-md5';

 export default function computeFileMD5(file) {
  //创建异步对象
   return new Promise((resolve, reject) => {
      //为了兼容各种浏览器
      let blobSlice = File.prototype.slice || File.prototype.mozSlice || File.prototype.webkitSlice;

      //分片大小 按照每片2MB分片
      let chunkSize = 2097152;

      //文件总片数
      let chunks = Math.ceil(file.size / chunkSize); 

      //当前片数
      let currentChunk = 0;

      //spark-md5工具
      let spark = new SparkMD5.ArrayBuffer();

      //用于读取文件的API
      let fileReader = new FileReader();

      //当前时间(生产环境需删除)
      let time = new Date().getTime();

      //定义fileReader事件，当分片文件读取完时触发
      fileReader.onload = function (e) {
        //追加当前分片数据md5
        spark.append(e.target.result);

        currentChunk++;

        //如果当前片数小于总片数 说明还需要继续分片读取文件数据
        if (currentChunk < chunks) {
          loadNext();
        } else {
          //最终md5值
          let md5 = spark.end(); 

          //释放缓存
          spark.destroy(); 

          //详细调试日志(生产环境需删除)
          console.log(`MD5计算完成\n文件名:${file.name} \nMD5:${md5} \n总片数:${chunks} 文件大小:${file.size} 计算用时:${new Date().getTime() - time} ms`);
          resolve(md5);
        }
      };

      //定义fileReader事件，当文件读取错误时触发
      fileReader.onerror = function (e) {
        console.warn('文件读取错误');
        reject(e);
      };

      //定义loadNext函数，用于分片读取文件
      function loadNext() {
        let start = currentChunk * chunkSize,
        end = ((start + chunkSize) >= file.size) ? file.size : start + chunkSize;

        //截取二进制数据
        //注意webupload是file.source.getSource()，如果是elementui的上传文件组件应该用file.file拿到文件
        let blob = blobSlice.call(file.source.getSource(), start, end);

        //读取二进制数据
        fileReader.readAsArrayBuffer(blob);
      }

      //调用loadNext函数读取文件数据，并触发fileReader的事件
      loadNext();
   }) 
 }