package indian;

import java.util.Scanner;

public class GameControl {
	static int pMoney;
	static int aMoney;
	int pBet;
	int aBet;
	int bettingSum;
	static boolean turnPass = false;  //선배팅 ai나 플레이어가 죽었을경우 콜다이를 패스하기위해
	static boolean first = true;  //플레이어 선일 true
	
	GameControl(int pMoney, int aMoney){  //생성자
		GameControl.pMoney = pMoney; 
		GameControl.aMoney = aMoney;
	}
	
	void playerTurn(){
		Scanner scan = new Scanner(System.in);
		if(first == true){
			System.out.println("선배팅입니다 ! ");
			//apMoney();  //가진 칩 수
			System.out.printf("상대 카드는 %s %d 입니다.\n",
					(Card.card[Card.cardCnt+1].cardName == 0?"스페이드":"하트"),
					Card.card[Card.cardCnt+1].cardNum);
			System.out.println("1 . 배팅한다. (칩1개 이상 배팅)");
			System.out.println("2 . 죽는다. (칩1개 배팅후 현재턴 패배)");
			switch(Input.input(2)){
			case 1:  //배팅시
				while(true){
					System.out.print("배팅액 입력 >> ");
					String sTmp = scan.next(); 
					try{
						pBet = Integer.parseInt(sTmp);
					}catch(Exception e){
						System.out.println("보기에 있는것 안에서 입력해주세요.");
						continue;
					}
					
					if(pBet == 0){
						System.out.println("0개는 배팅할수없습니다.");
					}else if(pBet > pMoney){
						System.out.printf("칩이 부족합니다 (당신의칩 %d개)\n",pMoney);
					}else if(pBet > aMoney){
						System.out.printf("상대가 가진 칩보다 많이 배팅할수없습니다.(상대가 가진 칩%d개)\n",aMoney);
					}else{  //배팅 완벽한 성공
						pMoney -= pBet;  //돈에서 배팅액만큼 차감
						System.out.printf("%d개를 배팅하였습니다.\n",pBet);
						break;
					}
				}
				//조건확인  --------
				break;
			case 2:  //죽을시
				System.out.println("죽었습니다 당신의 칩이 1개 차감되고 상대의 칩이 1개 증가합니다.");
				System.out.printf("당신의 카드는 %s %d 였습니다.\n",
						(Card.card[Card.cardCnt].cardName == 0?"스페이드":"하트"),
						Card.card[Card.cardCnt].cardNum);
				pMoney --;  				//1차감
				aMoney += (1+bettingSum);	//내배팅액+모인배팅액 몰아주기 (내가선이기문에 상대배팅액이없음)
				turnPass = true;
				//여기에서 확인
				break;
			}
			
		}else if(turnPass == false && first == false){
			System.out.println("후배팅입니다 ! ");
			//apMoney();
			System.out.printf("상대 카드는 %s %d 입니다.\n",
					(Card.card[Card.cardCnt+1].cardName == 0?"스페이드":"하트"),
					Card.card[Card.cardCnt+1].cardNum);
			System.out.println("1 . 콜한다. (상대의 배팅과 같은 칩수만큼 건다)");
			System.out.println("2 . 죽는다. (칩 1개 배팅후 현재턴 패배)");
			switch(Input.input(2)){
			case 1:  //콜일시
				pMoney -= aBet;  //상대 배팅액만큼 차감
				pBet = aBet;
				//죽지않았을 경우에는 조건확인을 해줍시다!!!!!!!!!!!!!
				outCome();
				break;
			case 2:  //죽을시
				System.out.printf("당신의 카드는 %s %d 이였습니다.\n",
						(Card.card[Card.cardCnt].cardName == 0?"스페이드":"하트"),
						Card.card[Card.cardCnt].cardNum);
				pMoney --;  				//1차감
				aMoney += (1+aBet+bettingSum);	//내배팅액+상대배팅액+모인배팅액 몰아주기
				betReset();  //배팅 리셋
				break;
			}
		}
	}
	
	void aiTurn(){
		Rand betRand = new Rand();  //지능적인 랜덤 배팅 컨트롤
		if(turnPass == false && first == true){  //Ai 후배팅
			if(betRand.aiCall() == true){  //콜일시
				System.out.println("상대가 이번배팅에 콜 했습니다.");
				aMoney -= pBet;  //플레이어가 배팅한것만큼 뺌
				aBet = pBet;  //p가 배팅한것만큼 넣는다
				outCome();
			}else{
				System.out.println("상대가 이번배팅에서 죽었습니다.");
				System.out.printf("당신의 카드는 %s %d 입니다.\n",
						(Card.card[Card.cardCnt].cardName == 0?"스페이드":"하트"),
						Card.card[Card.cardCnt].cardNum);
				aMoney --;  				//상대 1차감
				pMoney += (1+pBet+bettingSum);	//상대배팅액+내배팅액+모인배팅액 몰아주기
				betReset();  //배팅 리셋
				
			}
		}else if(first == false){  //Ai 선배팅
			if(betRand.aiCall() == true){  //배팅하기
				int nTmp = betRand.aiBet();
				System.out.println("상대가 "+nTmp+"개 배팅하였습니다.");
				aBet = nTmp;
				aMoney -= aBet;
			}else{  //배팅포기
				System.out.printf("상대의 카드는 %s %d 입니다.\n",
						(Card.card[Card.cardCnt+1].cardName == 0?"스페이드":"하트"),
						Card.card[Card.cardCnt+1].cardNum);
				System.out.println("상대가 배팅을 포기하였습니다.");
				System.out.printf("당신의의 카드는 %s %d 였습니다.\n",
						(Card.card[Card.cardCnt].cardName == 0?"스페이드":"하트"),
						Card.card[Card.cardCnt].cardNum);
				aMoney --;  				//1차감
				pMoney += (1+bettingSum);	//내배팅액+모인배팅액 몰아주기 (내가선이기문에 상대배팅액이없음)
				turnPass = true;
			}
		}
	}
	
	int dieCheak(){
		if(pMoney == 0 && aMoney == 0){
			System.out.println("당신과 상대 둘다 칩이 없습니다 둘다 파산햇습니다.");
			System.out.println("-------- GameOver --------");
			return -1;
		}else if(pMoney == 0){  //패배여부 확인후 메인복귀
			System.out.println("더이상 칩이 없습니다. 당신은 파산했습니다...");
			System.out.println("-------- GameOver --------");
			return 1;
		}else if(aMoney == 0){
			System.out.println("상대의 칩이 없습니다. 당신은 승리했습니다!!!");
			System.out.println("-------- GameOver --------");
			return 2;
		}else{
			return 0;  //순조로운 진행일때
		}
	}
	
	void apMoney() {  //당신과 상대의 칩 출력
		System.out.printf("-------------------------\n"
						+ "당신이 가진 칩의 개수 : %d\n"
				  		+ "상대가 가진 칩의 개수 : %d\n"
						+ "-------------------------\n"
				  			,pMoney,aMoney);
	}
	
	void outCome(){  //턴이돈뒤 결과
		//플레이어 					//상대
		if(Card.card[Card.cardCnt].cardNum > Card.card[Card.cardCnt+1].cardNum){
			System.out.printf("플레이어의 카드는 %s %d 였습니다.\n",
					(Card.card[Card.cardCnt].cardName == 0?"스페이드":"하트"),
					Card.card[Card.cardCnt].cardNum);
			System.out.println("승리하였습니다.");
			pMoney += pBet;  //플레이어 배팅
			pMoney += aBet;  //ai 배팅
			pMoney += bettingSum;  //모인 합계도 넣기
			bettingSum = 0;  //모인 합계 초기화
		}else if(Card.card[Card.cardCnt].cardNum < Card.card[Card.cardCnt+1].cardNum){
			System.out.printf("플레이어의 카드는 %s %d 였습니다.\n",
					(Card.card[Card.cardCnt].cardName == 0?"스페이드":"하트"),
					Card.card[Card.cardCnt].cardNum);
			System.out.println("패배하였습니다.");
			aMoney += aBet;  //ai 배팅 가져오기
			aMoney += pBet;  //플레이업 배팅 가져오기
			aMoney += bettingSum;
			bettingSum = 0;
		}else{  //같을경우
			System.out.printf("플레이어의 카드는 %s %d 였습니다.\n",
					(Card.card[Card.cardCnt].cardName == 0?"스페이드":"하트"),
					Card.card[Card.cardCnt].cardNum);
			System.out.println("비겼습니다.");
			bettingSum += pBet;
			bettingSum += aBet;
		}
	}
	
	void betReset(){  //서로의 배팅 초기화.
		aBet = 0;
		pBet = 0;
	}
	
}