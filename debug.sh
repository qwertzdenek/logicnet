#!/bin/sh
export JPDA_SUSPEND="y"
export CATALINA_OPTS=-Djava.security.auth.login.config=/home/haswi/git/logicnet/target/apache-tomee/conf/login.config
target/apache-tomee/bin/catalina.sh jpda run
