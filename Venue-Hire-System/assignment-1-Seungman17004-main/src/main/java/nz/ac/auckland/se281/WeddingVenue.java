package nz.ac.auckland.se281;

public class WeddingVenue {

  private String venueName;
  private String venueCode;
  private String venueCapacity;
  private String venueHireFee;

  public WeddingVenue(
      String venueName, String venueCode, String venueCapacity, String venueHireFee) {

    this.venueName = venueName;
    this.venueCode = venueCode;
    this.venueCapacity = venueCapacity;
    this.venueHireFee = venueHireFee;
  }

  public String getVenueName() {

    return venueName;
  }

  public String getVenueCode() {

    return venueCode;
  }

  public String getVenueCapacity() {

    return venueCapacity;
  }

  public String getVenueHireFee() {

    return venueHireFee;
  }
}
