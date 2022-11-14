package indian;

public class IndianPoker {

	static int pMoney=0;  //초기설정
	static int aMoney=0;
	
	public static void main(String[] args) {
		choose_start();
	}
	
	static void choose_start(){
		Game_info Info = new Game_info();
		while(true){
			System.out.println("------인디언 포커입니다------");
			System.out.println("1 . 게임을 시작합니다.");
			System.out.println("2 . 게임설명을봅니다");
			System.out.println("3 . 게임을 종료합니다.");
			switch(Input.input(3)){
			case 1:
				System.out.println("1. 스텐다드 모드 분배(50/50)");
				System.out.println("2. 이지 모드(100/50)");
				System.out.println("3. 하드 모드(50/150)");
				System.out.println("4. 커스텀 모드(설정)");
				switch(Input.input(4)){
				case 1:
					pMoney = 50;
					aMoney = 50;
					break;
				case 2:
					pMoney = 100;
					aMoney = 50;
					break;
				case 3:
					pMoney = 50;
					aMoney = 100;
					break;
				case 4:
					System.out.println("플레이어칩 1~500");
					pMoney = Input.input(500);
					System.out.println("상칩 1~500");
					aMoney = Input.input(500);
					break;
				}
				System.out.println("게임을 시작합니다.");
				game_board();
				break;
			case 2:
				System.out.println("게임설명을봅니다");
				Info.intro();
				break;
			case 3:
				System.out.println("게임을 종료합니다.");
				System.exit(0);
			}
		}
	}
	
	static void game_board(){  //게임판
		GameControl gc = new GameControl(pMoney,aMoney);  
		//게임 컨트롤러 선언 (첫인자 내돈 둘째 인자 상대돈)
		Card card = new Card();  //카드 선언
		card.shake_card();  //카드 섞기
		int turn = 0;
		for(turn=0;;turn++){
			System.out.printf("--------%d턴 시작--------\n",turn+1);  //턴 종료창
			gc.apMoney();  //서로의 칩수 중계
			if(GameControl.first == true){  //플레이어선
				gc.playerTurn();	//플레이어턴
				gc.aiTurn();  		//상대턴
				GameControl.first = false;	//순서 바꾸기
			}else if(GameControl.first == false){
				gc.aiTurn();
				gc.playerTurn();
				GameControl.first = true;
			}
			Card.cardCnt += 2;	//카운트 올리기
			if(Card.cardCnt == 20){
				card.shake_card();  //카드섞기
				Card.cardCnt = 0;  //카드 카운트 초기화
			}
			
			GameControl.turnPass = false;  	//후배팅 패스 초기화
			//조건확인
			if(gc.dieCheak() != 0){  //누군가가 죽으면 ..
				break;
			}
		}
		System.out.println((turn+1)+"턴 만에 게임이 끝났습니다!");
	}
	
}