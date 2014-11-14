package com.doodlejump;

import java.util.Stack;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

public class PropsMode extends Activity {
	private MyView myview;
	private Drawable background;
	private Drawable jellyfish;
	private Drawable monsterMouth_pic;
	private boolean mouthOpend = false;
	private int jellyfishX;
	private int jellyfishY;
	private final int VY = 30;
	private int vy = VY;
	private int vx = 0;
	private final int terminalVX = 5;
	private Handler timerHandler;
	private boolean freefall = false;
	private float mGX = 0;
	private SensorManager sensorMgr;
	private Sensor sensor;
	private final int WIDTH = 720;
	private final int HEIGHT = 1280;
	private int altitude = 0;
	private int dy = 0;
	private Stack<Integer> pedalX = new Stack<Integer>();
	private Stack<Integer> pedalY = new Stack<Integer>();
	private Drawable pedal;
	private boolean touch = false;
	private boolean downAnimation = false;
	private boolean dead = false;
	private Drawable deadpic;
	private MediaPlayer mp3;
	// chuan
	private Drawable posion;
	private Drawable tochange;
	private Stack<Integer> itemX = new Stack<Integer>();
	private Stack<Integer> itemY = new Stack<Integer>();
	private boolean item = false;

	private int chooseI;

	private Drawable ghost;
	private Drawable shark;
	private Stack<Integer> monsterX = new Stack<Integer>();
	private Stack<Integer> monsterY = new Stack<Integer>();
	private boolean monster = false;

	private int chooseM;

	private boolean downItemAnimation = false;
	private boolean downMonsterAnimation = false;

	private int n = 4;// 次數
	private int s = 3;

	// chuan
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		background = getResources().getDrawable(R.drawable.background);
		jellyfish = getResources().getDrawable(R.drawable.jellyfish);
		pedal = getResources().getDrawable(R.drawable.bubbley);
		deadpic = getResources().getDrawable(R.drawable.dead);
		monsterMouth_pic = getResources()
				.getDrawable(R.drawable.monster_closed);

		setMusic();
		// chuan
		posion = getResources().getDrawable(R.drawable.posion);
		tochange = getResources().getDrawable(R.drawable.tochange);

		ghost = getResources().getDrawable(R.drawable.ghost);
		shark = getResources().getDrawable(R.drawable.shark);
		// chuan
		// chuan
		setItemInitialCoordinates();
		setMonsterInitialCoordinates();
		// chuan
		setPedalsInitialCoordinates();

		jellyfishX = 290;
		jellyfishY = 1280 - jellyfish.getIntrinsicHeight();

		timerHandler = new Handler();
		myview = new MyView(this);
		setContentView(myview);
		timerHandler.postDelayed(jellyfishfloat, 17);
		timerHandler.postDelayed(monsterMouth, 500);
		/* 得到SensorManager物件 */
		sensorMgr = (SensorManager) getSystemService(SENSOR_SERVICE);
		sensor = sensorMgr.getDefaultSensor(Sensor.TYPE_GRAVITY);
		sensorMgr.registerListener(sensorEventListener, sensor,
				SensorManager.SENSOR_DELAY_GAME);

	}

	private Runnable monsterMouth = new Runnable() {
		public void run() {
			if (mouthOpend) {
				monsterMouth_pic = getResources().getDrawable(
						R.drawable.monster_closed);
				mouthOpend = false;
			} else {
				monsterMouth_pic = getResources().getDrawable(
						R.drawable.monster_opened);
				mouthOpend = true;
			}
			myview.invalidate();
			timerHandler.postDelayed(monsterMouth, 500);
		}
	};

	private void setMusic() {
		mp3 = MediaPlayer.create(PropsMode.this, R.raw.little_talk);
		mp3.setLooping(true);
		mp3.start();
	}

	private void setPedalsInitialCoordinates() {
		int x;
		int y;
		for (int i = 0; i < 10; i++) {
			x = (int) (Math.random() * (WIDTH - pedal.getIntrinsicWidth()));
			pedalX.push(x);
			y = (int) (Math.random() * (HEIGHT - pedal.getIntrinsicHeight()));
			pedalY.push(y);
		}
	}

	private void setItemInitialCoordinates() {
		int x;
		int y;
		chooseI = (int) (Math.random() * 2);
		if (chooseI == 0) {
			x = (int) (Math.random() * (WIDTH - posion.getIntrinsicWidth()));
			itemX.push(x);
			y = (int) (Math.random() * (HEIGHT * 3 / 4 - posion
					.getIntrinsicHeight()));
			itemY.push(y);

		} else if (chooseI == 1) {
			x = (int) (Math.random() * (WIDTH - tochange.getIntrinsicWidth()));
			itemX.push(x);
			y = (int) (Math.random() * (HEIGHT * 3 / 4 - tochange
					.getIntrinsicHeight()));
			itemY.push(y);

		}
	}

	private void setMonsterInitialCoordinates() {
		int x;
		int y;
		chooseM = (int) (Math.random() * 2);
		if (chooseM == 0) {
			x = (int) (Math.random() * (WIDTH - ghost.getIntrinsicWidth()));
			monsterX.push(x);
			y = (int) (Math.random() * (HEIGHT * 3 / 4 - ghost
					.getIntrinsicHeight()));
			monsterY.push(y);

		} else if (chooseM == 1) {
			x = (int) (Math.random() * (WIDTH - shark.getIntrinsicWidth()));
			monsterX.push(x);
			y = (int) (Math.random() * (HEIGHT * 3 / 4 - shark
					.getIntrinsicHeight()));
			monsterY.push(y);

		}
	}

	private void setPedalsCoordinates() {
		int x;
		int y;
		for (int i = 0; i < 4; i++) {
			x = (int) (Math.random() * (WIDTH - pedal.getIntrinsicWidth()));
			pedalX.push(x);
			if (altitude <= 15000) {
				if (i < 2) {
					y = (int) (-Math.random() * HEIGHT * 1 / 4);
					pedalY.push(y);
				} else {
					y = (-HEIGHT * 1 / 4)
							+ (int) (-Math.random() * HEIGHT * 1 / 4);
					pedalY.push(y);
				}
			} else if (altitude <= 30000) {
				y = (int) (-Math.random() * HEIGHT * 1 / 2);
				pedalY.push(y);
			} else if (altitude <= 45000) {
				y = (int) (-Math.random() * HEIGHT * 1 / 4);
				pedalY.push(y);
			} else if (altitude <= 60000) {
				y = (int) (-Math.random() * HEIGHT * 1 / 6);
				pedalY.push(y);
			}
		}
	}

	private SensorEventListener sensorEventListener = new SensorEventListener() {
		@Override
		public void onSensorChanged(SensorEvent e) {
			mGX = e.values[SensorManager.DATA_X];
			if (mGX > 1) {
				if (vx < -terminalVX) {
					vx = -terminalVX;
				}
				vx--;
				jellyfishX += vx;
				if (jellyfishX <= 0 - jellyfish.getIntrinsicWidth()) {
					jellyfishX = myview.getWidth();
				}
			}
			if (mGX < -1) {
				if (vx > terminalVX) {
					vx = terminalVX;
				}
				vx++;
				jellyfishX += vx;
				if (jellyfishX >= myview.getWidth()) {
					jellyfishX = 0 - jellyfish.getIntrinsicWidth();
				}
			}
			myview.invalidate();
		}

		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {

		}
	};
	private Runnable jellyfishfloat = new Runnable() {
		public void run() {
			jellyfishY -= vy;
			vy--;
			if (vy == 0) {
				freefall = true;
			}
			// chuan
			touchItem();
			touchMonster();
			if (freefall) {
				touchPedal();
			}
			// chuan
			if (freefall && touch) {
				vy = VY;
				freefall = false;
				dy = (1280 - jellyfish.getIntrinsicHeight()) - jellyfishY;
				if (dy > 0) {
					altitude += dy;
					setPedalsCoordinates();
					if (!downAnimation) {
						downTheStairs();
					}
					if (!downItemAnimation) {
						downItem();
					}
					if (!downMonsterAnimation) {
						downMonster();
					}
				}
			}

			// 水母死亡
			if(monster){
				jellyfishY = HEIGHT+1;
			}
			if (jellyfishY > HEIGHT) {
				if (!dead) {
					dead = true;
					pedalX.clear();
					pedalY.clear();
					Bundle bundle = new Bundle();
					bundle.putInt("point", altitude);
					Intent intent = new Intent();
					intent.putExtras(bundle);

					intent.setClass(PropsMode.this, PropsFinalActivity.class);
					startActivity(intent);
					PropsMode.this.finish();
				}
			}

			myview.invalidate();// 相當於repaint()
			timerHandler.postDelayed(this, 17);
		}

	};

	private void downTheStairs() {
		downAnimation = true;
		timerHandler.postDelayed(downStairs, 5);
	}

	private void downItem() {
		downItemAnimation = true;
		timerHandler.postDelayed(downItemFloat, 5);
	}

	private void downMonster() {
		downMonsterAnimation = true;
		timerHandler.postDelayed(downMonsterFloat, 5);
	}

	private Runnable downStairs = new Runnable() {
		public void run() {
			jellyfishY += 4;
			if (!pedalY.empty()) {
				for (int i = 0; i < pedalY.size(); i++) {
					int x = pedalY.get(i) + 6;
					pedalY.set(i, x);
				}
			}
			if (vy == 0) {
				timerHandler.removeCallbacks(downStairs);
				downAnimation = false;
			}
			timerHandler.postDelayed(downStairs, 5);
		}
	};
	private Runnable downItemFloat = new Runnable() {
		public void run() {
			if (!itemY.empty()) {
				for (int i = 0; i < itemY.size(); i++) {
					int x = itemY.get(i) + 6;
					itemY.set(i, x);
				}
			}
			if (vy == 0) {
				timerHandler.removeCallbacks(downItemFloat);
				downItemAnimation = false;
			}
			timerHandler.postDelayed(downItemFloat, 5);
		}

	};

	private Runnable downMonsterFloat = new Runnable() {
		public void run() {
			if (!itemY.empty()) {
				for (int i = 0; i < itemY.size(); i++) {
					int x = monsterY.get(i) + 6;
					monsterY.set(i, x);
				}
			}
			if (vy == 0) {
				timerHandler.removeCallbacks(downMonsterFloat);
				downMonsterAnimation = false;
			}
			timerHandler.postDelayed(downMonsterFloat, 5);
		}
	};

	private void touchPedal() {
		for (int i = 0; i < pedalX.size(); i++) {
			if (pedalX.get(i) <= jellyfishX + jellyfish.getIntrinsicWidth() * 6
					/ 7
					&& pedalY.get(i) <= jellyfishY
							+ jellyfish.getIntrinsicHeight()
					&& jellyfishX + jellyfish.getIntrinsicWidth() / 7 <= pedalX
							.get(i) + pedal.getIntrinsicWidth()
					&& pedalY.get(i) + pedal.getIntrinsicHeight() >= jellyfishY
							+ jellyfish.getIntrinsicHeight()) {
				touch = true;
				break;
			} else {
				touch = false;
			}
		}
	}

	// chuan
	private void touchMonster() {
		if (altitude == 3000 * s) {
			setMonsterInitialCoordinates();
		}
		for (int i = 0; i < monsterX.size(); i++) {

			if (chooseM == 0) {

				if (monsterX.get(i) <= jellyfishX
						+ jellyfish.getIntrinsicWidth() * 6 / 7
						&& monsterY.get(i) <= jellyfishY
								+ jellyfish.getIntrinsicHeight()
						&& jellyfishX + jellyfish.getIntrinsicWidth() / 7 <= monsterX
								.get(i) + ghost.getIntrinsicWidth()
						&& monsterY.get(i) + ghost.getIntrinsicHeight() >= jellyfishY
								+ jellyfish.getIntrinsicHeight()) {
					monster = true;

					//dead = true;
					//PropsMode.this.finish();
					// Toast.makeText(getApplicationContext(),
					// "Ghost2",/Toast.LENGTH_SHORT).show();
				} else {
					monster = false;
				}
			} else if (chooseM == 1) {
				if (monsterX.get(i) <= jellyfishX
						+ jellyfish.getIntrinsicWidth() * 6 / 7
						&& monsterY.get(i) <= jellyfishY
								+ jellyfish.getIntrinsicHeight()
						&& jellyfishX + jellyfish.getIntrinsicWidth() / 7 <= monsterX
								.get(i) + shark.getIntrinsicWidth()
						&& monsterY.get(i) + shark.getIntrinsicHeight() >= jellyfishY
								+ jellyfish.getIntrinsicHeight()) {
					monster = true;

					//dead = true;
					//PropsMode.this.finish();
					// Toast.makeText(getApplicationContext(),
					// "Shark2",Toast.LENGTH_SHORT).show();
				} else {
					monster = false;
				}
			}
		}
	}

	private void touchItem() {
		if (altitude == 2000 * n) {
			setMonsterInitialCoordinates();
		}
		for (int i = 0; i < itemX.size(); i++) {

			if (chooseI == 0) {
				if (itemX.get(i) <= jellyfishX + jellyfish.getIntrinsicWidth()
						* 6 / 7
						&& itemY.get(i) <= jellyfishY
								+ jellyfish.getIntrinsicHeight()
						&& jellyfishX + jellyfish.getIntrinsicWidth() / 7 <= itemX
								.get(i) + posion.getIntrinsicWidth()
						&& itemY.get(i) + posion.getIntrinsicHeight() >= jellyfishY
								+ jellyfish.getIntrinsicHeight()) {
					item = true;
					altitude-=500;
					// Toast.makeText(getApplicationContext(),
					// "Posion2",Toast.LENGTH_SHORT).show();
				} else {
					item = false;
				}
			} else if (chooseI == 1) {
				if (itemX.get(i) <= jellyfishX + jellyfish.getIntrinsicWidth()
						* 6 / 7
						&& itemY.get(i) <= jellyfishY
								+ jellyfish.getIntrinsicHeight()
						&& jellyfishX + jellyfish.getIntrinsicWidth() / 7 <= itemX
								.get(i) + tochange.getIntrinsicWidth()
						&& itemY.get(i) + tochange.getIntrinsicHeight() >= jellyfishY
								+ jellyfish.getIntrinsicHeight()) {
					item = true;
					jellyfish = getResources().getDrawable(R.drawable.change);
					// Toast.makeText(getApplicationContext(),
					// "Tochange2",Toast.LENGTH_SHORT).show();
				} else {
					item = false;
				}
			}
		}
	}

	// chuan
	private class MyView extends View {
		public MyView(Context context) {
			super(context);
		}

		@Override
		protected void onDraw(Canvas canvas) {
			super.onDraw(canvas);
			// canvas.getWidth() = 720px
			// canvas.getHeight() = 1280px
			background.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
			background.draw(canvas);
			// draw pedal
			if (!pedalX.empty()) {
				for (int i = 0; i < pedalX.size(); i++) {
					int px = pedalX.get(i);
					int py = pedalY.get(i);
					if (py < HEIGHT) {
						pedal.setBounds(px, py, px + pedal.getIntrinsicWidth(),
								py + pedal.getIntrinsicHeight());
						pedal.draw(canvas);
					} else {
						pedalX.removeElementAt(i);
						pedalY.removeElementAt(i);
					}
				}
			}
			jellyfish.setBounds(jellyfishX, jellyfishY,
					jellyfishX + jellyfish.getIntrinsicWidth(), jellyfishY
							+ jellyfish.getIntrinsicHeight());
			jellyfish.draw(canvas);

			// chuan

			if (chooseI == 0) {
				int x = itemX.peek();
				int y = itemY.peek();
				posion.setBounds(x, y, x + posion.getIntrinsicWidth(), y
						+ posion.getIntrinsicHeight());
				posion.draw(canvas);
			} else if (chooseI == 1) {
				int x = itemX.peek();
				int y = itemY.peek();
				tochange.setBounds(x, y, x + tochange.getIntrinsicWidth(), y
						+ posion.getIntrinsicHeight());
				tochange.draw(canvas);
			}

			if (chooseM == 0) {
				int x = monsterX.peek();
				int y = monsterY.peek();
				ghost.setBounds(x, y, x + ghost.getIntrinsicWidth(),
						y + ghost.getIntrinsicHeight());
				ghost.draw(canvas);
			} else if (chooseM == 1) {
				int x = itemX.peek();
				int y = itemY.peek();
				shark.setBounds(x, y, x + shark.getIntrinsicWidth(),
						y + shark.getIntrinsicHeight());
				shark.draw(canvas);
			}

			// chuan
			// 怪獸嘴巴
			monsterMouth_pic.setBounds(0, 1050, WIDTH, HEIGHT);
			monsterMouth_pic.draw(canvas);
			// 最後畫骷髏
			if (dead) {
				deadpic.setBounds(65, 200, 65 + deadpic.getIntrinsicWidth(),
						200 + deadpic.getIntrinsicHeight());
				deadpic.draw(canvas);
			}
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		timerHandler.removeCallbacks(downStairs);
		timerHandler.removeCallbacks(jellyfishfloat);
		mp3.stop();
		mp3.release();

	}
}
