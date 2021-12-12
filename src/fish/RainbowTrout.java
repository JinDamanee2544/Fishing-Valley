package fish;

import javafx.scene.image.ImageView;
import logic.GameLogic;

public class RainbowTrout extends Fish {
	public RainbowTrout() {
		super();
		fishType = FishType.RAINBOWTROUT;
	}

	@Override
	public ImageView imageViewFish() {
		if(isRight) {
			return new ImageView(GameLogic.getInstance().rainbowTrout_Right);
		} else {
			return new ImageView(GameLogic.getInstance().rainbowTrout_Left);
		}
	}
	@Override
	protected boolean isNeedToRotate() {
		return true;
	}
}
