package com.mhc.rpc.service;

import com.mhc.rpc.api.IRpcService;
import com.mhc.rpc.common.MyDecoder;
import com.mhc.rpc.common.MyEncoder;
import com.mhc.rpc.common.MyHandler;
import com.mhc.rpc.register.RpcRegister;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

/**
 * @author ：menghui.cao, menghui.cao@leyantech.com
 * @date ：2021-01-14 14:07
 */
@Configuration
@ComponentScan("com.mhc.rpc.service.impl")
public class RpcService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RpcService.class);

    public static void main(String[] args) {

        try {
            String host = "localhost";
            Integer port = 8090;

            //启动服务
            AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(RpcService.class);
            IRpcService iRpcService = context.getBean(IRpcService.class);

            //暴露服务
            NioEventLoopGroup boosGroup = new NioEventLoopGroup();
            NioEventLoopGroup workGroup = new NioEventLoopGroup();

            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(boosGroup, workGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline()
                                    .addLast(new MyDecoder(HashMap.class))
                                    .addLast(new MyEncoder())
                                    .addLast(new MyHandler(context));

                        }
                    }).option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);
            ChannelFuture future = serverBootstrap.bind(host, port).sync();
            LOGGER.info("RpcService export:" + host + "=" + port);

            //注册服务到注册中心
            future.channel().closeFuture().sync();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
