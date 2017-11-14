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
public class DiserTransportCli {
    private static EventLoopGroup group;
    private static Channel channel;
    private static Bootstrap b;

    private static DiserTransportCli instance = null;

    protected DiserTransportCli() {

    }

    public Object runCommand(String command) throws InterruptedException, ExecutionException {
        connect();
        channel.writeAndFlush(command + "\r\n");
        ChannelPipeline pipeline = channel.pipeline();
        ClientHandler clientHandler = (ClientHandler) pipeline.last();
        Object object = clientHandler.getObject();
        channel.close();
        channel.disconnect();
        return object;
    }

    public static DiserTransportCli connect(String host, int port) throws InterruptedException {
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
        ChannelFuture f = b.connect(host, port).sync();
        channel = f.channel();
        return new DiserTransportCli();
    }

    private DiserTransportCli connect() throws InterruptedException {
        return connect(DiserClient.HOST, DiserClient.PORT);
    }


    public void disconnect() throws InterruptedException {
        if (channel.isOpen()) {
            channel.close();
        }
        group.shutdownGracefully();
    }
}
