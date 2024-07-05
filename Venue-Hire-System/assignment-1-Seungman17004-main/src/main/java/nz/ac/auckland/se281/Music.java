package nz.ac.auckland.se281;

public class Music extends Services {

  public Music(String bookingReference) {

    super("Music", 500, bookingReference);
  }

  public String getBookingReference() {

    return bookingReference;
  }
}
