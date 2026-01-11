
#!/usr/bin/env bash
#docker run --rm -v $(pwd):/work ghcr.io/skylot/jadx:latest jadx /work/app.apk -d /work/out
#docker run --rm -v $(pwd):/work jadx-cli /work/app-debug.apk -d /work/out


docker run --user $(id -u):$(id -g) --rm -it -v `pwd`:/work abh1sek/jadx jadx /work/app-debug.apk --deobf -d /work/out 
