/*
package com.mygdx.game.GameSets;


import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

    public class LogoClass extends ApplicationAdapter {
        private GGame parent;

        private SpriteBatch batch;
        private Texture  img;
        private static final int LOADING_STATUS = 0;
        private static final int STARTED_STATUS = 1;
        private int status;
        private Rectangle logoImage;
        private float alpha = 0;
        private boolean fadein = true;
        private float timeRate = 1f / 120f;

        OrthographicCamera camera = null;
        ExtendViewport viewport;
        Texture texture = null;

        @Override
        public void resize(int width, int height) {
            // TODO Auto-generated method stub
            // super.resize(width, height);
            viewport.update(width, height, true);
            batch.setProjectionMatrix(camera.combined);
        }

        @Override
        public void pause() {
            // TODO Auto-generated method stub
            super.pause();
        }

        @Override
        public void resume() {
            // TODO Auto-generated method stub
            super.resume();
        }

        @Override
        public void create () {
            int w;
            int h;

            batch = new SpriteBatch();
            texture = new Texture(Gdx.files.internal("isd_logo-500x500.png"));
            w = Gdx.graphics.getWidth();
            h = Gdx.graphics.getHeight();
            camera = new OrthographicCamera(w, h);
            viewport = new ExtendViewport(800, 600, camera);
            camera.position.set(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2, 0);
            camera.update();

            logoImage = new Rectangle();
            logoImage.width = 300;
            logoImage.height = 300;
            logoImage.x = Gdx.graphics.getWidth() / 2 - logoImage.width / 2;
            logoImage.y = Gdx.graphics.getHeight() / 2 - logoImage.height / 2;

            status = LOADING_STATUS;

        }

        @Override
        public void render () {
            if (status == LOADING_STATUS)
                drawLogo();
            else if (status == STARTED_STATUS) {
                Gdx.gl.glClearColor(1, 0, 0, 1);
                Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


                // TO DO
            }
        }

        @Override
        public void dispose () {
            batch.dispose();
            texture.dispose();
        }

        private void drawLogo() {
            Gdx.gl.glClearColor(200, 200, 200, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            batch.begin();
            if (fadein) {
                alpha += timeRate;
                if (alpha + timeRate > 1) {
                    fadein = false;
                    alpha = 1;
                }
            } else if (alpha - timeRate > 0) {
                alpha -= timeRate;
            } else {
                alpha = 0;
                status = STARTED_STATUS;

            }
            batch.setColor(1.0f, 1.0f, 1.0f, alpha);

            batch.draw(texture, camera.position.x - (logoImage.width / 2),
                    camera.position.y - (logoImage.height / 2), logoImage.width, logoImage.height);
            batch.end();
        }
    }

}
*/