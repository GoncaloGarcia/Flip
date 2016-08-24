package com.jtbgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by ggarc_000 on 23/06/2016.
 */
public class Hero {

    int width;
    int height;
    Vector2 position;
    Vector2 speed;
    Vector2 acceleration;
    Vector2 tmp;
    Rectangle hitbox;
    int gravity;
    float rotation;
    GameWorld world;
    Vector2 futurepos = new Vector2(0,0);
    boolean isJumping;
    float elapsed;
    boolean upDown;


    public Hero(int x, int y, int width, int height, GameWorld world) {
        this.width = width;
        this.height = height;
        position = new Vector2(x,y);
        speed = new Vector2(0,0);


        acceleration = new Vector2(0,-980);

        hitbox = new Rectangle(x,y,width,height);
        tmp = new Vector2(0,0);
        this.world = world;

        Gdx.input.setInputProcessor(new GestureDetector(new GestureDetector.GestureListener() {
            @Override
            public boolean touchDown(float x, float y, int pointer, int button) {

                return false;
            }

            @Override
            public boolean tap(float x, float y, int count, int button) {
                jump();
                System.out.println("jump");
                return true;
            }

            @Override
            public boolean longPress(float x, float y) {
                return false;
            }

            @Override
            public boolean fling(float velocityX, float velocityY, int button) {
                System.out.println("fling");
                if(Math.abs(velocityX)<Math.abs(velocityY)){
                    if(velocityY < 0 && !upDown){
                        flip();
                        System.out.println("flipDown");
                    }else if (velocityY > 0 && upDown){
                        flip();
                        System.out.println("flipUp");
                    }
                }
                return true;
            }

            //<editor-fold desc="Description">
            @Override
            public boolean pan(float x, float y, float deltaX, float deltaY) {
                return false;
               }

            @Override
            public boolean panStop(float x, float y, int pointer, int button) {
                return false;
            }

            @Override
            public boolean zoom(float initialDistance, float distance) {
                return false;
            }

            @Override
            public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
                return false;
            }

            @Override
            public void pinchStop() {

            }
            //</editor-fold>
        }));

    }

    public void update(float delta){
        tmp.set(acceleration);
        tmp.scl(delta);
        speed.add(tmp);

        if(Math.abs(speed.y) > 600){
            if(speed.y > 0) {
                speed.y = 600;
            }else{
                speed.y = -600;
            }
        }
        if(isJumping){
            rotation -=5.5;
        } else {
            rotation = 0;
        }

        elapsed += delta;
        tmp.set(speed);
        tmp.scl(delta);
        position.add(tmp);
        futurepos.set(position);
        futurepos.sub(tmp);
        futurepos.sub(tmp);
        hitbox.setPosition(position.x, position.y);

        if (position.y < -50){
            position.y = -50;
            world.dead = true;
        }

        if(position.y > 400){
            position.y = 400;
            world.dead = true;
        }

        for(Platform plat : world.plats){
            plat.checkHeroColision(this);
        }


        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            jump();

        }

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            flip();

        }


    }

    public void jump(){
        if(!isJumping) {
            isJumping = true;
            if(speed.y < 0){
                speed.y = 400;
            } else {
                speed.y = -400;
            }

        }

    }

    public void flip(){
        if(!isJumping) {
            upDown = !upDown;
            elapsed = 0;
            isJumping = true;
            acceleration.y *= -1;
            speed.y = acceleration.y;
        }
    }
}
