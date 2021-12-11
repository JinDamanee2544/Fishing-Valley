package logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;

public class GameLogic {
	private static final GameLogic instance = new GameLogic();
	private List<IRenderable> entities;
	private Comparator<IRenderable> comparator;
	
	public static Image map;
	public static Image fishPic;
	public static Image playerPic;
	public static Image emptySprite;
	

	static {
		loadResource();
	}

	public GameLogic() {
		entities = new ArrayList<IRenderable>();
		comparator = (IRenderable o1, IRenderable o2) -> {
			if (o1.getZ() > o2.getZ())
				return 1;
			return -1;
		};
	}

	public static GameLogic getInstance() {
		return instance;
	}

	public static void loadResource() {
		map = new Image(ClassLoader.getSystemResource("beach.png").toString());
		fishPic = new Image(ClassLoader.getSystemResource("blueFish.png").toString());
		playerPic = new Image(ClassLoader.getSystemResource("willy.png").toString());
		emptySprite = new Image(ClassLoader.getSystemResource("EmptySprite.png").toString());
	}

	public void add(IRenderable entity) {
		// System.out.println("add");
		entities.add(entity);
		Collections.sort(entities, comparator);
		/*
		for(IRenderable x: entities){
			if(x instanceof Tank) System.out.println("tank");
			if(x instanceof Mine) System.out.println("mine");
			if(x instanceof Field) System.out.println("field");
			
		}
		*/
	}

	public void update() {
		for (int i = entities.size() - 1; i >= 0; i--) {
			if (entities.get(i).isDestroyed())
				entities.remove(i);
		}
	}

	public List<IRenderable> getEntities() {
		return entities;
	}
}