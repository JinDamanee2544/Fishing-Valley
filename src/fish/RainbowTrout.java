package fish;

import javafx.scene.image.ImageView;
import logic.GameObject;

public class RainbowTrout extends Fish {
	public RainbowTrout() {
		super();
		fishType = FishType.RAINBOWTROUT;
		this.price = 300;
		this.name = "Rainbow Trout";
		this.setSpeed(2*fishSpeedFactor);
	}

	@Override
	public ImageView imageViewFish() {
		if(isRight) {
			return new ImageView(GameObject.getInstance().rainbowTrout_Right);
		} else {
			return new ImageView(GameObject.getInstance().rainbowTrout_Left);
		}
	}
	@Override
	protected boolean isNeedToRotate() {
		return true;
	}
}
