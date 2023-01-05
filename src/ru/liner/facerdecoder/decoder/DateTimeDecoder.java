package ru.liner.facerdecoder.decoder;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * @author : "Line'R"
 * @mailto : serinity320@mail.com
 * @created : 01.01.2023, воскресенье
 **/
public class DateTimeDecoder extends Decoder {
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
    private boolean is24HourFormat = true;

    public void setIs24HourFormat(boolean is24HourFormat) {
        this.is24HourFormat = is24HourFormat;
    }

    public boolean isIs24HourFormat() {
        return is24HourFormat;
    }

    public DateTimeDecoder(String value) {
        super(value);
    }

    @Override
    public boolean onElement(String element, StringBuilder stringBuilder) {
        if(element.contains("#"))
            element = element.replace("#", "");
        switch (element) {
            case "DWE": //Time elapsed since last watch face view (in seconds with 0.01 parts)
                stringBuilder.append(Double.toString(((double) System.currentTimeMillis()) / 1000.0d).substring(0, 5));
                 return true;
            case "DNOW": //Current timestamp
            case "DSYNC": //Timestamp at which watch face was synced
                long timestamp = System.currentTimeMillis() / 1000;
                stringBuilder.append(timestamp);
                 return true;
            case "Dy": //Year
                stringBuilder.append(getDateForFormat("y"));
                 return true;
            case "Dyy": //Short Year
                stringBuilder.append(getDateForFormat("yy"));
                 return true;
            case "Dyyyy": //Long Year
                stringBuilder.append(getDateForFormat("yyyy"));
                 return true;
            case "DM": //Month in Year (Numeric)
                stringBuilder.append(getDateForFormat("M"));
                 return true;
            case "DMM": //Month in Year (Numeric) with leading 0
                stringBuilder.append(getDateForFormat("MM"));
                 return true;
            case "DMMM": //Month in Year (Short String)
                stringBuilder.append(getDateForFormat("MMM"));
                 return true;
            case "DMMMM": //Month in Year (String)
                stringBuilder.append(getDateForFormat("MMMM"));
                 return true;
            case "DW": //Week in Month
                stringBuilder.append(getDateForFormat("W"));
                 return true;
            case "Dw": //Week in Year
                stringBuilder.append(getDateForFormat("w"));
                 return true;
            case "DD":
                stringBuilder.append(getDateForFormat("DD"));
                 return true;
            case "Dd":
                stringBuilder.append(getDateForFormat("d"));
                 return true;
            case "DdL":
                stringBuilder.append(leadingZero(getDateForFormat("d")));
                 return true;
            case "DIM":
                stringBuilder.append(Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH));
                 return true;
            case "DE":
                stringBuilder.append(getDateForFormat("E"));
                 return true;
            case "DES":
                stringBuilder.append(getDateForFormat("E").charAt(0));
                 return true;
            case "DOW":
                stringBuilder.append(Integer.parseInt(getDateForFormat("u")) - 1);
                 return true;
            case "DOWB":
                stringBuilder.append(getDateForFormat("u"));
                 return true;
            case "DEEEE":
                stringBuilder.append(getDateForFormat("EEEE"));
                 return true;
            case "DF":
                stringBuilder.append(getDateForFormat("F"));
                 return true;
            case "Da":
                stringBuilder.append(getDateForFormat("a"));
                 return true;
            case "Db":
                if (is24HourFormat) {
                    stringBuilder.append(leadingZero(getDateForFormat("k")));
                } else {
                    stringBuilder.append(leadingZero(getDateForFormat("h")));
                }
                 return true;
            case "Dh":
                stringBuilder.append(getDateForFormat("h"));
                 return true;
            case "Dk":
                stringBuilder.append(getDateForFormat("k"));
                 return true;
            case "DH":
                stringBuilder.append(getDateForFormat("H"));
                 return true;
            case "DK":
                stringBuilder.append(getDateForFormat("K"));
                 return true;
            case "DHZ":
                stringBuilder.append(leadingZero(getDateForFormat("H")));
                 return true;
            case "DkZ":
                stringBuilder.append(leadingZero(getDateForFormat("k")));
                 return true;
            case "DkZA":
                stringBuilder.append(leadingZero(getDateForFormat("k")));
                 return true;
            case "DkZB":
                stringBuilder.append(leadingZero(getDateForFormat("k")));
                 return true;
            case "DKZ":
                stringBuilder.append(leadingZero(getDateForFormat("K")));
                 return true;
            case "DhZ":
                stringBuilder.append(leadingZero(getDateForFormat("h")));
                 return true;
            case "DhZA":
                stringBuilder.append(leadingZero(getDateForFormat("h")).charAt(0));
                 return true;
            case "DhZB":
                stringBuilder.append(leadingZero(getDateForFormat("h")).charAt(1));
                 return true;
            case "DhoT":
                stringBuilder.append((Integer.parseInt(getDateForFormat("H")) % 12) * 30);
                 return true;
            case "DhoTb":
                stringBuilder.append(Integer.parseInt(getDateForFormat("H")) * 15);
                 return true;
            case "DWFK":
                stringBuilder.append((Integer.parseInt(getDateForFormat("K")) % 12) * 30);
                 return true;
            case "DWFH":
                stringBuilder.append(Integer.parseInt(getDateForFormat("K")) * 15);
                 return true;
            case "DWFKS":
                stringBuilder.append(((double) ((Integer.parseInt(getDateForFormat("H")) % 12) * 30)) + (((double) Integer.parseInt(getDateForFormat("m"))) * 0.5d));
                 return true;
            case "DWFHS":
                stringBuilder.append(((double) ((Integer.parseInt(getDateForFormat("H")) % 12) * 30)) + (((double) Integer.parseInt(getDateForFormat("m"))) * 0.25d));
                 return true;
            case "DhT":
                stringBuilder.append((Integer.parseInt(getDateForFormat("K")) % 12 == 0 ? hourText[11] : hourText[((Integer.parseInt(getDateForFormat("K")) % 12) - 1)]));
                 return true;
            case "DkT":
                stringBuilder.append((Integer.parseInt(getDateForFormat("H")) % 12 == 0 ? hourText[11] : hourText[((Integer.parseInt(getDateForFormat("H")) % 12) - 1)]));
                 return true;
            case "Dm":
                stringBuilder.append(getDateForFormat("m"));
                 return true;
            case "DmZ":
                stringBuilder.append(leadingZero(getDateForFormat("m")));
                 return true;
            case "DWFM":
                stringBuilder.append(Integer.parseInt(getDateForFormat("m")) * 6);
                 return true;
            case "DWFMS":
                stringBuilder.append(((double) (Integer.parseInt(getDateForFormat("m")) * 6)) + (((double) Integer.parseInt(getDateForFormat("s"))) * 0.1d));

                 return true;
            case "DmT":
                String tempString = "";
                if (Integer.parseInt(getDateForFormat("m")) > 20 || Integer.parseInt(getDateForFormat("m")) == 0) {
                    if (Integer.parseInt(getDateForFormat("m")) != 0) {
                        switch (Integer.parseInt(getDateForFormat("m")) / 10) {
                            case 2:
                                tempString = hourText[19];
                                break;
                            case 3:
                                tempString = minuteText[2];
                                break;
                            case 4:
                                tempString = minuteText[3];
                                break;
                            case 5:
                                tempString = minuteText[4];
                                break;
                        }
                        stringBuilder.append(tempString).append(" ").append(hourText[(Integer.parseInt(getDateForFormat("m")) % 10) - 1]);
                    }
                }
                stringBuilder.append(tempString);

                 return true;
            case "DmMT":
                stringBuilder.append((Integer.parseInt(getDateForFormat("m")) / 10) >= 2 ? minuteText[(Integer.parseInt(getDateForFormat("m")) / 10) - 1] : "");

                 return true;
            case "DmST":
                if (Integer.parseInt(getDateForFormat("m")) % 10 != 0 && Integer.parseInt(getDateForFormat("m")) / 10 != 1) {
                    stringBuilder.append(hourText[Integer.parseInt(getDateForFormat("m")) % 10 - 1]);
                } else if (Integer.parseInt(getDateForFormat("m")) / 10 == 1) {
                    stringBuilder.append(hourText[((Integer.parseInt(getDateForFormat("m")) / 10 * 10) + Integer.parseInt(getDateForFormat("m")) % 10) - 1]);
                }

                 return true;
            case "Ds":
                stringBuilder.append(getDateForFormat("s"));
                 return true;
            case "DsZ":
            case "DSZ":
                stringBuilder.append(leadingZero(getDateForFormat("s")));
                 return true;
            case "Dsm":
                stringBuilder.append(String.format(Locale.getDefault(), "%.3f", ((double) ((float) Integer.parseInt(getDateForFormat("s")))) + (((double) System.currentTimeMillis() % 1000) * 0.001d)));
                 return true;
            case "DWFS":
            case "DseT":
                stringBuilder.append(Integer.parseInt(getDateForFormat("s")) * 6);

                 return true;
            case "DWFSS":
                stringBuilder.append(String.format(Locale.US, "%.3f", ((double) (Integer.parseInt(getDateForFormat("s")) * 6)) + (((double) System.currentTimeMillis() % 1000) * 0.006d)));
                 return true;
            case "Dz":
                stringBuilder.append(getDateForFormat("z"));
                 return true;
            case "Dzzzz":
                stringBuilder.append(getDateForFormat("zzzz"));
                 return true;
            case "DWR":
                stringBuilder.append((360.0f / ((float) Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_WEEK))) * ((float) (Integer.parseInt(getDateForFormat("F")))));
                 return true;
            case "DMR":
                stringBuilder.append((360.0f / ((float) Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH))) * ((float) Integer.parseInt(getDateForFormat("M"))));
                 return true;
            case "DYR":
                stringBuilder.append((360.0f / ((float) Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_YEAR))) * ((float) (Integer.parseInt(getDateForFormat("Y")))));
                 return true;
            case "DMYR":
                stringBuilder.append((Integer.parseInt(getDateForFormat("M"))) * 30);
                 return true;
            case "DUh":
                stringBuilder.append(getUTCDateForFormat("h"));
                 return true;
            case "DUm":
                stringBuilder.append(getUTCDateForFormat("m"));
                 return true;
            case "DUmZ":
                stringBuilder.append(leadingZero(getUTCDateForFormat("m")));
                return true;
            case "DUs":
                stringBuilder.append(getUTCDateForFormat("s"));
                 return true;
            case "DUsZ":
                stringBuilder.append(leadingZero(getUTCDateForFormat("s")));
                 return true;
            case "DUk":
                stringBuilder.append(getUTCDateForFormat("k"));
                 return true;
            case "DUH":
                stringBuilder.append(getUTCDateForFormat("H"));
                 return true;
            case "DUK":
                stringBuilder.append(getUTCDateForFormat("K"));
                 return true;
            case "DUb":
                stringBuilder.append(is24HourFormat ? "24" : "12");
                 return true;
            default:
                return false;
        }
    }

    private String getDateForFormat(String formatString) {
        if (formatString == null) {
            return "";
        }
        SimpleDateFormat format = new SimpleDateFormat(formatString, Locale.getDefault());
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        format.setCalendar(calendar);
        return format.format(calendar.getTime());
    }

    private String getUTCDateForFormat(String formatString) {
        if (formatString == null) {
            return "";
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        SimpleDateFormat format = new SimpleDateFormat(formatString, Locale.getDefault());
        format.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date date = new Date(System.currentTimeMillis());
        return format.format(date);
    }

    private String leadingZero(int value){
        return value < 10 ? "0" + value : String.valueOf(value);
    }
    private String leadingZero(String value){
        return leadingZero(Integer.parseInt(value));
    }

}
