package game;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;
import java.util.StringTokenizer;

public class TcpServer {
	Scanner sc = new Scanner(System.in);
	Random r = new Random();

	public void main() {
		ServerSocket ss = null;
		try {
			ss = new ServerSocket(7777);// 서버소켓 오픈정도가 끝


			System.out.println("서버 소켓을 생성 하였습니다.");
		} catch (Exception e) {

			// TODO: handle exception

			e.printStackTrace();

		}



		System.out.println("클라이언트의 접속을 기다립니다 . . . . . .");
		try {
			Socket so = ss.accept();// 얘가 실제로 연결을 기다리는 중이다.
			System.out.println(getTime() + so.getInetAddress() + "로부터 접속성공");// 요청들어온 IP 갖고온다.
			String numb=null;
			int [] ran=new int [3];



			//numb=(r.nextInt(10)+1)+" "+(r.nextInt(10)+1)+" "+(r.nextInt(10)+1);
			//StringTokenizer sr = new StringTokenizer(numb," ");

			for(int i=0; i<ran.length; i++) {//i부터 시작해서 ran의 길이까지 i의값을 증가시켰다.
				ran[i]=r.nextInt(10)+1;//ran배열의 i번째에  랜덤 수를 넣었다.
				for(int j=0; j<i; j++) {//여기서부터는 중복검사 소스이다.
					if(ran[i]==ran[j]) {//중복검사소스이다.
						i--;//같을경우 다시 i를 입력하게끔 할려고 돌아갔다.
						break;//위에 for문으로 가야되니 다시 돌아갔다.
					}
				}
			}

			numb=ran[0]+" "+ran[1]+" "+ran[2];//이걸 해준 이유는 그냥 표시하기 위해서이다. 아래에다 그냥 변수안만들고 해도 상관은 없다.
			System.out.println("서버 숫자 ->"+numb);

			InputStream in = so.getInputStream();
			DataInputStream dis = new DataInputStream(in);
			OutputStream out = so.getOutputStream();// 만들어진 소켓의 아웃풋스트림넣겠다.
			DataOutputStream dos = new DataOutputStream(out);

			dos.writeUTF("야구 게임을 시작합니다.");//처음에 보낼 말이다.


			int oout=0;//이걸 준 이유는 총 10번 왔다갔다할경우 서버가 이기게끔 하기 위해서이다.
			while (true) {//반복시킨 이유는 반복할려고
				int strike=0;
				int ball=0;
				dos.writeUTF("세 수를 입력해주세요(ex: 1 2 3)");

				String a = dis.readUTF();
				System.out.println("클라이언트가 입력한 수 -> " + a);

				StringTokenizer st = new StringTokenizer(a," ");

				int [] nnum=new int [3];
				for(int i=0; i<3;i++) {
					nnum[i]=Integer.parseInt(st.nextToken());//입력받은것
				}

				for(int i=0; i<3; i++) {
					for(int j=0; j<3; j++) {
						if(ran[i]==nnum[j]) {
							if(i==j) {
								strike++;
							}
							else {
								ball++;
							}
						}


					}
				}

				String str=strike+" 스트라이크 "+ball+" 볼   ("+oout+"  )번째";
				System.out.println(str);
				dos.writeUTF(str);

				oout++;
				if(oout>=10) {
					dos.writeUTF("win");
					System.out.println("서버승리");
					break;
				}
				else if(strike==3) {
					System.out.println("서버패배");
					dos.writeUTF("lose");
					break;
				}
				else {
					dos.writeUTF("");
				}


			}
			dos.close();
			dis.close();
			so.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} //
	}

	// }

	static String getTime() {// 메소드 끝나고 안사라진다. static일경우에
		SimpleDateFormat f = new SimpleDateFormat("[hh:mm:ss]");
		return f.format(new Date());

	}
	public static void main(String[] args) {
		TcpServer ts = new TcpServer();
		ts.main();
	}

}

