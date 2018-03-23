game:
	javac mud/Edge.java; \
	javac mud/MUD.java; \
	javac mud/Vertex.java; \
	javac mud/MUDClient.java; \
	javac mud/MUDServerImpl.java; \
	javac mud/MUDServerInterface.java; \
	javac mud/MUDServerMainline.java

clean:
	rm mud/*.class
	clear
