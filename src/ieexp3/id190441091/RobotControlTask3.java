package ieexp3.id190441091;

import ieexp3.library.CaoAPI;
import javafx.concurrent.Task;

public class RobotControlTask3 extends Task<Void> {
	private String[] holes;
	volatile private boolean threadStatus;

	public RobotControlTask3(String[] holes) {
		this.holes = holes;
	}

	@Override
	protected Void call() throws Exception {
		try {
			threadStatus = false;

			if (BasicTask3Controller.operation == "connect") {
				connect();

				threadStatus = true;
				BasicTask3Controller.operation = "no operation";
				System.out.println("Connected completely.");
				updateMessage("State:Connected.");
			}

			while (threadStatus) {
				switch (BasicTask3Controller.operation) {
				case "run":
					System.out.println("Start!");
					process();

					BasicTask3Controller.operation = "no operation";
					updateMessage("State:Run.");
					break;

				case "disconnect":
					disconnect();

					threadStatus = false;
					System.out.println("Disconnected completely.");
					BasicTask3Controller.operation = "no operation";
					updateMessage("State:Disconnected");
					break;

				default:
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.out.println("Service restart.");
		}

		return null;
	}

	private void connect() throws Exception {
		// CAOエンジンの初期化
		CaoAPI.init("TestWorkspace");
		System.out.println("CAO engine is initialized.");

		// コントローラに接続
		CaoAPI.connect("RC8", "VE026A");
		System.out.println("Controller and Robot are connected.");

		// 自動モードに設定
		CaoAPI.setControllerMode(CaoAPI.CONTROLLER_MODE_AUTO);
		System.out.println("Operation mode is set to Auto mode.");

		// モータを起動
		CaoAPI.turnOnMotor();
		System.out.println("Motor is turned on.");

		// ロボットの外部速度/加速度/減速度を設定
		float speed = 50.0f, accel = 25.0f, decel = 25.0f;
		CaoAPI.setExtSpeed(speed, accel, decel);
		System.out.println(
				"External speed/acceleration/deceleration is set to " + speed + "/" + accel + "/" + decel + ".");
	}

	/**
	 * ロボットを制御するコマンドを記述する。
	 * 
	 * @throws Exception ロボットの制御に失敗した場合
	 */
	private void process() throws Exception {
		// ロボット操作

		// TakeArm Keep = 0
		CaoAPI.takeArm(0L, 0L);

		// Speed 100
		CaoAPI.speed(-1L, 100.0f);

		// Approach P, P5, @0 50
		// Move L, @0 P5, S = 50
		// DriveA (7, F1)
		// Depart L, @P 50
		CaoAPI.approach(1L, "P" + holes[0], "@0 50", "");
		CaoAPI.move(2L, "@0 P" + holes[0], "S = 50");
		CaoAPI.driveAEx("(7, -45)", "");
		CaoAPI.depart(2L, "@P 50", "");

		// Approach P, P5, @0 50
		// Move L, @0 P5, S = 50
		// DriveA (7, F1)
		// Depart L, @P 50
		CaoAPI.approach(1L, "P" + holes[1], "@0 50", "");
		CaoAPI.move(2L, "@0 P" + holes[1], "S = 50");
		CaoAPI.driveAEx("(7, 25)", "");
		CaoAPI.depart(2L, "@P 50", "");

		// Approach P, P5, @0 50
		// Move L, @0 P5, S = 50
		// DriveA (7, F1)
		// Depart L, @P 50
		CaoAPI.approach(1L, "P" + holes[1], "@0 50", "");
		CaoAPI.move(2L, "@0 P" + holes[1], "S = 50");
		CaoAPI.driveAEx("(7, -45)", "");
		CaoAPI.depart(2L, "@P 50", "");

		// Approach P, P5, @0 50
		// Move L, @0 P5, S = 50
		// DriveA (7, F1)
		// Depart L, @P 50
		CaoAPI.approach(1L, "P" + holes[2], "@0 50", "");
		CaoAPI.move(2L, "@0 P" + holes[2], "S = 50");
		CaoAPI.driveAEx("(7, 25)", "");
		CaoAPI.depart(2L, "@P 50", "");

		CaoAPI.giveArm();
	}

	private void disconnect() throws Exception {
		// モータを停止
		CaoAPI.turnOffMotor();
		System.out.println("Moter is turned off.");

		// コントローラから切断
		CaoAPI.disconnect();
		System.out.println("Controller and Robot is disconnected.");
	}
}
