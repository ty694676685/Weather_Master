package mg.studio.weatherappdesign;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new DownloadUpdate().execute();
    }

    public void btnClick(View view) {
        new DownloadUpdate().execute();
    }


    private class DownloadUpdate extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            String stringUrl = "http://t.weather.sojson.com/api/weather/city/101040100";
            HttpURLConnection urlConnection = null;
            BufferedReader reader;

            try {
                URL url = new URL(stringUrl);

                // Create the request to get the information from the server, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();

                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Mainly needed for debugging
                    Log.d("TAG", line);
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                //The temperature
               return buffer.toString();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
        }

            return null;
        }

        @Override
    protected void onPostExecute(String All) {
        //Update the temperature displayed
            try{
                JSONObject obj = new JSONObject(All);
                int result = obj.getInt("status");
                if (result == 200) {
                    JSONObject cityInfo = obj.getJSONObject("cityInfo");
                    JSONObject data = obj.getJSONObject("data");
                    JSONArray forecastArray = data.getJSONArray("forecast");

                    String date = obj.getString("time");
                    String city = cityInfo.getString("city");
                    String wendu = data.getString("wendu");

                    String week[] = new String[5];
                    String type[] = new String[5];
                    for(int i=0;i<5;i++){
                        week[i] = forecastArray.getJSONObject(i).getString("week");
                        type[i] = forecastArray.getJSONObject(i).getString("type");
                    }

                    ((TextView) findViewById(R.id.tv_date)).setText(date);
                    ((TextView) findViewById(R.id.tv_location)).setText(city);
                    ((TextView) findViewById(R.id.temperature_of_the_day)).setText(wendu);

                    ((TextView) findViewById(R.id.Week)).setText(week[0]);
                    switch (type[0]){
                        case "晴" :{ImageView imageview = (ImageView)findViewById(R.id.img_weather);
                                    imageview.setImageResource(R.drawable.sunny_small); }break;
                        case "小雨" :{ImageView imageview = (ImageView)findViewById(R.id.img_weather);
                                      imageview.setImageResource(R.drawable.rainy_small); }break;
                        case "阴" :{ImageView imageview = (ImageView)findViewById(R.id.img_weather);
                                    imageview.setImageResource(R.drawable.partly_sunny_small); }break;
                    }

                    ((TextView) findViewById(R.id.Week_1)).setText(week[1]);
                    switch (type[1]){
                        case "晴" :{ImageView imageview = (ImageView)findViewById(R.id.img_weather_1);
                            imageview.setImageResource(R.drawable.sunny_small); }break;
                        case "小雨" :{ImageView imageview = (ImageView)findViewById(R.id.img_weather_1);
                            imageview.setImageResource(R.drawable.rainy_small); }break;
                        case "阴" :{ImageView imageview = (ImageView)findViewById(R.id.img_weather_1);
                            imageview.setImageResource(R.drawable.partly_sunny_small); }break;
                    }

                    ((TextView) findViewById(R.id.Week_2)).setText(week[2]);
                    switch (type[3]){
                        case "晴" :{ImageView imageview = (ImageView)findViewById(R.id.img_weather_2);
                            imageview.setImageResource(R.drawable.sunny_small); }break;
                        case "小雨" :{ImageView imageview = (ImageView)findViewById(R.id.img_weather_2);
                            imageview.setImageResource(R.drawable.rainy_small); }break;
                        case "阴" :{ImageView imageview = (ImageView)findViewById(R.id.img_weather_2);
                            imageview.setImageResource(R.drawable.partly_sunny_small); }break;
                    }

                    ((TextView) findViewById(R.id.Week_3)).setText(week[3]);
                    switch (type[3]){
                        case "晴" :{ImageView imageview = (ImageView)findViewById(R.id.img_weather_3);
                            imageview.setImageResource(R.drawable.sunny_small); }break;
                        case "小雨" :{ImageView imageview = (ImageView)findViewById(R.id.img_weather_3);
                            imageview.setImageResource(R.drawable.rainy_small); }break;
                        case "阴" :{ImageView imageview = (ImageView)findViewById(R.id.img_weather_3);
                            imageview.setImageResource(R.drawable.partly_sunny_small); }break;
                    }

                    ((TextView) findViewById(R.id.Week_4)).setText(week[4]);
                    switch (type[4]){
                        case "晴" :{ImageView imageview = (ImageView)findViewById(R.id.img_weather_4);
                            imageview.setImageResource(R.drawable.sunny_small); }break;
                        case "小雨" :{ImageView imageview = (ImageView)findViewById(R.id.img_weather_4);
                            imageview.setImageResource(R.drawable.rainy_small); }break;
                        case "阴" :{ImageView imageview = (ImageView)findViewById(R.id.img_weather_4);
                            imageview.setImageResource(R.drawable.partly_sunny_small); }break;
                    }
                    Toast toastCenter = Toast.makeText(MainActivity.this,"更新成功！",Toast.LENGTH_SHORT);
                    toastCenter.setGravity(Gravity.CENTER,0,0);
                    toastCenter.show();
                }
            }catch(JSONException e){
                e.printStackTrace();
            }
    }
}
}
