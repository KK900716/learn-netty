package org.example;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.DefaultPromise;
import java.util.concurrent.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestFuture {
  public static void main(String[] args) throws ExecutionException, InterruptedException {
    EventLoopGroup eventExecutors = new NioEventLoopGroup();
    DefaultPromise<Integer> promise = new DefaultPromise<>(eventExecutors.next());
    new Thread(
            () -> {
              try {
                Thread.sleep(5000);
                promise.setSuccess(1);
              } catch (InterruptedException e) {
                promise.setFailure(e);
                throw new RuntimeException(e);
              }
            })
        .start();
    System.out.println(promise.get());
  }
}
