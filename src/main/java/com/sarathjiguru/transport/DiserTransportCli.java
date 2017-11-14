package com.sarathjiguru.transport;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.concurrent.ExecutionException;

/**
 * Created by sarath on 12/11/17.
 */
public class CommandExecutor {
    private static ChannelFuture f;
    private EventLoopGroup group;
    private Channel channel;
    private DiserCliInitializer diserCliInitializer;
    private Bootstrap b;

    public Object write(String command) throws InterruptedException, ExecutionException {
        connect();
        channel.writeAndFlush(command + "\r\n");
        ChannelPipeline pipeline = channel.pipeline();
        ClientHandler clientHandler = (ClientHandler) pipeline.last();
        Object object = clientHandler.getObject();
        channel.close();
        channel.disconnect();
        return object;
    }

    private void connect() throws InterruptedException {
        if (group == null) {
            group = new NioEventLoopGroup();
            b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline p = ch.pipeline();
                            //p.addLast(new LoggingHandler(LogLevel.INFO));
                            p.addLast(new DiserCliInitializer());
                        }
                    });
        }
        // Start the client.
        f = b.connect(DiserClient.HOST, DiserClient.PORT).sync();
        channel = f.channel();
    }

    public void disconnect() throws InterruptedException {
        if (channel.isOpen()) {
            channel.close();
        }
        group.shutdownGracefully();
    }
}
