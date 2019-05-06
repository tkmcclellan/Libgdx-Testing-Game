package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.HashMap;

public class AnimationController {
    private HashMap<String, AnimationWrapper> animations;
    private AnimationWrapper currentWrapper = null;
    private TextureAtlas atlas;
    private String atlasName;
    private float time = 0;

    public class AnimationWrapper {
        public Animation<TextureRegion> animation;
        public String name;
        public float speedup;
        public boolean looping;

        public AnimationWrapper (Animation<TextureRegion> a, String n, float s, boolean l) {
            name = n;
            animation = a;
            speedup = s;
            looping = l;
        }
    }

    public AnimationController(TextureAtlas a, String aName) {
        animations = new HashMap<String, AnimationWrapper>();
        atlas = a;
        atlasName = aName;
    }

    public void addAnimation(String name, int[] frames, int offset, float speed, boolean looping) {
        TextureRegion[] regions = new TextureRegion[frames.length];
        for (int i = 0; i < frames.length; i++) {
            regions[i] = atlas.findRegion(atlasName, offset + frames[i]);
        }

        Animation<TextureRegion> animation = new Animation<TextureRegion>(speed, regions);
        AnimationWrapper wrapper = new AnimationWrapper(animation, name, 1, looping);
        animations.put(name, wrapper);
        if (currentWrapper == null) currentWrapper = wrapper;
    }

    public boolean changeAnimation(String key) {
        AnimationWrapper wrapper = animations.get(key);

        if (wrapper != null && wrapper != currentWrapper) {
            currentWrapper = wrapper;
            return true;
        } else {
            return false;
        }
    }

    public TextureRegion getKeyFrame() {
        return currentWrapper.animation.getKeyFrame(time*currentWrapper.speedup, currentWrapper.looping);
    }

    public boolean updateSpeedup(String key, float speedup) {
        AnimationWrapper wrapper = animations.get(key);
        if (wrapper != null) {
            wrapper.speedup = speedup;
            return true;
        } else {
            return false;
        }
    }

    public boolean updateLooping(String key, boolean looping) {
        AnimationWrapper wrapper = animations.get(key);
        if (wrapper != null) {
            wrapper.looping = looping;
            return true;
        } else {
            return false;
        }
    }

    public boolean isAnimationFinished() {
        return currentWrapper.animation.isAnimationFinished(time);
    }

    public void setTime(float t) {
        time = t;
    }

    public float getTime() {
        return time;
    }

    public AnimationWrapper getCurrentWrapper() {
        return currentWrapper;
    }
}
