package org.example;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;

/**
 * @author 44380
 */
public class Server {
  public static void main(String[] args) {
    // 启动器
    new ServerBootstrap()
        // 2. BossEventLoop,WorkerEventLoop(selector,thread),group 组
        .group(new NioEventLoopGroup())
        // 3. 选择服务器的 ServerSocketChannel 实现
        .channel(NioServerSocketChannel.class)
        // 4. boss 负责处理连接 worker(child) 负责处理读写，决定了 worker(child) 能执行哪些操作 (handler)
        .childHandler(
            // 5. channel 代表和客户端进行数据读写的通道 Initializer 初始化，负责添加别的 handler
            new ChannelInitializer<>() {
              @Override
              protected void initChannel(Channel ch) throws Exception {
                // 6. 添加具体 handler
                // 将 ByteBuf 转换为字符串
                ch.pipeline().addLast(new StringDecoder());
                // 自定义 Handler
                ch.pipeline()
                    .addLast(
                        new ChannelInboundHandlerAdapter() {
                          // 读事件
                          @Override
                          public void channelRead(ChannelHandlerContext ctx, Object msg)
                              throws Exception {
                            System.out.println(msg);
                          }
                        });
              }
            })
        // 7. 绑定监听端口
        .bind(8888);
  }
}
