package musinsa_assignment.style_coordinator.catalog.domain;

public enum CategoryType {
  TOP("상의", 0),
  OUTER("아우터", 1),
  PANTS("바지", 2),
  SNEAKERS("스니커즈", 3),
  BAG("가방", 4),
  HAT("모자", 5),
  SOCKS("양말", 6),
  ACCESSORY("악세서리", 7);

  private final String name;
  private final int exposureOrder;

  CategoryType(String name, int exposureOrder) {
    this.name = name;
    this.exposureOrder = exposureOrder;
  }

  public String getName() {
    return name;
  }

  public int getExposureOrder() {
    return exposureOrder;
  }
}
