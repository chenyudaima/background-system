package com.chenyudaima.web.socket;

import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * web socket接口
 * 每次浏览器刷新，session会发生变化
 */
@Slf4j
@Component
@ServerEndpoint(value = "/ws")
public class WebSocketServer {
    /**
     * 统计在线人数
     */
    private static final AtomicInteger OnlineCount = new AtomicInteger(0);

    /**
     * 存放客户端对应的Session对象
     */
    private static final CopyOnWriteArraySet<Session> SessionSet = new CopyOnWriteArraySet<>();


    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session) {
        SessionSet.add(session);
        log.info("在线人数: {}", OnlineCount.incrementAndGet());
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(Session session) {
        SessionSet.remove(session);
        log.info("在线人数: {}", OnlineCount.decrementAndGet());
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param session 当前客户端session
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("来自客户端的消息: {}", message);
    }

    /**
     * 出现错误
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("发生错误: {}，Session ID: {}", error.getMessage(), session.getId());
        error.printStackTrace();
    }

    /**
     * 指定Session发送消息
     */
    public static void sendMessage(Session session, String message) {
        try {
            session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            log.error("发送消息出错: {}", e.getMessage());
        }
    }

    /**
     * 指定sessionId发送消息
     */
    public static void sendMessage(String sessionId, String message) {
        for (Session session : SessionSet) {
            if (session.getId().equals(sessionId)) {
                sendMessage(session, message);
                break;
            }
        }
    }

    /**
     * 群发消息
     */
    public static void sendMessage(String message) {
        for (Session session : SessionSet) {
            if (session.isOpen()) {
                sendMessage(session, message);
            }
        }
    }

}

