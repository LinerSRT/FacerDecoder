package ru.liner.facerdecoder;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


@SuppressWarnings({"DuplicateBranchesInSwitch", "EnhancedSwitchMigration", "SpellCheckingInspection"})
public class TimeEvaluator extends VariableEvaluator {
    private static final String[] hourText = new String[]{
            "ONE",
            "TWO",
            "THREE",
            "FOUR",
            "FIVE",
            "SIX",
            "SEVEN",
            "EIGHT",
            "NINE",
            "TEN",
            "ELEVEN",
            "TWELVE",
            "THIRTEEN",
            "FOURTEEN",
            "FIFTEEN",
            "SIXTEEN",
            "SEVENTEEN",
            "EIGHTEEN",
            "NINETEEN",
            "TWENTY",
            "TWENTY ONE",
            "TWENTY TWO",
            "TWENTY THREE",
            "TWENTY FOUR"
    };
    private static final String[] minuteText = new String[]{
            "TEN",
            "TWENTY",
            "THIRTY",
            "FORTY",
            "FIFTY"
    };
    private final Calendar calendar;

    public TimeEvaluator() {
        this.calendar = Calendar.getInstance();
    }

    @Override
    public void update(long currentTimeMillis) {
        calendar.setTimeInMillis(System.currentTimeMillis());
        super.update(currentTimeMillis);
    }

    @Override
    public String evaluateVariable(String variable) {
        String variableToCheck = variable.contains(VARIABLE_ENCODER) ? variable.replace(VARIABLE_ENCODER, "") : variable;
        switch (variableToCheck) {
            case "DWE":
                return "5.12"; 
            case "DNOW":
                return String.valueOf(System.currentTimeMillis()); 
            case "DSYNC":
                return String.valueOf(System.currentTimeMillis()); 
            case "Dy":
                return fromDateFormat("y"); 
            case "Dyy":
                return fromDateFormat("yy"); 
            case "Dyyyy":
                return fromDateFormat("yyyy"); 
            case "DM":
                return fromDateFormat("M"); 
            case "DMM":
                return fromDateFormat("M", true); 
            case "DMMM":
                return fromDateFormat("MMM"); 
            case "DMMMM":
                return fromDateFormat("MMMM"); 
            case "DW":
                return String.valueOf(calendar.get(Calendar.WEEK_OF_MONTH)); 
            case "Dw":
                return String.valueOf(calendar.get(Calendar.WEEK_OF_YEAR)); 
            case "DD":
                return String.valueOf(calendar.get(Calendar.DAY_OF_YEAR)); 
            case "Dd":
                return String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)); 
            case "DdL":
                return String.format("%02d", calendar.get(Calendar.DAY_OF_MONTH)); 
            case "DIM":
                return String.valueOf(calendar.getActualMaximum(Calendar.DAY_OF_MONTH)); 
            case "DIY":
                return String.valueOf(calendar.getActualMaximum(Calendar.DAY_OF_YEAR)); 
            case "DISLEAPYEAR":
                return String.valueOf(calendar.getActualMaximum(Calendar.DAY_OF_YEAR) > 365); 
            case "DE":
                return fromDateFormat("E"); 
            case "DES":
                return String.valueOf(fromDateFormat("E").charAt(0)); 
            case "#DOW#":
                return String.valueOf((calendar.get(Calendar.DAY_OF_WEEK) + 5) % 7); 
            case "#DOWB#":
                return String.valueOf((calendar.get(Calendar.DAY_OF_WEEK) + 5) % 7 + 1); 
            case "DEEEE":
                return fromDateFormat("EEEE"); 
            case "DF":
                return String.valueOf(calendar.get(Calendar.DAY_OF_WEEK_IN_MONTH)); 
            case "Da":
                return calendar.getDisplayName(Calendar.AM_PM, Calendar.SHORT, Locale.getDefault()); 
            case "Db":
                return dataProvider.get("is24HourFormat", true) ? fromDateFormat("k") : fromDateFormat("h");
            case "DbZ":
                return dataProvider.get("is24HourFormat", true) ? fromDateFormat("k", true) : fromDateFormat("h", true);
            case "Dh":
                return fromDateFormat("k"); 
            case "Dk":
                return fromDateFormat("h"); 
            case "DH":
                return fromDateFormat("H"); 
            case "DK":
                return fromDateFormat("K"); 
            case "DHZ":
                return fromDateFormat("H", true); 
            case "DkZ":
                return fromDateFormat("K", true); 
            case "DkZA":
                return String.valueOf(fromDateFormat("H", true).charAt(0)); 
            case "DkZB":
                return String.valueOf(fromDateFormat("H", true).charAt(1)); 
            case "DKZ":
                return fromDateFormat("K", true); 
            case "DhZ":
                return fromDateFormat("h", true); 
            case "DhZA":
                return String.valueOf(fromDateFormat("h", true).charAt(0)); 
            case "DhZB":
                return String.valueOf(fromDateFormat("h", true).charAt(1)); 
            case "DhoT":
            case "DWFK":
                return String.valueOf(Time.HOUR.smoothAngle(calendar.getTimeInMillis()) / 30f * 360f);
            case "DhoTb":
            case "DWFH":
                return String.valueOf(Time.HOUR.smoothAngle(calendar.getTimeInMillis()) / 15f * 360f);
            case "DWFKS":
            case "DWFHS":
                return String.valueOf(Time.HOUR.smoothAngle(calendar.getTimeInMillis()));
            case "DhT":
                return (Integer.parseInt(fromDateFormat("K")) % 12 == 0 ? hourText[11] : hourText[((Integer.parseInt(fromDateFormat("K")) % 12) - 1)]);
            case "DkT":
                return (Integer.parseInt(fromDateFormat("H")) % 12 == 0 ? hourText[11] : hourText[((Integer.parseInt(fromDateFormat("H")) % 12) - 1)]);
            case "Dm":
                return fromDateFormat("m");
            case "DmZ":
                return fromDateFormat("m", true);
            case "DWFM":
                return String.valueOf(Time.MINUTE.smoothAngle(calendar.getTimeInMillis()) / 6f * 360f);
            case "DWFMS":
                return String.valueOf(Time.MINUTE.smoothAngle(calendar.getTimeInMillis()));
            case "DmT":
                String minuteTextTemp = "";
                if (Integer.parseInt(fromDateFormat("m")) > 20 || Integer.parseInt(fromDateFormat("m")) == 0) {
                    if (Integer.parseInt(fromDateFormat("m")) != 0) {
                        switch (Integer.parseInt(fromDateFormat("m")) / 10) {
                            case 2:
                                minuteTextTemp = hourText[19];
                                break;
                            case 3:
                                minuteTextTemp = minuteText[2];
                                break;
                            case 4:
                                minuteTextTemp = minuteText[3];
                                break;
                            case 5:
                                minuteTextTemp = minuteText[4];
                                break;
                        }
                        minuteTextTemp = minuteTextTemp + " " + hourText[(Integer.parseInt(fromDateFormat("m")) % 10) - 1];
                    }
                }
                return minuteTextTemp;
            case "DmMT":
                return (Integer.parseInt(fromDateFormat("m")) / 10) >= 2 ? minuteText[(Integer.parseInt(fromDateFormat("m")) / 10) - 1] : "";
            case "DmST":
                if (Integer.parseInt(fromDateFormat("m")) % 10 != 0 && Integer.parseInt(fromDateFormat("m")) / 10 != 1) {
                    return hourText[Integer.parseInt(fromDateFormat("m")) % 10 - 1];
                } else if (Integer.parseInt(fromDateFormat("m")) / 10 == 1) {
                    return hourText[((Integer.parseInt(fromDateFormat("m")) / 10 * 10) + Integer.parseInt(fromDateFormat("m")) % 10) - 1];
                }
            case "Ds":
                return fromDateFormat("s");
            case "DsZ":
                return fromDateFormat("s", true);
            case "Dsm":
                return String.format(Locale.getDefault(), "%.3f", ((double) ((float) Integer.parseInt(fromDateFormat("s")))) + (((double) System.currentTimeMillis() % 1000) * 0.001d));
            case "DseT":
                return String.valueOf(Math.round(Time.SECOND.smoothAngle(calendar.getTimeInMillis()) / 6f * 360f));
            case "DWFS":
                return String.valueOf(Time.SECOND.smoothAngle(calendar.getTimeInMillis()) / 6f * 360f);
            case "DWFSS":
                return String.valueOf(Time.SECOND.smoothAngle(calendar.getTimeInMillis()));
            case "DSMOOTH":
                return "true";
            case "Dz":
                return fromDateFormat("z");
            case "Dzzzz":
                return fromDateFormat("zzzz");
            case "DZ":
                return fromDateFormat("Z");
            case "DZZ":
                return fromDateFormat("ZZ");
            case "#DOFST#":
                return String.valueOf(calendar.getTimeZone().inDaylightTime(calendar.getTime()));
            case "DMYR":
                return String.valueOf(Time.MONTH.angle(calendar.getTimeInMillis()));
            default:
                if (variableToCheck.charAt(0) == TIME_MARKER)
                    return "0";
                return variable;
        }
    }

    private String fromDateFormat(String formatString, boolean leadingZero) {
        SimpleDateFormat format = new SimpleDateFormat(formatString, Locale.US);
        format.setCalendar(calendar);
        return leadingZero ? String.format("%02d", Integer.parseInt(format.format(calendar.getTime()))) : format.format(calendar.getTime());
    }

    private String fromDateFormat(String formatString) {
        return fromDateFormat(formatString, false);
    }
}
