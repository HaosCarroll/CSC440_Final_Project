package utilities;

import org.joda.time.*;

public class PairDateTime {

    private final DateTime firstDateTime;
    private final DateTime finalDateTime;

    public PairDateTime(DateTime firstDateTime, DateTime finalDateTime) {
        this.firstDateTime = firstDateTime;
        this.finalDateTime = finalDateTime;
    }

    public DateTime getFirst() {
        return firstDateTime;
    }

    public DateTime getFinal() {
        return finalDateTime;
    }
}