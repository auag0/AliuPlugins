rootProject.name = "AliuPlugins"

File(rootDir, "/plugins").list()?.forEach { pluginName ->
    include(":$pluginName")
    project(":$pluginName").projectDir = File(rootDir, "/plugins/$pluginName")
}