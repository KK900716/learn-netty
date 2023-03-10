package org.example;

import io.netty.channel.DefaultEventLoopGroup;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 44380
 */
@Slf4j
public class TestEventLoop {
  public static void main(String[] args) {
    // 1. 创建事件循环组
    // io事件、普通任务、定时任务
    EventLoopGroup group = new NioEventLoopGroup(2);
    // 普通任务、定时任务
    EventLoopGroup group2 = new DefaultEventLoopGroup();
    // 2. 获取下一个事件循环对象
    System.out.println(group.next());
    System.out.println(group.next());
    System.out.println(group.next());
    System.out.println(group.next());
    // 3. 执行普通任务
    group
        .next()
        .submit(
            () -> {
              try {
                Thread.sleep(1000);
              } catch (InterruptedException e) {
                throw new RuntimeException(e);
              }
              log.info("ok");
            });
    // 4. 执行定时任务
    group.next().scheduleAtFixedRate(() -> log.info("1"), 0, 1, TimeUnit.SECONDS);
  }
}
