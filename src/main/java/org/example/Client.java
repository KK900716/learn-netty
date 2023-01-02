package org.example;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import java.util.Scanner;

/**
 * @author 44380
 */
public class Client {
  public static void main(String[] args) throws InterruptedException {
    NioEventLoopGroup eventExecutors = new NioEventLoopGroup();
    Channel channel =
        new Bootstrap()
            .group(eventExecutors)
            .channel(NioSocketChannel.class)
            .handler(
                new ChannelInitializer<>() {
                  @Override
                  protected void initChannel(Channel ch) {
                    ch.pipeline().addLast(new StringEncoder());
                  }
                })
            .connect("localhost", 8888)
            .sync()
            .channel();
    new Thread(
            () -> {
              Scanner scanner = new Scanner(System.in);
              while (true) {
                String s = scanner.nextLine();
                if ("q".equals(s)) {
                  channel.close();
                  break;
                }
                channel.writeAndFlush(s);
              }
            },
            "input")
        .start();
    channel
        .closeFuture()
        .addListener((ChannelFutureListener) future -> eventExecutors.shutdownGracefully());
  }
}
