package nz.ac.auckland.se281;

public abstract class Services {

  protected String cateringTypeName;
  protected int cateringTypeCost;
  protected String bookingReference;

  // Constructor for services
  public Services(String cateringTypeName, int cateringTypeCost, String bookingReference) {

    this.cateringTypeName = cateringTypeName;
    this.cateringTypeCost = cateringTypeCost;
    this.bookingReference = bookingReference;
  }
}
