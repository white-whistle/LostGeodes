package com.bajookie.lost_geodes.util.sided;

public abstract class ShiftingUtil {
    public static ShiftingUtil IMPL = new ShiftingUtil() {
        @Override
        public boolean get() {
            return false;
        }
    };

    public abstract boolean get();

    public static boolean isShifting() {
        return IMPL.get();
    }
}
