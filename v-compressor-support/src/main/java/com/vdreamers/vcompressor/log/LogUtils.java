package com.vdreamers.vcompressor.log;

import android.util.Log;

import com.vdreamers.vcompressor.BuildConfig;

/**
 * 日志工具类
 * <p>
 * date 2019/12/09 14:26:54
 *
 * @author <a href="mailto:codepoetdream@gmail.com">Mr.D</a>
 */
@SuppressWarnings({"unused", "WeakerAccess", "ConstantConditions"})
public class LogUtils {
    public static final int VERBOSE = 1;
    public static final int DEBUG = 2;
    public static final int INFO = 3;
    public static final int WARN = 4;
    public static final int ERROR = 5;
    public static final int NOTHING = 6;
    private static int level = BuildConfig.DEBUG ? DEBUG : NOTHING;

    public static final String TAG = LogUtils.class.getSimpleName();

    private LogUtils() {
        // cannot be instantiated
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public static void setLevel(int level) {
        LogUtils.level = level;
    }

    public static void v(String tag, String msg) {
        if (level <= VERBOSE) {
            Log.v(tag, msg);
        }
    }

    public static void d(String tag, String msg) {
        if (level <= DEBUG) {
            Log.d(tag, msg);
        }
    }

    public static void i(String tag, String msg) {
        if (level <= INFO) {
            Log.i(tag, msg);
        }
    }

    public static void w(String tag, String msg) {
        if (level <= WARN) {
            Log.w(tag, msg);
        }
    }

    public static void e(String tag, String msg) {
        if (level <= ERROR) {
            Log.e(tag, msg);
        }
    }

    public static void v(String msg) {
        if (level <= VERBOSE) {
            Log.v(TAG, msg);
        }
    }

    public static void d(String msg) {
        if (level <= DEBUG) {
            Log.d(TAG, msg);
        }
    }

    public static void i(String msg) {
        if (level <= INFO) {
            Log.i(TAG, msg);
        }
    }

    public static void w(String msg) {
        if (level <= WARN) {
            Log.w(TAG, msg);
        }
    }

    public static void e(String msg) {
        if (level <= ERROR) {
            Log.e(TAG, msg);
        }
    }

    public static void v(Exception e) {
        if (level <= VERBOSE && e != null) {
            Log.v(TAG, e.getLocalizedMessage());
        }
    }

    public static void d(Exception e) {
        if (level <= DEBUG && e != null) {
            Log.d(TAG, e.getLocalizedMessage());
        }
    }

    public static void i(Exception e) {
        if (level <= INFO && e != null) {
            Log.i(TAG, e.getLocalizedMessage());
        }
    }

    public static void w(Exception e) {
        if (level <= WARN && e != null) {
            Log.w(TAG, e.getLocalizedMessage());
        }
    }

    public static void e(Exception e) {
        if (level <= ERROR && e != null) {
            Log.e(TAG, e.getLocalizedMessage());
        }
    }
}