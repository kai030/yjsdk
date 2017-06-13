package com.yj.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.NinePatch;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.graphics.drawable.NinePatchDrawable;
import android.text.TextPaint;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

import com.yj.entity.Constants;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.HashMap;
import java.util.Map;


public class BitmapCache {

	public static Map<String, Bitmap> table = new HashMap<String, Bitmap>();

	private static int density;

	public static Bitmap getBitmap(Context ctx, String path) {
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
				return Bitmap.createBitmap(50, 50, Config.RGB_565);
			} finally {
				if (in != null) {
					try {
						in.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}

	public static Drawable getDrawable(Context context,int resId,int x,int y){
		Drawable drawable = context.getResources().getDrawable(resId);
		drawable.setBounds(0,0,DimensionUtil.dip2px(context,x),DimensionUtil.dip2px(context,y));
		return drawable;
	}

	public static Drawable getDrawable(Context ctx, String path) {
		Bitmap bitmap = getBitmap(ctx, path);
		return getDrawable(ctx, bitmap);
	}

	public static Drawable getDrawable(Context ctx, Bitmap bitmap) {

		if (density == 0) {
			DisplayMetrics metrics = new DisplayMetrics();
			WindowManager wm = (WindowManager) ctx
					.getSystemService(Context.WINDOW_SERVICE);
			wm.getDefaultDisplay().getMetrics(metrics);
			density = metrics.densityDpi;
		}

		BitmapDrawable d = new BitmapDrawable(bitmap);
		d.setTargetDensity((int) (density * (density * 1.0f / 550)));
		return d;
	}

	public static void remove(Bitmap bitmap) {
		synchronized (table) {

			for (String key : table.keySet()) {
				Bitmap bitmap2 = table.get(key);
				if (bitmap2 == bitmap) {
					table.remove(key);

					return;
				}
			}
		}
	}

	/**
	 * 循环遍历 释放资源
	 *
	 * @param
	 */

	private static void recycle() {
		Bitmap bitmap = null;
		synchronized (table) {
			for (String key : table.keySet()) {
				bitmap = table.get(key);
				{
					if (null != bitmap && !bitmap.isRecycled()) {
						bitmap.recycle();
						bitmap = null;
					}
				}
			}
		}
	}

	public static void clear() {
		synchronized (table) {

			recycle();
			table.clear();
			System.gc();
		}
	}

	public static Bitmap getBitmapForSDCard(Context ctx, String path) {
		synchronized (table) {

			if (table.containsKey(path)) {
				return table.get(path);
			}

			InputStream in = null;
			try {
				File target = new File(path);
				if (!target.exists() || target.isDirectory()) {
					return null;
				}
				Bitmap bm = BitmapFactory.decodeFile(path);
				table.put(path, bm);
				return bm;
			} catch (Exception e) {
				return Bitmap.createBitmap(50, 50, Config.RGB_565);
			} finally {
				if (in != null) {
					try {
						in.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}

	public static Drawable getDrawableForSDCard(Context ctx, String path) {
		Bitmap bitmap = getBitmapForSDCard(ctx, path);
		return getDrawable(ctx, bitmap);
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

	public static NinePatchDrawable getNinePatchDrawable(Context ctx,
                                                             String path) {
		Bitmap bm;

		synchronized (table) {

			try {
				if (table.containsKey(path)) {
					bm = table.get(path);
				} else {
					bm = BitmapFactory.decodeStream(ctx.getAssets().open(
							Constants.ASSETS_RES_PATH + path));
					table.put(path, bm);

				}

				byte[] chunk = bm.getNinePatchChunk();
				boolean isChunk = NinePatch.isNinePatchChunk(chunk);
				if (!isChunk) {
					return null;
				}
				Rect rect = new Rect();
				// rect.left = 20;
				// rect.top = 20;
				// rect.right = 20;
				// rect.bottom = 20;
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

	/**
	 * 图片圆角切换
	 *
	 * @param bitmap
	 * @param
	 * @return
	 */
	public static Drawable toRoundCorner(Bitmap bitmap, int pixelsX, int color) {
		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                                                    bitmap.getHeight(), Config.ARGB_8888);

		Canvas canvas = new Canvas(output);

		// final int color = 0xff424242;

		final Paint paint = new Paint();

		final Rect rect = new Rect(0, 0, bitmap.getWidth() / 2,
                                           bitmap.getHeight());

		final RectF rectF = new RectF(rect);

		final float roundpX = pixelsX;
		final float roundpY = pixelsX;

		paint.setAntiAlias(true);

		canvas.drawARGB(0, 0, 0, 0);

		paint.setColor(color);

		canvas.drawRoundRect(rectF, roundpX, roundpY, paint);

		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));

		canvas.drawBitmap(bitmap, rect, rect, paint);

		return new BitmapDrawable(output);

	}

	// //颜色值转换为bitmap
	// public static Bitmap colorToBitmap(int color){
	// Drawable draw = new ColorDrawable(color);
	// BitmapDrawable bitmapDrawable =new ;
	// Bitmap bitmap = bitmapDrawable.getBitmap();
	// return bitmap;
	// }
	/**
	 * 颜色图片在边框效果
	 *
	 * @param context
	 * @return
	 */
	public static SampleViewUtil getInstance(Context context) {
		return new SampleViewUtil(context);
	}

	/**
	 * @tree
	 *
	 */
	public static class SampleViewUtil extends View {
		public static GradientDrawable mDrawable;

		/**
		 * @param context
		 */
		public SampleViewUtil(Context context) {
			super(context);
			// TODO Auto-generated constructor stub
		}

		/**
		 * 获取圆角 mDrawable
		 *
		 * @param color
		 *            颜色值
		 * @param tpye
		 *            需要圆角的类型
		 * @param apha
		 *            透明图（不需要传0）
		 * @return
		 */
		public static GradientDrawable getCorner(int color, int tpye, int apha) {
			mDrawable = new GradientDrawable(Orientation.TL_BR, new int[] {color, color });
			mDrawable.setShape(GradientDrawable.RECTANGLE);
			mDrawable.setGradientRadius((float) (Math.sqrt(2) * 60));
			float r = 12;
			// 上面两个圆角
			if (tpye == 1) {

				mDrawable = setCornerRadii(mDrawable, r, r, 0, 0);
			}
			// 下面两个圆角
			if (tpye == 2) {
				mDrawable = setCornerRadii(mDrawable, 0, 0, r, r);

			}
			// 右边两个圆角
			if (tpye == 3) {

				mDrawable = setCornerRadii(mDrawable, 0, r, r, 0);

			}
			// 左边两个圆角
			if (tpye == 4) {
				mDrawable = setCornerRadii(mDrawable, r, 0, 0, r);
			}
			// “\两个”
			if (tpye == 5) {

				mDrawable = setCornerRadii(mDrawable, r, 0, r, 0);

			}
			// “/壮两个角”
			if (tpye == 6) {

				mDrawable = setCornerRadii(mDrawable, 0, r, 0, r);
			}
			if (tpye == 7) {
				mDrawable = setCornerRadii(mDrawable, r, r, r, r);

			}
			if (apha != 0) {
				mDrawable.setAlpha(apha);
			}
			return mDrawable;

		};
		/**
		 * 渐变颜色并且圆角
		 * @param beginColor
		 * @param endColor
		 * @param tpye
		 * @param apha
		 * @return
		 */
		public static GradientDrawable getGradientCorner(int beginColor, int endColor, int tpye, int apha) {
			mDrawable = new GradientDrawable(Orientation.TOP_BOTTOM, new int[] {beginColor, endColor});
			mDrawable.setShape(GradientDrawable.RECTANGLE);
			mDrawable.setGradientRadius((float) (Math.sqrt(2) * 30));
			float r = 12;
			// 上面两个圆角
			if (tpye == 1) {

				mDrawable = setCornerRadii(mDrawable, r, r, 0, 0);
			}
			// 下面两个圆角
			if (tpye == 2) {
				mDrawable = setCornerRadii(mDrawable, 0, 0, r, r);

			}
			// 右边两个圆角
			if (tpye == 3) {

				mDrawable = setCornerRadii(mDrawable, 0, r, r, 0);

			}
			// 左边两个圆角
			if (tpye == 4) {
				mDrawable = setCornerRadii(mDrawable, r, 0, 0, r);
			}
			// “\两个”
			if (tpye == 5) {

				mDrawable = setCornerRadii(mDrawable, r, 0, r, 0);

			}
			// “/壮两个角”
			if (tpye == 6) {

				mDrawable = setCornerRadii(mDrawable, 0, r, 0, r);
			}
			if (tpye == 7) {
				mDrawable = setCornerRadii(mDrawable, r, r, r, r);

			}
			if (apha != 0) {
				mDrawable.setAlpha(apha);
			}
			return mDrawable;

		};
		static GradientDrawable setCornerRadii(GradientDrawable drawable,
                                                       float r0, float r1, float r2, float r3) {
			drawable.setCornerRadii(new float[] { r0, r0, r1, r1, r2, r2, r3,
					r3 });
			return drawable;
		}

	}

	/**
	 * 将颜色的drawable 转化为 bitmap
	 *
	 * @param drawable
	 * @return
	 */
	public static Bitmap drawableToBitmap(Drawable drawable, int x, int y) {

		Bitmap bitmap = Bitmap
                    .createBitmap(x, y, drawable.getOpacity() != PixelFormat.OPAQUE ? Config.ARGB_8888 : Config.RGB_565);

		Canvas canvas = new Canvas(bitmap);

		// canvas.setBitmap(bitmap);

		drawable.setBounds(0, 0, x, y);

		drawable.draw(canvas);

		return bitmap;

	}

	/**
	 * 给图片加水印效果
	 * 
	 * @param src
	 * @param watermark
	 * @param x
	 * @param y
	 * @return
	 */
	public static Bitmap watermarkBitmap(Bitmap src, Bitmap watermark, int x,
                                             int y, String title) {
		if (src == null) {
			return null;
		}

		int w = x;
		int h = y;
		// 需要处理图片太大造成的内存超过的问题,这里我的图片很小所以不写相应代码了
		Bitmap newb = Bitmap.createBitmap(w, h, Config.ARGB_8888);// 创建一个新的和SRC长度宽度一样的位图
		Canvas cv = new Canvas(newb);
		cv.drawBitmap(src, 0, 0, null);// 在 0，0坐标开始画入src
		Paint paint = new Paint();
		// 加入图片
		if (watermark != null) {
			int ww = watermark.getWidth();
			int wh = watermark.getHeight();
			paint.setAlpha(255);
			// 画的位置
			for (int i = 0; i < w / ww + 1; i++) {
				for (int j = 0; j < h / wh + 1; j++) {
					cv.drawBitmap(watermark, i * ww, j * wh, paint);
				}

			}
		}

		if (title != null) {
			String familyName = "宋体";
			Typeface font = Typeface.create(familyName, Typeface.BOLD);
			TextPaint textPaint = new TextPaint();
			textPaint.setColor(Color.RED);
			textPaint.setTypeface(font);
			textPaint.setTextSize(22);
			textPaint.setTextSkewX(10);
			cv.drawText(title, 0, 40, paint);
		}

		cv.save(Canvas.ALL_SAVE_FLAG);// 保存
		cv.restore();// 存储
		return newb;
	}
	
	public static GradientDrawable getGradientDrawable(int beginColor, int endColor){
		GradientDrawable gradientDrawabel = new GradientDrawable(Orientation.TOP_BOTTOM, new int[]{beginColor, endColor});
		return gradientDrawabel;
	}

}
