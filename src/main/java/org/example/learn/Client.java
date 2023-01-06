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
    for (int i = 0; i < 1; i++) {
      send();
    }
  }

  public static void send() {
    NioEventLoopGroup eventExecutors = new NioEventLoopGroup();
    try {
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
                              ByteBuf buffer = ctx.alloc().buffer(16);
                              buffer.writeBytes(("hello".repeat(5)).getBytes());
                              ctx.writeAndFlush(buffer);
                              ctx.channel().close();
                            }
                          });
                }
              })
          .connect("127.0.0.1", 8888)
          .sync();
    } catch (Exception e) {
      log.info("error", e);
    } finally {
      eventExecutors.shutdownGracefully();
    }
  }
}
