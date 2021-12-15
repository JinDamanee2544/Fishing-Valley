package fish;

import javafx.scene.image.ImageView;
import logic.GameObject;

public class Tuna extends Fish {
	public int kokdafaff;
	public Tuna() {
		super();
		fishType = FishType.TUNA;
		this.price = 200;
		this.name = "Tuna";
		this.setSpeed(2*fishSpeedFactor);
		// ADD BELOW
	}

	@Override
	public ImageView imageViewFish() {
		if(isRight) {
			return new ImageView(GameObject.getInstance().tuna_Right);
		} else {
			return new ImageView(GameObject.getInstance().tuna_Left);
		}
	}
	@Override
	protected boolean isNeedToRotate() {
		return true;
	}
	
}
