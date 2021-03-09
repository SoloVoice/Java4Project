import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.file.Path;
import java.nio.file.Paths;

public class ClientHandler extends ChannelInboundHandlerAdapter {
    private Path repPath = Paths.get("ClientRepository");

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof SendingFile) {
            FileHandler.save(repPath, (SendingFile) msg);
        }
    }
}
