package game;
// 클라이언트 메시지를 서버로 보내는 thread
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

public class ClientThread extends Thread {
	Socket socket = null;
 	String outMsg;
 	
	public ClientThread(Socket socket, String outMsg) {
		this.socket=socket;
		this.outMsg=outMsg;
		
	}
	
	@Override
	public void run() {
		try {
			PrintStream out = new PrintStream(socket.getOutputStream());
			
			out.println(outMsg);
			System.out.println(outMsg);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
