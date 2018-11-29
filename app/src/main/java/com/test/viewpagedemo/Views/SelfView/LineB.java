package com.test.viewpagedemo.Views.SelfView;

public class LineB {
    String value;
    String bottomLeftText;
    String bottomRightText;
    float x;

    public LineB() {
    }

    public LineB(String value, String bottomLeftText, String bottomRightText) {
        this.value = value;
        this.bottomLeftText = bottomLeftText;
        this.bottomRightText = bottomRightText;
    }

    @Override
    public String toString() {
        return "LineB{" +
                "value='" + value + '\'' +
                ", bottomLeftText='" + bottomLeftText + '\'' +
                ", bottomRightText='" + bottomRightText + '\'' +
                ", x=" + x +
                '}';
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getBottomLeftText() {
        return bottomLeftText;
    }

    public void setBottomLeftText(String bottomLeftText) {
        this.bottomLeftText = bottomLeftText;
    }

    public String getBottomRightText() {
        return bottomRightText;
    }

    public void setBottomRightText(String bottomRightText) {
        this.bottomRightText = bottomRightText;
    }

}
