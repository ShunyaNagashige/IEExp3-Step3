package ieexp3.id190441091;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class BasicTask3Controller {
	@FXML private Button buttonConnect;
	@FXML private Button buttonDisconnect;
	@FXML private Button buttonRun;
	@FXML private TextField textField1;
	@FXML private TextField textField2;
	@FXML private TextField textField3;
	@FXML private Label labelState;

	private RobotControlService3 service;
	
	//メインスレッドは変数を共有できる(ってことは，この変数はヒープ領域にある？というのも，スレッド間で共有できるメモリはヒープ領域のはずだから。)
	public static String operation;
	
	private final int holeNum=3;

	@FXML
	protected void handleButtonConnectAction(ActionEvent event) {
		try {			
			String[] holes = new String[holeNum];
			TextField[] textFields = {textField1,textField2,textField3};

			for(int i=0;i<holeNum;i++) {
				holes[i] = textFields[i].getText();
			}
			
			for (String hole : holes) {
				if (Integer.parseInt(hole) <= 0 || Integer.parseInt(hole) > 6)
					throw new IllegalArgumentException("1から6の数字を入力してください。");
			}
			
			service=new RobotControlService3();
			
			labelState.textProperty().bind(service.messageProperty());

			operation="connect";			
			service.setHoles(holes);
			service.start();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	protected void handleButtonRunAction(ActionEvent event) {
		try {			
			operation="run";
			service.restart();
			
			labelState.textProperty().bind(service.messageProperty());
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	protected void handleButtonDisconnectAction(ActionEvent event) {
		try {
			operation="disconnect";
			service.restart();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
