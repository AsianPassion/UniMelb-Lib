
/**
 * Stores information of each wave
 *
 */
public class Wave {

	private int waveNum;
	private String type;
	private int number;
	private String enemyType;
	private int delay;
	
	public Wave(int waveNum, String type, int number, String enemyType, int delay) {
		this.waveNum = waveNum;
		this.type = type;
		this.number = number;
		this.enemyType = enemyType;
		this.delay = delay;
	}
	
	public Wave(int waveNum, String type, int delay) {
		this.waveNum = waveNum;
		this.type = type;
		this.delay = delay;
	}

	public int getWaveNum() {
		return waveNum;
	}

	public String getType() {
		return type;
	}

	public int getNumber() {
		return number;
	}

	public String getEnemyType() {
		return enemyType;
	}

	public int getDelay() {
		return delay;
	}
	

}
