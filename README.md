## Overview

The object of this project is to assist users with learning the API for the Hyper-Enabled Operator (HEO) system.  The HEO system is based on Google's gRPC framework but deep knowledge of gRPC is not required.  The provided client code (in multiple languges) provide templates for invoking a subset of the HEO API.

The top level structure of the project consists of the proto files that define the sample API along with a folder for each language-based example.  The proto files describe the interfaces between the service provider and the service consumer.  These files are used by the gRPC framework to generate the supporting code that forms the connection.  Currently the top level file structure is:

  - java
  - proto
  - python
  
The proto folder contains the two protobuf-based interface files, common.proto and media.proto.  Both of these files are used by the different language examples.  The client code invokes a method that is hosted at heo-api-simulator.devwerx.org within the AWS cloud.  

The method in these examples return data about a media source.  In the returned data, there is a field with the name of StreamUri (Java) and stream_uri (Python). The value of this field represents a link to a video stream.  The HEO API Simulator is implemented using Docker and the IP address in the URI is the internal IP used by the Docker image.  If you replace this IP with the AWS host name (heo-api-simulator.devwerx.org) you can use a media viewer application like VLC to display the video stream.

## Java Client

The Java client example consists of a single Java class...MediaServiceClient.java in the <HOME>/java/src/main/java/client folder.  

The Java example was built using Java v13.0.01 and Maven v3.6.6.  Different versions may be used.

To build the Java example, navigate to the <HOME>/java folder, and invoke the following Maven command:

  mvn package
  
This process will generate the required Java classes and download the required libraries.  A single JAR file will be constructured.  To execute the client code, use the following command from the <HOME>/java directory:

  java -cp target/examples-1.0.0-jar-with-dependencies.jar client.MediaServiceClient heo-api-simulator.devwerx.org 

The console output should be similar to the following:

  C:\heo-api-simulator-examples\java>java -cp target/examples-1.0.0-jar-with-dependencies.jar client.MediaServiceClient heo-api-simulator.devwerx.org
  Jan 07, 2020 9:49:30 AM client.MediaServiceClient main
  INFO: Using heo-api-simulator.devwerx.org as the host server.
  Jan 07, 2020 9:49:31 AM client.MediaServiceClient getMediaSource
  INFO: Entering getMediaSource()
  Jan 07, 2020 9:49:31 AM client.MediaServiceClient getMediaSource
  INFO: Received iterator
  Jan 07, 2020 9:49:32 AM client.MediaServiceClient getMediaSource
  INFO: next MediaSource
  Jan 07, 2020 9:49:32 AM client.MediaServiceClient getMediaSource
  INFO: Name = unknown MOHOC
  Jan 07, 2020 9:49:32 AM client.MediaServiceClient getMediaSource
  INFO: Desc = MOHOC video source for unknown
  Jan 07, 2020 9:49:32 AM client.MediaServiceClient getMediaSource
  INFO: StreamUri = rtsp://172.17.0.2:8554/live
  Jan 07, 2020 9:49:32 AM client.MediaServiceClient getMediaSource
  INFO: IsActive = true
  Jan 07, 2020 9:49:32 AM client.MediaServiceClient getMediaSource
  INFO: StreamFormat = VIDEO
  Jan 07, 2020 9:49:32 AM client.MediaServiceClient getMediaSource
  INFO: UniqueId = rtsp://172.17.0.2:8554/live
  Jan 07, 2020 9:49:32 AM client.MediaServiceClient getMediaSource
  INFO: PlatformId = kjYheEwNRbp9aYdk
  Jan 07, 2020 9:49:32 AM client.MediaServiceClient getMediaSource
  INFO: checking for next MediaSource
  Jan 07, 2020 9:49:32 AM client.MediaServiceClient getMediaSource
  INFO: Exiting getMediaSource()
  Jan 07, 2020 9:49:32 AM client.MediaServiceClient main
  INFO: Shutting down...
  Jan 07, 2020 9:49:37 AM client.MediaServiceClient main
  INFO: done.

## Python Client

The Python client example consists of a single Python file...media_client.py in the <HOME>/python directory.  

The Python example was built using Python v3.7.4.  Different versions may be used.

There are two gRPC modules that need to be installed.  The module with the core cord is grpcio.  The grpcio-tools module has the code that generates the supporting Python files from the proto interface files.  To install these modules, use the following commands:  

 python -m pip install grpcio
 python -m pip install grpcio-tools

To prepare the Python example, navigate to the <HOME>/python folder, and invoke the following commands:

  python -m grpc_tools.protoc -I../proto --python_out=. --grpc_python_out=. heo/common.proto

  python -m grpc_tools.protoc -I../proto --python_out=. --grpc_python_out=. heo/media.proto
  
This process will generate the required support files.  To execute the client code, use the following command from the <HOME>/python directory:

  python media_client.py 

The console output should be similar to the following:

  C:\heo-api-simulator-examples\python>python media_client.py
  mediaSource.name: unknown MOHOC
  mediaSource.desc: MOHOC video source for unknown
  mediaSource.stream_uri: rtsp://172.17.0.2:8554/live
  mediaSource.active: True
  mediaSource.stream_format: 2
  mediaSource.unique_id: rtsp://172.17.0.2:8554/live
  mediaSource.platform_id: kjYheEwNRbp9aYdk

