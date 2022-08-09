
public class Predictor6400 extends Predictor {
	
	Table t;
	Register bhr;

	public Predictor6400() {
		t = new Table(2048,2);
		bhr = new Register(11);
		for(int i=0;i<11;i++) bhr.setBitAtIndex(i,false);
		for(int j=0;j<2048;j++) t.setInteger(j,0,1,1);
	}


	public void Train(long address, boolean outcome, boolean predict) {
		int effective_address = ((int)(address%2048))^bhr.getInteger(0,10);
		bhr.setInteger(0,10,bhr.getInteger(0,10)/2);
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
		int effective_address = ((int)(address%2048))^bhr.getInteger(0,10);
		int counter = t.getInteger(effective_address,0,1);
		if(counter==0||counter==1){
			return false;
		}
		else{
			return true;
		}
	}

}
