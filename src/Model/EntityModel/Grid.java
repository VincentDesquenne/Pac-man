package Model.EntityModel;

import java.awt.Point;
import java.beans.Beans;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Random;

import Model.Enumerate.Move;
import Model.StaticModel.*;

public class Grid extends Observable implements Runnable {
	private Empty c = new Empty();
	private Wall m = new Wall();
	private Random r = new Random();
	private int timer;
	private Ghost rouge, bleu, rose, orange;
	private SimplePacMan spm;
	private Spawn s = new Spawn();
	private boolean estDemarre;
	private boolean resetGame = true;
	public Map<EntityModel, Point> map = new HashMap<>();
	private final int SIZE_X = 28;
	private final int SIZE_Y = 31;
	private StaticModel[][] tab = {
			{ m, m, m, m, m, m, m, m, m, m, m, m, m, m, m, m, m, m, m, m, m, m, m, m, m, m, m, m },
			{ m, c, c, c, c, c, c, c, c, c, c, c, c, m, m, c, c, c, c, c, c, c, c, c, c, c, c, m },
			{ m, c, m, m, m, m, c, m, m, m, m, m, c, m, m, c, m, m, m, m, m, c, m, m, m, m, c, m },
			{ m, c, m, m, m, m, c, m, m, m, m, m, c, m, m, c, m, m, m, m, m, c, m, m, m, m, c, m },
			{ m, c, m, m, m, m, c, m, m, m, m, m, c, m, m, c, m, m, m, m, m, c, m, m, m, m, c, m },
			{ m, c, c, c, c, c, c, c, c, c, c, c, c, c, c, c, c, c, c, c, c, c, c, c, c, c, c, m },
			{ m, c, m, m, m, m, c, m, m, c, m, m, m, m, m, m, m, m, c, m, m, c, m, m, m, m, c, m },
			{ m, c, m, m, m, m, c, m, m, c, m, m, m, m, m, m, m, m, c, m, m, c, m, m, m, m, c, m },
			{ m, c, c, c, c, c, c, m, m, c, c, c, c, m, m, c, c, c, c, m, m, c, c, c, c, c, c, m },
			{ m, m, m, m, m, m, c, m, m, m, m, m, c, m, m, c, m, m, m, m, m, c, m, m, m, m, m, m },
			{ m, m, m, m, m, m, c, m, m, m, m, m, c, m, m, c, m, m, m, m, m, c, m, m, m, m, m, m },
			{ m, m, m, m, m, m, c, m, m, c, c, c, c, c, c, c, c, c, c, m, m, c, m, m, m, m, m, m },
			{ m, m, m, m, m, m, c, m, m, c, m, m, m, c, c, m, m, m, c, m, m, c, m, m, m, m, m, m },
			{ m, m, m, m, m, m, c, m, m, c, m, s, s, s, s, s, s, m, c, m, m, c, m, m, m, m, m, m },
			{ c, c, c, c, c, c, c, c, c, c, m, s, s, s, s, s, s, m, c, c, c, c, c, c, c, c, c, c },
			{ m, m, m, m, m, m, c, m, m, c, m, s, s, s, s, s, s, m, c, m, m, c, m, m, m, m, m, m },
			{ m, m, m, m, m, m, c, m, m, c, m, m, m, m, m, m, m, m, c, m, m, c, m, m, m, m, m, m },
			{ m, m, m, m, m, m, c, m, m, c, c, c, c, c, c, c, c, c, c, m, m, c, m, m, m, m, m, m },
			{ m, m, m, m, m, m, c, m, m, c, m, m, m, m, m, m, m, m, c, m, m, c, m, m, m, m, m, m },
			{ m, m, m, m, m, m, c, m, m, c, m, m, m, m, m, m, m, m, c, m, m, c, m, m, m, m, m, m },
			{ m, c, c, c, c, c, c, c, c, c, c, c, c, m, m, c, c, c, c, c, c, c, c, c, c, c, c, m },
			{ m, c, m, m, m, m, c, m, m, m, m, m, c, m, m, c, m, m, m, m, m, c, m, m, m, m, c, m },
			{ m, c, m, m, m, m, c, m, m, m, m, m, c, m, m, c, m, m, m, m, m, c, m, m, m, m, c, m },
			{ m, c, c, c, m, m, c, c, c, c, c, c, c, c, c, c, c, c, c, c, c, c, m, m, c, c, c, m },
			{ m, m, m, c, m, m, c, m, m, c, m, m, m, m, m, m, m, m, c, m, m, c, m, m, c, m, m, m },
			{ m, m, m, c, m, m, c, m, m, c, m, m, m, m, m, m, m, m, c, m, m, c, m, m, c, m, m, m },
			{ m, c, c, c, c, c, c, m, m, c, c, c, c, m, m, c, c, c, c, m, m, c, c, c, c, c, c, m },
			{ m, c, m, m, m, m, m, m, m, m, m, m, c, m, m, c, m, m, m, m, m, m, m, m, m, m, c, m },
			{ m, c, m, m, m, m, m, m, m, m, m, m, c, m, m, c, m, m, m, m, m, m, m, m, m, m, c, m },
			{ m, c, c, c, c, c, c, c, c, c, c, c, c, c, c, c, c, c, c, c, c, c, c, c, c, c, c, m },
			{ m, m, m, m, m, m, m, m, m, m, m, m, m, m, m, m, m, m, m, m, m, m, m, m, m, m, m, m }, };

	private StaticModel[][] bonus = new StaticModel[SIZE_X][SIZE_Y];
	private StaticModel[][] transpo = new StaticModel[SIZE_X][SIZE_Y];

	public Grid(Ghost rouge, Ghost bleu, Ghost rose, Ghost orange, SimplePacMan pacman) {
		this.rouge = rouge;
		this.bleu = bleu;
		this.rose = rose;
		this.orange = orange;
		this.spm = pacman;

		for (int i = 0; i < SIZE_X; i++) {
			for (int j = 0; j < SIZE_Y; j++) {
				transpo[i][j] = tab[j][i];
			}
		}

		for (int i = 0; i < SIZE_X; i++) {
			for (int j = 0; j < SIZE_Y; j++) {
				if (transpo[i][j].isWall() || transpo[i][j].isSpawn()) {
					bonus[i][j] = new Empty();
				} else if ((i == 1 && j == 3) || (i == 1 && j == 23) || (i == 26 && j == 3) || (i == 26 && j == 23)) {
					bonus[i][j] = new PacGom();
				} else {
					bonus[i][j] = new Bean();
				}
			}
		}

	}

	public boolean okDepl(Move d, EntityModel e) {
		Point p = map.get(e);
		if (d != null) {
			switch (d) {
			case LEFT:
				if (p.getX() - 1 < 0) {
					return (transpo[(int) (SIZE_X - 1)][(int) p.getY()].isEmpty());
				} else
					return (transpo[(int) (p.getX() - 1)][(int) p.getY()].isEmpty());

			case RIGHT:
				if (p.getX() + 1 == SIZE_X) {
					return (transpo[0][(int) p.getY()] instanceof Empty);
				} else
					return (transpo[(int) (p.getX() + 1)][(int) p.getY()].isEmpty());

			case UP:
				if (transpo[(int) (p.getX())][(int) p.getY() - 1].isEmpty()) {
					return true;

				}
				break;
			case DOWN:
				if (transpo[(int) p.getX()][(int) p.getY() + 1].isEmpty()) {
					return true;
				}
				break;
			default:
				return false;
			}

		}
		return false;
	}

	public Map<EntityModel, Point> getMap() {
		return map;
	}

	public StaticModel[][] getTranspo() {
		return transpo;
	}

	public void setTab(int i, int j, StaticModel m) {
		tab[i][j] = m;
	}

	public void setTranspo(int i, int j, StaticModel m) {
		transpo[i][j] = m;
	}

	public StaticModel[][] getbonus() {
		return bonus;
	}

	public void setEmptyBonus(int x, int y) {
		this.bonus[x][y] = new Empty();
	}

	public boolean isResetGame() {
		return resetGame;
	}

	public void setResetGame(boolean resetGame) {
		this.resetGame = resetGame;
	}

	public void start() {
		new Thread(this).start();
	}

	public void run() {
		while (true) {
			spawnGhost();
			if (resetGame) {
				setSpawnGhost();
				try {
					Thread.sleep(4500);
				} catch (InterruptedException e) {

					e.printStackTrace();
				}
				spm.setStart(true);
				spm.setMove(Move.LEFT);
				resetGame = false;
			}
			setChanged();
			notifyObservers();
		}
	}

	public void spawnGhost() {
		if (!estDemarre) {
			orange.setTimerSpawn(System.currentTimeMillis());
			estDemarre = true;
		} else if (System.currentTimeMillis() - orange.getTimerSpawn() > 8000) {
			estDemarre = false;
			if (rouge.isSpawn()) {
				rouge.setMove(Move.UP);
				rouge.setSpawn(false);
			} else if (bleu.isSpawn()) {
				bleu.setMove(Move.UP);
				bleu.setSpawn(false);

			} else if (rose.isSpawn()) {
				rose.setMove(Move.UP);
				rose.setSpawn(false);
			}
		}
	}

	public void setSpawnGhost() {
		this.getMap().get(orange).setLocation(SIZE_X / 2, SIZE_Y / 2 - 2);
		orange.setMove(Move.UP);
		this.getMap().get(bleu).setLocation(SIZE_X / 2, SIZE_Y / 2 - 2);
		bleu.setSpawn(true);
		bleu.setMove(null);
		this.getMap().get(rouge).setLocation(SIZE_X / 2 - 1, SIZE_Y / 2 - 2);
		rouge.setSpawn(true);
		rouge.setMove(null);
		this.getMap().get(rose).setLocation(SIZE_X / 2 - 1, SIZE_Y / 2 - 2);
		rose.setSpawn(true);
		rose.setMove(null);
		estDemarre = false;
	}

	public void shortPath(Ghost g) {
		double distanceX = map.get(g).getX() - map.get(spm).getX();
		double distanceY = map.get(g).getY() - map.get(spm).getY();

		double normeX = Math.sqrt(Math.pow(map.get(g).getX(), 2) - Math.pow(map.get(spm).getX(), 2));
		double normeY = Math.sqrt(Math.pow(map.get(g).getY(), 2) - Math.pow(map.get(spm).getY(), 2));

		if (!spm.isGhostEating()) {
			if (distanceX > 0) {
				if (okDepl(Move.LEFT, g)) {
					g.setdBuffer(Move.LEFT);
				} else if (distanceY <= 0) {
					if (okDepl(Move.DOWN, g)) {
						g.setdBuffer(Move.DOWN);
					} else
						g.setdBuffer(Move.UP);
				} else if (distanceY > 0) {
					if (okDepl(Move.UP, g)) {
						g.setdBuffer(Move.UP);
					} else
						g.setdBuffer(Move.DOWN);
				}
				if (normeX < 6 && distanceY < 0) {
					g.setdBuffer(Move.DOWN);
				}
				if (normeX < 6 && distanceY > 0) {
					g.setdBuffer(Move.UP);
				}
			} else {
				if (okDepl(Move.RIGHT, g)) {
					g.setdBuffer(Move.RIGHT);
				} else if (distanceY < 0) {
					if (okDepl(Move.DOWN, g)) {
						g.setdBuffer(Move.DOWN);
					} else
						g.setdBuffer(Move.UP);
				} else if (distanceY > 0) {
					if (okDepl(Move.UP, g)) {
						g.setdBuffer(Move.UP);
					} else
						g.setdBuffer(Move.DOWN);
				}
				if (normeX < 6 && distanceY < 0) {
					g.setdBuffer(Move.DOWN);
				}
				if (normeX < 6 && distanceY > 0) {
					g.setdBuffer(Move.UP);
				}
			}
		} else {
			switch (r.nextInt(4) + 1) {
			case 1:
				g.setdBuffer(Move.LEFT);
				break;
			case 2:
				g.setdBuffer(Move.RIGHT);
				break;
			case 3:
				g.setdBuffer(Move.UP);
				break;
			case 4:
				g.setdBuffer(Move.DOWN);
				break;
			default:
				break;
			}
		}

	}

	public void randomPath(Ghost g) {
		timer++;
		if (timer % 15 == 0) {
			switch (r.nextInt(4) + 1) {
			case 1:
				g.setMove(Move.LEFT);
				break;
			case 2:
				g.setMove(Move.RIGHT);
				break;
			case 3:
				g.setMove(Move.UP);
				break;
			case 4:
				g.setMove(Move.DOWN);
				break;
			default:
				break;
			}
		}
	}

	public void moveGhost(Ghost g) {
		if (spm.isStart() && !spm.isGameOver() && !spm.getvictory()) {
			if (g.getMove() != null) {
				if (!g.getDelAleatoire()) {
					shortPath(g);
				} else {
					randomPath(g);
				}
				if (okDepl(g.getdBuffer(), g)) {
					switch (g.getdBuffer()) {
					case LEFT:
						if (map.get(g).getX() == 0 && map.get(g).getY() == 14)
							map.get(g).setLocation(27, map.get(g).getY());
						else
							map.get(g).setLocation(map.get(g).getX() - 1, map.get(g).getY());
						break;
					case RIGHT:
						if (map.get(g).getX() == 27 && map.get(g).getY() == 14)
							map.get(g).setLocation(0, map.get(g).getY());
						else
							map.get(g).setLocation(map.get(g).getX() + 1, map.get(g).getY());
						break;
					case UP:
						map.get(g).setLocation(map.get(g).getX(), map.get(g).getY() - 1);
						break;
					case DOWN:
						map.get(g).setLocation(map.get(g).getX(), map.get(g).getY() + 1);
						break;
					}

					g.setMove(g.getdBuffer());
					g.setdBuffer(null);
				}

				else if (okDepl(g.getMove(), g)) {
					switch (g.getMove()) {
					case LEFT:
						if (map.get(g).getX() == 0 && map.get(g).getY() == 14)
							map.get(g).setLocation(27, map.get(g).getY());
						else
							map.get(g).setLocation(map.get(g).getX() - 1, map.get(g).getY());
						break;
					case RIGHT:
						if (map.get(g).getX() == 27 && map.get(g).getY() == 14)
							map.get(g).setLocation(0, map.get(g).getY());
						else
							map.get(g).setLocation(map.get(g).getX() + 1, map.get(g).getY());
						break;
					case UP:
						map.get(g).setLocation(map.get(g).getX(), map.get(g).getY() - 1);
						break;
					case DOWN:
						map.get(g).setLocation(map.get(g).getX(), map.get(g).getY() + 1);
						break;
					}
				} else {
					switch (r.nextInt(4) + 1) {
					case 1:
						g.setMove(Move.LEFT);
						break;
					case 2:
						g.setMove(Move.RIGHT);
						break;
					case 3:
						g.setMove(Move.UP);
						break;
					case 4:
						g.setMove(Move.DOWN);
						break;
					default:
						break;
					}
				}
			}
		}
	}

	public void movePacMan() {
		if (spm.isStart() && !spm.isDead() && !spm.isGameOver() && !spm.getvictory()) {
			if (okDepl(spm.getdBuffer(), spm)) {
				spm.setMove(spm.getdBuffer());
				spm.setdBuffer(null);
			}

			if (okDepl(spm.getMove(), spm)) {
				switch (spm.getMove()) {
				case LEFT:
					if (map.get(spm).getX() == 0 && map.get(spm).getY() == 14)
						map.get(spm).setLocation(27, map.get(spm).getY());
					else
						map.get(spm).setLocation(map.get(spm).getX() - 1, map.get(spm).getY());
					break;
				case RIGHT:
					if (map.get(spm).getX() == 27 && map.get(spm).getY() == 14)
						map.get(spm).setLocation(0, map.get(spm).getY());
					else
						map.get(spm).setLocation(map.get(spm).getX() + 1, map.get(spm).getY());
					break;
				case UP:
					map.get(spm).setLocation(map.get(spm).getX(), map.get(spm).getY() - 1);
					break;
				case DOWN:
					map.get(spm).setLocation(map.get(spm).getX(), map.get(spm).getY() + 1);
					break;
				}
			}

		}

	}

	public void collision(Ghost g) {
		if (map.get(spm).equals(map.get(g))) {
			if (spm.isGhostEating() && !(g.isInvulnaribility())) {
				g.setDead(true);
				g.setTimerDeath(System.currentTimeMillis());
				g.setInvulnaribility(true);
				g.setMove(null);
				spm.setScore(100);
				map.get(g).setLocation(SIZE_X / 2, SIZE_Y / 2 - 2);
			}

			else if (!g.isInvulnaribility()) {
				spm.setDead(true);
				spm.setReset(true);
				spm.setStart(false);
			}
		}
	}

	public void pacmanCollision() {
		Point point = map.get(spm);
		if (point.equals(map.get(orange))) {
			if (spm.isGhostEating() && (!orange.isInvulnaribility())) {
				orange.setDead(true);
				orange.setTimerDeath(System.currentTimeMillis());
				orange.setInvulnaribility(true);
				orange.setMove(null);
				spm.setScore(100);
				map.get(orange).setLocation(SIZE_X / 2, SIZE_Y / 2 - 2);
			} else if (!orange.isInvulnaribility()) {
				spm.setDead(true);
				spm.setReset(true);
				spm.setStart(false);
				spm.setnbDeath(1);

				map.get(orange).setLocation(SIZE_X / 2, SIZE_Y / 2 - 2);
			}
		}

		if (point.equals(map.get(rouge))) {
			if (spm.isGhostEating() && (!rouge.isInvulnaribility())) {
				rouge.setDead(true);
				rouge.setTimerDeath(System.currentTimeMillis());
				rouge.setInvulnaribility(true);
				rouge.setMove(null);
				spm.setScore(100);
				map.get(rouge).setLocation(SIZE_X / 2 - 1, SIZE_Y / 2 - 2);
			} else if (!rouge.isInvulnaribility()) {
				spm.setDead(true);
				spm.setReset(true);
				spm.setStart(false);
				spm.setnbDeath(1);
				map.get(rouge).setLocation(SIZE_X / 2 - 1, SIZE_Y / 2 - 2);
			}
		}

		else if (point.equals(map.get(bleu))) {
			if (spm.isGhostEating() && (!bleu.isInvulnaribility())) {
				bleu.setDead(true);
				bleu.setTimerDeath(System.currentTimeMillis());
				bleu.setInvulnaribility(true);
				bleu.setMove(null);
				spm.setScore(100);
				map.get(bleu).setLocation(SIZE_X / 2, SIZE_Y / 2 - 2);
			} else if (!bleu.isInvulnaribility()) {
				spm.setDead(true);
				spm.setReset(true);
				spm.setStart(false);
				spm.setnbDeath(1);

				map.get(bleu).setLocation(SIZE_X / 2, SIZE_Y / 2 - 2);
			}
		} else if (point.equals(map.get(rose))) {
			if (spm.isGhostEating() && (!rose.isInvulnaribility())) {
				rose.setDead(true);
				rose.setTimerDeath(System.currentTimeMillis());
				rose.setInvulnaribility(true);
				rose.setMove(null);
				spm.setScore(100);
				map.get(rose).setLocation(SIZE_X / 2 - 1, SIZE_Y / 2 - 2);
			} else if (!rose.isInvulnaribility()) {
				spm.setDead(true);
				spm.setReset(true);
				spm.setStart(false);
				spm.setnbDeath(1);

				map.get(rose).setLocation(SIZE_X / 2 - 1, SIZE_Y / 2 - 2);
			}
		}
	}
}
