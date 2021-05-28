FROM maven:3-openjdk-11

RUN mkdir /opt/foodtracker
WORKDIR /opt/foodtracker

COPY ./ /opt/foodtracker

RUN mvn clean install

ENTRYPOINT [ "mvn", "jetty:run"]
