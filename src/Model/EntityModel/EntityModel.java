package Model.EntityModel;

import java.util.Observable;


public abstract class EntityModel extends Observable implements Runnable {
	 abstract void realiserAction()   ;     
}
