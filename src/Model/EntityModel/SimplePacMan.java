package Model.EntityModel;

import java.util.logging.Level;
import java.util.logging.Logger;

import Model.Enumerate.Move;

public class SimplePacMan extends EntityModel {
	private int sizeX, sizeY, score, nbDeath;
	private int frameDeath = 1;
	private long timerEating, timerDeath;
	private Grid grid;
	private Move d, dBuffer;
	private Ghost g;
	private boolean start, GhostEating, Dead, gameOver, victory, reset;
	private int counterBeans, counterPacGom;

	public SimplePacMan(int _sizeX, int _sizeY) {
		sizeX = _sizeX;
		sizeY = _sizeY;
		score = 0;
		nbDeath = 0;
		d = null;
	}

	public void start() {
		new Thread(this).start();
	}

	public void sleep(int s) {
		try {
			Thread.sleep(s);
		} catch (InterruptedException e) {
			Logger.getLogger(SimplePacMan.class.getName()).log(Level.SEVERE, null, e);
		}
	}

	public Grid getGrid() {
		return grid;
	}

	public void setGrid(Grid Grid) {
		this.grid = Grid;
	}

	public void setMove(Move d) {
		this.d = d;
	}

	public Move getMove() {
		return d;
	}

	public Move getdBuffer() {
		return dBuffer;
	}

	public void setdBuffer(Move dBuffer) {
		this.dBuffer = dBuffer;
	}

	public boolean isStart() {
		return start;
	}
	public void setStart(boolean start) {
		this.start = start;
	}
	
	public int getScore() {
		return score;
	}

	public void setScore(int points) {
		score += points;
	}

	public void setnbDeath(int nb) {
		nbDeath += nb;
	}

	public int getnbDeath() {
		return nbDeath;
	}

	public boolean isDead() {
		return Dead;
	}

	public boolean isReset() {
		return reset;
	}

	public void setReset(boolean reset) {
		this.reset = reset;
	}

	public void setDead(boolean Dead) {
		this.Dead = Dead;
	}

	public long getTimerDeath() {
		return timerDeath;
	}

	public void setTimerDeath(long timerDeath) {
		this.timerDeath = timerDeath;
	}

	public int getFrameDeath() {
		return frameDeath;
	}

	public void setFrameDeath(int frameDeath) {
		this.frameDeath = frameDeath;
	}

	public boolean isGameOver() {
		return gameOver;
	}

	public void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}

	public boolean isGhostEating() {
		return GhostEating;
	}

	public void setGhostEating(boolean GhostEating) {
		this.GhostEating = GhostEating;
	}

	public boolean getvictory() {
		return victory;
	}

	@Override
	public void run() {
		while (true) {
			realiserAction();
			setChanged();
			notifyObservers(); // notification de l'observer
			grid.movePacMan();
			grid.pacmanCollision();
			try {
				Thread.sleep(225);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public void realiserAction() {
		
		if (grid.getbonus()[(int) grid.getMap().get(this).getX()][(int) grid.getMap().get(this).getY()].isBean()) {
			score += 1;
			grid.setEmptyBonus((int) grid.getMap().get(this).getX(), (int) grid.getMap().get(this).getY());
			counterBeans++;
		}

		if (grid.getbonus()[(int) grid.getMap().get(this).getX()][(int) grid.getMap().get(this).getY()].isPacGom()) {
			score += 10;
			grid.setEmptyBonus((int) grid.getMap().get(this).getX(), (int) grid.getMap().get(this).getY());
			timerEating = System.currentTimeMillis();
			GhostEating = true;
			counterPacGom++;
		}
		if (counterBeans == 296 && counterPacGom == 4) {
			victory = true;
		}

		if (GhostEating) {
			if (System.currentTimeMillis() - timerEating > 6000) {
				GhostEating = false;
				timerEating = 0;
			}
		}
		if(reset) {
			//System.out.println(frameDeath);
			frameDeath++;
			if(frameDeath >=10) {
				frameDeath = 0;
				reset = false;
			}
		}	
		
		if(Dead) {
			if(!reset) {
				grid.getMap().get(this).setLocation(sizeX/2 - 1,sizeY/2 + 2);
				grid.setResetGame(true);
				Dead = false;
			}			
		}
		
		if (nbDeath == 3) {
			gameOver = true; // game over
		}
	}
}
