import android.webkit.WebView;

public class WebViewExample {
  public void configureWebView() {
    WebView webView = (WebView) findViewById(R.id.webview);
    webView.getSettings().setAllowFileAccess(true); // Sensitive
    webView.getSettings().setAllowContentAccess(true); // Sensitive
  }
}
