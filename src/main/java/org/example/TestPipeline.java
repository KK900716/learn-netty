package org.example;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestPipeline {
  public static void main(String[] args) {
    new ServerBootstrap()
        .group(new NioEventLoopGroup())
        .channel(NioServerSocketChannel.class)
        .childHandler(
            new ChannelInitializer<>() {
              @Override
              protected void initChannel(Channel ch) throws Exception {
                ChannelPipeline pipeline = ch.pipeline();
                pipeline.addLast(
                    "h1",
                    new ChannelInboundHandlerAdapter() {
                      @Override
                      public void channelRead(ChannelHandlerContext ctx, Object msg)
                          throws Exception {
                        log.info("{}", 1);
                        super.channelRead(ctx, msg);
                      }
                    });
              }
            })
        .bind(8888);
  }
}
