package at.ac.tuwien.ims.towardsthelight;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;

public class Sprite {

    private Bitmap bitmap;
    private int x;
    private int y;
    public int frameWidth;
    public int frameHeight;
    private int totalFrames;
    private float frameDisplayDuration;
    private float elapsedTime;
    private Rect sourceRect;
    private RectF targetRect;

    public int startFrame;
    public int endFrame;
    public boolean loop = false;

    private boolean done = false;

    public Sprite(Bitmap bitmap, int x, int y, int frameWidth, int frameHeight, int totalFrames, float frameDisplayDuration) {
        this.bitmap = bitmap;
        this.x = x;
        this.y = y;
        this.frameWidth = frameWidth;
        this.frameHeight = frameHeight;
        this.totalFrames = totalFrames;
        this.frameDisplayDuration = frameDisplayDuration;

        elapsedTime = 0;
        this.startFrame = 0;
        this.endFrame = totalFrames;

        sourceRect = new Rect(0, 0, frameWidth, frameHeight);
        targetRect = new RectF(x, y, x + frameWidth, y + frameHeight);
    }

    public void update(float delta) {
        elapsedTime += delta;

        int frameNumber = (int)(elapsedTime / frameDisplayDuration);

        if (loop || frameNumber < endFrame - startFrame) {
            int frameIndex = frameNumber % (endFrame - startFrame) + startFrame;

            sourceRect.left = frameIndex * frameWidth;
            sourceRect.right = sourceRect.left + frameWidth;
        } else {
            // end of animation
            done = true;
        }
    }

    public void draw(Canvas canvas) {
        if (!done) {
            canvas.drawBitmap(bitmap, sourceRect, targetRect, null);
        }
    }

    public void setPosition(int x, int y) {
        targetRect.set(x, y, x + frameWidth, y + frameHeight);
    }

    public void reset() {
        elapsedTime = 0;
        done = false;
        sourceRect.left = 0;
        sourceRect.right = frameWidth;
    }

    public boolean isDone() {
        return done;
    }
}
