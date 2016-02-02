# RX-Volley
将Volley请求转换成RxJava形式，默认支持post与get方式请求

# 使用方法
-----
* 初始化Volley，建议在Application.onCreate()方法中调用
```java
  public class FrameworkApplication extends Application {

      @Override
      public void onCreate() {
          super.onCreate();
          initVolley();
      }
      private void initVolley() {
          RequestManager.init(this);
      }
  }

```
* 请求网络
```java
  String url = "https://raw.githubusercontent.com/cml8655/note/master/data/test.json";
  RxRequest.<MyModel>post(url, MyModel.class).subscribe(new Subscriber<MyModel>() {
                    @Override
                    public void onCompleted() {
                        Log.i("RxRequest", "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("RxRequest", "onError", e);
                    }

                    @Override
                    public void onNext(MyModel myModel) {
                        Log.i("RxRequest", "onNext==>" + myModel);
                    }
                });

```
---------------