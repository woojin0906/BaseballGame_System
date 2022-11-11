package game;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class TcpClient {
	Scanner sc = new Scanner(System.in);



	public void main() {

		try {

			System.out.print("서버 IP입력 : ");

			// String serverIP = sc.next();

			String serverIp = "localhost";

			System.out.println("서버에 연결중입니다....." + serverIp);

			// 소켓생성

			Socket so = new Socket(serverIp, 7777);

			System.out.println("클라이언트 소켓을 생성 했습니다.");

			// 서버에서 메세지 받아야 한다.

			InputStream in = so.getInputStream();

			DataInputStream dis = new DataInputStream(in);

			



			//

			OutputStream out = so.getOutputStream();// 만들어진 소켓의 아웃풋스트림넣겠다.

			DataOutputStream dos = new DataOutputStream(out);



			System.out.println(dis.readUTF());

			

			//System.out.println("종규받은것 " + dis.readUTF());

			

			while (true) {

				System.out.println(dis.readUTF());

				

				//System.out.print("내가 입력한거 : ");

				System.out.print("-->");

				String text = sc.nextLine();

				dos.writeUTF(text);//어떤건지 보내는것

				

				

				System.out.println(dis.readUTF());

				

				String sel =dis.readUTF();//스트라이크 볼 받는것

				

				if(sel.equals("win")) {

					System.out.println("클라이언트가 졌다.");

					break;

				}

				

				if(sel.equals("lose")) {

					System.out.println("클라이언트가 이겼다.");

					break;

				}

			}

			System.out.println("연결종료합니다.");

			dis.close();

			dos.close();

			so.close();



		} catch (Exception e) {



		}

	}
public static void main(String[] args) {
	TcpClient tc = new TcpClient();
	tc.main();
}
}

