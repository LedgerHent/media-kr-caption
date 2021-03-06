package com.kr.caption.designmode.jiketime.jike64Chapter.v3;

import com.kr.caption.designmode.jiketime.jike64Chapter.State;


/**
 * MarioStateMachine 和各个状态类之间是双向依赖关系，各个状态类需要更新 MarioStateMachine 中的两个变量，score 和 currentState。
 */
public class MarioStateMachine {

    private int score;
    private IMario currentState; // 不再使用枚举来表示状态

    public MarioStateMachine() {
        this.score = 0;
        this.currentState = new SmallMario(this);
    }

    public void obtainMushRoom() {
        this.currentState.obtainMushRoom();
    }

    public void obtainCape() {
        this.currentState.obtainCape();
    }

    public void obtainFireFlower() {
        this.currentState.obtainFireFlower();
    }

    public void meetMonster() {
        this.currentState.meetMonster();
    }

    public int getScore() {
        return this.score;
    }

    public State getCurrentState() {
        return this.currentState.getName();
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setCurrentState(IMario currentState) {
        this.currentState = currentState;
    }
}
