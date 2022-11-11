package game;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JFrame;

public class ServerUi{
	int port;
	private ArrayList<ServerThread> threadList = new ArrayList<>();
	private ServerThread serverThread; //클라이언트와 통신할 서버소켓
	private ServerSocket serverSocket = null;
	private Socket socket = null;
	int count = 0;
	public ServerUi(int port) {
		this.port=port;
	}
	
	public void start() {
		
		 try{
	    	  
	          serverSocket = new ServerSocket(port);
	          System.out.println("연결 기다리는 중....");
	          
	          while(true){ 
	        	 
	            	   socket = serverSocket.accept(); // 클라이언트의 접속을 기다린다.
	            	   System.out.println("연결 성공");
	            	   
	            	   serverThread = new ServerThread(socket, threadList); 
	            	   threadList.add(serverThread);
	            	   serverThread.start();
	          }   
	        } catch(IOException e){
	           // 서버가 정상적으로 동작하지 않는 경우를 처리한다.
	        	e.printStackTrace();
	        } finally {
	        	try {
	        		serverSocket.close();
	        	} catch (IOException e) {
	        		System.out.println("오류 발생");
	        	}
		 }
	}

}
