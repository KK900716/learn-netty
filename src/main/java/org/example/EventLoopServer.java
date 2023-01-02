package org.example;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import java.nio.charset.Charset;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 44380
 */
@Slf4j
public class EventLoopServer {
  public static void main(String[] args) {
    DefaultEventLoopGroup group = new DefaultEventLoopGroup();
    new ServerBootstrap()
        // boss or worker
        .group(new NioEventLoopGroup(), new NioEventLoopGroup(2))
        .channel(NioServerSocketChannel.class)
        .childHandler(
            new ChannelInitializer<NioSocketChannel>() {
              @Override
              protected void initChannel(NioSocketChannel ch) throws Exception {
                ch.pipeline()
                    .addLast(
                        group,
                        "handler1",
                        new ChannelInboundHandlerAdapter() {
                          @Override
                          public void channelRead(ChannelHandlerContext ctx, Object msg)
                              throws Exception {
                            ByteBuf m = (ByteBuf) msg;
                            log.info(m.toString(Charset.defaultCharset()));
                          }
                        });
              }
            })
        .bind(8888);
  }
}
