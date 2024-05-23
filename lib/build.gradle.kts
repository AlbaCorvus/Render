plugins {
    id("java")
}
group = "jschimera.loc.pong"
version = "1.0-SNAPSHOT"

repositories {
    flatDir {
        dirs("libs")
    }
    mavenCentral()
}
val lwjglVersion = "3.2.3"
val jomlVersion = "1.9.23"
val lwjglNatives = "natives-windows"
val imguiVersion = "1.81.0"

dependencies {
	// https://mvnrepository.com/artifact/org.apache.commons/commons-lang3
	implementation("org.apache.commons:commons-lang3:3.0")

	testImplementation(platform("org.junit:junit-bom:5.10.0"))
	testImplementation("org.junit.jupiter:junit-jupiter")
    // Box2D
    // https://mvnrepository.com/artifact/org.jbox2d/jbox2d-library
    implementation("org.jbox2d:jbox2d-library:2.2.1.1")
  
    // GSON
    implementation("com.google.code.gson:gson:2.8.6")
    // ImGUI stuff
    implementation("io.github.spair:imgui-java-app:$imguiVersion")
    // LWJGL stuff
    implementation(platform("org.lwjgl:lwjgl-bom:$lwjglVersion"))
    implementation("org.lwjgl:lwjgl")
    implementation("org.lwjgl:lwjgl-assimp")
    implementation("org.lwjgl:lwjgl-glfw")
    implementation("org.lwjgl:lwjgl-nfd")
    implementation("org.lwjgl:lwjgl-openal")
    implementation("org.lwjgl:lwjgl-opengl")
    implementation("org.lwjgl:lwjgl-stb")
    runtimeOnly("org.lwjgl:lwjgl::$lwjglNatives")
    runtimeOnly("org.lwjgl:lwjgl-assimp::$lwjglNatives")
    runtimeOnly("org.lwjgl:lwjgl-glfw::$lwjglNatives")
    runtimeOnly("org.lwjgl:lwjgl-nfd::$lwjglNatives")
    runtimeOnly("org.lwjgl:lwjgl-openal::$lwjglNatives")
    runtimeOnly("org.lwjgl:lwjgl-opengl::$lwjglNatives")
    runtimeOnly("org.lwjgl:lwjgl-stb::$lwjglNatives")
    implementation("org.joml:joml:$jomlVersion")
    implementation(files("libs/OPGL.jar"))
    implementation(files("libs/render.jar"))
    }
    
   tasks.test {
	useJUnitPlatform()
	testLogging {
		events("passed", "skipped", "failed")
	}
}

tasks.withType<JavaCompile>().configureEach {
	options.release.set(8)
}