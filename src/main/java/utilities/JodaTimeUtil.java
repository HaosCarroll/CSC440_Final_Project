package utilities;

import org.joda.time.*;

import utilities.PairDateTime;

public class JodaTimeUtil{
    
    private int dayOfWeekCutoff = 5;
    private int hourCutoff = 21;
    private int minuteCutoff = 0;
    private int secondCutoff = 0;
    private int milliSecondCutoff = 0;
    
    public JodaTimeUtil(){
    }
    
    public PairDateTime getPreviousAndFollowingDateTimeCutoffsOfDateTime(DateTime dateTimeToCalc){
        
        DateTime previousDateTime = getPreviousDateTime(dateTimeToCalc);
        DateTime followingDatTime = getFollowingDatTime(dateTimeToCalc);
        
        PairDateTime returnPair = new PairDateTime(previousDateTime, followingDatTime);
        return returnPair;
    }
    
    private DateTime getPreviousDateTime(DateTime dateTimeToCalc){
        DateTime returnDateTime = null;
       
        if (dateTimeToCalc.getDayOfWeek() < dayOfWeekCutoff){
            returnDateTime = dateTimeToCalc.minusWeeks(1).withDayOfWeek(dayOfWeekCutoff).withTime(hourCutoff, minuteCutoff, secondCutoff, milliSecondCutoff);
        } else if (dateTimeToCalc.getDayOfWeek() > dayOfWeekCutoff){
            returnDateTime = dateTimeToCalc.withDayOfWeek(dayOfWeekCutoff).withTime(hourCutoff, minuteCutoff, secondCutoff, milliSecondCutoff);
        } else {
            if (dateTimeToCalc.getHourOfDay() < hourCutoff){
                returnDateTime = dateTimeToCalc.minusWeeks(1).withDayOfWeek(dayOfWeekCutoff).withTime(hourCutoff, minuteCutoff, secondCutoff, milliSecondCutoff);
            } else if (dateTimeToCalc.getHourOfDay() > hourCutoff){
                returnDateTime = dateTimeToCalc.withDayOfWeek(dayOfWeekCutoff).withTime(hourCutoff, minuteCutoff, secondCutoff, milliSecondCutoff);
            } else {
                if (dateTimeToCalc.getMinuteOfHour() < minuteCutoff){
                    returnDateTime = dateTimeToCalc.minusWeeks(1).withDayOfWeek(dayOfWeekCutoff).withTime(hourCutoff, minuteCutoff, secondCutoff, milliSecondCutoff);
                } else if  (dateTimeToCalc.getMinuteOfHour() > minuteCutoff){
                    returnDateTime = dateTimeToCalc.withDayOfWeek(dayOfWeekCutoff).withTime(hourCutoff, minuteCutoff, secondCutoff, milliSecondCutoff);
                } else {
                    if (dateTimeToCalc.getSecondOfMinute() < secondCutoff){
                        returnDateTime = dateTimeToCalc.minusWeeks(1).withDayOfWeek(dayOfWeekCutoff).withTime(hourCutoff, minuteCutoff, secondCutoff, milliSecondCutoff);
                    } else if  (dateTimeToCalc.getSecondOfMinute() > secondCutoff){
                        returnDateTime = dateTimeToCalc.withDayOfWeek(dayOfWeekCutoff).withTime(hourCutoff, minuteCutoff, secondCutoff, milliSecondCutoff);
                    } else {
                        if (dateTimeToCalc.getMillisOfSecond() < milliSecondCutoff){
                            returnDateTime = dateTimeToCalc.minusWeeks(1).withDayOfWeek(dayOfWeekCutoff).withTime(hourCutoff, minuteCutoff, secondCutoff, milliSecondCutoff);
                        } else if  (dateTimeToCalc.getMillisOfSecond() > milliSecondCutoff){
                            returnDateTime = dateTimeToCalc.withDayOfWeek(dayOfWeekCutoff).withTime(hourCutoff, minuteCutoff, secondCutoff, milliSecondCutoff);
                        } else {
                            returnDateTime = dateTimeToCalc.minusWeeks(1);
                        }
                    }
                }
            }
        }
        
        return returnDateTime;
    }
    
    private DateTime getFollowingDatTime(DateTime dateTimeToCalc){
        DateTime returnDateTime = null;

        if (dateTimeToCalc.getDayOfWeek() < dayOfWeekCutoff){
            returnDateTime = dateTimeToCalc.withDayOfWeek(dayOfWeekCutoff).withTime(hourCutoff, minuteCutoff, secondCutoff, milliSecondCutoff);
        } else if (dateTimeToCalc.getDayOfWeek() > dayOfWeekCutoff){
            returnDateTime = dateTimeToCalc.plusWeeks(1).withDayOfWeek(dayOfWeekCutoff).withTime(hourCutoff, minuteCutoff, secondCutoff, milliSecondCutoff);
        } else {
            if (dateTimeToCalc.getHourOfDay() < hourCutoff){
                returnDateTime = dateTimeToCalc.withDayOfWeek(dayOfWeekCutoff).withTime(hourCutoff, minuteCutoff, secondCutoff, milliSecondCutoff);
            } else if (dateTimeToCalc.getHourOfDay() > hourCutoff){
                returnDateTime = dateTimeToCalc.plusWeeks(1).withDayOfWeek(dayOfWeekCutoff).withTime(hourCutoff, minuteCutoff, secondCutoff, milliSecondCutoff);
            } else {
                if (dateTimeToCalc.getMinuteOfHour() < minuteCutoff){
                    returnDateTime = dateTimeToCalc.withDayOfWeek(dayOfWeekCutoff).withTime(hourCutoff, minuteCutoff, secondCutoff, milliSecondCutoff);
                } else if  (dateTimeToCalc.getMinuteOfHour() > minuteCutoff){
                    returnDateTime = dateTimeToCalc.plusWeeks(1).withDayOfWeek(dayOfWeekCutoff).withTime(hourCutoff, minuteCutoff, secondCutoff, milliSecondCutoff);
                } else {
                    if (dateTimeToCalc.getSecondOfMinute() < secondCutoff){
                        returnDateTime = dateTimeToCalc.withDayOfWeek(dayOfWeekCutoff).withTime(hourCutoff, minuteCutoff, secondCutoff, milliSecondCutoff);
                    } else if  (dateTimeToCalc.getSecondOfMinute() > secondCutoff){
                        returnDateTime = dateTimeToCalc.plusWeeks(1).withDayOfWeek(dayOfWeekCutoff).withTime(hourCutoff, minuteCutoff, secondCutoff, milliSecondCutoff);
                    } else {
                        if (dateTimeToCalc.getMillisOfSecond() < milliSecondCutoff){
                            returnDateTime = dateTimeToCalc.withDayOfWeek(dayOfWeekCutoff).withTime(hourCutoff, minuteCutoff, secondCutoff, milliSecondCutoff);
                        } else if  (dateTimeToCalc.getMillisOfSecond() > milliSecondCutoff){
                            returnDateTime = dateTimeToCalc.plusWeeks(1).withDayOfWeek(dayOfWeekCutoff).withTime(hourCutoff, minuteCutoff, secondCutoff, milliSecondCutoff);
                        } else {
                            returnDateTime = dateTimeToCalc;
                            //System.out.println("\n\n\n\n* * * * * * \n\n\n\n");
                        }
                    }
                }
            }
        }
        
        return returnDateTime;
    }
}
