package ieexp3.id190441091;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class RobotControlService3 extends Service<Void>{
	private String[] holes;
	
	public void setHoles(String[] holes) {
		this.holes=holes;
	}
	
	@Override
	protected Task<Void> createTask(){
		return new RobotControlTask3(holes);
	}
}
