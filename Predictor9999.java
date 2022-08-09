
public class Predictor9999 extends Predictor {
	
	Table t;
	Register bhr;

	public Predictor9999() {
		t = new Table(4096,2);
		bhr = new Register(12);
		for(int i=0;i<12;i++) bhr.setBitAtIndex(i,false);
		for(int j=0;j<4096;j++) t.setInteger(j,0,1,1);
	}


	public void Train(long address, boolean outcome, boolean predict) {
		int effective_address = ((int)(address%4096))^bhr.getInteger(0,11);
		bhr.setInteger(0,11,bhr.getInteger(0,11)/2);
		if(outcome==true) bhr.setBitAtIndex(0,true);
		int counter = t.getInteger(effective_address,0,1);
		if(outcome==false&&counter!=0){
			t.setInteger(effective_address,0,1,counter-1);
		}
		else if(outcome==true&&counter!=3){
			t.setInteger(effective_address,0,1,counter+1);
		}
	}


	public boolean predict(long address){
		int effective_address = ((int)(address%4096))^bhr.getInteger(0,11);
		int counter = t.getInteger(effective_address,0,1);
		if(counter==0||counter==1){
			return false;
		}
		else{
			return true;
		}
	}

}
