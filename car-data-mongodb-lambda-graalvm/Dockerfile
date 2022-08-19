FROM public.ecr.aws/sam/build-provided.al2:1.53.0-20220629191818

RUN yum -y update \
    && yum install -y unzip tar gzip bzip2-devel ed gcc gcc-c++ gcc-gfortran \
    less libcurl-devel openssl openssl-devel readline-devel xz-devel \
    zlib-devel glibc-static libcxx libcxx-devel llvm-toolset-7 zlib-static \
    && rm -rf /var/cache/yum

ENV JAVA_VERSION=java17
ENV GRAAL_VERSION=22.1.0
ENV GRAAL_FOLDERNAME=graalvm-ce-${JAVA_VERSION}-${GRAAL_VERSION}
ENV GRAAL_FILENAME=graalvm-ce-${JAVA_VERSION}-linux-amd64-${GRAAL_VERSION}.tar.gz
RUN curl -4 -L https://github.com/graalvm/graalvm-ce-builds/releases/download/vm-$GRAAL_VERSION/${GRAAL_FILENAME} | tar -xvz
RUN mv $GRAAL_FOLDERNAME /usr/lib/graalvm
RUN rm -rf $GRAAL_FOLDERNAME

RUN /usr/lib/graalvm/bin/gu install native-image
RUN ln -s /usr/lib/graalvm/bin/native-image /usr/bin/native-image
RUN ln -s /usr/lib/maven/bin/mvn /usr/bin/mvn

#nodejs for spotless prettier
RUN curl --silent --location https://rpm.nodesource.com/setup_14.x | bash -
RUN yum -y install nodejs

ENV JAVA_HOME=/usr/lib/graalvm