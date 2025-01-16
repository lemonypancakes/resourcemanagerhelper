# ResourceManagerHelper  

Ever wanted to read and analyze data packs but realized Bukkit doesn't offer an API for that?  
Introducing **ResourceManagerHelper**, an API that simplifies reading and analyzing data pack contents!  

## Features  
- **Easily list resources** from specified directories with customizable filters.  
- **Read and process JSON resources** effortlessly.  
- Lightweight, intuitive, and integrates seamlessly with existing Bukkit projects.  

---

## Getting Started  

### Code Example  
Here's an example of how to use ResourceManagerHelper in your project:  

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
            // Process the input stream
            Reader reader = new InputStreamReader(inputStream);
            JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);

            if (jsonObject != null && jsonObject.has("name")) {
                String name = jsonObject.get("name").getAsString();
                if (name != null) {
                    LOGGER.severe(name);
                    LOGGER.severe(resourceLocation.getNamespace());
                    LOGGER.severe(resourceLocation.getPath());
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    });
}
```

---

##Contributing
We welcome contributions!
Feel free to open an issue or submit a pull request.

---

##License
ResourceManagerHelper is released under the GPL-3.0 License.
