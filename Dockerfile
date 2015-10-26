FROM centos:centos6

# yum install build tools
RUN yum -y update; yum clean all
RUN yum -y install java-1.8.0-openjdk-devel; yum clean all
RUN yum -y install git; yum clean all
RUN yum -y install unzip; yum clean all
RUN yum -y install wget; yum clean all

# change to temp directory
RUN mkdir -p /tmp
WORKDIR /tmp

# install maven
RUN mkdir -p /opt/gradle
RUN wget -N http://services.gradle.org/distributions/gradle-2.7-bin.zip

RUN unzip -oq ./gradle-2.7-bin.zip -d /opt/gradle/
RUN ln -sfnv gradle-2.7 /opt/gradle/latest

RUN echo export M2_HOME=/usr/local/maven >> /etc/profile.d/maven.sh
RUN echo export PATH=${M2_HOME}/bin:${PATH} >> /etc/profile.d/maven.sh

# setup environment variables
ENV GRADLE_HOME /opt/gradle/latest
ENV JAVA_HOME /usr/lib/jvm/java-1.8.0-openjdk.x86_64
ENV M2_HOME /usr/local/maven 
ENV PATH $PATH:$GRADLE_HOME/bin:$JAVA_HOME

# clean up
RUN rm -rf /var/lib/apt/lists/* /var/tmp/* /var/cache/yum /usr/lib/locale /usr/lib/gcc /usr/share/locale
RUN mkdir -p /data/app

# get the source
WORKDIR /data/app
RUN git clone https://bb54503d3da64ef1f73709ad130dd5a2f887323a:x-oauth-basic@github.com/MoveInc/rezone.geocoder.git

# build and deploy
WORKDIR /data/app/rezone.geocoder/rezone.geocoder.api
RUN gradle build



