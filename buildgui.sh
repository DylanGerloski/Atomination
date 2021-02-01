#! /bin/bash

javac -cp .;lib/core.jar *.java

#Uncomment the platform you are using.

# Linux 64bit
#java -cp .:lib/core.jar:lib/gluegen-rt.jar:lib/gluegen-rt-natives-linux-amd64.jar:lib/jogl-all-natives-linux-amd64.jar:lib/jogl-all.jar AtominationGUI

#Windows 64bit
java -cp .;lib/core.jar;lib/gluegen-rt.jar;lib/gluegen-rt-natives-windows-amd64.jar;lib/jogl-all-natives-windows-amd64.jar;lib/jogl-all.jar AtominationGUI

# MacOS Universal
#java -cp .:lib/core.jar:lib/gluegen-rt.jar:lib/gluegen-rt-natives-macosx-universal.jar:lib/jogl-all-natives-macosx-universal.jar:lib/jogl-all.jar AtominationGUI

# Windows 32bit
#java -cp .:lib/core.jar:lib/gluegen-rt.jar:lib/gluegen-rt-natives-windows-i585.jar:lib/jogl-all-natives-windows-i585.jar:lib/jogl-all.jar AtominationGUI

# Linux ARM-32bit (Raspberry Pi)
#java -cp .:lib/core.jar:lib/gluegen-rt.jar:lib/gluegen-rt-natives-linux-armv6hf.jar:lib/jogl-all-natives-linux-armv6hf.jar:lib/jogl-all.jar AtominationGUI
