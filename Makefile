JAVA_SOURCE = $(shell pwd)
JAVA_HOME = /Users/Mikhail_Egorkin/Library/Java/JavaVirtualMachines/openjdk-19.0.1/Contents/Home/bin/java
FLAG1 = -Dmaven.multiModuleProjectDirectory=
FLAG2 = "-Dmaven.home=/Applications/IntelliJ IDEA CE.app/Contents/plugins/maven/lib/maven3"
FLAG3 = "-Dclassworlds.conf=/Applications/IntelliJ IDEA CE.app/Contents/plugins/maven/lib/maven3/bin/m2.conf"
FLAG3_1= "-Dmaven.ext.class.path=/Applications/IntelliJ IDEA CE.app/Contents/plugins/maven/lib/maven-event-listener.jar"
FLAG4 = "-javaagent:/Applications/IntelliJ IDEA CE.app/Contents/lib/idea_rt.jar=57533:/Applications/IntelliJ IDEA CE.app/Contents/bin"
FLAG5 = -Dfile.encoding=UTF-8 -Dsun.stdout.encoding=UTF-8 -Dsun.stderr.encoding=UTF-8 -classpath "/Applications/IntelliJ IDEA CE.app/Contents/plugins/maven/lib/maven3/boot/plexus-classworlds-2.6.0.jar"
FLAG6 = org.codehaus.classworlds.Launcher -Didea.version=2022.2.3 package

all: clean build create_image save_image
all_19: clean build_19 create_image save_image

clean:
	rm -rf ./target
	rm -rf ./image

build:
	/Users/Mikhail_Egorkin/Library/Java/JavaVirtualMachines/openjdk-19.0.1/Contents/Home/bin/java -Dmaven.multiModuleProjectDirectory=/Users/Mikhail_Egorkin/Documents/Java_Trainings_Project/Gift-Card-Service -Dmaven.home="/Applications/IntelliJ IDEA CE.app/Contents/plugins/maven/lib/maven3" -Dclassworlds.conf="/Applications/IntelliJ IDEA CE.app/Contents/plugins/maven/lib/maven3/bin/m2.conf" -Dmaven.ext.class.path="/Applications/IntelliJ IDEA CE.app/Contents/plugins/maven/lib/maven-event-listener.jar" -javaagent:"/Applications/IntelliJ IDEA CE.app/Contents/lib/idea_rt.jar=57793:/Applications/IntelliJ IDEA CE.app/Contents/bin" -Dfile.encoding=UTF-8 -Dsun.stdout.encoding=UTF-8 -Dsun.stderr.encoding=UTF-8 -classpath "/Applications/IntelliJ IDEA CE.app/Contents/plugins/maven/lib/maven3/boot/plexus-classworlds.license:/Applications/IntelliJ IDEA CE.app/Contents/plugins/maven/lib/maven3/boot/plexus-classworlds-2.6.0.jar" org.codehaus.classworlds.Launcher -Didea.version=2022.2.3 package

build_19:
	$(JAVA_HOME) $(FLAG1)$(JAVA_SOURCE) $(FLAG2) $(FLAG3) $(FLAG3_1) $(FLAG4) $(FLAG5) $(FLAG6)

create_image:
	docker build . -t mikhailegorkin/gift-card-service

save_image:
	mkdir image
	docker save mikhailegorkin/gift-card-service > ./image/gift-card-service.tar
	echo 'docker load -i gift-card-service.tar' > ./image/load_image_gift-card-service.sh
	chmod +x ./image/load_image_gift-card-service.sh

docker-run:
	docker run -it -p 8090:8090 --name=gift-card-service mikhailegorkin/gift-card-service