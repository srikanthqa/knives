#!/bin/sh

# @see https://help.ubuntu.com/community/Java
#
# Usage: ./install_jdk.sh /path/to/jdk-archive.tar.gz installation_path
#
# Example:
# ./install_jdk.sh /path/to/jdk-archive.tar.gz /opt/jvm/jdk-{version}

JDK_ARCHIVE=$1
INSTALL_PATH=$2
INSTALL_BIN=$INSTALL_PATH/bin

mkdir -p $INSTALL_PATH
tar xvf $JDK_ARCHIVE -C $INSTALL_PATH --strip=1

for exec in `ls $INSTALL_BIN`
do
	update-alternatives --verbose --install /usr/bin/$exec $exec $INSTALL_BIN/$exec 10
done

update-alternatives --verbose --install "/usr/lib/mozilla/plugins/libjavaplugin.so" "mozilla-javaplugin.so" "$INSTALL_PATH/jre/lib/i386/libnpjp2.so" 10
