package com.kt.std.tpluslivewp;

import android.graphics.Canvas;
import android.graphics.Movie;

import android.os.Handler;
import android.service.wallpaper.WallpaperService;
import android.util.Log;
import android.view.SurfaceHolder;

import java.io.IOException;

public class GIFWallpaperService extends WallpaperService {

    @Override
    public WallpaperService.Engine onCreateEngine() {
        try {
            Movie movie = Movie.decodeStream(
                    getResources().getAssets().open("moon4K1.gif"));

            return new GIFWallpaperEngine(movie);
        }catch(IOException e){
            Log.d("GIF", "Could not load asset");
            return null;
        }
    }

    private class GIFWallpaperEngine extends WallpaperService.Engine {
        private final int frameDuration = 20;

        private SurfaceHolder holder;
        private Movie movie;
        private boolean visible;
        private Handler handler;

        public GIFWallpaperEngine(Movie movie) {
            this.movie = movie;
            handler = new Handler();
        }

        @Override
        public void onCreate(SurfaceHolder surfaceHolder) {
            super.onCreate(surfaceHolder);
            this.holder = surfaceHolder;
        }

        private Runnable drawGIF = new Runnable() {
            public void run() {
                draw();
            }
        };

        private void draw() {
            if (visible) {
                Canvas canvas = holder.lockCanvas();
                canvas.save();
                // Adjust size and position so that
                // the image looks good on your screen
                canvas.scale(2.7f, 2.7f);
                movie.draw(canvas, -90, -100);
                canvas.restore();
                holder.unlockCanvasAndPost(canvas);
                if (movie.duration() != 0) movie.setTime((int) (System.currentTimeMillis() % movie.duration()));
                else movie.setTime(10000);

                handler.removeCallbacks(drawGIF);
                handler.postDelayed(drawGIF, frameDuration);
            }
        }

        @Override
        public void onVisibilityChanged(boolean visible) {
            this.visible = visible;
            if (visible) {
                handler.post(drawGIF);
            } else {
                handler.removeCallbacks(drawGIF);
            }
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
            handler.removeCallbacks(drawGIF);
        }
    }

}
