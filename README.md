# VRaptor4js Plugin

Overview
--------
This plugin creates a JS bridge to your VRaptor controllers. It works as an abstraction layer to expose the application actions to your JS components. By using it, you can invoke the controllers methods directly without worring about the underlying infrastructure required to effectively get access to them (URLs, HTTP methods, CORS support, parameters binding, etc).

Dependencies
------------
- VRaptor 4

Installation
------------
Add VRaptor4js Maven dependency to you pom.xml:
```
<dependency>
	<groupId>com.github</groupId>
	<artifactId>vraptor4js-plugin</artifactId>
	<version>1.0.0</version>
</dependency>
```

> * As this plugin isn't available in a public Maven repo yet, in order to be able to use it, you have to clone this repository first and run ```mvn install``` to have it installed in your local Maven repository. 

How it works
------------
Look at this. Imagine you have REST actions in your Controllers like this one: 
```
@Controller
public class PersonController {
    @Get("/person/details")
    public void details(){
        final Person person = new Person("Marcio"); // retrieve a person record
        result.use(Results.json()).from(person).recursive().serialize();
    }
}
```
Now, from within your JS code you can invoke them as easily as this: 
```
<script src="v4js/ctrl/PersonController"></script>
<script>
    PersonController.details(function(person)){
    	// the person object is fully loaded at this point
    	console.log(person.name);
    }
</script>
```
See? You get a JS object with your controller's name with all public methods of it available. You don't have to worry about the underlying Ajax infrastructure involved in binding your JS component to your VRaptor Controller methods. You can freely change the methods URLs, HTTP Methods (GET, POST, etc) as you wish and VRaptor4js does the trick for you automatically using either AngularJS (default) or jQuery (that's configurable).

Configuration
-------------
1) Choose your prefered JavaScript library and include it in your application:
- AngularJS (default) - https://angularjs.org/
- jQuery - https://jquery.org/

2) Then, simply place this directive before your JS controllers:

```
<!-- JS library definition -->
<script src="v4js/lib/jquery"></script> <!-- Must be either angular or jquery -->
<!-- JS Controllers go here -->
<script src="v4js/ctrl/PersonController"></script>
```
If Angular JS is your choice, you can ommit it as it is the default library.

That's it. At this point you all set to access your controllers.

CORS support
------------

In case your REST services are provided by an application with a different domain/subdomain (like ```api.domain.com```), just add the absolute path to the resources (instead of relative paths), like this:
```
<!-- JS library definition -->
<script src="http://api.domain.com/v4js/lib/angular"></script>
<!-- JS Controllers go here -->
<script src="http://api.domain.com/v4js/ctrl/PersonController"></script>
```
VRaptor4js will ensure to enable CORS support in your application.

Features
--------
- Easy connectivity from your front-end to your application actions;
- Methods URLs, HTTP verbs and Ajax infrastructure abstraction;
- Built-in CORS support (see http://pt.wikipedia.org/wiki/Cross-origin_resource_sharing);
- AngularJS and jQuery implementations available;
- Simple and object parameters binding (```'Marcio'``` or ```{name:'Marcio'}```).

Known issues / limitations
--------------------------
As of now...
- Only GET and POST methods are supported;
- Template URLs not supported (like ```@Get("/path/{param}")```
- With AngularJS, your namespaces must be ```app```, ```app.controllers``` and ```app.services```;
- CORS headers not customizable (ex: ```Origin:*```, ```Allow-Methods:<all>```, etc).

Feedback
--------
I would love hearing what you think about this plugin. I encourage you to drop me a message at marcioferlan@gmail.com. Your suggestions, contributions, and donations are welcome! ;)