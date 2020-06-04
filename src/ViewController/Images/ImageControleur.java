package ViewController.Images;

import Ressources.Sprites.*;
import java.util.HashMap;
import javafx.scene.image.Image;

public class ImageControleur {
	
	private HashMap<ImageId,Image> ImageList;
	public ImageControleur() {
		ImageList = new HashMap<>();
		for(int i=0; i<55;i++) {
			ImageList.put(ImageId.values()[i], new Image("Ressources/Sprites/"+Folder(i)+ImageId.values()[i].toString()+".png"));
		}
	}
	public HashMap<ImageId, Image> getImageList() {
		return ImageList;
	}
	public String Folder(int i) {
		if(i <= 15) {
			return "Pacman/";
		}
		if( i >= 16 && i<=50) {
			return"Ghost/";
		}
		
		return "Grid/";
	}
}
