import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ClientApp {
    private Path repPath = Paths.get("ClientRepository");
    private SocketChannel channel;
    private static final String HOST = "localhost";
    private static final int PORT = 8189;

    public ClientApp() {
        new Thread(() -> {
            EventLoopGroup mainG = new NioEventLoopGroup();
            try {
                Bootstrap b = new Bootstrap();
                b.group(mainG)
                        .channel(NioSocketChannel.class)
                        .handler(new ChannelInitializer<SocketChannel>() {
                            @Override
                            protected void initChannel(SocketChannel socketChannel) throws Exception {
                                channel = socketChannel;
                                socketChannel.pipeline().addLast(
                                        new ObjectEncoder(),
                                        new ObjectDecoder(150*1024*1024,ClassResolvers.cacheDisabled(null))
                                );
                            }
                        });
                ChannelFuture f = b.connect(HOST, PORT).sync();
                f.channel().closeFuture().sync();
            } catch (Exception e) {
                e.printStackTrace();
            }
            finally {
                mainG.shutdownGracefully();
            }
        }).start();
    }

    public void fileRequest (String fileName) {
        channel.writeAndFlush(new FileRequest(fileName));
    }

    public void sendFile(String fileName) {
        Path path = repPath.resolve(fileName);
        try {
            channel.writeAndFlush(new SendingFile(path));
            System.out.println("Файл отправлен");
        } catch (IOException e) {
            System.out.println("Такого файла не существует!");
        }

    }
}
