package com.yj.sdk;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.NinePatch;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ImageButton;

import com.yj.entity.Constants;
import com.yj.ui.ControllerView;
import com.yj.util.Utils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lufengkai 
 * @date 2015年5月27日
 * @copyright 游鹏科技
 */
public class ControllerViewImpl {

	/**
	 * 悬浮view
	 **/
	private ControllerView controllerView;
	private Map<String, Bitmap> table = new HashMap<String, Bitmap>();

	private static ControllerViewImpl instance = null;

	private boolean isExist = false;
	private int density = 0;

	private Drawable drawable_left = null;
	private Drawable drawable_right = null;

	private Context mContext;
	private ImageButton imageButton;
	private SharedPreferences sharedPreferences;
	// isOne true半圆状态
//	private boolean isOne;

	// 半圆浮标切换
	private Handler mHandler = new Handler();
	private final Runnable runnable = new Runnable() {
		@Override
		public void run() {
//			isOne = true;
			if (imageButton == null) {
				return;
			}
			WindowManager.LayoutParams layoutParams = controllerView
					.getWMParams();
			if (layoutParams.x == 0
					|| layoutParams.x == controllerView.getWindow_width()) {
				if (layoutParams.x > controllerView.getWindow_width() / 2) {
					imageButton.setBackgroundDrawable(drawable_right);
					controllerView.x = controllerView.getWindow_width();
				} else {
					// 左边
					imageButton.setBackgroundDrawable(drawable_left);
					layoutParams.x = 0;
				}
				controllerView.setShow(true);
			}
			mHandler.removeCallbacks(runnable);
			mHandler.postDelayed(runnable, 3000);
		}
	};

	private OnClickListener iconOnClick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			mHandler.removeCallbacks(runnable);
			// 防止连续点击
			if (ControllerView.downTime > 200)
				return;
			if (!controllerView.isShow()) {
				sharedPreferences = v.getContext().getSharedPreferences(
                                    "SaveSetting", Context.MODE_PRIVATE);
				SharedPreferences.Editor editor = sharedPreferences.edit();
				editor.putInt("actions",
						sharedPreferences.getInt("actions", 0) + 1);
				editor.commit();

				// 启动个人中心
			/*	Intent intent = new Intent(mContext, PersonCenterActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				mContext.startActivity(intent);*/

			} else {
				/*controllerView.setShow(false);
				imageButton.setBackgroundDrawableDrawable(Utils.getStateListDrawable(
						mContext, "float_icon.png", "float_icon2.png"));
				ControllerView.downTime = 0;*/
			}
			mHandler.postDelayed(runnable, 3000);
		}
	};

	private ControllerViewImpl(Context context) {
		init(context.getApplicationContext());
	}

	public synchronized static ControllerViewImpl getInstance(Context context) {
		if (instance == null) {
			instance = new ControllerViewImpl(context);
		}
		return instance;
	}

	protected void init(Context context) {
		this.mContext = context;
		controllerView = new ControllerView(context);
		imageButton = new ImageButton(context);
		imageButton.setBackgroundDrawable(Utils.getStateListDrawable(mContext,
                                                                             "float_icon.png", "float_icon2.png"));
		controllerView.addView(imageButton);

		imageButton.setOnClickListener(iconOnClick);

		drawable_left = getDrawable(mContext, Constants.ASSETS_RES_PATH
				+ "menu_lefe.png");
		drawable_right = getDrawable(mContext, Constants.ASSETS_RES_PATH
				+ "menu_right.png");

		// 启动半圆图标定时器
		mHandler.postDelayed(runnable, 3000);
	}

	protected void setControllerView(int visibility) {
		controllerView.setVisibility(visibility);
	}

	protected void removeControllerView() {
		if (isExist) {
			controllerView.removeControllerView();
			isExist = false;
			instance = null;
		}
		if (controllerView.isShow()) {
			mHandler.removeCallbacks(runnable);
			controllerView.setShow(false);
		}

	}

	private Drawable getDrawable(Context ctx, String path) {
		Bitmap bitmap = getBitmap(ctx, path);
		return getDrawable(ctx, bitmap);
	}

	private Drawable getDrawable(Context ctx, Bitmap bitmap) {

		if (density == 0) {
			DisplayMetrics metrics = new DisplayMetrics();
			WindowManager wm = (WindowManager) ctx
					.getSystemService(Context.WINDOW_SERVICE);
			wm.getDefaultDisplay().getMetrics(metrics);
			density = metrics.densityDpi;
		}

		BitmapDrawable d = new BitmapDrawable(bitmap);
		d.setTargetDensity((int) (density * (density * 1.0f / 240)));
		return d;
	}

	protected void addToWindow(Context context, int paramX, int paramY) {
		// 读取上次退出时的位置坐标，如果没有数据，默认为参数坐标
/*		SharedPreferences saveXY = context.getSharedPreferences("savaXY",
				Context.MODE_PRIVATE);
		int iconY = saveXY.getInt("y", paramY);
		int iconX = saveXY.getInt("x", paramX);
//		MyLog.d("读取上次位置--x:" + iconX +"---y:"+iconY);
		if (!isExist) {
			isExist = true;
			controllerView.addToWindow(iconX, iconY);
			}

		if (imageButton != null) {
			imageButton.setBackgroundDrawable(Utils.getStateListDrawable(
					mContext, "float_icon.png", "float_icon2.png"));
		}
		controllerView.setVisibility(View.VISIBLE);
		controllerView.initStatusBaHeight(context);
//		MyLog.d("iconX--->" + iconX);
//		MyLog.d("iconY--->" + iconY);
		//更新位置
		mHandler.postDelayed(runnable, 3000);*/
		
		// 读取上次退出时的位置坐标，如果没有数据，默认为参数坐标
		SharedPreferences saveXY = context.getSharedPreferences("savaXY",
                                                                        Context.MODE_PRIVATE);
		int iconY = saveXY.getInt("y", paramY);
		int iconX = saveXY.getInt("x", paramX);
		if (!isExist) {
			isExist = true;
			controllerView.addToWindow(iconX, iconY);
			}

		if (imageButton != null) {
			imageButton.setBackgroundDrawable(Utils.getStateListDrawable(
					mContext, "float_icon.png", "float_icon2.png"));
		}
		controllerView.setVisibility(View.VISIBLE);
		controllerView.initStatusBaHeight(context);
		//更新位置
		mHandler.postDelayed(runnable, 3000);
	}

	private Bitmap getBitmap(Context ctx, String path) {
		synchronized (table) {
			if (table.containsKey(path)) {
				return table.get(path);
			}
			InputStream in = null;
			try {
				in = ctx.getAssets().open(path);
				Bitmap bitmap = BitmapFactory.decodeStream(in);
				table.put(path, bitmap);
				return bitmap;
			} catch (IOException e) {
				return Bitmap.createBitmap(50, 50, Bitmap.Config.RGB_565);
			} finally {
				if (in != null) {
					try {
						in.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	public NinePatchDrawable getNinePatchDrawable(Context ctx, String path) {
		Bitmap bm;

		synchronized (table) {
			try {
				if (table.containsKey(path)) {
					bm = table.get(path);
				} else {
					bm = BitmapFactory.decodeStream(ctx.getAssets().open(path));
					table.put(path, bm);

				}

				byte[] chunk = bm.getNinePatchChunk();
				boolean isChunk = NinePatch.isNinePatchChunk(chunk);
				if (!isChunk) {
					return null;
				}
				Rect rect = new Rect();
				NinePatchChunk npc = NinePatchChunk.deserialize(chunk);
				NinePatchDrawable d = new NinePatchDrawable(bm, chunk,
                                                                            npc.mPaddings, null);
				d.getPadding(rect);
				return d;
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
	}

	static class NinePatchChunk {

		public static final int NO_COLOR = 0x00000001;
		public static final int TRANSPARENT_COLOR = 0x00000000;

		public Rect mPaddings = new Rect();

		public int mDivX[];
		public int mDivY[];
		public int mColor[];

		private static void readIntArray(int[] data, ByteBuffer buffer) {
			for (int i = 0, n = data.length; i < n; ++i) {
				data[i] = buffer.getInt();
			}
		}

		private static void checkDivCount(int length) {
			if (length == 0 || (length & 0x01) != 0) {
				throw new RuntimeException("invalid nine-patch: " + length);
			}
		}

		public static NinePatchChunk deserialize(byte[] data) {
			ByteBuffer byteBuffer = ByteBuffer.wrap(data).order(
                            ByteOrder.nativeOrder());

			byte wasSerialized = byteBuffer.get();
			if (wasSerialized == 0)
				return null;

			NinePatchChunk chunk = new NinePatchChunk();
			chunk.mDivX = new int[byteBuffer.get()];
			chunk.mDivY = new int[byteBuffer.get()];
			chunk.mColor = new int[byteBuffer.get()];

			checkDivCount(chunk.mDivX.length);
			checkDivCount(chunk.mDivY.length);

			// skip 8 bytes
			byteBuffer.getInt();
			byteBuffer.getInt();

			chunk.mPaddings.left = byteBuffer.getInt();
			chunk.mPaddings.right = byteBuffer.getInt();
			chunk.mPaddings.top = byteBuffer.getInt();
			chunk.mPaddings.bottom = byteBuffer.getInt();

			// skip 4 bytes
			byteBuffer.getInt();

			readIntArray(chunk.mDivX, byteBuffer);
			readIntArray(chunk.mDivY, byteBuffer);
			readIntArray(chunk.mColor, byteBuffer);
			return chunk;
		}
	}

	public class MyCustomView extends View {

		public MyCustomView(Context context) {
			super(context);
		}

	}
	//
}