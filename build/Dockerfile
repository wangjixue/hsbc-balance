# 使用 CentOS 7 作为基础镜像，你也可按需替换为其他版本
FROM centos:7

# 设置环境变量以避免交互模式
ENV LANG en_US.UTF-8
ENV LC_ALL en_US.UTF-8
ENV DEBIAN_FRONTEND noninteractive

# 备份并替换镜像源
RUN mv /etc/yum.repos.d/CentOS-Base.repo /etc/yum.repos.d/CentOS-Base.repo.backup && \
    curl -o /etc/yum.repos.d/CentOS-Base.repo https://mirrors.aliyun.com/repo/Centos-7.repo && \
    yum makecache

# 更新系统并安装必要的基础工具
RUN yum -y update && \
    yum -y install wget unzip tar which && \
    yum clean all

# 安装 Adoptium 的 GPG 密钥
RUN rpm --import https://packages.adoptium.net/artifactory/api/gpg/key/public

# 手动创建 adoptium.repo 文件
RUN echo '[Adoptium]' > /etc/yum.repos.d/adoptium.repo && \
    echo 'name=Adoptium' >> /etc/yum.repos.d/adoptium.repo && \
    echo 'baseurl=https://packages.adoptium.net/artifactory/rpm/centos/7/x86_64' >> /etc/yum.repos.d/adoptium.repo && \
    echo 'enabled=1' >> /etc/yum.repos.d/adoptium.repo && \
    echo 'gpgcheck=1' >> /etc/yum.repos.d/adoptium.repo && \
    echo 'gpgkey=https://packages.adoptium.net/artifactory/api/gpg/key/public' >> /etc/yum.repos.d/adoptium.repo

# 安装 JDK 17
RUN yum -y install temurin-17-jdk && \
    yum clean all

# 设置 JAVA_HOME 环境变量
ENV JAVA_HOME /usr/lib/jvm/temurin-17-jdk
ENV PATH $PATH:$JAVA_HOME/bin

# 下载并安装 Maven 3.8.8
RUN wget https://dlcdn.apache.org/maven/maven-3/3.8.8/binaries/apache-maven-3.8.8-bin.tar.gz && \
    tar -zxvf apache-maven-3.8.8-bin.tar.gz -C /opt && \
    rm apache-maven-3.8.8-bin.tar.gz

# 设置 MAVEN_HOME 环境变量
ENV MAVEN_HOME /opt/apache-maven-3.8.8
ENV PATH $PATH:$MAVEN_HOME/bin

# 安装 Git
RUN yum -y install git && \
    yum clean all

# 创建工作目录
WORKDIR /app

# 从 GitHub 拉取代码
RUN git clone https://github.com/wangjixue/hsbc-balance.git

# 进入项目目录
WORKDIR /app/hsbc-balance

# 切换到 master 分支
RUN git checkout master

RUN pwd

RUN ls -l

# 使用 Maven 构建项目
RUN mvn clean package -DskipTests

# 暴露 8080 端口
EXPOSE 8080

# 运行项目，需将 target/*.jar 替换为实际生成的 JAR 文件名
CMD ["java", "-jar", "hsbc-balance-start/target/hsbc-balance-start-1.0-SNAPSHOT.jar"]
