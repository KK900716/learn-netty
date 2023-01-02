package org.example;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestByteBuf {
  public static void main(String[] args) {
    ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer();
    log.info("{}", buffer);
    buffer.writeBytes("a".repeat(300).getBytes());
    log.info("{}", buffer);
  }
}
