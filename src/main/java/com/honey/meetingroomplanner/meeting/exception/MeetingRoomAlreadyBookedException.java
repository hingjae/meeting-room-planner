package com.honey.meetingroomplanner.meeting.exception;

public class MeetingRoomAlreadyBookedException extends RuntimeException {
    private static final String ERROR_CODE = "AlreadyBooked.MeetingRoom";
    public MeetingRoomAlreadyBookedException() {
        super("ERROR_CODE");
    }

    public MeetingRoomAlreadyBookedException(String message) {
        super(message);
    }

    public MeetingRoomAlreadyBookedException(String message, Throwable cause) {
        super(message, cause);
    }

    public MeetingRoomAlreadyBookedException(Throwable cause) {
        super(cause);
    }

    protected MeetingRoomAlreadyBookedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
