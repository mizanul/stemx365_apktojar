FROM eclipse-temurin:17-jdk-jammy

ENV JADX_VERSION=1.4.7

WORKDIR /opt

RUN apt-get update && apt-get install -y \
    wget unzip \
    && rm -rf /var/lib/apt/lists/*

# Download & extract jadx
RUN wget https://github.com/skylot/jadx/releases/download/v${JADX_VERSION}/jadx-${JADX_VERSION}.zip \
    && unzip jadx-${JADX_VERSION}.zip \
    && JADX_DIR=$(find /opt -maxdepth 1 -type d -name "jadx*") \
    && echo "Found jadx at: $JADX_DIR" \
    && chmod +x $JADX_DIR/bin/jadx \
    && ln -sf $JADX_DIR/bin/jadx /usr/local/bin/jadx \
    && jadx --version

WORKDIR /work

ENTRYPOINT ["jadx"]
