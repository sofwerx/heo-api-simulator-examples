package client;

import com.kopismobile.heo_proto.CommonProto;
import com.kopismobile.heo_proto.MediaProto;
import com.kopismobile.heo_proto.MediaServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;

import java.util.Iterator;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A simple client that requests the information for the media source.
 */
public class MediaServiceClient {
  private static final Logger logger = Logger.getLogger(MediaServiceClient.class.getName());

  private final ManagedChannel channel;
  private final MediaServiceGrpc.MediaServiceBlockingStub blockingStub;

  /** Construct client connecting to MediaService at {@code host:port}. */
  public MediaServiceClient(String host, int port) {
    this(ManagedChannelBuilder.forAddress(host, port)
        // Channels are secure by default (via SSL/TLS). For the example we disable TLS to avoid
        // needing certificates.
        .usePlaintext()
        .build());
  }

  /** Construct client for accessing server using the existing channel. */
  MediaServiceClient(ManagedChannel channel) {
    this.channel = channel;
    blockingStub = MediaServiceGrpc.newBlockingStub(channel);
  }

  public void shutdown() throws InterruptedException {
    channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
  }

  /** call the GetMediaSource on server. */
  public void getMediaSource() {
    logger.info("Entering getMediaSource()");
    CommonProto.Empty request = null;
    Iterator iter = null;
    try {
      iter = blockingStub.getMediaSource(request);
    } catch (StatusRuntimeException e) {
      logger.log(Level.WARNING, "RPC failed: {0}", e.getStatus());
      return;
    }
    logger.info("Received iterator");
    // hack - this should be while( iter.hasNext() ) but the code hangs on the second item
    if ( iter.hasNext() ) {
      logger.info("next MediaSource");
      MediaProto.MediaSource mediaSource = (MediaProto.MediaSource)iter.next();
      logger.info( "Name = " + mediaSource.getName() );
      logger.info( "Desc = " + mediaSource.getDesc() );
      logger.info( "StreamUri = " + mediaSource.getStreamUri() );
      logger.info( "IsActive = " + mediaSource.getActive() );
      logger.info( "StreamFormat = " + mediaSource.getStreamFormat() );
      logger.info( "UniqueId = " + mediaSource.getUniqueId() );
      logger.info( "PlatformId = " + mediaSource.getPlatformId() );
      logger.info("checking for next MediaSource");
    }
    logger.info("Exiting getMediaSource()");
  }

  /*
  public void getMediaSource() {
    logger.info("Entering getMediaSource()");
    MediaProto.Empty request = null;
    MediaProto.MediaSource mediaSource = null;
    try {
      mediaSource = blockingStub.getMediaSource(request);
    } catch (StatusRuntimeException e) {
      logger.log(Level.WARNING, "RPC failed: {0}", e.getStatus());
      return;
    }
    logger.info("Received mediaSource");
    logger.info( "Name = " + mediaSource.getName() );
    logger.info( "Desc = " + mediaSource.getDesc() );
    logger.info( "StreamUri = " + mediaSource.getStreamUri() );
    logger.info( "IsActive = " + mediaSource.getActive() );
    logger.info( "StreamFormat = " + mediaSource.getStreamFormat() );
    logger.info( "UniqueId = " + mediaSource.getUniqueId() );
    logger.info( "PlatformId = " + mediaSource.getPlatformId() );

    logger.info("Exiting getMediaSource()");
  }
  */
  /**
   * Get media sources from the service.
   */
  public static void main(String[] args) throws Exception {
    String host = "localhost";

    if (args.length > 1) {
      logger.info("USAGE: MediaServiceClient <HOST>");
      System.exit(1);
    }
    else if (args.length < 1) {
      logger.info("No HOST value specified...using \"" + host + "\".");
    }
    else if (args.length == 1) {
      host = args[0];
      logger.info("Using " + host + " as the host server.");
    }

    // Access a service running on the local machine on port 50051
    MediaServiceClient client = new MediaServiceClient(host, 50051);
    try {
      client.getMediaSource();
    }
    catch (Exception e) {
      logger.severe( "Error = " + e.getMessage() );
    }
    finally {
      logger.info( "Shutting down..." );
      client.shutdown();
      logger.info( "done." );
    }
  }
}
