
public class Predictor2400 extends Predictor {
	
	Table t;
	Register bhr;

	public Predictor2400() {
		t = new Table(1024,2);
		bhr = new Register(10);
		for(int i=0;i<10;i++) bhr.setBitAtIndex(i,false);
		for(int j=0;j<1024;j++) t.setInteger(j,0,1,1);
	}


	public void Train(long address, boolean outcome, boolean predict) {
		int effective_address = ((int)(address%1024))^bhr.getInteger(0,9);
		bhr.setInteger(0,9,bhr.getInteger(0,9)/2);
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
		int effective_address = ((int)(address%1024))^bhr.getInteger(0,9);
		int counter = t.getInteger(effective_address,0,1);
		if(counter==0||counter==1){
			return false;
		}
		else{
			return true;
		}
	}

}
