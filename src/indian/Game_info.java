package indian;

public class Game_info {
	void intro(){
		while(true){
		System.out.println("원하는 설명을 골라주세요.\n"
		+ "1 . 설명나가기\n"
		+ "2 . 인디언 포커란 뭐죠?\n"
		+ "3 . 플레이는 어떻게하요?\n"
		+ "4 . 승리공략\n");
		int input = Input.input(4);
			if(input == 1) break;
			switch(input){  //4개중 선택
			
			case 2:
				info1();
				break;
			case 3:
				info2();
				break;
			case 4:
				info3();
				break;
			}
		}
	}
	
	void info1(){
		System.out.println("---인디언 포커 소개---\n"
				
		+ "인디언 포커란 더 지니어스라는 tv 프로그램에 나온\n"
		+ "인디언들의 전통게임을 포커와 합쳐 만든 심리전 게임입니다.\n");
	}
	
	void info2(){
		System.out.println("---플레이 방식---\n"
		+ "10장의 카드덱 2세트가 섞여있는 카드덱에서\n"
		+ "서로 한장을 뽑아 자신은 보지않고 상대만 보여줍니다.\n"
		+ "숫자가 큰카드를 가지고 있는사람이 이깁니다.\n"
		+ "카드를 보여주고 난뒤에는 배팅을해야하는데\n"
		+ "배팅을 포기할수있고 포기했을경우 자동으로 1개씩 배팅됩니다.\n"
		+ "선배팅인 플레이어가 배팅을하면 후배팅인 플레이어가 배팅에 콜하거나 죽습니다.\n"
		+ "콜하면 선배팅 플레이어가 배팅한만큼 똑같이 배팅하게됩니다.\n");
	}
	
	void info3(){
		System.out.println("---팁---\n"
		+ "상대의 숫자가 클경우 높은수를 배팅하지않거나 배팅을 포기하는것이 좋습니다.\n"
		+ "");
	}
}