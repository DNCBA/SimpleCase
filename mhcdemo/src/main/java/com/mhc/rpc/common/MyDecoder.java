package com.mhc.rpc.common;

import com.alibaba.fastjson.JSON;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @author ：menghui.cao, menghui.cao@leyantech.com
 * @date ：2021-01-14 15:15
 */
public class MyDecoder extends ByteToMessageDecoder {


    private Class<?> t;

    public MyDecoder(Class t) {
        this.t = t;
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        if (byteBuf.readableBytes() < 4) {
            return;
        }
        int length = byteBuf.readInt();
        if (length < 0) {
            channelHandlerContext.close();
        }
        if (byteBuf.readableBytes() < length) {
            byteBuf.resetReaderIndex();
        }
        byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes);
        Object o = JSON.parseObject(bytes, t);
        list.add(o);
    }
}
