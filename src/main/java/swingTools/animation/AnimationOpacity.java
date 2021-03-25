package swingTools.animation;

import swingTools.interfaces.AnimationEvent;
import swingTools.interfaces.ForbidOpacity;

import java.math.BigDecimal;
import java.math.MathContext;

/**
 * An animation class that makes it possible to use fade in an element through the use of {@link Animation} class and the {@link ForbidOpacity} interface.
 *
 * @author anywaythanks
 * @version 0.1
 */
public class AnimationOpacity<T extends ForbidOpacity> {
    private final Animation animation;
    private static final int START_VAL = 0, END_VAL = 1;
    private BigDecimal accelerationEveryTick = BigDecimal.ONE;
    private T component;
    private boolean reverse;

    AnimationOpacity(T component, double duration) {
        animation = new Animation(duration, START_VAL, END_VAL, null);
        setComponent(component);
    }

    public void setComponent(T component) {
        this.component = component;
        animation.setAnimationEvent(new AnimationEvent() {
            @Override
            public void changeValue(Number newVal) {
                component.setOpacity(newVal.doubleValue());
                if (accelerationEveryTick.compareTo(BigDecimal.ONE) != 0) {
                    animation.setSpeed(animation.getSpeed().multiply(accelerationEveryTick));
                }
            }

            @Override
            public void onEndAnimation() {

            }
        });
    }

    public void setStartValue(double startValue) {
        animation.setStartValue(startValue);
    }

    public void setStartValue(BigDecimal startValue) {
        animation.setStartValue(startValue);
    }

    public BigDecimal getStartValue() {
        return animation.getStartValue();
    }

    public void setEndValue(double endValue) {
        animation.setEndValue(endValue);
    }

    public void setEndValue(BigDecimal endValue) {
        animation.setEndValue(endValue);
    }

    public BigDecimal getEndValue() {
        return animation.getEndValue();
    }

    public void setDuration(double duration) {
        animation.setDuration(duration);
    }

    public void setDuration(BigDecimal duration) {
        animation.setDuration(duration);
    }

    public BigDecimal getDuration() {
        return animation.getDuration();
    }

    public void setSpeed(double speed) {
        animation.setSpeed(speed);
    }

    public void setSpeed(BigDecimal speed) {
        animation.setSpeed(speed);
    }

    public BigDecimal getSpeed() {
        return animation.getSpeed();
    }

    public void setDelayTimer(int delayTimer) {
        animation.setDelayTimer(delayTimer);
    }

    public int getDelayTimer() {
        return animation.getDelayTimer();
    }

    public void setAccelerationEveryTick(double accelerationEveryTick) {
        setAccelerationEveryTick(new BigDecimal(accelerationEveryTick));
    }

    public void setRounded(MathContext rounded) {
        animation.setRounded(rounded);
    }

    public void setAccelerationEveryTick(BigDecimal accelerationEveryTick) {
        if (accelerationEveryTick.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("accelerationEveryTick must be strictly greater than zero.");
        }
        this.accelerationEveryTick = accelerationEveryTick;
    }

    public BigDecimal getAccelerationEveryTick() {
        return accelerationEveryTick;
    }

    public boolean isReverse() {
        return reverse;
    }

    public void setReverse(boolean reverse) {
        this.reverse = reverse;
        animation.setStartValue(this.reverse ? END_VAL : START_VAL);
        animation.setEndValue(this.reverse ? START_VAL : END_VAL);
    }

    public void start() {
        component.setDisable(!reverse);
        animation.start();
    }

    public void pause() {
        animation.pause();
    }

    public void stop() {
        animation.stop();
    }
}
