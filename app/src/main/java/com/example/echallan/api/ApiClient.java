import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import com.example.models.ZoneResponse;
import com.example.models.Violation;
import com.example.models.ViolationResponse;

public class ApiClient {

    private static final String BASE_URL = "https://your-api-url.com/";  // Your API base URL
    private static ApiClient instance;
    private ApiInterface apiInterface;

    private ApiClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())  // Gson converter to parse JSON
                .build();

        apiInterface = retrofit.create(ApiInterface.class);
    }

    public static ApiClient getInstance() {
        if (instance == null) {
            instance = new ApiClient();
        }
        return instance;
    }

    // Method to store violation details
    public void storeViolationDetails(Violation violation, final ApiCallback<ViolationResponse> callback) {
        Call<ViolationResponse> call = apiInterface.storeViolationDetails(violation);

        call.enqueue(new Callback<ViolationResponse>() {
            @Override
            public void onResponse(Call<ViolationResponse> call, Response<ViolationResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onFailure("Failed to store violation details");
                }
            }

            @Override
            public void onFailure(Call<ViolationResponse> call, Throwable t) {
                callback.onFailure(t.getMessage());
            }
        });
    }

    // Method to fetch zone data (school, hospital zones, etc.)
    public void fetchZoneData(final ApiCallback<ZoneResponse> callback) {
        Call<ZoneResponse> call = apiInterface.getZoneData();

        call.enqueue(new Callback<ZoneResponse>() {
            @Override
            public void onResponse(Call<ZoneResponse> call, Response<ZoneResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onFailure("Failed to fetch zone data");
                }
            }

            @Override
            public void onFailure(Call<ZoneResponse> call, Throwable t) {
                callback.onFailure(t.getMessage());
            }
        });
    }
}
