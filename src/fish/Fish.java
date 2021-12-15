package fish;

import java.util.Random;

import animation.Animateable;
import animation.SpriteAnimation;
import input.InputUtility;
import javafx.animation.Animation;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;
import logic.Direction;
import logic.Entity;
import logic.FishingSystem;
import logic.FishingSyncAble;
import logic.GameObject;
import logic.ShopSystem;
import logic.Updateable;
import main.Main;

public abstract class Fish extends Entity implements Updateable, Animateable,FishingSyncAble {

	protected abstract ImageView imageViewFish();

	protected abstract boolean isNeedToRotate();

	protected static float fishSpeedFactor = ShopSystem.getFishSpeedFactor();
	public static Random random = new Random();

	// For Child Class
	protected FishType fishType;
	protected String name;
	protected int price;

	private boolean isLeft, isRight;
	private float speedX;
	private ImageView imageView;
	private Animation animation;
	private boolean isHook, isDead, isTurnLeft, isTurnRight, isNearMe;
	private double fishHookX, fishHookY;

	private FishWhere fishwhere;

	// Sea Level in range(7,17) Due to Height of GameScreen 600px
	public Fish() {
		isLeft = random.nextBoolean();
		isRight = !isLeft;
		z = 20;
		x = 20+random.nextInt(360);
		y = 7 * 32 + random.nextInt(10 * 32);
		fishwhere = FishWhere.SEA; // START IN SEA
		createFirstSprite();
		upDateSprite();
	}
	
	private boolean checkHitBox() { // Near Hook Count as Catch (HITBOX 20*30)
		return Math.abs(fishHookX + 16 - (getX() + 16)) <= 10 && Math.abs(fishHookY + 10 - (getY() + 16)) <= 15;
	}

	private void move(Direction dir) {
		if (dir == Direction.RIGHT) {
			if (x >= 800 - 32 - 20) { // OffSet Right 20
				isTurnLeft = isLeft = true;
				isRight = false;
				upDateSprite();
			} else {
				x += speedX;
			}
		} else {
			if (x <= 0 + 20) { // OffSet Left 20
				isTurnRight = isRight = true;
				isLeft = false;
				upDateSprite();
			} else {
				x -= speedX;
			}
		}
	}

	private void killFish(Fish fish) {
		fish.isHook = false;
		fish.isDead = true;
		fish.fishwhere = FishWhere.DEAD;
		fish.setDestroyed(true);
		fish.upDateSprite();
		FishingSystem.getInstance();
		FishingSystem.decreaseFishCount();
	}
	public void fishingSync() {
		// Pull Global to local
		fishHookX = FishingSystem.getGlobalFishHookX();
		fishHookY = FishingSystem.getGlobalFishHookY();
		isNearMe = FishingSystem.getNearMe();
		fishSpeedFactor = ShopSystem.getFishSpeedFactor();
	}
	// Handle Logic
	public void logicUpdate() {
		fishingSync();
		
		if (isHook) {
			x = fishHookX;
			y = fishHookY;
		}
		if (fishType != FishType.BOMB && !isHook && !FishingSystem.isHookFull() && checkHitBox()) {
			// CATCH FISH
			isHook = true;
			fishwhere = FishWhere.HOOK;
			System.out.println("CATCH FISH : " + this.name);
			System.out.println("IS FULL : " + FishingSystem.isHookFull());
			GameObject.catchFishSound.play();

		} else if (this.fishType == FishType.BOMB && checkHitBox()) { // BOMB ATTACH
			x = 200;
			y = 0;
			this.fishType = FishType.NONE; // preventing for bombing again
			killFish(this); // kill Bomb

			//new Thread(() -> {
				for (Fish fish : FishingSystem.getInstance().getAllFishContainer()) {
					if (fish.fishwhere == FishWhere.HOOK) {
						fish.fishwhere = FishWhere.DEAD;
						System.out.println("FISH BOMB : " + fish.name);
						killFish(fish);
					}
				}
			//}).start();
			GameObject.getInstance();
			GameObject.bombSound.play();

		} else if (isNearMe && InputUtility.getKeyPressed(KeyCode.E)) { // Keep Fish
			//new Thread(() -> {
				for (Fish fish : FishingSystem.getInstance().getAllFishContainer()) {
					if (fish.fishwhere == FishWhere.HOOK) {
						fish.fishwhere = FishWhere.DEAD; // DIE
						System.out.println("SELL : " + fish.name);
						killFish(fish);
						ShopSystem.setMoney(ShopSystem.getMoney() + fish.price);
					}
				}
			//}).start();
			System.out.println("YOU GOT : " + ShopSystem.getMoney());
			GameObject.pingSound.play();
		}

		if (!isHook && !isDead) {
			if (isRight) {
				move(Direction.RIGHT);
			} else {
				move(Direction.LEFT);
			}
		}
	}

	// For Sprite Animation
	private static final int COLUMNS = 4;
	private static final int COUNT = 4;
	private static final int OFFSET_X = 0;
	private static final int OFFSET_Y = 0;
	private static final int WIDTH = 32;
	private static final int HEIGHT = 32;

	// Handle Display
	public void draw(GraphicsContext gc) {
		upDateImageView();
	}

	public void upDateImageView() {
		Main.removeFromPane(imageView);
		imageView.relocate(getX(), getY());
		Main.addToPane(imageView);
	}

	public void createFirstSprite() {
		// GetImageView Of each Fish
		imageView = imageViewFish();
	}

	public void upDateSprite() {
		if (isDead) { // Dead Fish when Bomb or Sell
			imageView.setImage(null);
			animation.stop();
			GameObject.getInstance();
			imageView.setImage(GameObject.emptySprite);
		}
		if (isTurnRight) { // TurnRight
			imageView.setImage(null);
			animation.stop();
			imageView = imageViewFish();
			isTurnRight = false;
		}
		if (isTurnLeft) { // TurnLeft
			imageView.setImage(null);
			animation.stop();
			imageView = imageViewFish();
			isTurnLeft = false;
		}
		imageView.setViewport(new Rectangle2D(OFFSET_X, OFFSET_Y, WIDTH, HEIGHT));
		if (isNeedToRotate()) {
			if (isRight) {
				imageView.setRotate(45);
			} else {
				imageView.setRotate(-45);
			}
		}
		startAnimation();
	}

	public void startAnimation() {
		if (!isDead) { // CheckAgain
			animation = new SpriteAnimation(imageView, Duration.millis(1000), COUNT, COLUMNS, OFFSET_X, OFFSET_Y, WIDTH,
					HEIGHT);
			animation.setCycleCount(Animation.INDEFINITE);
			animation.play();
		}
	}
	
	// Getter-Setter
	
	public void setSpeed(float speed) {
		this.speedX = speed;
	}
	
	public FishWhere getFishwhere() {
		return fishwhere;
	}

	public boolean getLeft() {
		return isLeft;
	}

	public boolean getRight() {
		return isRight;
	}
}
