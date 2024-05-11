package com.mcyizy.editor.component.function;

public class Util {
    
    //截取文本
    public String InterceptingText(String mText, String mStartText, String mEndText, boolean mIncludeTruncator) {
        int left = mText.indexOf(mStartText);
        if (left == -1) {
            return "";
        }
        int right = mText.indexOf(mEndText, left + mStartText.length());
        if (right == -1) {
            return "";
        }
        String temp;
        if (mIncludeTruncator) {
            temp = Takethemiddleoftext(mText, left, right + mEndText.length() - 1);
        } else {
            temp = Takethemiddleoftext(mText, left + mStartText.length(), right - 1);
        }
        return temp;
    }

    public String Takethemiddleoftext(String Text,int sta, int end) {
        return Text.substring(sta, end + 1);
    }

}
