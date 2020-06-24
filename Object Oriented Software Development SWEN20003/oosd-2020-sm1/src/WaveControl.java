import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
import java.util.function.Consumer;

import bagel.util.Point;

/**  The wave events are processed in order until there are no wave 
 * events left to process. When there are no events to process for 
 * the current wave, and there are no enemies left on the map, 
 * the wave is considered finished and the player is to be awarded 
 * $150+wave¡Á$100 where wave is the wave number that just finished
 * 
 * Customisation:
 * 1. a wave is processed immediately after previous wave without waiting
 *    time (wait the previous wave to be finished)
 * 2. rawards are given when a new wave is processed
 * 3. rewards calculation is changed to $200 * waveNum
 *
 */
public class WaveControl {

	private Queue<Wave> waveQueue;
	private int delayTimer;
	private int currentWave;
	private boolean levelEnd;
	
	private static final String WAVE_FILE = "res/levels/waves.txt";
	private static final int REWARD = 200;
	
	
	public WaveControl() {
		waveQueue = new LinkedList<Wave>();
		delayTimer = 0;
		currentWave = 0;
		levelEnd = false;
	}

	
	public void readWaves() {
		try {
			File waves = new File(WAVE_FILE);
			Scanner reader = new Scanner(waves);
			while (reader.hasNextLine()) {
				String data = reader.nextLine();
				String[] elements = data.split(",");
				if (elements[1].equals("delay")) {
					waveQueue.add(new Wave(Integer.parseInt(elements[0]), 
							elements[1], 
							Integer.parseInt(elements[2])));
				}else {
					waveQueue.add(new Wave(Integer.parseInt(elements[0]), 
							elements[1], 
							Integer.parseInt(elements[2]), 
							elements[3], 
							Integer.parseInt(elements[4])));
				}
				
			}
			reader.close();
		} catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}
	
	public void applyWave(Level lv) {
		if (isDelaying() || levelEnd) {
			if (waveQueue.size() == 0) {
				levelEnd = true;
			}
			return;
		}
		Map<String, Consumer<Integer>> actionMaps = 
				new HashMap<String, Consumer<Integer>>();
		Wave temp = waveQueue.poll();
		currentWave = temp.getWaveNum();
		
		lv.increaseMoney((currentWave - 1) * REWARD);
		
		if (temp.getType().equals("delay")) {
			delayTimer = temp.getDelay() * 60 / 1000;
		}else {
			List<Point> route = lv.getMap().getAllPolylines().get(0);
			actionMaps.put("slicer", 
					(t) -> {lv.addSprite(
							new Slicer(route, t * temp.getDelay()));});
			actionMaps.put("superslicer", 
					(t) -> {lv.addSprite(
							new SuperSlicer(route, t * temp.getDelay()));});
			actionMaps.put("megaslicer", 
					(t) -> {lv.addSprite(
							new MegaSlicer(route, t * temp.getDelay()));});
			actionMaps.put("apexslicer", 
					(t) -> {lv.addSprite(
							new ApexSlicer(route, t * temp.getDelay()));});
			if (temp.getType().equals("spawn")) {
				for (int i=0; i<temp.getNumber(); i++) {
					actionMaps.get(temp.getEnemyType()).accept(i);
				}
			}
			delayTimer = temp.getDelay() * temp.getNumber() * 60 / 1000;
		}
		StatusPanel.getInstance().setInProgress();
	}
	
	public void update(int timeScaler) {
		delayTimer = delayTimer > 0 ? delayTimer - timeScaler : 0;
	}
	
	public boolean isDelaying() {
		return delayTimer > 0;
	}


	public boolean isLevelEnd() {
		return levelEnd;
	}


	public int getCurrentWave() {
		return currentWave;
	}
	
	
	
	
}
