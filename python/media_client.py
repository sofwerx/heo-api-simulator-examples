from __future__ import print_function
import logging

import grpc

from heo import common_pb2
from heo import media_pb2
from heo import media_pb2_grpc

def run():

    with grpc.insecure_channel('heo-api-simulator.devwerx.org:50051') as channel:
        stub = media_pb2_grpc.MediaServiceStub(channel)
        for mediaSource in stub.GetMediaSource(common_pb2.Empty()):
            print("mediaSource.name: " + str(mediaSource.name))
            print("mediaSource.desc: " + str(mediaSource.desc))
            print("mediaSource.stream_uri: " + str(mediaSource.stream_uri))
            print("mediaSource.active: " + str(mediaSource.active))
            print("mediaSource.stream_format: " + str(mediaSource.stream_format))
            print("mediaSource.unique_id: " + str(mediaSource.unique_id))
            print("mediaSource.platform_id: " + str(mediaSource.platform_id))
            break
            
if __name__ == '__main__':
    logging.basicConfig()
    run()
