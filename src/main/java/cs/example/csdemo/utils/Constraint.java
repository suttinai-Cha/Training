package cs.example.csdemo.utils;

public class Constraint {

	public enum LogConstraint {
		IP_KEY("X-IP-ADDRESS"), REQUEST_ID("Request id");

		private final String name;

		private LogConstraint(String name) {
			this.name = name;
		}

		public String value() {
			return name;
		}
	}
	public enum LogType {
		INFO("INFORMATION"), ERROR("ERROR"), WARNING("WARNING");

		private final String name;

		private LogType(String name) {
			this.name = name;
		}

		public String value() {
			return name;
		}
	}
}
