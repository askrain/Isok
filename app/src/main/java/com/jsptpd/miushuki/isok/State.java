package com.jsptpd.miushuki.isok;

/**
 * Created by MIUSHUKI on 2017/6/14.
 * State为进行差异化比较的对象。每一次action都要先对比State然后判断做出反应
 */

public class State {
    public String section1;
    public String section2;
    public String section3;

    public State() {
    }

    public State(String section1, String section2, String section3) {
        this.section1 = section1;
        this.section2 = section2;
        this.section3 = section3;
    }

    @Override
    public boolean equals(Object obj) {
       if (!(obj instanceof State)){
            throw  new RuntimeException("类型错误");
       }
           State state=(State)obj;

        return this.section1.equals(state.section1)&&this.section2.equals(state.section2)&&this.section3.equals(state.section3);
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public String getSection1() {
        return section1;
    }

    public void setSection1(String section1) {
        this.section1 = section1;
    }

    public String getSection2() {
        return section2;
    }

    public void setSection2(String section2) {
        this.section2 = section2;
    }

    public String getSection3() {
        return section3;
    }

    public void setSection3(String section3) {
        this.section3 = section3;
    }
}
