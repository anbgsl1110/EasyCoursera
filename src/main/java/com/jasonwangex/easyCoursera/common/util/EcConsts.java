package com.jasonwangex.easyCoursera.common.util;

/**
 * Created by wangjz
 * on 17/5/12.
 */
public class EcConsts {
    public static class Exam {
        public static final int TYPE_DEFAULT = 0;
        public static final int TYPE_ANSWER_UNIQUE = 1;    // 表示答案唯一的题目
        public static final int TYPE_COMPLEX_CHOSE = 2;
        public static final int TYPE_QUESTION = 3;

        public static final int MAX_ANSWER_COUNT = 3;
    }

    public static class Qrcode {
        public static final int TYPE_DEFAULT = 0;
        public static final int TYPE_ANSWER_EXAM = 1;

    }
}
