#!/bin/sh
cd lib;
$M2_HOME/bin/mvn install:install-file -Dfile=xswingx.jar -DgroupId=org.jdesktop.xswingx -DartifactId=XSwingX -Dversion=1.0 -Dpackaging=jar -Dsources=xswingx-sources.jar;
$M2_HOME/bin/mvn install:install-file -Dfile=gateway.jar -DgroupId=org.bitlet.weupnp -DartifactId=Gateway -Dversion=1.0 -Dpackaging=jar
exit;
