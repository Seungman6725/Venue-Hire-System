package nz.ac.auckland.se281;

public class Booking {

  private String bookingReference;
  private String venueName;
  private String date;
  private String numberOfAttendees;
  private String customerEmail;
  private String dateOfBooking;

  public Booking(
      String bookingReference,
      String venueName,
      String date,
      String numberOfAttendees,
      String customerEmail,
      String dateOfBooking) {

    this.bookingReference = bookingReference;
    this.venueName = venueName;
    this.date = date;
    this.numberOfAttendees = numberOfAttendees;
    this.customerEmail = customerEmail;
    this.dateOfBooking = dateOfBooking;
  }

  public String getBookingReference() {

    return bookingReference;
  }

  public String getVenueName() {

    return venueName;
  }

  public String getBookingDate() {

    return date;
  }

  public String getNumberOfAtendees() {

    return numberOfAttendees;
  }

  public String getCustomerEmail() {

    return customerEmail;
  }

  public String getDateOfBooking() {

    return dateOfBooking;
  }
}
