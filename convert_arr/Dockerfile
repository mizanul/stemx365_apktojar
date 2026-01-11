# -----------------------------
# Dockerfile: aar-to-java-jvm
# -----------------------------
FROM ubuntu:22.04

ENV DEBIAN_FRONTEND=noninteractive

# Install required packages
RUN apt-get update && apt-get install -y \
    openjdk-17-jdk \
    unzip \
    wget \
    bash \
    && rm -rf /var/lib/apt/lists/*

# CFR decompiler
WORKDIR /opt
RUN wget https://www.benf.org/other/cfr/cfr-0.152.jar

# Create workspace
WORKDIR /workspace

#COPY api /workspace
# Copy conversion script into container
COPY convert-aar-jvm.sh /usr/local/bin/convert-aar-jvm
RUN chmod +x /usr/local/bin/convert-aar-jvm

# Default entrypoint
ENTRYPOINT ["convert-aar-jvm"]
