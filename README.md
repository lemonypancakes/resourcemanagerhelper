# ResourceManagerHelper
![Jenkins Build](https://img.shields.io/jenkins/build?jobUrl=https%3A%2F%2Fci.codemc.io%2Fjob%2Flemonypancakes%2Fjob%2Fresourcemanagerhelper)
![Sonatype Nexus (Repository)](https://img.shields.io/nexus/lemonypancakes/me.lemonypancakes.resourcemanagerhelper/resourcemanagerhelper?server=https%3A%2F%2Frepo.codemc.io)
![GitHub License](https://img.shields.io/github/license/lemonypancakes/resourcemanagerhelper)

Ever wanted to read and analyze data packs but realized Bukkit doesn't offer an API for that?
Introducing ResourceManagerHelper, an API that simplifies reading and analyzing data pack contents!

## Preqrequisites
- Java 21 or higher

## Installation
### Maven
Add the following to your `pom.xml`:
```xml
<repositories>
    <repository>
        <id>codemc-lemonypancakes</id>
        <url>https://repo.codemc.io/repository/lemonypancakes/</url>
    </repository>
</repositories>

<dependencies>
    <dependency>
        <groupId>me.lemonypancakes.resourcemanagerhelper</groupId>
        <artifactId>resourcemanagerhelper</artifactId>
        <version>1.0.0</version>
    </dependency>
</dependencies>
```

### Gradle (Groovy DSL)
Add the following to your `build.gradle`:
```groovy
repositories {
    maven {
        url 'https://repo.codemc.io/repository/lemonypancakes/'
    }
}

dependencies {
    implementation 'me.lemonypancakes.resourcemanagerhelper:resourcemanagerhelper:1.0.0'
}
```

### Gradle (Kotlin DSL)
Add the following to your `build.gradle.kts`:
```kotlin
repositories {
    maven {
        url = uri("https://repo.codemc.io/repository/lemonypancakes/")
    }
}

dependencies {
    implementation("me.lemonypancakes.resourcemanagerhelper:resourcemanagerhelper:1.0.0")
}
```

## Usage
```java
// List all resources matching your specified conditions:
// - Located in the "test" or "data/test" directory.
// - The resource name must end in ".json" (i.e., targeting only JSON files).
Map<ResourceLocation, Resource> resources = ResourceManagerHelper.listResources("test", 
                resourceLocation -> resourceLocation.toString().endsWith(".json"));

// Ensure resources are not null (optional)
if (resources != null) {
    // Create a new com.google.gson.Gson instance
    Gson gson = new Gson();

    // Iterate through each resource
    resources.forEach((resourceLocation, resource) -> {
        try (InputStream inputStream = resource.open()) {
            // Consume the stream however you like, rare, or medium rare.
            Reader reader = new InputStreamReader(inputStream);
            JsonObject json = gson.fromJson(reader, JsonObject.class);

            if (json != null && json.has("name")) {
                String name = json.get("name").getAsString();
                if (name != null) {
                    LOGGER.info(name);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    });
}
```

## Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

## Versioning
We use [SemVer](http://semver.org/) for versioning.

## Authors
- **lemonypancakes** - *Initial work* - [lemonypancakes](https://github.com/lemonypancakes)

See also the list of [contributors](https://github.com/lemonypancakes/resourcemanagerhelper/contributors) who participated in this project.

## Acknowledgments
- CodeMC for providing a repository for hosting the project.

## License
This project is licensed under the GNU General Public License v3.0 - see the [LICENSE](LICENSE) file for details.
