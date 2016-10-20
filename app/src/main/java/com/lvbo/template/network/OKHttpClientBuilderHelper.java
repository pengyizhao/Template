package com.lvbo.template.network;



import com.lvbo.template.config.AppConfig;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by kwongyuenpan on 23/10/15.
 */
public class OKHttpClientBuilderHelper {

    private static final String TAG = "OKHttpClientBuilderHelper";

    private static final int connectionTimeout = 30;

//    public static final SSLContext SSL_CONTEXT_GOOGLE_COM_EXPIRES_20140819
//            = new SslContextBuilder()
//            // CN=*.google.com, O=Google Inc, L=Mountain View, ST=California
//            .addCertificate(Constants.cert1)
//            .addCertificate(Constants.cert2)
//            .build();

    public static OkHttpClient.Builder getBuilder(){

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient();
        OkHttpClient.Builder builder = okHttpClient.newBuilder();

        //日志
        if (AppConfig.MODEL_DEBUG) {
            builder.addInterceptor(interceptor);
        }

        builder.connectTimeout(connectionTimeout, TimeUnit.SECONDS);
        builder.readTimeout(connectionTimeout, TimeUnit.SECONDS);
        builder.writeTimeout(connectionTimeout, TimeUnit.SECONDS);

        final TrustManager[] trustAllCerts = new TrustManager[] {
                new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                    }

                    @Override
                    public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                    }

                    @Override
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return new java.security.cert.X509Certificate[]{};
                    }
                }
        };
//        if(true){

            try {
                // Install the all-trusting trust manager
                SSLContext sslContext = SSLContext.getInstance("TLS");
                sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
//                sslContext.init(null, new TrustManager[]{}, new java.security.SecureRandom());
                // Create an ssl socket factory with our all-trusting manager
                final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

                builder.sslSocketFactory(sslSocketFactory);
                builder.hostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                });
            } catch (KeyManagementException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }

            return builder;
//        }else{
//
//        try {
//            String keyStoreType = KeyStore.getDefaultType();
//            KeyStore keyStore = KeyStore.getInstance(keyStoreType);
//            keyStore.load(null, null);
//            SSLSocketFactory socketFactory = SSL_CONTEXT_GOOGLE_COM_EXPIRES_20140819.getSocketFactory();
//            HostnameVerifier hostnameVerifier = org.apache.http.conn.ssl.SSLSocketFactory.STRICT_HOSTNAME_VERIFIER;
//            builder.hostnameVerifier(hostnameVerifier);
//
//            builder.sslSocketFactory(socketFactory);
//
//        } catch (CertificateException e1) {
//            e1.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        } catch (KeyStoreException e) {
//            e.printStackTrace();
//        }
//        return builder;
//        }
    }

}
