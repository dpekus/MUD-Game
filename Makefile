mud:
	javac cs3524/solutions/mud/Edge.java; \
	javac cs3524/solutions/mud/MUD.java; \
	javac cs3524/solutions/mud/Vertex.java; \
	javac cs3524/solutions/mud/MUDClient.java; \
	javac cs3524/solutions/mud/MUDServerImpl.java; \
	javac cs3524/solutions/mud/MUDServerInterface.java; \
	javac cs3524/solutions/mud/MUDServerMainline.java

clean:
	rm cs3524/solutions/mud/*.class
	clear
