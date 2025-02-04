fun Project.getLocalProperty(key: String, defaultValue: String = ""): String {
    val localProperties = java.util.Properties()
    val localPropertiesFile = rootProject.file("local.properties")
    if (localPropertiesFile.exists()) {
        localProperties.load(localPropertiesFile.inputStream())
    }
    return localProperties.getProperty(key) ?: defaultValue
}
extra["BASE_URL"] = getLocalProperty("BASE_URL")
