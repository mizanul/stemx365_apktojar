docker build -t aar-to-java-jvm .
docker run --rm -v $(pwd):/workspace aar-to-java-jvm kibo_rpc_api-debug-5th.aar