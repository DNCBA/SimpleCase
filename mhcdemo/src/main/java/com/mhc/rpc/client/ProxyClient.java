package com.mhc.rpc.client;

import com.alibaba.fastjson.JSON;
import com.mhc.rpc.common.MyDecoder;
import com.mhc.rpc.common.MyEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;

/**
 * @author ：menghui.cao, menghui.cao@leyantech.com
 * @date ：2021-01-14 16:00
 */
public class ProxyClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProxyClient.class);
    private final Object obj = new Object();
    private Object response;

    private String localHost;
    private Integer port;

    public ProxyClient(String localHost, int prot) {
        this.localHost = localHost;
        this.port = prot;
    }

    public <T> T getClient(Class<?> clazz) {
        T result = (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class<?>[]{clazz}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {


                HashMap<String, Object> map = new HashMap<>();
                map.put("method", method.getName());
                map.put("className", method.getDeclaringClass().getName());
                map.put("args", args);
                map.put("argsType", method.getParameterTypes());
                map.put("beanName", "rpcServiceImpl");

                NioEventLoopGroup workGroup = new NioEventLoopGroup();

                Bootstrap bootstrap = new Bootstrap();
                bootstrap.group(workGroup)
                        .channel(NioSocketChannel.class)
                        .handler(new ChannelInitializer<SocketChannel>() {
                            @Override
                            protected void initChannel(SocketChannel socketChannel) throws Exception {
                                socketChannel.pipeline()
                                        .addLast(new MyDecoder(HashMap.class))
                                        .addLast(new MyEncoder())
                                        .addLast(new SimpleChannelInboundHandler<HashMap>() {
                                            @Override
                                            protected void channelRead0(ChannelHandlerContext channelHandlerContext, HashMap result) throws Exception {
                                                LOGGER.info("recive server response {}", JSON.toJSONString(result));
                                                response = result.get("result");
//                                                channelHandlerContext.close();
                                            }
                                        });

                            }
                        }).option(ChannelOption.SO_KEEPALIVE, true);


                ChannelFuture future = bootstrap.connect(localHost, port).sync();

                future.channel().writeAndFlush(map).sync();
                LOGGER.info("client send request {}", JSON.toJSONString(map));


                future.channel().closeFuture().sync();
                LOGGER.info("proxy invoke result {}", response);
                return response;

            }
        });
        return result;
    }


}
