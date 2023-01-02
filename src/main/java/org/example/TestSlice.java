package org.example;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestSlice {
  public static void main(String[] args) {
    ByteBuf byteBuf = ByteBufAllocator.DEFAULT.buffer(10);
    byteBuf.writeBytes(new byte[] {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j'});
    ByteBuf slice = byteBuf.slice(0, 5);
    for (int i = 0; i < slice.capacity(); i++) {
      System.out.println(slice.readByte());
    }
  }
}
