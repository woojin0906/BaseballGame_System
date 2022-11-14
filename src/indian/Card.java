package indian;

import java.util.Random;

public class Card {

	public static Icard card[] = new Icard[20];  //카드구조체 배열
	static int cardCnt = 0;  //현재 까진 카드 번호
	
	void shake_card(){  //초기화와 스왑 같이함
		System.out.println("카드를 섞습니다.");
		Random rand = new Random();
		Icard temp;  //임시 구조체 만듬
		int i,r; //r은 렌덤값
		for(i=0;i<20;i++){  //차례대로 담기
			card[i] = new Icard();
			card[i].cardNum = i%10+1;  //10 경우
			card[i].cardName = i/10; //2  경우
		}
		for(i=0;i<20;i++){  //스왑 20번
			r = rand.nextInt(20);  
			temp = card[i];
			card[i] = card[r];
			card[r] = temp;
		}
	}
	
}
class Icard{  //카드 1장 구조체
	int cardName; //0이면 스페이드 1이면 하트
	int cardNum;
}