package ar.gov.anses.seginf.intrusos;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ConnectionlessBootstrap;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.FixedReceiveBufferSizePredictorFactory;
import org.jboss.netty.channel.socket.DatagramChannelFactory;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;

public class RSyslogServer {

	public static void main(String[] args) throws Exception {
		//Solo para UDP
//		DatagramChannelFactory f = new NioDatagramChannelFactory(Executors.newCachedThreadPool());
		
		//Solo TCP
		NioServerSocketChannelFactory f = new NioServerSocketChannelFactory(Executors.newCachedThreadPool(), Executors.newCachedThreadPool());

		ConnectionlessBootstrap bootstrap = new ConnectionlessBootstrap(f);
		
		//Start cep engine
		final CEPEngine engine = new CEPEngine();
		engine.initEngine();

		// Set up the pipeline factory.
		bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
			public ChannelPipeline getPipeline() throws Exception {
				
				return Channels.pipeline(new RSyslogServerHandler(engine.ep));
			}
		});

		bootstrap.setOption("receiveBufferSizePredictorFactory",new FixedReceiveBufferSizePredictorFactory(1024));
		
		// Bind and start to accept incoming connections.
		bootstrap.bind(new InetSocketAddress(1514));
		
		System.out.println("Escuchando conexiones UDP en el puerto 1514, dale petitt!");
	}
}
