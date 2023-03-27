import md5 from 'js-md5'
import qs from 'qs'
import uuidUtil from '/public/js/uuidUtil.js';

//对需要请求的参数进行签名
function signature(config) {

  //请求的服务器路径
  let url = config.baseURL + config.url + "/"

  //排序和格式化之后的参数(需要参与加密的参数)
  let params;

  //时间戳
  let timestamp = new Date().getTime()

  //参与加密的key，但不进行传参
  let accessKey = config.headers.Authorization
  
  //唯一标识
  let nonce = uuidUtil.getUUID()

  if (config.method == "get") {

    //对json中value为null的改为''空字符串
    //axios的请求路径不会拼接为null的，这样改为''，等于所有都进行拼接，所有都进行参数签名
    config.params = qs.parse(qs.stringify({
      ...config.params,
      timestamp: new Date().getTime(),
      accessKey: accessKey,
      nonce: nonce
    }))

    params = ""

    Object.keys(config.params).sort().forEach((key) => {
      params += `${key}=${config.params[key]}&`
    })

    params = params.substring(0, params.length - 1);

    //删除json对象指定属性 accessKey不参与传输
    delete config.params.accessKey

  } else if (config.method == "post" || config.method == "put" || config.method == "patch") {
    let body = config.data
    if (body instanceof URLSearchParams) {
      body.append("timestamp", timestamp)
      body.append("accessKey", accessKey)
      body.append("nonce", nonce)

      //按key排序，a字母在前，第一个相同继续看第二个字母，不看长度
      body.sort()

      //转换成 aa=aa&bb=bb 格式       
      params = decodeURIComponent(body.toString())

      //删除参数中的密钥
      body.delete("accessKey")

    } else if (body instanceof FormData) {
      body.append("timestamp", timestamp)
      body.append("nonce", nonce)

      let map = new Map();
      for (var kv of body.entries()) {
        //判断是否是文件，文件不参与参数加密
        if (kv[1] instanceof File) {
          continue
        }
        map.set(kv[0], kv[1])
      }

      map.set("accessKey", accessKey)

      //map进行排序
      map = new Map([...map].sort());

      params = ""
      //排序之后拼接参数
      for (var kv of map.entries()) {
        if (kv[1] instanceof Array) {
          params += kv[0] + "=" + JSON.stringify(kv[1]) + "&"
        } else {
          params += kv[0] + "=" + kv[1] + "&"
        }
      }

      params = params.substring(0, params.length - 1);

    } else if (config.data instanceof Object) {
      config.data = {
        ...config.data,
        timestamp: new Date().getTime(),
        accessKey: accessKey,
        nonce: nonce
      }

      params = ""

      Object.keys(config.data).sort().forEach(key => {
        if (config.data[key] instanceof Array) {
          //数组需要为 ["12","33"] 这种字符串格式
          params += `${key}=${JSON.stringify(config.data[key])}&`
        } else {
          params += `${key}=${config.data[key]}&`
        }

      })

      params = params.substring(0, params.length - 1);


      //删除json对象指定属性 accessKey不参与传输
      delete config.data.accessKey
    }
  } else if (config.method == "delete") {
    if (config.data instanceof Object) {
      config.data = {
        ...config.data,
        timestamp: new Date().getTime(),
        accessKey: accessKey,
        nonce: nonce
      }

      params = ""

      Object.keys(config.data).sort().forEach(key => {
        if (config.data[key] instanceof Array) {
          //数组需要为 ["12","33"] 这种字符串格式
          params += `${key}=${JSON.stringify(config.data[key])}&`
        } else {
          params += `${key}=${config.data[key]}&`
        }

      })

      params = params.substring(0, params.length - 1);


      //删除json对象指定属性 accessKey不参与传输
      delete config.data.accessKey
    } else {
      config.data = {
        timestamp: new Date().getTime(),
        accessKey: accessKey,
        nonce: nonce
      }

      params = ""

      Object.keys(config.data).sort().forEach(key => {
        if (config.data[key] instanceof Array) {
          //数组需要为 ["12","33"] 这种字符串格式
          params += `${key}=${JSON.stringify(config.data[key])}&`
        } else {
          params += `${key}=${config.data[key]}&`
        }

      })

      params = params.substring(0, params.length - 1);

      //删除json对象指定属性 accessKey不参与传输
      delete config.data.accessKey
    }
  }
  //设置参数签名请求头
  config.headers.signature = md5(params).toUpperCase()
}

export default
  {
    signature
  }