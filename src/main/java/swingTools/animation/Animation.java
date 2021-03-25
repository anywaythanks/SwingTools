package swingTools.animation;

import swingTools.interfaces.AnimationEvent;

import javax.swing.*;
import java.math.BigDecimal;
import java.math.MathContext;

/**
 * An animation class that changes the value from {@link #startValue} to {@link #endValue}. For {@link #duration} or with {@link #speed}.
 *
 * @author anywaythanks
 * @version 0.1
 */
public class Animation {
    /**
     * Duration(Millisecond) in ({@link #endValue} - {@link #startValue}) divide {@link #speed}. Is set in {@link #speedInDuration()} or {@link #setDuration(double)} .
     */
    private BigDecimal duration;
    /**
     * Speed(Millisecond^-1) animation in ({@link #endValue} - {@link #startValue}) divide {@link #duration}. Is set in {@link #durationInSpeed()} or {@link #setSpeed(BigDecimal)}.
     */
    private BigDecimal speed;
    private BigDecimal startValue, endValue, delayTimer;
    private BigDecimal intermediateValue, addVal;
    private MathContext rounded = MathContext.DECIMAL128;
    private AnimationEvent animationEvent;
    private Status status = Status.NOT_ACTIVE;

    private enum Status {
        ACTIVE, PAUSE, NOT_ACTIVE
    }

    private final Timer timer;

    public Animation(double duration, double startValue, double endValue, AnimationEvent animationEvent) {

        delayTimer = BigDecimal.ONE;
        timer = new Timer(delayTimer.intValue(), actionEvent -> {
            if (this.endValue.compareTo(this.startValue) >= 0) {
                intermediateValue = this.endValue.min(intermediateValue.add(addVal));
            } else {
                intermediateValue = this.endValue.max(intermediateValue.add(addVal));
            }
            this.animationEvent.changeValue(intermediateValue);
            if (intermediateValue.equals(this.endValue)) {
                animationEvent.onEndAnimation();
                stop();
            }

        });
        this.startValue = new BigDecimal(startValue);
        intermediateValue = this.startValue;
        this.endValue = new BigDecimal(endValue);
        setAnimationEvent(animationEvent);
        this.duration = new BigDecimal(duration);
        durationInSpeed();

        recalculateVal();
    }

    public void setStartValue(double startValue) {
        setStartValue(new BigDecimal(startValue));
    }

    public void setStartValue(BigDecimal startValue) {
        this.startValue = startValue;
        recalculateVal();
    }

    public BigDecimal getStartValue() {
        return startValue;
    }

    public void setEndValue(double endValue) {
        setStartValue(new BigDecimal(endValue));
    }

    public void setEndValue(BigDecimal endValue) {
        this.endValue = endValue;
        recalculateVal();
    }

    public BigDecimal getEndValue() {
        return endValue;
    }

    public void setDuration(double duration) {
        setDuration(new BigDecimal(duration));
    }

    public void setDuration(BigDecimal duration) {
        this.duration = duration;
        durationInSpeed();
        checkValuesValid();
        recalculateVal();
    }

    public BigDecimal getDuration() {
        return duration;
    }

    public void setSpeed(double speed) {
        setSpeed(new BigDecimal(speed));
    }

    public void setSpeed(BigDecimal speed) {
        this.speed = speed;
        speedInDuration();
        checkValuesValid();
        recalculateVal();
    }

    public BigDecimal getSpeed() {
        return speed;
    }

    public void setDelayTimer(int delayTimer) {
        timer.setDelay(delayTimer);
        timer.setInitialDelay(delayTimer);
        this.delayTimer = new BigDecimal(delayTimer);
        checkValuesValid();
        recalculateVal();
    }

    public int getDelayTimer() {
        return delayTimer.intValue();
    }

    public void setAnimationEvent(AnimationEvent animationEvent) {
        this.animationEvent = animationEvent;
    }

    public void start() {
        if (status == Status.NOT_ACTIVE) {
            intermediateValue = startValue;
        }
        status = Status.ACTIVE;
        startTimer();
    }

    public void pause() {
        if (status != Status.NOT_ACTIVE) {
            status = Status.PAUSE;
        }
        stopTimer();
    }

    public void stop() {
        intermediateValue = startValue;
        status = Status.NOT_ACTIVE;
        stopTimer();
    }

    public void setRounded(MathContext rounded) {
        this.rounded = rounded;
    }

    private void stopTimer() {
        timer.stop();
    }

    private void startTimer() {
        timer.start();
    }

    private void checkValuesValid() {
        if (delayTimer.compareTo(BigDecimal.ZERO) < 1) {
            throw new IllegalArgumentException("delayTimer must be strictly greater than zero.");
        }
        if (speed.equals(BigDecimal.ZERO)) {
            throw new IllegalArgumentException("speed should not be zero.");
        }
        if (endValue.compareTo(startValue) >= 0 && speed.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("speed must be strictly greater than zero.");
        }
        if (endValue.compareTo(startValue) <= 0 && speed.compareTo(BigDecimal.ZERO) > 0) {
            throw new IllegalArgumentException("speed must be strictly greater than zero.");
        }
    }

    private void recalculateVal() {
        addVal = (endValue.subtract(startValue)).divide(duration.divide(delayTimer, rounded), rounded);
    }

    private void durationInSpeed() {
        speed = (endValue.subtract(startValue)).divide(duration, rounded);
    }

    private void speedInDuration() {
        duration = (endValue.subtract(startValue)).divide(speed, rounded);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Animation{");
        sb.append("duration=").append(duration);
        sb.append(", speed=").append(speed);
        sb.append(", startValue=").append(startValue);
        sb.append(", endValue=").append(endValue);
        sb.append(", delayTimer=").append(delayTimer);
        sb.append(", intermediateValue=").append(intermediateValue);
        sb.append(", addVal=").append(addVal);
        sb.append(", rounded=").append(rounded);
        sb.append(", status=").append(status);
        sb.append(", timer=").append(timer);
        sb.append('}');
        return sb.toString();
    }
}
