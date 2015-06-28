#Android MVC Boilerplate
  Start your project with this Sample Application and ensure you're following the MVC pattern. All the project is focused in webservice based applications, meaning that you will need a webservice to communicate with this application. 

## How the Application works
At this code, we have a sample application using the MVC pattern. In the following lines we will explain better:

### Models

Simple Model
```java
public class Sample extends Base {

    public Sample() {

    }

    public JSONObject toJSON() {
        JSONObject json = new JSONObject();
        return json;
    }
}
```
### Controllers

Simple Controller
```java
public class SampleController extends ApplicationController {

    protected static final String SAMPLE_URL = "/sample";

    private Adapter mAdapter;
    private ArrayList<Sample> mSampleList;

    public SampleController(Context context,Fragment fragment) {
        super(context);
        mFragment = fragment;
    }
    ...
    
    private void get(String url){
      ...
    }
    
```
    



### Authors
 - Diego Marcolan - @diegosax
 - Franzé Jr. - @franzejr
