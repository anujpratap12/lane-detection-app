import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FineDetailsFetcher {

    private static final String TAG = "FineDetailsFetcher";

    public void fetchFineDetails(int violationId) {
        // Create an instance of Retrofit
        Retrofit retrofit = RetrofitClient.getRetrofitInstance();

        // Create an instance of ApiInterface
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

        // Call the API to get fine details
        Call<FineDetails> call = apiInterface.getFineDetails(violationId);

        // Enqueue the call asynchronously
        call.enqueue(new Callback<FineDetails>() {
            @Override
            public void onResponse(Call<FineDetails> call, Response<FineDetails> response) {
                if (response.isSuccessful()) {
                    // Handle the response here
                    FineDetails fineDetails = response.body();
                    Log.d(TAG, fineDetails.getFineBreakdown());
                } else {
                    // Handle failure response
                    Log.e(TAG, "Failed to fetch fine details.");
                }
            }

            @Override
            public void onFailure(Call<FineDetails> call, Throwable t) {
                // Handle network failure
                Log.e(TAG, "Error: " + t.getMessage());
            }
        });
    }
}
