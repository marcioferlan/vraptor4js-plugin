# VRaptor4js Plugin

[![Build Status](https://travis-ci.org/marcioferlan/vraptor4js-plugin.png?branch=master)](https://travis-ci.org/marcioferlan/vraptor4js-plugin)
vraptor4js
=====

Overview
--------
This plugin creates a JS bridge to your VRaptor controllers. It works as an abstraction layer to expose the application actions to your JS components. With it, you can invoke the controllers methods directly without worring about the underlying infrastructure required to effectively get access to them (URLs, HTTP methods, CORS support, parameters binding, etc).

Dependencies
------------
- VRaptor 4

Installation
------------
Add VRaptor4js Maven dependency to you pom.xml:
```
<dependency>
	<groupId>com.gtisolucoes</groupId>
	<artifactId>vraptor4js-plugin</artifactId>
	<version>1.5.0-SNAPSHOT</version> <!-- use the latest version available -->
</dependency>
```

> * As this plugin isn't available in a public Maven repo yet, in order to be able to use it, you have to clone this repository first and run ```mvn install``` to have it installed in your local Maven repository. 

How it works
------------
Look at this. Imagine you have REST actions in your Controllers like this one: 
```
@V4js @Controller
public class PersonController {
    @Get("/person/details")
    public void details(){
        Person person = new Person("Marcio"); // retrieve a person record
        result.use(Results.json()).from(person).recursive().serialize();
    }
}
```
Now, from within your JS code you can invoke them as easily as this: 
```
<script src="v4js/angular/PersonController"></script>
<script>
    PersonController.details(function(person){
    	// the person object is fully loaded at this point
    	console.log(person.name);
    });
</script>
```
See? You get a JS object with your controller's name and you don't have to worry about the underlying Ajax infrastructure involved in binding your JS component to your VRaptor Controller methods. You can freely change the methods URLs, HTTP Methods (GET, POST, etc) as you wish and VRaptor4js does the trick for you automatically using either AngularJS or jQuery (that's configurable).

Configuration
-------------

### VRaptor Controllers ###

1) Annotate your VRaptor Controller classes or their action methods with ```@V4js```.

> Notice that if you place the annotation at the type (class) level, all of its public methods will be exposed in your JS Controller. On the other hand, if the annotation is placed at the method level, only those methods will be exposed. Use this option if you need to expose only certain methods of your controller.
> 

### Client-side ###

1) Choose your prefered JavaScript library and include it in your page:
- AngularJS - https://angularjs.org/
- jQuery - https://jquery.org/

2) Then, simply add one of these directives to access your JS controllers:

```
<!-- AngularJS -->
<script src="v4js/angular/PersonController"></script>
or
<!-- JQuery -->
<script src="v4js/jquery/PersonController"></script>
```

That's it. At this point you should be all set!

CORS support
------------

In case your REST services are provided by an application in a different domain/subdomain (like ```api.domain.com```), you'll need CORS support enabled in order to access them.

In web.xml, just add these properties as appropriate to enable it:

```
<!-- V4js CORS Support -->
<context-param>
    <param-name>Access-Control-Allow-Origin</param-name>
    <param-value>*</param-value>
</context-param>
<context-param>
    <param-name>Access-Control-Allow-Credentials</param-name>
    <param-value>true</param-value>
</context-param>
<context-param>
    <param-name>Access-Control-Expose-Headers</param-name>
    <param-value>Content-Type, Location, X-Requested-With</param-value>
</context-param>
```

Then, invoke your JS Controllers using the absolute path to them (instead of relative paths), like this:
```
<!-- AngularJS -->
<script src="http://api.domain.com/v4js/angular/PersonController"></script>
or
<!-- JQuery -->
<script src="http://api.domain.com/v4js/jquery/PersonController"></script>
```
VRaptor4js will ensure to enable CORS support in your application.

Features
--------
- Easy connectivity from your front-end to your application actions;
- Methods URLs, HTTP verbs and Ajax infrastructure abstraction;
- Built-in CORS support (see http://pt.wikipedia.org/wiki/Cross-origin_resource_sharing);
- AngularJS and jQuery implementations available;
- Simple and object parameters binding (```'Marcio'``` or ```{name:'Marcio'}```).


Example (demo)
--------------

There's an example project with 2 modules (web apps) for you to test and get started to the use of VRaptor4js plugin. One of them is the server-side and the other one is the client-side app.

Please, refer to https://github.com/marcioferlan/vraptor4js-example and follow the instructions in the README file.

Known issues / limitations
--------------------------
As of now...
- Only GET and POST methods are supported;
- Template URLs not supported (like ```@Get("/path/{param}")```
- With AngularJS, your namespaces must be ```app```, ```app.controllers``` and ```app.services```;

Changelog
---------
* v1.5.0-SNAPSHOT
	* CORS headers customizable via <context-param> in web.xml.

* v1.4.0-SNAPSHOT
	* @V4js(ignore=true) to allow you to indicate methods you don't want to be exposed in your JS controllers
* v1.3.0
	* Base package and groupId renamed to com.gtisolucoes.vraptor4js (to publish the plugin in Sonatype repository)
* v1.2.0
	* Unit tests and Travis integration (contibution from @dtelaroli)
	* Base package renamed to br.com.caelum.vraptor.v4js
* v1.1.0
	* Ability to define your own success and/or error callbacks
	* Implementation of a default logging error callback
	* @V4js annotation to identify controllers and methods to expose in the JS Controller
* v1.0.0
	* Initial version

Feedback
--------
I would appreciate hearing what you think about this plugin. I encourage you to drop me a message at marcioferlan@gmail.com. Your suggestions, contributions, and donations are welcome! ;)
