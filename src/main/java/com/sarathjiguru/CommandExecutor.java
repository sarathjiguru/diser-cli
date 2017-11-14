package com.sarathjiguru;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.concurrent.ExecutionException;

/**
 * Created by sarath on 12/11/17.
 */
public class ClientConnect {
    private static ChannelFuture f;
    private EventLoopGroup group;
    private Channel channel;
    private EchoClientInitializer echoClientInitializer;
    private Bootstrap b;

    public Object write(String command) throws InterruptedException, ExecutionException {
        connect();
        channel.writeAndFlush(command + "\r\n");
        ChannelPipeline pipeline = channel.pipeline();
        EchoClientHandler echoClientHandler = (EchoClientHandler) pipeline.last();
        Object object = echoClientHandler.getObject();
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
                            p.addLast(new EchoClientInitializer());
                        }
                    });
        }
        // Start the client.
        f = b.connect(EchoClient.HOST, EchoClient.PORT).sync();
        channel = f.channel();
    }

    public void disconnect() throws InterruptedException {
        if(channel.isOpen()){
            channel.close();
        }
        group.shutdownGracefully();
    }
}
