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
    
    
#### Async Calls

We can do Async Calls just using one of these methods.

```java
public class AsyncClient {

    protected static final String BASE_URL = "http://192.168.1.16:3000/api/v1/";

    protected static AsyncHttpClient client = new AsyncHttpClient();

    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.post(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void post(String url, HttpEntity entity, AsyncHttpResponseHandler responseHandler) {
        client.post(null, getAbsoluteUrl(url), entity, "application/json", responseHandler);
    }

    public static void put(String url, HttpEntity entity, AsyncHttpResponseHandler responseHandler) {
        client.put(null, getAbsoluteUrl(url), entity, "application/json", responseHandler);
    }

    public static void put(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.put(null, getAbsoluteUrl(url), params, responseHandler);
    }

    public static void delete(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.delete(getAbsoluteUrl(url), responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }

    public static void cancelRequests(Context context) {
        client.cancelAllRequests(true);
    }

}

```



### Authors
 - Diego Marcolan - @diegosax
 - Franzé Jr. - @franzejr
