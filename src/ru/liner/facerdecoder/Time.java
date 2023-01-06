package ru.liner.facerdecoder;

import java.util.Calendar;

public enum Time {
    YEAR {
        @Override
        public int value(long millis) {
            return calendar(millis).get(Calendar.YEAR);
        }

        @Override
        public float angle(long millis) {
            return 0;
        }

        @Override
        public float smoothAngle(long millis) {
            return 0;
        }
    },
    MONTH {
        @Override
        public int value(long millis) {
            return calendar(millis).get(Calendar.MONTH) + 1;
        }

        @Override
        public float angle(long millis) {
            return smoothAngle(millis);
        }

        @Override
        public float smoothAngle(long millis) {
            Calendar calendar = Calendar.getInstance();
            calendar.roll(Calendar.DATE, -1);
            return value(millis) + 1 * 30f + DAY.value(millis) * 30f / calendar.get(Calendar.DATE);
        }
    },
    DAY {
        @Override
        public int value(long millis) {
            return calendar(millis).get(Calendar.DAY_OF_MONTH);
        }

        @Override
        public float angle(long millis) {
            return 0;
        }

        @Override
        public float smoothAngle(long millis) {
            return 0;
        }
    },
    DAY_OF_WEEK {
        @Override
        public int value(long millis) {
            return calendar(millis).get(Calendar.DAY_OF_WEEK);
        }

        @Override
        public float angle(long millis) {
            return smoothAngle(millis) * 360f / 7;
        }

        @Override
        public float smoothAngle(long millis) {
            return (value(millis) + HOUR.value(millis) * 1f / 24);
        }
    },
    HOUR {
        @Override
        public int value(long millis) {
            return calendar(millis).get(Calendar.HOUR_OF_DAY);
        }

        @Override
        public float angle(long millis) {
            return smoothAngle(millis) / 12f * 360f;
        }

        @Override
        public float smoothAngle(long millis) {
            return value(millis) + (MINUTE.value(millis) / 60f) + (SECOND.value(millis) / 60f / 60f);
        }
    },
    MINUTE {
        @Override
        public int value(long millis) {
            return calendar(millis).get(Calendar.MINUTE);
        }

        @Override
        public float angle(long millis) {
            return smoothAngle(millis) / 60f * 360f;
        }

        @Override
        public float smoothAngle(long millis) {
            return value(millis) + (SECOND.value(millis) / 60f);
        }
    },
    SECOND {
        @Override
        public int value(long millis) {
            return calendar(millis).get(Calendar.SECOND);
        }

        @Override
        public float angle(long millis) {
            return smoothAngle(millis) / 60f * 360f;
        }

        @Override
        public float smoothAngle(long millis) {
            return value(millis) + (MILLIS.value(millis) / 1000f);
        }
    },
    MILLIS {
        @Override
        public int value(long millis) {
            return calendar(millis).get(Calendar.MILLISECOND);
        }

        @Override
        public float angle(long millis) {
            return smoothAngle(millis) * 360f;
        }

        @Override
        public float smoothAngle(long millis) {
            return value(millis) / 1000f;
        }
    };

    public abstract int value(long millis);

    public abstract float angle(long millis);

    public abstract float smoothAngle(long millis);

    private static Calendar calendar(long millis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        return calendar;
    }
}
