package nz.ac.auckland.se281;

import java.util.ArrayList;
import nz.ac.auckland.se281.Types.CateringType;
import nz.ac.auckland.se281.Types.FloralType;

public class VenueHireSystem {

  private ArrayList<WeddingVenue> venueList = new ArrayList<>();
  private ArrayList<Booking> bookingList = new ArrayList<>();
  private ArrayList<Catering> cateringList = new ArrayList<>();
  private ArrayList<FloralService> floralList = new ArrayList<>();
  private ArrayList<Music> musicList = new ArrayList<>();
  private int numberOfVenues = 0;
  private String systemDate = "";

  // A method to convert the number of venues to words when the number of venues is from
  // 2 to 9.
  public String intToWord(int numberOfVenues) {

    // Switch case to check if the number of venues is from 2 to 9 and return according word
    // equivalent of the number
    switch (numberOfVenues) {
      case 2:
        return "two";

      case 3:
        return "three";

      case 4:
        return "four";

      case 5:
        return "five";

      case 6:
        return "six";

      case 7:
        return "seven";

      case 8:
        return "eight";

      case 9:
        return "nine";

      default:
        return "Invalid number";
    }
  }

  // Method that increments date
  public String nextDate(String currentDate) {

    int day;
    int month;
    int year;

    // Splits the current date in to days,months, and years into an array
    String[] dateParts = currentDate.split("/");

    // Assign variables to according part of date
    day = Integer.parseInt(dateParts[0]);
    month = Integer.parseInt(dateParts[1]);
    year = Integer.parseInt(dateParts[2]);

    day++;

    // Return the incremented date in the correct format
    return String.format("%02d/%02d/%04d", day, month, year);
  }

  // Method that returns next available date of specific venue
  public String nextAvailableDate(String venueName) {

    String tempDate = systemDate;
    ArrayList<String> bookedDates = new ArrayList<>();

    for (Booking booking : bookingList) {

      // Sort all booked dates for a specific venue into an arraylist
      if (venueName.equals(booking.getVenueName())) {

        bookedDates.add(booking.getBookingDate());
      }
    }

    // Initiate while loop to iterate over bookedDates
    while (bookedDates.contains(tempDate)) {

      tempDate = nextDate(tempDate);
    }

    return tempDate;
  }

  public void printVenues() {

    switch (numberOfVenues) {
      case 0:
        MessageCli.NO_VENUES.printMessage();
        break;

      case 1:
        MessageCli.NUMBER_VENUES.printMessage("is", "one", "");
        MessageCli.VENUE_ENTRY.printMessage(
            venueList.get(0).getVenueName(),
            venueList.get(0).getVenueCode(),
            venueList.get(0).getVenueCapacity(),
            venueList.get(0).getVenueHireFee(),
            nextAvailableDate(venueList.get(0).getVenueName()));
        break;
    }

    // When the number of venues is > 1 but < 10
    if (numberOfVenues > 1 && numberOfVenues < 10) {

      MessageCli.NUMBER_VENUES.printMessage("are", intToWord(numberOfVenues), "s");

      for (WeddingVenue venue : venueList) {

        MessageCli.VENUE_ENTRY.printMessage(
            venue.getVenueName(),
            venue.getVenueCode(),
            venue.getVenueCapacity(),
            venue.getVenueHireFee(),
            nextAvailableDate(venue.getVenueName()));
      }
    } else if (numberOfVenues >= 10) { // When the number of venues is 10 or more

      MessageCli.NUMBER_VENUES.printMessage("are", Integer.toString(numberOfVenues), "s");

      for (WeddingVenue venue : venueList) {

        MessageCli.VENUE_ENTRY.printMessage(
            venue.getVenueName(),
            venue.getVenueCode(),
            venue.getVenueCapacity(),
            venue.getVenueHireFee(),
            nextAvailableDate(venue.getVenueName()));
      }
    }
  }

  public void createVenue(
      String venueName, String venueCode, String capacityInput, String hireFeeInput) {

    // If the venue name is empty
    if (venueName.isEmpty()) {

      MessageCli.VENUE_NOT_CREATED_EMPTY_NAME.printMessage();
      return;
    }

    // If there are two instances of the same venue code in the system
    for (WeddingVenue venue : venueList) {

      if ((venue.getVenueCode()).equals(venueCode)) {

        MessageCli.VENUE_NOT_CREATED_CODE_EXISTS.printMessage(
            venue.getVenueCode(), venue.getVenueName());

        return;
      }
    }

    // If the capacity isnt a whole integer, isnt negative, isnt zero
    try {

      int intVenueCapacity = Integer.parseInt(capacityInput);

      if (intVenueCapacity <= 0) {

        MessageCli.VENUE_NOT_CREATED_INVALID_NUMBER.printMessage("capacity", " positive");
        return;
      }

    } catch (Exception e) {

      MessageCli.VENUE_NOT_CREATED_INVALID_NUMBER.printMessage("capacity", "");
      return;
    }

    // If the hire fee isnt a whole integer, isnt negative, isnt zero
    try {

      int intVenueHireFee = Integer.parseInt(hireFeeInput);

      if (intVenueHireFee <= 0) {

        MessageCli.VENUE_NOT_CREATED_INVALID_NUMBER.printMessage("hire fee", " positive");
        return;
      }

    } catch (Exception e) {

      MessageCli.VENUE_NOT_CREATED_INVALID_NUMBER.printMessage("hire fee", "");
      return;
    }

    // If none of these conditions are met, add to arraylist
    venueList.add(new WeddingVenue(venueName, venueCode, capacityInput, hireFeeInput));

    MessageCli.VENUE_SUCCESSFULLY_CREATED.printMessage(venueName, venueCode);

    numberOfVenues++;
  }

  public void setSystemDate(String dateInput) {

    systemDate = dateInput;

    MessageCli.DATE_SET.printMessage(systemDate);
  }

  public void printSystemDate() {

    // If system date has not been set, print according message.
    if (systemDate.isEmpty()) {

      MessageCli.CURRENT_DATE.printMessage("not set");

    } else {

      MessageCli.CURRENT_DATE.printMessage(systemDate);
    }
  }

  public void makeBooking(String[] options) {

    // If the system date has not been set.
    if (systemDate.isEmpty()) {

      MessageCli.BOOKING_NOT_MADE_DATE_NOT_SET.printMessage();

      return;

      // If there are no venues created
    } else if (numberOfVenues == 0) {

      MessageCli.BOOKING_NOT_MADE_NO_VENUES.printMessage();

      return;
    }

    // Initialise boolean flag for check
    boolean venueCodeFound = false;

    // If the venue code exists save it to variable and exit the for loop
    for (WeddingVenue venue : venueList) {

      if (venue.getVenueCode().equals(options[0])) {

        venueCodeFound = true;
        break;
      }
    }

    // If venue code doesn't exist, print corresponding message
    if (!venueCodeFound) {

      MessageCli.BOOKING_NOT_MADE_VENUE_NOT_FOUND.printMessage(options[0]);

      return;
    }

    // Check for if the inputted date is in the past

    // Split both inputted and system dates in to days,month, and year
    String[] inputDateParts = options[1].split("/");
    String[] systemDateParts = systemDate.split("/");

    // Convert both arrays so values contained are integers
    int[] intInputDateParts = new int[inputDateParts.length];
    int[] intSystemDateParts = new int[systemDateParts.length];

    for (int i = 0; i < intInputDateParts.length; i++) {

      intInputDateParts[i] = Integer.parseInt(inputDateParts[i]);
    }

    for (int i = 0; i < intSystemDateParts.length; i++) {

      intSystemDateParts[i] = Integer.parseInt(systemDateParts[i]);
    }

    // If the year of the inputted date is less than the system date return according message
    if ((intInputDateParts[2] < intSystemDateParts[2])) {

      MessageCli.BOOKING_NOT_MADE_PAST_DATE.printMessage(options[1], systemDate);

      return;
    } else if ((intInputDateParts[2] == intSystemDateParts[2])
        && intInputDateParts[1]
            < intSystemDateParts[1]) { // If the year is the same but the month count is lower

      MessageCli.BOOKING_NOT_MADE_PAST_DATE.printMessage(options[1], systemDate);

      return;
    } else if ((intInputDateParts[2] == intSystemDateParts[2])
        && intInputDateParts[1] == intSystemDateParts[1]
        && intInputDateParts[0]
            < intSystemDateParts[0]) { // If the year and month is the same but the date is lower

      MessageCli.BOOKING_NOT_MADE_PAST_DATE.printMessage(options[1], systemDate);

      return;
    }

    // Find venue name according to inputted venue code
    String venueName = "";

    for (WeddingVenue venue : venueList) {

      if (venue.getVenueCode().equals(options[0])) {

        venueName = venue.getVenueName();
      }
    }

    // If booking is attempted on an already booked date, print according message
    for (Booking booking : bookingList) {

      // Check if booking has same venue name
      if (booking.getVenueName().equals(venueName)) {
        if ((booking.getBookingDate()).equals(options[1])) {

          MessageCli.BOOKING_NOT_MADE_VENUE_ALREADY_BOOKED.printMessage(
              venueName, booking.getBookingDate());

          return;
        }
      }
    }

    // Check if number of attendees are less than 25% or more than 100% of the venue capacity and
    // adjust number accordingly
    // Find the venue capacity from corresponding venue code
    String venueCapacity = "";

    for (WeddingVenue venue : venueList) {

      if (venue.getVenueCode().equals(options[0])) {

        venueCapacity = venue.getVenueCapacity();
      }
    }

    // If number of attendees is less than 25% of the venue capacity adjust number accordingly
    // Initialise numberOfAttendees variable
    String numberOfAttendees = options[3];

    if (Integer.parseInt(options[3]) < (Integer.parseInt(venueCapacity) * 0.25)) {

      numberOfAttendees = Integer.toString((int) (Integer.parseInt(venueCapacity) * 0.25));

      MessageCli.BOOKING_ATTENDEES_ADJUSTED.printMessage(
          options[3], numberOfAttendees, venueCapacity);
    } else if (Integer.parseInt(options[3])
        > (Integer.parseInt(
            venueCapacity))) { // If number of attendees is more than 100% of the venue capacity
      // adjust number accordingly

      numberOfAttendees = venueCapacity;

      MessageCli.BOOKING_ATTENDEES_ADJUSTED.printMessage(
          options[3], numberOfAttendees, venueCapacity);
    }

    // After passing all checks, add booking to arraylist and print according message
    bookingList.add(
        new Booking(
            BookingReferenceGenerator.generateBookingReference(),
            venueName,
            options[1],
            numberOfAttendees,
            options[2],
            systemDate));

    // Retrieve the last booking instance from the bookingList
    Booking lastBooking = bookingList.get(bookingList.size() - 1);
    String bookingReference = lastBooking.getBookingReference();

    MessageCli.MAKE_BOOKING_SUCCESSFUL.printMessage(
        bookingReference, venueName, options[1], numberOfAttendees);
  }

  public void printBookings(String venueCode) {

    String venueName = "";
    boolean bookingPresent = false;
    boolean venueCreated = false;

    // Find corresponding venue name from code
    for (WeddingVenue venue : venueList) {

      if (venueCode.equals(venue.getVenueCode())) {

        venueName = venue.getVenueName();

        venueCreated = true;
        break;
      }
    }
    // Check if venueCode doesn't exist
    if (!venueCreated) {

      MessageCli.PRINT_BOOKINGS_VENUE_NOT_FOUND.printMessage(venueCode);
      return;
    }
    // Print header
    MessageCli.PRINT_BOOKINGS_HEADER.printMessage(venueName);

    // When there are bookings for the specified venue
    for (Booking booking : bookingList) {

      if (venueName.equals(booking.getVenueName())) {

        MessageCli.PRINT_BOOKINGS_ENTRY.printMessage(
            booking.getBookingReference(), booking.getBookingDate());

        bookingPresent = true;
      }
    }

    // When there are no bookings for the specified venue
    if (!bookingPresent) {

      MessageCli.PRINT_BOOKINGS_NONE.printMessage(venueName);
    }
  }

  public void addCateringService(String bookingReference, CateringType cateringType) {

    // Check if booking reference is valid
    boolean bookingReferencePresent = false;
    for (Booking booking : bookingList) {

      if (booking.getBookingReference().equals(bookingReference)) {

        bookingReferencePresent = true;

        break;
      }
    }

    // Check if the booking reference is not valid
    if (bookingReferencePresent == false) {

      MessageCli.SERVICE_NOT_ADDED_BOOKING_NOT_FOUND.printMessage("Catering", bookingReference);
      return;
    }

    // Crete catering instance
    cateringList.add(
        new Catering(cateringType.getName(), cateringType.getCostPerPerson(), bookingReference));

    MessageCli.ADD_SERVICE_SUCCESSFUL.printMessage(
        "Catering (" + cateringType.getName() + ")", bookingReference);
  }

  public void addServiceMusic(String bookingReference) {

    // Check if booking reference is valid
    boolean bookingReferencePresent = false;
    for (Booking booking : bookingList) {

      if (booking.getBookingReference().equals(bookingReference)) {

        bookingReferencePresent = true;

        break;
      }
    }

    // Check if the booking reference is not valid
    if (bookingReferencePresent == false) {

      MessageCli.SERVICE_NOT_ADDED_BOOKING_NOT_FOUND.printMessage("Music", bookingReference);
      return;
    }

    // Crete music service instance
    musicList.add(new Music(bookingReference));

    MessageCli.ADD_SERVICE_SUCCESSFUL.printMessage("Music", bookingReference);
  }

  public void addServiceFloral(String bookingReference, FloralType floralType) {

    // Check if booking reference is valid
    boolean bookingReferencePresent = false;
    for (Booking booking : bookingList) {

      if (booking.getBookingReference().equals(bookingReference)) {

        bookingReferencePresent = true;

        break;
      }
    }

    // Check if the booking reference is not valid
    if (bookingReferencePresent == false) {

      MessageCli.SERVICE_NOT_ADDED_BOOKING_NOT_FOUND.printMessage("Floral", bookingReference);
      return;
    }

    // Crete floral service instance
    floralList.add(new FloralService(floralType.getName(), floralType.getCost(), bookingReference));

    MessageCli.ADD_SERVICE_SUCCESSFUL.printMessage(
        "Floral (" + floralType.getName() + ")", bookingReference);
  }

  public void viewInvoice(String bookingReference) {

    // Check if booking reference is valid
    boolean bookingReferencePresent = false;
    for (Booking booking : bookingList) {

      if (booking.getBookingReference().equals(bookingReference)) {

        bookingReferencePresent = true;
        break;
      }
    }

    // Check if the booking reference is not valid
    if (bookingReferencePresent == false) {

      MessageCli.VIEW_INVOICE_BOOKING_NOT_FOUND.printMessage(bookingReference);
      return;
    }

    // Initialise local variables
    String customerEmail = "";
    String dateOfBooking = "";
    String partyDate = "";
    String numberOfAttendees = "";
    String venueName = "";
    String venueHireFee = "";
    String cateringName = "";
    String floralName = "";
    int cateringCost = 0;
    int floralCost = 0;
    int musicCost = 0;
    // Iterate through the booking list to find the corresponding booking

    for (Booking booking : bookingList) {

      if (booking.getBookingReference().equals(bookingReference)) {

        customerEmail = booking.getCustomerEmail();
        dateOfBooking = booking.getDateOfBooking();
        partyDate = booking.getBookingDate();
        numberOfAttendees = booking.getNumberOfAtendees();
        venueName = booking.getVenueName();
        break;
      }
    }

    // Iterate through the venue list to find the corresponding venue
    for (WeddingVenue venue : venueList) {

      if (venue.getVenueName().equals(venueName)) {

        venueHireFee = venue.getVenueHireFee();
        break;
      }
    }

    // Iterate through cateringList to find corresponding price and name
    boolean cateringPresent = false;

    for (Catering cater : cateringList) {

      if (cater.getBookingReference().equals(bookingReference)) {

        cateringName = cater.getCateringTypeName();
        cateringCost = cater.getCateringTypeCost();
        cateringPresent = true;
        break;
      }
    }

    // Iterate through musicList to see if music service is present
    boolean musicPresent = false;
    for (Music music : musicList) {

      if (music.getBookingReference().equals(bookingReference)) {

        musicPresent = true;
        musicCost = 500;
        break;
      }
    }

    // Iterate through floralList to find corresponding price and name
    boolean floralPresent = false;
    for (FloralService floral : floralList) {

      if (floral.getBookingReference().equals(bookingReference)) {

        floralName = floral.getCateringTypeName();
        floralCost = floral.getCateringTypeCost();
        floralPresent = true;
        break;
      }
    }

    // Print according message

    MessageCli.INVOICE_CONTENT_TOP_HALF.printMessage(
        bookingReference, customerEmail, dateOfBooking, partyDate, numberOfAttendees, venueName);

    MessageCli.INVOICE_CONTENT_VENUE_FEE.printMessage(venueHireFee);

    if (cateringPresent == true) {

      MessageCli.INVOICE_CONTENT_CATERING_ENTRY.printMessage(
          cateringName, Integer.toString(cateringCost * Integer.parseInt(numberOfAttendees)));
    }

    if (musicPresent == true) {

      MessageCli.INVOICE_CONTENT_MUSIC_ENTRY.printMessage("500");
    }

    if (floralPresent == true) {

      MessageCli.INVOICE_CONTENT_FLORAL_ENTRY.printMessage(
          floralName, Integer.toString(floralCost));
    }

    MessageCli.INVOICE_CONTENT_BOTTOM_HALF.printMessage(
        Integer.toString(
            Integer.parseInt(venueHireFee)
                + (cateringCost * Integer.parseInt(numberOfAttendees))
                + musicCost
                + floralCost));
  }
}
