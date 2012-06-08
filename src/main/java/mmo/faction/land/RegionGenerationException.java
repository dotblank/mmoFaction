package mmo.faction.land;

public class RegionGenerationException extends Exception {
	enum Reason {
		OCEAN,
		INVALID,
		FAILURE
	}
	Reason reason;
	RegionGenerationException(Reason r) {
		reason = r;	
	}
	public String toString() {
		String ret = "Unable to generate region: " + reason.name() + "\n";
		ret += super.toString();
		return ret;
	}
}
