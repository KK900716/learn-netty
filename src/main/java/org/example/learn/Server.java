package org.example.learn;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import java.nio.charset.Charset;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Server {
  public static void main(String[] args) throws InterruptedException {
    NioEventLoopGroup boss = new NioEventLoopGroup(1);
    NioEventLoopGroup worker = new NioEventLoopGroup(2);
    new ServerBootstrap()
        .option(ChannelOption.SO_RCVBUF, 5)
        .group(boss, worker)
        .channel(NioServerSocketChannel.class)
        .childHandler(
            new ChannelInitializer<>() {
              @Override
              protected void initChannel(Channel ch) throws Exception {
                ch.pipeline()
                    .addLast(
                        new ChannelInboundHandlerAdapter() {
                          @Override
                          public void channelRead(ChannelHandlerContext ctx, Object msg)
                              throws Exception {
                            if (msg instanceof ByteBuf o) {
                              log.info("msg:{}", o.toString(Charset.defaultCharset()));
                            }
                            super.channelRead(ctx, msg);
                          }
                        });
              }
            })
        .bind(8888);
  }
}
