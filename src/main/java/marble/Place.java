package marble;

public class Place {
	private String name;
	private String qrCode;
	private double lat;
	private double lng;

	public Place(String name, String qrCode) {
		this(name, qrCode, 0, 0);
	}
	
	public Place(String name, String qrCode, double lat, double lng) {
		this.name = name;
		this.qrCode = qrCode;
		this.lat = lat;
		this.lng = lng;
	}
	
	public String getName() {
		return name;
	}
	
	public String getQrCode() {
		return qrCode;
	}

	public double getLat() {
		return lat;
	}

	public double getLng() {
		return lng;
	}

	@Override
	public String toString() {
		return name;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof  String) {
			System.out.println(obj);
		}
		if (obj instanceof Place) {
			Place p = (Place) obj;
			return name.equals(p.getName());
		}
		return super.equals(obj);
	}
}
