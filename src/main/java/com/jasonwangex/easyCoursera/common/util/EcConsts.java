package com.jasonwangex.easyCoursera.common.util;

/**
 * Created by wangjz
 * on 17/5/12.
 */
public class EcConsts {
    public static class Exam {
        public static final int TYPE_DEFAULT = 0;
        public static final int TYPE_ANSWER_NEED_CHECK = 1;
        public static final int TYPE_ANSWER_UNIQUE = 2;    // 表示答案唯一的题目

        public static final int MAX_ANSWER_COUNT = 3;
    }

    public static class Qrcode {
        public static final int TYPE_DEFAULT = 0;
        public static final int TYPE_EXAM = 1;
        public static final int TYPE_TAG = 2;
        public static final int TYPE_TAG_EXAM = 3;
        public static final int TYPE_COURSE = 4;

    }
}
