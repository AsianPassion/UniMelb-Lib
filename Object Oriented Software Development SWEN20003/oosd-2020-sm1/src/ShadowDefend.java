import bagel.AbstractGame;
import bagel.Input;
import bagel.Keys;

public class ShadowDefend extends AbstractGame {
	
	private static final int INIT_LV = 1;
	private static final int MAX_LV = 2;
	
	private Level level;
	private int levelNum;

    /**
     * Entry point for Bagel game
     *
     */
    public static void main(String[] args) {
        // Create new instance of game and run it
        new ShadowDefend().run();
    }

    /**
     * Setup the game
     */
    public ShadowDefend() {
    	levelNum = INIT_LV;
    	level = new Level(levelNum);
    }
    
    /**
     * Updates the game state approximately 60 times a second
     *
     */
    @Override
    protected void update(Input input) {
    	if (input.wasReleased(Keys.S)) {
        	level.start();
        }
    	if (input.wasReleased(Keys.L)) {
        	level.increaseTimescale();
        }
    	if (input.wasReleased(Keys.K)) {
        	level.decreaseTimescale();
        }
    	if (input.wasReleased(Keys.P)) {
        	level.superPower();
        }


    	level.update(input);
    	
    	if (level.isGameOver()) {
    		// game over, reset current level
    		level = new Level(levelNum);
    	}
    	
    	if (level.isLevelOver()) {
    		levelNum = levelNum + 1;
    		if (levelNum <= MAX_LV) {
    			level = new Level(levelNum);
    		}else {
    			// game over, winner
    			StatusPanel.getInstance().setWinner();
    		}
    		
    	}
    }

}