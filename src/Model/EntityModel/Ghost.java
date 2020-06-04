package Model.EntityModel;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import Model.Enumerate.Move;




public class Ghost extends EntityModel{
	private int sizeX, sizeY , timerEating;
	private long timerDeath;
    private Move d, dBuffer;
    private Random r = new Random();
    private Grid grid;
    private int timer;
    private boolean  depAleatoire, dead , invulnaribility , pacmanDeath, spawn;
    private SimplePacMan spm;
    private long timerSpawn;
    private String color;
   
    private static boolean start;
    
    public Ghost(int _sizeX, int _sizeY, SimplePacMan spm, boolean depAleatoire, String color) {
        
    	sizeX = _sizeX;
        sizeY = _sizeY;
        spawn = true;
        this.spm = spm;
        this.depAleatoire = depAleatoire;
        invulnaribility = false;
        this.color = color;
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
	
	public Move getMove() {
		return d;
	}
	
	public void setMove(Move d) {
		this.d = d;
	}
		
	public boolean isDead() {
		return dead;
	}
	
	public boolean ispacmanDeath() {
		return pacmanDeath;
	}
	public long getTimerSpawn() {
		return timerSpawn;
	}

	public void setTimerSpawn(long timerSpawn) {
		this.timerSpawn = timerSpawn;
	}
	
	
	public boolean isSpawn() {
		return spawn;
	}
	
	public void setSpawn(boolean b) {
		spawn = b;
	}

	public void run() {
		while(true) {
			
			realiserAction();
			setChanged();
			notifyObservers();
			grid.moveGhost(this);
			grid.collision(this);
			try {
				if(dead) Thread.sleep(120); 
				else Thread.sleep(225); //pause
	        } catch (InterruptedException ex) {
	            Logger.getLogger(SimplePacMan.class.getName()).log(Level.SEVERE, null, ex);
	        }
		}
	}
	@Override
    void realiserAction() {
		if(dead) {
			if(System.currentTimeMillis() - timerDeath > 3000 && !spm.isGhostEating()) {
				dead = false;
				invulnaribility = false;
				d = Move.UP;
				timerDeath = 0;
			}
		}
	}
	
	

	public void setD(Move d) {
		this.d = d;
	}

	public void setdBuffer(Move dBuffer) {
		this.dBuffer = dBuffer;
	}
	
	public Move getdBuffer() {
		return dBuffer;
	}

	public boolean getStart() {
		
		return start;
	}

	public boolean getDelAleatoire() {
		
		return depAleatoire;
	}

	public boolean isInvulnaribility() {
		
		return invulnaribility;
	}

	public void setDead(boolean b) {
		// TODO Auto-generated method stub
		dead = b;
	}

	public void setTimerDeath(long currentTimeMillis) {
		timerDeath = currentTimeMillis;
		
	}

	public void setInvulnaribility(boolean b) {
		invulnaribility = b;
		
	}	
}
