package fish;

import javafx.scene.image.ImageView;
import logic.GameObject;

public class Carp extends Fish {
	public Carp() {
		super();
		fishType = FishType.CARP;
		this.price = 350;
		this.name = "Carp";
		this.setSpeed(3*fishSpeedFactor);
	
	}

	@Override
	public ImageView imageViewFish() {
		if(isRight) {
			return new ImageView(GameObject.carp_Right);
		} else {
			return new ImageView(GameObject.carp_Left);
		}
	}
	@Override
	protected boolean isNeedToRotate() {
		return true;
	}
}
