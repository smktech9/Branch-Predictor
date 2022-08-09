
public class Predictor32000 extends Predictor {
	
	Table t;
	Register bhr;

	public Predictor32000() {
		t = new Table(8192,2);
		bhr = new Register(13);
		for(int i=0;i<13;i++) bhr.setBitAtIndex(i,false);
		for(int j=0;j<8192;j++) t.setInteger(j,0,1,1);
	}


	public void Train(long address, boolean outcome, boolean predict) {
		int effective_address = ((int)(address%8192))^bhr.getInteger(0,12);
		bhr.setInteger(0,12,bhr.getInteger(0,12)/2);
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
		int effective_address = ((int)(address%8192))^bhr.getInteger(0,12);
		int counter = t.getInteger(effective_address,0,1);
		if(counter==0||counter==1){
			return false;
		}
		else{
			return true;
		}
	}

}
