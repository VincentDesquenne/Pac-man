package ViewController;

import java.awt.Point;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

import Model.EntityModel.Ghost;
import Model.EntityModel.Grid;
import Model.EntityModel.SimplePacMan;
import Model.Enumerate.Move;
import Model.StaticModel.Empty;
import Model.StaticModel.Spawn;
import Model.StaticModel.Wall;
import ViewController.Images.ImageControleur;
import ViewController.Images.ImageId;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class GameView extends Application {
	public final int SIZE_X = 28;
	public final int SIZE_Y = 31;
	private boolean start, mouth, stepO, stepR, stepB, stepP;
	private int startDelay = 0;
	Label label4 = new Label("3");
	Label label2 = new Label("0");
	Label labelGameOver = new Label("Game Over");
	Label labelVictoire = new Label("Bravo !");
	String vies;
	String score;
	long timer;
	StackPane root = new StackPane();

	@Override
	public void start(Stage primaryStage) {

		SimplePacMan spm = new SimplePacMan(SIZE_X, SIZE_Y); // initialisation du modèle

		Ghost ghO = new Ghost(SIZE_X, SIZE_Y, spm, true, "orange");
		Ghost ghR = new Ghost(SIZE_X, SIZE_Y, spm, false, "rouge");
		Ghost ghP = new Ghost(SIZE_X, SIZE_Y, spm, false, "rose");
		Ghost ghB = new Ghost(SIZE_X, SIZE_Y, spm, true, "bleu");

		Grid modelGrid = new Grid(ghR, ghB, ghP, ghO, spm);
		spm.setGrid(modelGrid);
		ghO.setGrid(modelGrid);
		ghP.setGrid(modelGrid);
		ghR.setGrid(modelGrid);
		ghB.setGrid(modelGrid);

		modelGrid.getMap().put(spm, new Point(SIZE_X / 2 - 1, SIZE_Y / 2 + 2));
		modelGrid.getMap().put(ghO, new Point(SIZE_X / 2, SIZE_Y / 2 - 2));
		modelGrid.getMap().put(ghR, new Point(SIZE_X / 2 - 1, SIZE_Y / 2 - 2));
		modelGrid.getMap().put(ghP, new Point(SIZE_X / 2 - 1, SIZE_Y / 2 - 2));
		modelGrid.getMap().put(ghB, new Point(SIZE_X / 2, SIZE_Y / 2 - 2));
		modelGrid.setEmptyBonus(SIZE_X / 2 - 1, SIZE_Y / 2 - 3);
		modelGrid.setEmptyBonus(SIZE_X / 2, SIZE_Y / 2 - 3);

		GridPane grid = new GridPane(); // création de la grid

		ImageView[][] tab = new ImageView[SIZE_X][SIZE_Y]; // tableau permettant de récupérer les cases graphiques
															// lors du rafraichissement

		for (int i = 0; i < SIZE_X; i++) { // initialisation de la grid (sans image)
			for (int j = 0; j < SIZE_Y; j++) {
				ImageView img = new ImageView();

				tab[i][j] = img;

				grid.add(img, i, j);
			}

		}

		HashMap<ImageId, Image> ImageList = new ImageControleur().getImageList();
		Observer o = new Observer() {
			@Override
			public void update(Observable o, Object arg) {
				Platform.runLater(new Runnable() {

					@Override
					public void run() {
						for (int i = 0; i < SIZE_X; i++) {
							for (int j = 0; j < SIZE_Y; j++) {

								if (modelGrid.getMap().get(spm).getX() == i
										&& modelGrid.getMap().get(spm).getY() == j) {
									if (spm.isReset()) {
										switch ((int) spm.getFrameDeath()) {
										case 1:
											tab[i][j].setImage(ImageList.get(ImageId.Pac0));
											break;
										case 2:
											tab[i][j].setImage(ImageList.get(ImageId.Pac1));
											break;
										case 3:
											tab[i][j].setImage(ImageList.get(ImageId.Pac2));
											break;
										case 4:
											tab[i][j].setImage(ImageList.get(ImageId.Pac3));
											break;
										case 5:
											tab[i][j].setImage(ImageList.get(ImageId.Pac4));
											break;
										case 6:
											tab[i][j].setImage(ImageList.get(ImageId.Pac5));
											break;
										case 7:
											tab[i][j].setImage(ImageList.get(ImageId.Pac6));
											break;
										default :tab[i][j].setImage(ImageList.get(ImageId.PacManBall)); break;
										}
									}
									else if(!spm.isStart()) {tab[i][j].setImage(ImageList.get(ImageId.PacManBall)); }
									else {
										if (spm.getMove() != null) {
											switch (spm.getMove()) {
											case RIGHT:
												if (mouth == false) {
													mouth = true;
													tab[i][j].setImage(ImageList.get(ImageId.Pacman));
												} else {
													mouth = false;
													tab[i][j].setImage(ImageList.get(ImageId.PacManOpen));
												}
												break;

											case LEFT:
												if (mouth == false) {
													mouth = true;
													tab[i][j].setImage(ImageList.get(ImageId.Pacman180));
												} else {
													mouth = false;
													tab[i][j].setImage(ImageList.get(ImageId.PacManOpen180));
												}
												break;

											case UP:
												if (mouth == false) {
													mouth = true;
													tab[i][j].setImage(ImageList.get(ImageId.Pacman270));
												} else {
													mouth = false;
													tab[i][j].setImage(ImageList.get(ImageId.PacManOpen270));
												}
												break;
											case DOWN:
												if (mouth == false) {
													mouth = true;
													tab[i][j].setImage(ImageList.get(ImageId.Pacman90));
												} else {
													mouth = false;
													tab[i][j].setImage(ImageList.get(ImageId.PacManOpen90));
												}
												break;
											}
										} else {
											tab[i][j].setImage(ImageList.get(ImageId.PacManBall));
										}
									}
								}

								else if (modelGrid.getMap().get(ghO).getX() == i
										&& modelGrid.getMap().get(ghO).getY() == j) {

									if (ghO.isDead())
										tab[i][j].setImage(ImageList.get(ImageId.GhostDead));

									else if (spm.isGhostEating())
										if (stepO) {
											stepO = false;
											tab[i][j].setImage(ImageList.get(ImageId.GhostSick));
										} else {
											stepO = true;
											tab[i][j].setImage(ImageList.get(ImageId.GhostSick2));
										}
									else if (ghO.getMove() != null) {
										switch (ghO.getMove()) {
										case RIGHT:
											if (stepO) {
												stepO = false;
												tab[i][j].setImage(ImageList.get(ImageId.GhostOrange));
											} else {
												stepO = true;
												tab[i][j].setImage(ImageList.get(ImageId.GhostOrange2));
											}
											break;
										case LEFT:
											if (stepO) {
												stepO = false;
												tab[i][j].setImage(ImageList.get(ImageId.GhostOrangeLeft));
											} else {
												stepO = true;
												tab[i][j].setImage(ImageList.get(ImageId.GhostOrangeLeft2));
											}
											break;
										case UP:
											if (stepO) {
												stepO = false;
												tab[i][j].setImage(ImageList.get(ImageId.GhostOrangeUp));
											} else {
												stepO = true;
												tab[i][j].setImage(ImageList.get(ImageId.GhostOrangeUp2));
											}
											break;
										case DOWN:
											if (stepO) {
												stepO = false;
												tab[i][j].setImage(ImageList.get(ImageId.GhostOrangeDown));
											} else {
												stepO = true;
												tab[i][j].setImage(ImageList.get(ImageId.GhostOrangeDown2));
											}
											break;
										}
									} else
										tab[i][j].setImage(ImageList.get(ImageId.GhostOrange));
								}

								else if (modelGrid.getMap().get(ghR).getX() == i
										&& modelGrid.getMap().get(ghR).getY() == j) {
									if (ghR.isDead()) {
										tab[i][j].setImage(ImageList.get(ImageId.GhostDead));
									} else if (spm.isGhostEating())
										if (stepR) {
											stepR = false;
											tab[i][j].setImage(ImageList.get(ImageId.GhostSick));
										} else {
											stepR = true;
											tab[i][j].setImage(ImageList.get(ImageId.GhostSick2));
										}
									else if (ghR.getMove() != null) {
										switch (ghR.getMove()) {
										case RIGHT:
											if (stepR) {
												stepR = false;
												tab[i][j].setImage(ImageList.get(ImageId.GhostRed));
											} else {
												stepR = true;
												tab[i][j].setImage(ImageList.get(ImageId.GhostRed2));
											}
											break;
										case LEFT:
											if (stepR) {
												stepR = false;
												tab[i][j].setImage(ImageList.get(ImageId.GhostRedLeft));
											} else {
												stepR = true;
												tab[i][j].setImage(ImageList.get(ImageId.GhostRedLeft2));
											}
											break;
										case UP:
											if (stepR) {
												stepR = false;
												tab[i][j].setImage(ImageList.get(ImageId.GhostRedUp));
											} else {
												stepR = true;
												tab[i][j].setImage(ImageList.get(ImageId.GhostRedUp2));
											}
											break;
										case DOWN:
											if (stepR) {
												stepR = false;
												tab[i][j].setImage(ImageList.get(ImageId.GhostRedDown));
											} else {
												stepR = true;
												tab[i][j].setImage(ImageList.get(ImageId.GhostRedDown2));
											}
											break;
										}
									} else
										tab[i][j].setImage(ImageList.get(ImageId.GhostRed));
								}

								else if (modelGrid.getMap().get(ghP).getX() == i
										&& modelGrid.getMap().get(ghP).getY() == j) {
									if (ghP.isDead())
										tab[i][j].setImage(ImageList.get(ImageId.GhostDead));
									else if (spm.isGhostEating())
										if (stepP) {
											stepP = false;
											tab[i][j].setImage(ImageList.get(ImageId.GhostSick));
										} else {
											stepP = true;
											tab[i][j].setImage(ImageList.get(ImageId.GhostSick2));
										}
									else if (ghP.getMove() != null) {
										switch (ghP.getMove()) {
										case RIGHT:
											if (stepP) {
												stepP = false;
												tab[i][j].setImage(ImageList.get(ImageId.GhostPink));
											} else {
												stepP = true;
												tab[i][j].setImage(ImageList.get(ImageId.GhostPink2));
											}
											break;
										case LEFT:
											if (stepP) {
												stepP = false;
												tab[i][j].setImage(ImageList.get(ImageId.GhostPinkLeft));
											} else {
												stepP = true;
												tab[i][j].setImage(ImageList.get(ImageId.GhostPinkLeft2));
											}
											break;
										case UP:
											if (stepP) {
												stepP = false;
												tab[i][j].setImage(ImageList.get(ImageId.GhostPinkUp));
											} else {
												stepP = true;
												tab[i][j].setImage(ImageList.get(ImageId.GhostPinkUp2));
											}
											break;
										case DOWN:
											if (stepP) {
												stepP = false;
												tab[i][j].setImage(ImageList.get(ImageId.GhostPinkDown));
											} else {
												stepP = true;
												tab[i][j].setImage(ImageList.get(ImageId.GhostPinkDown2));
											}
											break;
										}
									} else
										tab[i][j].setImage(ImageList.get(ImageId.GhostPink));
								}

								else if (modelGrid.getMap().get(ghB).getX() == i
										&& modelGrid.getMap().get(ghB).getY() == j) {
									if (ghB.isDead())
										tab[i][j].setImage(ImageList.get(ImageId.GhostDead));
									else if (spm.isGhostEating())
										if (stepB) {
											stepB = false;
											tab[i][j].setImage(ImageList.get(ImageId.GhostSick));
										} else {
											stepB = true;
											tab[i][j].setImage(ImageList.get(ImageId.GhostSick2));
										}
									else if (ghB.getMove() != null) {
										switch (ghB.getMove()) {
										case RIGHT:
											if (stepB) {
												stepB = false;
												tab[i][j].setImage(ImageList.get(ImageId.GhostBlue));
											} else {
												stepB = true;
												tab[i][j].setImage(ImageList.get(ImageId.GhostBlue2));
											}
											break;
										case LEFT:
											if (stepB) {
												stepB = false;
												tab[i][j].setImage(ImageList.get(ImageId.GhostBlueLeft));
											} else {
												stepB = true;
												tab[i][j].setImage(ImageList.get(ImageId.GhostBlueLeft2));
											}
											break;
										case UP:
											if (stepB) {
												stepB = false;
												tab[i][j].setImage(ImageList.get(ImageId.GhostBlueUp));
											} else {
												stepB = true;
												tab[i][j].setImage(ImageList.get(ImageId.GhostBlueUp2));
											}
											break;
										case DOWN:
											if (stepB) {
												stepB = false;
												tab[i][j].setImage(ImageList.get(ImageId.GhostBlueDown));
											} else {
												stepB = true;
												tab[i][j].setImage(ImageList.get(ImageId.GhostBlueDown2));
											}
											break;
										}
									} else
										tab[i][j].setImage(ImageList.get(ImageId.GhostBlue));
								}

								else if (modelGrid.getTranspo()[i][j].isWall()) {
									tab[i][j].setImage(ImageList.get(ImageId.Wall));
								}

								else if (modelGrid.getbonus()[i][j].isPacGom()) {
									tab[i][j].setImage(ImageList.get(ImageId.PacGum));
								}

								else if (modelGrid.getbonus()[i][j].isBean()) {

									tab[i][j].setImage(ImageList.get(ImageId.Bean));
								} else {
									tab[i][j].setImage(ImageList.get(ImageId.Space));
								}

								score = String.valueOf(spm.getScore());
								label2.setText(score);
								vies = String.valueOf(3 - spm.getnbDeath());
								label4.setText(vies);

								if (spm.isGameOver()) {
									if (!root.getChildren().contains(labelGameOver))
										root.getChildren().add(labelGameOver);
								}
								if (spm.getvictory()) {
									if (!root.getChildren().contains(labelVictoire))
										root.getChildren().add(labelVictoire);
								}
							}
						}
					}
				});
			}
		};

		// ModelGrid.addObserver(o);
		spm.addObserver(o);
		ghO.addObserver(o);
		ghR.addObserver(o);
		ghP.addObserver(o);
		ghB.addObserver(o);

		modelGrid.start();
		spm.start();
		ghO.start();
		ghO.setMove(Move.UP);
		ghR.start();
		ghP.start();
		ghB.start();

		Empty c = new Empty();

		Label label1 = new Label("Score :");
		label1.setTranslateX(-200);
		label1.setTranslateY(275);
		label1.setTextFill(Color.WHITE);

		label2.setTranslateX(-120);
		label2.setTranslateY(275);
		label2.setTextFill(Color.WHITE);

		Label label3 = new Label("Vies :");
		label3.setTranslateX(120);
		label3.setTranslateY(275);
		label3.setTextFill(Color.WHITE);

		label4.setTranslateX(200);
		label4.setTranslateY(275);
		label4.setTextFill(Color.WHITE);

		labelGameOver.setTranslateY(275);
		labelGameOver.setTextFill(Color.WHITE);

		labelVictoire.setTranslateY(275);
		labelVictoire.setTextFill(Color.WHITE);

		try {

			final Font f = Font.loadFont(new FileInputStream(new File("src/Ressources/Font/CrackMan.TTF")), 20);
			label1.setFont(f);
			label2.setFont(f);
			label3.setFont(f);
			label4.setFont(f);
			labelGameOver.setFont(f);
			labelVictoire.setFont(f);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		root.getChildren().add(grid);
		root.getChildren().add(label1);
		root.getChildren().add(label2);
		root.getChildren().add(label3);
		root.getChildren().add(label4);
		root.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));

		Scene scene = new Scene(root, 500, 600);
		primaryStage.setTitle("PacMan");
		primaryStage.setScene(scene);
		primaryStage.show();

		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				Platform.exit();
				System.exit(0);
			}
		});

		root.setOnKeyPressed(new EventHandler<javafx.scene.input.KeyEvent>() { // on écoute le clavier

			@Override
			public void handle(javafx.scene.input.KeyEvent event) {

				if (event.getCode().isArrowKey()) {
					if (modelGrid.okDepl(Move.valueOf(event.getCode().toString()), spm) && start) {
						spm.setMove(Move.valueOf(event.getCode().toString()));
					} else {
						spm.setdBuffer(Move.valueOf(event.getCode().toString()));
					}

				}

			}

		});
		grid.requestFocus();
	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {

		launch(args);

	}
}
