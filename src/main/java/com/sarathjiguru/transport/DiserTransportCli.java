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
    private final int port;
    private final String host;
    private static Bootstrap b = new Bootstrap();


    public DiserTransportCli(DiserUrl diserUrl) throws InterruptedException {
        this(diserUrl.host(), diserUrl.port());
    }


    public DiserTransportCli(String host, int port) throws InterruptedException {
        this.host = host;
        this.port = port;
    }

    /**
     * Runs command and returns the message retrieved from server
     *
     * @param command
     * @return
     * @throws InterruptedException
     * @throws ExecutionException
     */
    public Object runCommand(String command) throws InterruptedException, ExecutionException {
        ChannelFuture f = connect(host, port);
        Channel channel = f.channel();
        channel.writeAndFlush(command + "\r\n");
        ChannelPipeline pipeline = channel.pipeline();
        ClientHandler clientHandler = pipeline.get(ClientHandler.class);
        Object object = clientHandler.getObject();
        channel.close();
        channel.disconnect();
        return object;
    }

    private ChannelFuture connect(String host, int port) throws InterruptedException {
        if (group == null) {
            b = new Bootstrap();
            group = new NioEventLoopGroup();
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
        return b.connect(host, port).sync();
    }


    public void disconnect() throws InterruptedException {
        if (group != null) {
            group.shutdownGracefully();
        }
        group = null;
    }
}
