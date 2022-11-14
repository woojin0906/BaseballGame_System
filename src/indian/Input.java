package indian;

import java.util.Scanner;

public class Input {
	static int input(int num){  //입력받을 개수를 정함
		Scanner scan = new Scanner(System.in);
		int input = 0;
		String sTmp = "";
		while(true){
			System.out.print("입력 >>  ");
			sTmp = scan.next();
			try{
				input = Integer.parseInt(sTmp);
			}catch(Exception e){
				System.out.println("보기에 있는것에서 입력해주세요.");
				continue;
			}
			for(int i=1;i<=num;i++){  // 받는거 리턴
				if(input == i){
					return i;
				}
			}
			System.out.println("보기에 있는것만 입력해주세요");
		}
	}
}