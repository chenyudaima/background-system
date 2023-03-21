package com.chenyudaima.util.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;


/**
 * @author 沉鱼代码
 * @date 2023/3/20
 */
public class NettyClient {
    private static final Logger log = LoggerFactory.getLogger(NettyClient.class);

    /**
     * 连接的ip
     */
    private final String ip;

    /**
     * 连接的端口
     */
    private final int port;

    /**
     * 连接通道的处理器
     */
    private ChannelInboundHandlerAdapter[] channelInboundHandlerAdapter;


    /**
     * 连接通道
     */
    private ChannelFuture channelFuture;

    public NettyClient(String ip, int port, ChannelInboundHandlerAdapter... channelInboundHandlerAdapter) {
        this.ip = ip;
        this.port = port;
        this.channelInboundHandlerAdapter = channelInboundHandlerAdapter;
    }

    public void connect() {
        connect(new NioEventLoopGroup());
    }

    public void connect(EventLoopGroup eventLoopGroup) {
        try {
            //创建bootstrap对象，配置参数
            Bootstrap bootstrap = new Bootstrap()
                    .remoteAddress(ip, port)

                    //设置线程组
                    .group(eventLoopGroup)

                    //设置客户端的通道实现类型
                    .channel(NioSocketChannel.class)

                    //通道方式
                    .option(ChannelOption.SO_KEEPALIVE, true)

                    //使用匿名内部类初始化通道
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) {
                            //添加客户端通道的处理器
                            socketChannel.pipeline().addLast(channelInboundHandlerAdapter);
                        }
                    });

            //添加连接监听
            channelFuture = bootstrap.connect().addListener((ChannelFuture futureListener) -> {
                final EventLoop eventLoop = futureListener.channel().eventLoop();
                if (!futureListener.isSuccess()) {
                    log.error("与服务端:" + ip + ":" + port + "连接失败! 10秒后重试!");
                    eventLoop.schedule(() -> connect(eventLoop), 10, TimeUnit.SECONDS);
                } else {
                    log.info("与服务端:" + ip + ":" + port + "连接!");
                }
            });

            ////连接服务端 对通道关闭进行监听（阻塞）
            channelFuture.channel().closeFuture().sync();

        }catch (Exception e) {
            if(channelFuture == null) {
                log.error("与服务端:" + ip + ":" + port + "连接失败! 10秒后重试!");
                eventLoopGroup.schedule(() -> connect(eventLoopGroup), 10, TimeUnit.SECONDS);
            }
        }
    }

}
