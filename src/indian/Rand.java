package indian;

import java.util.Random;

public class Rand {
	boolean percent(int per){  //퍼센트를 받아 true 혹은 false로 리턴
		Random rand = new Random();
		int nTmp = rand.nextInt(100)+1;
		return (nTmp >= per ? true : false);
	}
	
	int aiBet() {  //배팅 지능 컨트롤 (ai 선배팅일때)
		Random rand = new Random();
		int nTmp = 0;
//		if(GameControl.first == true){ //내가선일때
//			
//		}
		//--------------임시----------------
		nTmp = rand.nextInt(GameControl.pMoney > GameControl.aMoney ? GameControl.aMoney : GameControl.pMoney)+1;  //내가 가진 돈보다 많이 배팅할수 없음.
		//--------------임시----------------
		
		return nTmp;
	}
	
	boolean aiCall(){  //콜 다이 컨트롤 (ai 후배팅일때)
		//임시 -----------
		return percent(50);
	}
	
}