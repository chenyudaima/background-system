# background-system

优点：干净，清晰，整洁

background-system-server为后端服务

background-system-web为前端页面

## background-system-server

服务统一响应格式

```json
{
    "code": 200,
    "message": "成功",
    "data": null
}
```

状态码有200(处理成功)，403(权限异常)，404(路径错误)，500(处理失败)