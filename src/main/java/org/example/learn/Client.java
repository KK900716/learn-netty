package org.example.learn;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Client {
  public static void main(String[] args) throws InterruptedException {
    NioEventLoopGroup eventExecutors = new NioEventLoopGroup();
    new Bootstrap()
        .group(eventExecutors)
        .channel(NioSocketChannel.class)
        .handler(
            new ChannelInitializer<>() {
              @Override
              protected void initChannel(Channel ch) throws Exception {
                ch.pipeline()
                    .addLast(
                        new ChannelInboundHandlerAdapter() {
                          @Override
                          public void channelActive(ChannelHandlerContext ctx) throws Exception {
                            for (int i = 0; i < 10; i++) {
                              ByteBuf buffer = ctx.alloc().buffer(16);
                              buffer.writeBytes(("hello" + i).getBytes());
                              ctx.writeAndFlush(buffer);
                            }
                            super.channelActive(ctx);
                          }
                        });
              }
            })
        .connect("127.0.0.1", 8888);
  }
}
