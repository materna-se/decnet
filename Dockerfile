FROM jboss/wildfly:17.0.1.Final

ARG VERSION
WORKDIR /opt/jboss/wildfly

COPY ["./server/target/decnet-${VERSION}.war", "./standalone/deployments/ROOT.war"]

CMD ["./bin/standalone.sh", "-b", "0.0.0.0"]