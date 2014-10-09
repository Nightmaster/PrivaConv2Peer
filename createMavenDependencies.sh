cd lib;
$M2_HOME/bin/mvn install:install-file -Dfile=prompt.jar -DgroupId=org.jdesktop.xswingx -DartifactId=PromptSupport -Dversion=1.0 -Dpackaging=jar;
$M2_HOME/bin/mvn install:install-file -Dfile=gateway.jar -DgroupId=org.bitlet.weupnp -DartifactId=Gateway -Dversion=1.0 -Dpackaging=jar
exit;
