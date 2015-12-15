package stage.editor;

public enum AddableObject {
	BOX("1", "Box"), SLOPE("2", "Slope");

	private String objectName;
	private String keyName;

	private AddableObject(String keyName, String objectName) {
		this.objectName = objectName;
		this.keyName = keyName;
	}

	protected String getKeyName() {
		return keyName;
	}

	protected String getObjectName() {
		return objectName;
	}
}